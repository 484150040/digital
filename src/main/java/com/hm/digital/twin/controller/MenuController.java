package com.hm.digital.twin.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.digital.common.enums.ErrorCode;
import com.hm.digital.common.rest.BaseController;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.inface.biz.RedisService;
import com.hm.digital.inface.biz.RoleMenuService;
import com.hm.digital.inface.biz.UserService;
import com.hm.digital.inface.entity.Menu;
import com.hm.digital.inface.mapper.MenuMapper;
import com.hm.digital.twin.dto.MenuDto;
import com.hm.digital.twin.vo.MenuVO;

import lombok.SneakyThrows;

@RestController
@CrossOrigin
@RequestMapping("/menu")
public class MenuController extends BaseController<MenuMapper, Menu> {
  @Autowired
  private RedisService redisService;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleMenuService roleMenuService;
  /**
   * 查询菜单信息
   *
   * @param menuVO
   * @return
   */
  @SneakyThrows
  @RequestMapping("/findAllPage")
  public ResultData findAllPage(@RequestBody MenuVO menuVO) {
    Page<Menu> users = baseBiz.findAll(menuVO.toSpec(),menuVO.toPageable());
    if (users==null) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    return ResultData.success(users);
  }

  /**
   * 查询菜单信息列表
   *
   * @param menuVO
   * @return
   */
  @SneakyThrows
  @RequestMapping("/findAllList")
  public ResultData findAllList(@RequestBody MenuVO menuVO) {
    List<MenuDto> menuDtos = new ArrayList<>();
    List<Menu> menus = baseBiz.findAll(menuVO.toSpec());
    for (Menu menu : menus) {
      MenuDto menuDto = new MenuDto();
      BeanUtils.copyProperties(menu, menuDto);
      MenuVO menuVo = new MenuVO();
      menuVo.setMenuParentId(menu.getMenuName());
      menuVo.setAction(menu.getAction());
      List<Menu> menuList = baseBiz.findAll(menuVo.toSpec());
      if (StringUtils.isNotBlank(menu.getMenuParentId())){
        continue;
      }
      menuDto.setChild(menuList);
      menuDtos.add(menuDto);
    }
    if (menus==null) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    return ResultData.success(menuDtos);
  }

  /**
   * 查询菜单信息列表
   *
   * @return
   */
  @SneakyThrows
  @RequestMapping("/findList")
  public ResultData findList() {
    String username = redisService.get("username")!=null?redisService.get("username"):null;
    List<String> stringList = roleMenuService.getMenuId(username);

    MenuVO menuVO = new MenuVO();
    menuVO.setMenuList(stringList);
    List<MenuDto> menuDtos = new ArrayList<>();
    List<Menu> menus = baseBiz.findAll(menuVO.toSpec());
    for (Menu menu : menus) {
      MenuDto menuDto = new MenuDto();
      BeanUtils.copyProperties(menu, menuDto);
      List<Menu> menuList= getMenuVO(menu.getMenuName(),menu.getAction(),stringList);
      List<Menu> menuList1= getMenuVO(menu.getMenuName(),"function",stringList);
      if (StringUtils.isNotBlank(menu.getMenuParentId())){
        continue;
      }
      menuDto.setChild(menuList);
      menuDto.setFunction(menuList1);
      menuDtos.add(menuDto);
    }
    if (menus==null) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    return ResultData.success(menuDtos);
  }

  /**
   * 查询数据信息
   *
   * @param menuName
   * @param action
   * @param stringList
   * @return
   */
  private List<Menu> getMenuVO(String menuName, String action, List<String> stringList) {
    MenuVO menuVo = new MenuVO();
    menuVo.setMenuParentId(menuName);
    menuVo.setAction(action);
    menuVo.setMenuList(stringList);
    List<Menu> menuList = baseBiz.findAll(menuVo.toSpec());
    return menuList;
  }
}
