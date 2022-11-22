package com.hm.digital.twin.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.hutool.json.JSONUtil;
import com.dahuatech.icc.oauth.http.DefaultClient;
import com.dahuatech.icc.oauth.http.IClient;
import com.dahuatech.icc.oauth.model.v202010.GeneralRequest;
import com.dahuatech.icc.oauth.model.v202010.GeneralResponse;
import com.hm.digital.twin.util.DhHttpClientUtil;

import lombok.SneakyThrows;

@Controller
public class TestController {


  @Value("${icc.host}")
  private String host;
  @Value("${icc.clientId}")
  private String clientId;
  @Value("${icc.clientSecret}")
  private String clientSecret;

  @Autowired
  private DhHttpClientUtil dewHttpClientUtil;

  @SneakyThrows
  @RequestMapping("/tttt")
  @ResponseBody
  public String tttt(@RequestBody Map<String,Object> param,@RequestParam String url) {
      return dewHttpClientUtil.post(url,param);
  }

  @SneakyThrows
  @RequestMapping("/tttt1/{id}")
  @ResponseBody
  public String tttt1(@PathVariable String id,@RequestParam String url) {
    return dewHttpClientUtil.get(url,id);
  }
}
