package com.hm.digital.twin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.digital.common.enums.ErrorCode;
import com.hm.digital.common.rest.BaseController;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.inface.biz.RoleService;
import com.hm.digital.inface.entity.Role;
import com.hm.digital.inface.mapper.RoleMapper;
import com.hm.digital.twin.vo.RoleVO;

import lombok.SneakyThrows;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleController extends BaseController<RoleMapper, Role> {
//
//  @Autowired
//  private RoleService roleService;

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

}
