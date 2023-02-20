package com.hm.digital.twin.biz.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hm.digital.inface.biz.EquimentService;
import com.hm.digital.inface.entity.Equipment;
import com.hm.digital.inface.mapper.EquipmentMapper;
import com.hm.digital.twin.vo.ConfigVO;
import com.hm.digital.twin.vo.EquipmentVO;


@Service
@Transactional
public class EquimentServiceimpl implements EquimentService {

  @Autowired
  private EquipmentMapper equimentMapper;

  @Override
  public List<Equipment> findAll(Equipment equioment) {
    EquipmentVO equipmentVO = new EquipmentVO();
    BeanUtils.copyProperties(equioment, equipmentVO);
    return equimentMapper.findAll(equipmentVO.toSpec());
  }
}
