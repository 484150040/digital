package com.hm.digital.twin.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hm.digital.common.enums.ErrorCode;
import com.hm.digital.common.rest.BaseController;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.inface.biz.RoleMenuService;
import com.hm.digital.inface.biz.RoleService;
import com.hm.digital.inface.entity.Role;
import com.hm.digital.inface.entity.RoleMenu;
import com.hm.digital.inface.entity.User;
import com.hm.digital.inface.entity.UserRole;
import com.hm.digital.inface.mapper.RoleMapper;
import com.hm.digital.twin.vo.RoleMenuVO;
import com.hm.digital.twin.vo.RoleVO;
import com.hm.digital.twin.vo.UserRoleVo;
import com.hm.digital.twin.vo.UserVO;

import lombok.SneakyThrows;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleController extends BaseController<RoleMapper, Role> {

  @Autowired
  private RoleMenuService roleMenuService;

  /**
   * 查询角色信息
   *
   * @param roleVO
   * @return
   */
  @SneakyThrows
  @RequestMapping("/findAllPage")
  public ResultData findAllPage(@RequestBody RoleVO roleVO) {
    Page<Role> users = baseBiz.findAll(roleVO.toSpec(),roleVO.toPageable());
    if (users==null) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    return ResultData.success(users);
  }

  /**
   * 查询角色信息列表
   *
   * @param roleVO
   * @return
   */
  @SneakyThrows
  @RequestMapping("/findAllList")
  public ResultData findAllList(@RequestBody RoleVO roleVO) {
    List<Role> users = baseBiz.findAll(roleVO.toSpec());
    if (users==null) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    return ResultData.success(users);
  }

  /**
   * 添加一条记录
   *
   * @param entity
   * @return
   */
  @RequestMapping(value = "add", method = RequestMethod.POST)
  @ResponseBody
  public ResultData add(@RequestBody RoleVO entity) {
    Role role = new Role();
    BeanUtils.copyProperties(entity, role);
    Role object = baseBiz.save(role);
    entity.getMenuList().forEach(menu -> {
      RoleMenu roleMenu = new RoleMenu();
      roleMenu.setMenuId(menu.getId());
      roleMenu.setRoleId(object.getId());
      roleMenuService.save(roleMenu);
    });
    return ResultData.success(object);
  }


  /**
   * 根据id或作业编号更新一条记录
   *
   * @param id
   * @param entity
   * @return
   */
  @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public ResultData update(@PathVariable String id,
      @RequestBody RoleVO entity) {
    Role role = new Role();
    BeanUtils.copyProperties(entity, role);
    baseBiz.saveAndFlush(role);
//    角色删除
    RoleMenuVO roleMenu = new RoleMenuVO();
    roleMenu.setRoleId(role.getId());
    List<RoleMenu> userRoles = roleMenuService.select(roleMenu.toSpec());
    roleMenuService.deleteAll(userRoles);
    //角色新增
    if (!CollectionUtils.isEmpty(entity.getMenuList())){
      entity.getMenuList().forEach(entitys->{
        RoleMenu roleMenus = new RoleMenu();
        roleMenus.setRoleId(role.getId());
        roleMenus.setMenuId(entitys.getId());
        roleMenuService.save(roleMenus);
      });
    }
    return ResultData.success(entity);
  }
}
