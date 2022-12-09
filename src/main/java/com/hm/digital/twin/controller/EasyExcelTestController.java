package com.hm.digital.twin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.digital.twin.dto.EasyExcelDto;
import com.hm.digital.twin.util.EasyExcelUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

/**
 * 测试 easyexcel 模板导出
 *
 * @author YYS
 */
@RestController
@RequestMapping("/excelTest")
public class EasyExcelTestController {

  @Data
  @AllArgsConstructor
  class TestObj {
    private String name;
    private String list1;
    private String list2;
    private String list3;
    private String list4;
  }

  /**
   * 单列表多数据
   */
  @SneakyThrows
  @GetMapping("/exportOne")
  public void export(HttpServletResponse response) {
    Map<String,String> map = new HashMap<>();
    map.put("title","单列表多数据");
    map.put("test1","数据测试1");
    map.put("test2","数据测试2");
    map.put("test3","数据测试3");
    map.put("test4","数据测试4");
    map.put("testTest","666");
    List<TestObj> list = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      list.add(new TestObj("单列表测试"+i,"列表测试"+i,"列表测试"+i,"列表测试"+i,"列表测试"+i));
    }
    EasyExcelDto obj = new EasyExcelDto()
        .setExportName("单列表多数据导出")
        .setTemplateName("单列表多数据")
        .setExportPath("D:")
        .setData(new Object[]{map,list});
    EasyExcelUtil.doPost(response,EasyExcelUtil.export(obj));
  }

}
