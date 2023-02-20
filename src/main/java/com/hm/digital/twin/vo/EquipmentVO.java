package com.hm.digital.twin.vo;

import org.springframework.data.jpa.domain.Specification;

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
  @QueryCondition(func = MatchType.equal)
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


  @Override
  public Specification<Equipment> toSpec() {
    return super.toSpecWithAnd();
  }
}
