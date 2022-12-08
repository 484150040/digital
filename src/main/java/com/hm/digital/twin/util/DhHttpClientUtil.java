package com.hm.digital.twin.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.hutool.json.JSONUtil;
import com.dahuatech.icc.oauth.http.DefaultClient;
import com.dahuatech.icc.oauth.http.IClient;
import com.dahuatech.icc.oauth.model.v202010.GeneralRequest;
import com.dahuatech.icc.oauth.model.v202010.GeneralResponse;

import lombok.SneakyThrows;

@Component
public class DhHttpClientUtil {

  @Value("${icc.host}")
  private String host;
  @Value("${icc.clientId}")
  private String clientId;
  @Value("${icc.clientSecret}")
  private String clientSecret;

  /**
   * 大华post请求
   *
   * @param url
   * @param param
   * @return
   */
  @SneakyThrows
  public String post(String url, Map<String, Object> param) {
    IClient iClient = new DefaultClient(host, clientId, clientSecret);
    GeneralRequest generalRequest =
        new GeneralRequest(url, Method.POST);
    generalRequest.body(JSONUtil.toJsonStr(JSONUtil.parseObj(param)));
    GeneralResponse subscribeResponse =
        iClient.doAction(generalRequest, generalRequest.getResponseClass());
    return subscribeResponse.toString();
  }

  /**
   * 大华get请求地址
   *
   * @param url
   * @param param
   * @return
   */
  @SneakyThrows
  public String get(String url, Map<String, Object> param) {
    IClient iClient = new DefaultClient(host, clientId, clientSecret);
    GeneralRequest generalRequest =
        new GeneralRequest(url, Method.GET);
    generalRequest.form(param);
    GeneralResponse subscribeResponse =
        iClient.doAction(generalRequest, generalRequest.getResponseClass());
    return subscribeResponse.toString();
  }

  /**
   * 大华get请求地址
   *
   * @param url
   * @param param
   * @return
   */
  @SneakyThrows
  public String get(String url, String param) {
    IClient iClient = new DefaultClient(host, clientId, clientSecret);
    GeneralRequest generalRequest =
        new GeneralRequest(url, Method.GET);
    generalRequest.get(generalRequest.getUrl()+"/"+param);
    GeneralResponse subscribeResponse =
        iClient.doAction(generalRequest, generalRequest.getResponseClass());
    return subscribeResponse.toString();
  }
}
