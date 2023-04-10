package com.hm.digital.twin.dto;

import java.util.List;

import com.hm.digital.inface.entity.User;

import lombok.Data;

@Data
public class UserDto {

  /**
   * 用户信息
   */
  private User user;

  /**
   * 角色ID列表
   */
  private List<String> roles;
}
