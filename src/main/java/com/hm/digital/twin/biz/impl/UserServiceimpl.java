package com.hm.digital.twin.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hm.digital.inface.biz.UserService;
import com.hm.digital.inface.entity.User;
import com.hm.digital.inface.mapper.UserMapper;

@Service
public class UserServiceimpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public User getById(Specification<User> userSpecification) {
    return userMapper.findOne(userSpecification).get();
  }
}
