package com.hm.digital.twin.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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

import lombok.SneakyThrows;

@Controller
public class TestController {


  @Value("${icc.host}")
  private String host;
  @Value("${icc.clientId}")
  private String clientId;
  @Value("${icc.clientSecret}")
  private String clientSecret;

  @SneakyThrows
  @RequestMapping("/tttt")
  @ResponseBody
  public String tttt(@RequestBody Map<String,Object> param,@RequestParam String url) {
      IClient iClient = new DefaultClient(host, clientId, clientSecret);
      GeneralRequest generalRequest =
          new GeneralRequest(url, Method.POST);
      generalRequest.body(JSONUtil.toJsonStr(JSONUtil.parseObj(param)));
      GeneralResponse subscribeResponse =
          iClient.doAction(generalRequest, generalRequest.getResponseClass());
      return subscribeResponse.toString();
  }

}
