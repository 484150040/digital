package com.hm.digital.twin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.hm.digital.common.enums.ErrorCode;
import com.hm.digital.common.rest.BaseController;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.inface.biz.EquimentService;
import com.hm.digital.inface.entity.Config;
import com.hm.digital.inface.entity.Equipment;
import com.hm.digital.inface.mapper.EquipmentMapper;
import com.hm.digital.twin.dto.EquipmentDto;

import lombok.SneakyThrows;

@RestController
@CrossOrigin
@RequestMapping("/equipment")
public class EquipmentController extends BaseController<EquipmentMapper, Config> {

  @Autowired
  private EquimentService equiomentService;


  /**
   * 查询配置信息
   *
   * @param equioment
   * @return
   */
  @SneakyThrows
  @RequestMapping("/findAll")
  public ResultData findAll(@RequestBody Equipment equioment) {
    List<Equipment> equipments = equiomentService.findAll(equioment);
    if (CollectionUtils.isEmpty(equipments)) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    return ResultData.success(equipments);
  }



  /**
   * 查询配置信息
   *
   * @param equioment
   * @return
   */
  @SneakyThrows
  @RequestMapping("/findList")
  public List<EquipmentDto> findList(@RequestBody Equipment equioment) {
    List<EquipmentDto> equiomentList = new ArrayList<>();
    List<Equipment> equipments = equiomentService.findAll(equioment);
    equipments.forEach(e -> {
      if (CollectionUtils.isEmpty(equiomentList)) {
        EquipmentDto equipmentDto =EquipmentDto.builder().id("0").isvalid(1).name("首页").equipments(new ArrayList<>()).build();
        equiomentList.add(equipmentDto);
      }
      EquipmentDto equipmentDto = new EquipmentDto();
      BeanUtils.copyProperties(e, equipmentDto);
      Equipment equiomentone = new Equipment();
      equiomentone.setParentId(e.getId());
      List<Equipment> equipmentone = equiomentService.findAll(equiomentone);
      equipmentDto.setEquipments(equipmentone);
      equiomentList.add(equipmentDto);
    });
    if (CollectionUtils.isEmpty(equiomentList)) {
      return equiomentList;
    }
    return equiomentList;
  }
}
