package com.hm.digital.twin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.hm.digital.common.config.QueryCondition;
import com.hm.digital.common.enums.MatchType;
import com.hm.digital.common.query.BaseQuery;
import com.hm.digital.inface.entity.Equipment;
import com.hm.digital.inface.entity.User;

import lombok.Data;

@Data
public class UserVO extends BaseQuery<User> {

  /**
   * 作业编号
   */
  @QueryCondition(func = MatchType.equal)
  private String id;


  /**
   * 用户名称
   */
  @QueryCondition(func = MatchType.like)
  private String name;

  /**
   * 用户名
   */
  @QueryCondition(func = MatchType.equal)
  private String username;

  /**
   * 密码
   */
  @QueryCondition(func = MatchType.equal)
  private String password;

  /**
   * 用户邮箱
   */
  @QueryCondition(func = MatchType.equal)
  private String email;

  /**
   * 用户手机
   */
  @QueryCondition(func = MatchType.equal)
  private String number;

  /**
   * 处理状态
   */
  @QueryCondition(func = MatchType.equal)
  private Integer status;

  /**
   * 登录次数
   */
  @QueryCondition(func = MatchType.gt)
  private Integer loginCount;

  /**
   * 地址
   */
  @QueryCondition(func = MatchType.like)
  private String addr;

  /**
   * 版本
   */
  @QueryCondition(func = MatchType.equal)
  private Integer version;

  /**
   * 城市
   */
  @QueryCondition(func = MatchType.equal)
  private String city;

  /**
   * 省
   */
  @QueryCondition(func = MatchType.equal)
  private String province;

  /**
   * 国家
   */
  @QueryCondition(func = MatchType.equal)
  private String country;

  /**
   * 邮政编码
   */
  @QueryCondition(func = MatchType.equal)
  private String postalCode;

  /**
   * 记录密码输错次数
   */
  @QueryCondition(func = MatchType.equal)
  private Integer passwordErrorTimes;

  /**
   * 密码输入错误锁，0为正常。1为锁定
   */
  @QueryCondition(func = MatchType.equal)
  private Integer passwordErrorLock;

  /**
   * 开始时间
   */
  private Date startTime;

  /**
   * 结束时间
   */
  private Date endDate;

  @Override
  public Specification<User> toSpec() {
    Specification<User> spec = super.toSpecWithAnd();
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
