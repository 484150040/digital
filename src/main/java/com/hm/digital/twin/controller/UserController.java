package com.hm.digital.twin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.hm.digital.common.utils.MD5Utils;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.inface.biz.RedisService;
import com.hm.digital.inface.entity.User;
import com.hm.digital.inface.mapper.UserMapper;
import com.hm.digital.twin.util.JwtUtils;
import com.hm.digital.twin.vo.UserVO;

import lombok.SneakyThrows;

import static com.hm.digital.twin.util.JwtUtils.EXPIRATION;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController extends BaseController<UserMapper, User> {
  @Autowired
  private RedisService redisService;
  //  @Autowired
//  private UserService userService;

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
}
