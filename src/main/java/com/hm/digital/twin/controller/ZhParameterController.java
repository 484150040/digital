package com.hm.digital.twin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.digital.common.exception.BaseException;
import com.hm.digital.inface.biz.ElectronicCallService;
import com.hm.digital.inface.biz.PrisonerRecordService;
import com.hm.digital.inface.biz.StatisticalService;
import com.hm.digital.inface.entity.ElectronicCall;
import com.hm.digital.inface.entity.PrisonerRecord;
import com.hm.digital.inface.entity.Statistical;
import com.hm.digital.twin.vo.ElectronicCallVO;
import com.hm.digital.twin.vo.PrisonerRecordListVO;
import com.hm.digital.twin.vo.StatisticalVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("dataStatistics")
@Slf4j
public class ZhParameterController {

  @Autowired
  private StatisticalService statisticalService;

  @Autowired
  private PrisonerRecordService prisonerRecordService;

  @Autowired
  private ElectronicCallService electronicCallService;

  /**
   * 查询统计表
   *
   * @param statisticalVO
   * @return
   */
  @RequestMapping("/getChart")
  @ExceptionHandler(value = BaseException.class)
  public List<Statistical> getChart(StatisticalVO statisticalVO) {
    List<Statistical> statisticals = statisticalService.findAll(statisticalVO.toSpec());
    return statisticals;
  }

  /**
   * 查询列表参数数据
   *
   * @param prisonerRecordList
   * @return
   */
  @RequestMapping("/getList")
  @ExceptionHandler(value = BaseException.class)
  public Page<PrisonerRecord> getList(PrisonerRecordListVO prisonerRecordList) {
    Page<PrisonerRecord> prisonerRecords = prisonerRecordService
        .findAll(prisonerRecordList.toSpec(), prisonerRecordList.toPageable());
    return prisonerRecords;
  }

  /**
   * 电子点名
   *
   * @param electronicCallVO
   * @return
   */
  @RequestMapping("/findByPrisonIdAndType")
  @ExceptionHandler(value = BaseException.class)
  public List<ElectronicCall> findByPrisonIdAndType(ElectronicCallVO electronicCallVO) {
    List<ElectronicCall> statisticals = electronicCallService.findAll(electronicCallVO.toSpec());
    return statisticals;
  }
}
