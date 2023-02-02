package com.hm.digital.twin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.hm.digital.common.enums.ErrorCode;
import com.hm.digital.common.rest.BaseController;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.inface.biz.ConfigsService;
import com.hm.digital.inface.entity.Config;
import com.hm.digital.inface.mapper.ConfigMapper;

import lombok.SneakyThrows;

@RestController
@CrossOrigin
@RequestMapping("/config")
public class ConfigsController extends BaseController<ConfigMapper, Config> {

  @Autowired
  private ConfigsService configsService;


  /**
   * 查询配置信息
   *
   * @param config
   * @return
   */
  @SneakyThrows
  @RequestMapping("/configListResult")
  public ResultData configListResult(@RequestBody Config config) {
    List<Config> configs = configsService.getValue(config);
    if (CollectionUtils.isEmpty(configs)) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    return ResultData.success(configs);
  }


  /**
   * 查询配置信息
   *
   * @param config
   * @return
   */
  @SneakyThrows
  @RequestMapping("/configList")
  public List<Config> configList(@RequestBody Config config) {
    List<Config> configs = configsService.getValue(config);
    if (!CollectionUtils.isEmpty(configs)) {
      return configs;
    }
    return configs;
  }
}
