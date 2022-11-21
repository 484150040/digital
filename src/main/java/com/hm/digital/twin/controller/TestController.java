/*
package com.hm.digital.twin.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.hutool.json.JSONObject;
import com.dahuatech.hutool.json.JSONUtil;
import com.dahuatech.icc.brm.model.v202010.card.BrmCardDelRequest;
import com.dahuatech.icc.brm.model.v202010.card.BrmCardDelResponse;
import com.dahuatech.icc.exception.ClientException;
import com.dahuatech.icc.oauth.http.DefaultClient;
import com.dahuatech.icc.oauth.http.IClient;
import com.dahuatech.icc.oauth.http.IccTokenResponse;
import com.dahuatech.icc.oauth.model.v202010.GeneralRequest;
import com.dahuatech.icc.oauth.model.v202010.GeneralResponse;
import com.hm.digital.twin.utils.HttpClientUtil;

@Controller
public class TestController {


  @Value("${icc.host}")
  private String host;
  @Value("${icc.username}")
  private String username;
  @Value("${icc.password}")
  private String password;
  @Value("${icc.clientId}")
  private String clientId;
  @Value("${icc.clientSecret}")
  private String clientSecret;
  @Value("${zh.httpGetList}")
  private String httpGetList;

    @RequestMapping("/deletetest")
    @ResponseBody
    public String deletetest(ModelMap modelMap) throws  ClientException {
      IClient iClient = new DefaultClient(host, username, password, clientId, clientSecret);
        List<Long> list = new ArrayList<>();
        list.add(249764l);
        BrmCardDelRequest brmCardDelRequest = new BrmCardDelRequest();
        brmCardDelRequest.setCardIds(list);
        BrmCardDelResponse brmCardDelResponse = iClient.doAction(brmCardDelRequest,brmCardDelRequest.getResponseClass());
        System.out.println("---------------" + JSONUtil.toJsonStr(brmCardDelResponse));
        return JSONUtil.toJsonStr(brmCardDelResponse);
    }

    @RequestMapping("/deletetest1")
    @ResponseBody
    public String deletetest1(ModelMap modelMap) throws  ClientException {
      IClient iClient = new DefaultClient(host, username, password, clientId, clientSecret);
        GeneralRequest brmCardDelRequest = new GeneralRequest("/evo-apigw/evo-brm/1.0.0/card/delete", Method.DELETE);
        JSONObject jsonObject = new JSONObject();
        List<Long> list = new ArrayList<>();
        list.add(249764l);
        jsonObject.put("cardIds",list);
        brmCardDelRequest.setBody(jsonObject.toJSONString(0));
        GeneralResponse brmCardDelResponse = iClient.doAction(brmCardDelRequest,brmCardDelRequest.getResponseClass());
        System.out.println("-----------------" + JSONUtil.toJsonStr(brmCardDelResponse));
        return JSONUtil.toJsonStr(brmCardDelResponse);
    }
  @RequestMapping("/eeee")
  @ResponseBody
  public String evo_brm_dept_page_general() throws ClientException {
    IClient iClient = new DefaultClient(host, username, password, clientId, clientSecret);
    IccTokenResponse.IccToken token = iClient.getAccessToken();
    GeneralRequest generalRequest =
        new GeneralRequest("/evo-apigw/evo-brm/1.0.0/department/add", Method.POST);
    // 设置参数
    Map<String, Object> param = new HashMap();
    param.put("id", "10000");
    param.put("parentId", "1");
    param.put("name", "二级小区H");
    param.put("memo", "二级小区H");
    param.put("service", "evo-event");
    generalRequest.body(JSONUtil.toJsonStr(param));
    generalRequest.header("authorization","bearer "+ token.getAccess_token());
    GeneralResponse response = iClient.doAction(generalRequest, generalRequest.getResponseClass());
    return JSONUtil.toJsonStr(response);
  }

  @RequestMapping("/tttt")
  @ResponseBody
  public String tttt() {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("item","nowArraignList");
    parameters.put("prisonId","330100111");
    String response = HttpClientUtil.sendGet(httpGetList,parameters);
    return JSONUtil.toJsonStr(response);
  }

}
*/
