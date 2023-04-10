package com.hm.digital.twin.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hm.digital.inface.biz.RoleMenuService;
import com.hm.digital.inface.entity.RoleMenu;
import com.hm.digital.inface.mapper.RoleMenuMapper;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

  @Autowired
  private RoleMenuMapper roleMenuMapper;
  @Override
  public List<String> getMenuId(String username) {
    return roleMenuMapper.getMenuId(username);
  }
  @Override
  public RoleMenu save(RoleMenu roleMenu) {
    return roleMenuMapper.save(roleMenu);
  }
  @Override
  public void deleteAll(List<RoleMenu> userRoles) {
    roleMenuMapper.deleteAll(userRoles);
  }
  @Override
  public List<RoleMenu> select(Specification<RoleMenu> toSpec) {
    return roleMenuMapper.findAll(toSpec);
  }
}
