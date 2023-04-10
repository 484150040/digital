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
import com.hm.digital.inface.entity.Dictionary;
import com.hm.digital.inface.entity.Menu;
import com.hm.digital.inface.mapper.DictionaryMapper;
import com.hm.digital.twin.dto.MenuDto;
import com.hm.digital.twin.vo.DictionaryVO;
import com.hm.digital.twin.vo.MenuVO;

import lombok.SneakyThrows;

@RestController
@CrossOrigin
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController<DictionaryMapper, Dictionary> {
  @Autowired
  private RedisService redisService;
  
  /**
   * 查询字典信息
   *
   * @param dictionaryVol
   * @return
   */
  @SneakyThrows
  @RequestMapping("/findAllPage")
  public ResultData findAllPage(@RequestBody DictionaryVO dictionaryVol) {
    Page<Dictionary> users = baseBiz.findAll(dictionaryVol.toSpec(),dictionaryVol.toPageable());
    if (users==null) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    return ResultData.success(users);
  }


}
