package com.hm.digital.twin.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hm.digital.inface.biz.UserRoleService;
import com.hm.digital.inface.entity.UserRole;
import com.hm.digital.inface.mapper.UserRoleMapper;

@Service
public class UserRoleServiceimpl implements UserRoleService {

  @Autowired
  private UserRoleMapper userRoleMapper;

  @Override
  public UserRole save(UserRole userRole) {
    return userRoleMapper.save(userRole);
  }
  @Override
  public List<UserRole> select(Specification<UserRole> toSpec) {
    return userRoleMapper.findAll(toSpec);
  }
  @Override
  public void deleteAll(List<UserRole> userRoles) {
    userRoleMapper.deleteAll(userRoles);
  }
}
