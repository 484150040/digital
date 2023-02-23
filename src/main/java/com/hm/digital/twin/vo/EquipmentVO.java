package com.hm.digital.twin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.hm.digital.common.config.QueryCondition;
import com.hm.digital.common.enums.MatchType;
import com.hm.digital.common.query.BaseQuery;
import com.hm.digital.inface.entity.Equipment;

import lombok.Data;

@Data
public class EquipmentVO extends BaseQuery<Equipment> {
  /**
   * 作业编号
   */
  @QueryCondition(func = MatchType.equal)
  private String id;

  /**
   * 监所编号
   */
  @QueryCondition(func = MatchType.equal)
  private String prisonId;

  /**
   * 设备品牌
   */
  @QueryCondition(func = MatchType.equal)
  private String brand;

  /**
   * 设备名称
   */
  @QueryCondition(func = MatchType.like)
  private String name;

  /**
   * 是否有效
   */
  @QueryCondition(func = MatchType.equal)
  private Integer isvalid;

  /**
   * 设备code
   */
  @QueryCondition(func = MatchType.equal)
  private String code;

  /**
   * 父级设备id
   */
  @QueryCondition(func = MatchType.equal)
  private String parentId;

  /**
   * 开始时间
   */
  private Date startTime;

  /**
   * 结束时间
   */
  private Date endDate;

  @Override
  public Specification<Equipment> toSpec() {
    Specification<Equipment> spec = super.toSpecWithAnd();
    return ((root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicatesList = new ArrayList<>();
      predicatesList.add(spec.toPredicate(root, criteriaQuery, criteriaBuilder));
      if (startTime != null) {
        predicatesList.add(
            criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(
                    root.get("createTime"), startTime)));
      }
      if (endDate != null) {
        predicatesList.add(
            criteriaBuilder.and(
                criteriaBuilder.lessThanOrEqualTo(
                    root.get("createTime"), endDate)));
      }
      return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
    });
  }
}
