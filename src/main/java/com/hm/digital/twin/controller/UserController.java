package com.hm.digital.twin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hm.digital.common.enums.ErrorCode;
import com.hm.digital.common.rest.BaseController;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.inface.biz.RedisService;
import com.hm.digital.inface.biz.UserRoleService;
import com.hm.digital.inface.biz.UserService;
import com.hm.digital.inface.entity.User;
import com.hm.digital.inface.entity.UserRole;
import com.hm.digital.inface.mapper.UserMapper;
import com.hm.digital.twin.dto.UserDto;
import com.hm.digital.twin.util.JwtUtils;
import com.hm.digital.twin.vo.UserRoleVo;
import com.hm.digital.twin.vo.UserVO;

import lombok.SneakyThrows;

import static com.hm.digital.twin.util.JwtUtils.EXPIRATION;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController extends BaseController<UserMapper, User> {
  @Autowired
  private RedisService redisService;
  @Autowired
  private UserRoleService userRoleService;

  /**
   * 查询用户信息
   *
   * @param user
   * @return
   */
  @SneakyThrows
  @RequestMapping("/findAllPage")
  public ResultData findAllPage(@RequestBody UserVO user) {
    Page<User> users = baseBiz.findAll(user.toSpec(), user.toPageable());
    if (users == null) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    return ResultData.success(users);
  }


  /**
   * 用户登录接口
   *
   * @param userVO
   * @return
   */
  @PostMapping("/login")
  public ResultData<?> login(@RequestBody UserVO userVO) {
    Map<String,Object> map = new HashMap<>();
    List<User> user = baseBiz.findAll(userVO.toSpec());
    if (CollectionUtils.isEmpty(user)){
      map.put("user",null);
    }else {
      map.put("user",user.get(0));
    }

    if (CollectionUtils.isEmpty(user)) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    Map<String,String> payload =  new HashMap<>();
    payload.put("id",user.get(0).getId());
    payload.put("name",user.get(0).getName());
    String token = JwtUtils.createToken(payload);
    Long time = System.currentTimeMillis() + EXPIRATION * 1000;
    redisService.set(user.get(0).getUsername()+"token",token,time);
    redisService.set("username",user.get(0).getUsername(),time);
    User user1 = user.get(0);
    user1.setLoginCount(user1.getLoginCount()!=null?user1.getLoginCount()+1:1);
    user1.setLoginTime(new Date());
    baseBiz.save(user1);
    map.put("token",token);
    if (StringUtils.isEmpty(token)) {
      return ResultData.error(ErrorCode.UNKNOWN_ERROR.getValue(), ErrorCode.UNKNOWN_ERROR.getDesc());
    }
    return ResultData.success(map);
  }

  /**
   * 添加一条记录
   *
   * @param entity
   * @return
   */
  @RequestMapping(value = "add", method = RequestMethod.POST)
  @ResponseBody
  public ResultData<Object> add(@RequestBody UserVO entity) {
    UserVO userVO = new UserVO();
    userVO.setUsername(entity.getUsername());
    userVO.setPassword(entity.getPassword());
    List<User> userList = baseBiz.findAll(userVO.toSpec());
    if (!CollectionUtils.isEmpty(userList)){
      return ResultData.error(ErrorCode.ERROR_OBJ.getValue(), ErrorCode.ERROR_OBJ.getDesc());
    }
    User user = new User();
    BeanUtils.copyProperties(entity, user);
    User object = baseBiz.save(user);
    //新增角色
    if (!CollectionUtils.isEmpty(entity.getRoleList())){
      entity.getRoleList().forEach(entitys->{
        UserRole userRole = new UserRole();
        userRole.setUserId(object.getId());
        userRole.setRoleId(entitys.getId());
        userRoleService.save(userRole);
      });
    }
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
      @RequestBody UserVO entity) {
    User user = new User();
    BeanUtils.copyProperties(entity, user);
    baseBiz.saveAndFlush(user);
//    角色删除
    UserRoleVo userRole1 = new UserRoleVo();
    userRole1.setUserId(user.getId());
    List<UserRole> userRoles = userRoleService.select(userRole1.toSpec());
    userRoleService.deleteAll(userRoles);
    //角色新增
    if (!CollectionUtils.isEmpty(entity.getRoleList())){
      entity.getRoleList().forEach(entitys->{
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(entitys.getId());
        userRoleService.save(userRole);
      });
    }
    return ResultData.success(entity);
  }

  /**
   * 查询一条信息数据
   *
   * @param entity
   * @return
   */
  @RequestMapping(value = "select", method = RequestMethod.POST)
  @ResponseBody
  public ResultData selectId(@RequestBody UserVO entity) {
    List<User> userList = baseBiz.findAll(entity.toSpec());
    if (CollectionUtils.isEmpty(userList)||userList.size()>1){
      return ResultData.error(ErrorCode.ERROR_OBJ.getValue(), ErrorCode.ERROR_OBJ.getDesc());
    }
    User user = userList.get(0);
    UserRoleVo userRole = new UserRoleVo();
    userRole.setUserId(user.getId());
    List<UserRole> userRoles = userRoleService.select(userRole.toSpec());
    List<String> roles = new ArrayList<>();
    userRoles.forEach(userRole1 -> {
      roles.add(userRole1.getRoleId());
    });
    UserDto userDto = new UserDto();
    userDto.setUser(user);
    userDto.setRoles(roles);
    return ResultData.success(userDto);
  }

}
