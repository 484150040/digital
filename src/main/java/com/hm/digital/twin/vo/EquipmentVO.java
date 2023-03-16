package com.hm.digital.twin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.alibaba.excel.annotation.ExcelProperty;
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
  @ExcelProperty("作业编号")
  @QueryCondition(func = MatchType.equal)
  private String id;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  @QueryCondition(func = MatchType.equal)
  private String content;

  /**
   * 设备品牌
   */
  @ExcelProperty("设备品牌")
  @QueryCondition(func = MatchType.equal)
  private String brand;

  /**
   * 设备名称
   */
  @ExcelProperty("设备名称")
  @QueryCondition(func = MatchType.like)
  private String name;

  /**
   * 是否有效
   */
  @ExcelProperty("是否有效")
  @QueryCondition(func = MatchType.equal)
  private Integer isvalid;

  /**
   * 设备code
   */
  @ExcelProperty("设备code")
  @QueryCondition(func = MatchType.equal)
  private String code;

  /**
   * 父级设备id
   */
  @ExcelProperty("父级设备id")
  @QueryCondition(func = MatchType.equal)
  private String parentId;

  /**
   * IP地址
   */
  @ExcelProperty("IP地址")
  private String ipAddress;

  /**
   * 子网掩码
   */
  @ExcelProperty("子网掩码")
  private String subnetMask;

  /**
   * 网关
   */
  @ExcelProperty("网关")
  private String gateway;

  /**
   * Vlan
   */
  @ExcelProperty("Vlan")
  private String vlan;

  /**
   * 存储IP地址
   */
  @ExcelProperty("存储IP地址")
  private String storageIp;

  /**
   * 位置
   */
  @ExcelProperty("位置")
  private String position;

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
