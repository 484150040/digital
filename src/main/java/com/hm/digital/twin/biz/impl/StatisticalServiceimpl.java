package com.hm.digital.twin.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hm.digital.clocking.biz.StatisticalService;
import com.hm.digital.clocking.entity.Statistical;
import com.hm.digital.clocking.mapper.StatisticalMapper;
import com.hm.digital.clocking.vo.StatisticalVO;

@Service
public class StatisticalServiceimpl implements StatisticalService {

  @Autowired
  private StatisticalMapper statisticalMapper;

  @Override
  public void insertAll(List<Statistical> list) {
    statisticalMapper.saveAll(list);
  }
  @Override
  public List<Statistical> selectAll() {
    return statisticalMapper.findAll();
  }
  @Override
  public Page<Statistical> statisticalPage() {
    StatisticalVO statisticalVO = new StatisticalVO();
    return statisticalMapper.findAll(statisticalVO.toSpec(),statisticalVO.toPageable());
  }
  @Override
  public List<Statistical> findAll(Specification<Statistical> toSpec) {
    return statisticalMapper.findAll(toSpec);
  }
}
