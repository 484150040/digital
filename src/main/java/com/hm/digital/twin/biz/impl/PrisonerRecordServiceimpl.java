package com.hm.digital.twin.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hm.digital.inface.biz.PrisonerRecordService;
import com.hm.digital.inface.entity.PrisonerRecord;
import com.hm.digital.inface.mapper.PrisonerRecordMapper;


@Service
public class PrisonerRecordServiceimpl implements PrisonerRecordService {

  @Autowired
  private PrisonerRecordMapper prisonerRecordMapper;

  @Override
  public void insertAll(List<PrisonerRecord> list) {
    list.forEach(prisonerRecord->{
      prisonerRecordMapper.save(prisonerRecord);
    });

  }
  @Override
  public Page<PrisonerRecord> findAll(Specification<PrisonerRecord> toSpec, Pageable toPageable) {
    return prisonerRecordMapper.findAll(toSpec, toPageable);
  }
}
