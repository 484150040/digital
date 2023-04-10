package com.hm.digital.twin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hm.digital.common.config.QueryCondition;
import com.hm.digital.common.enums.MatchType;
import com.hm.digital.common.query.BaseQuery;
import com.hm.digital.inface.entity.Menu;
import com.hm.digital.inface.entity.Role;
import com.hm.digital.inface.entity.User;

import lombok.Data;

@Data
public class RoleVO extends BaseQuery<Role> {
  /**
   * 作业编号
   */
  @QueryCondition(func = MatchType.equal)
  private String id;

  /**
   * 角色名称
   */
  @QueryCondition(func = MatchType.like)
  private String name;

  /**
   * 处理状态
   */
  @QueryCondition(func = MatchType.equal)
  private Integer status;


  /**
   * 父级角色id
   */
  @QueryCondition(func = MatchType.equal)
  private String parentId;

  /**
   * 角色描述
   */
  @QueryCondition(func = MatchType.equal)
  private String content;

  /**
   * 批量查询角色id
   */
  private List<String> roleIdList;

  /**
   * 存放的数据信息
   */
  private List<Menu> menuList;

  /**
   * 开始时间
   */
  private Date startTime;

  /**
   * 创建时间
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  /**
   * 修改时间
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date modifyTime;

  /**
   * 结束时间
   */
  private Date endDate;

  @Override
  public Specification<Role> toSpec() {
    Specification<Role> spec = super.toSpecWithAnd();
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
      if (!CollectionUtils.isEmpty(roleIdList)) {
        Path<Object> path = root.get("id");  //定义查询的字段
        CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
        for (int i = 0; i <roleIdList.size() ; i++) {
          in.value(roleIdList.get(i));//存入值
        }
        predicatesList.add(criteriaBuilder.and(criteriaBuilder.and(in)));
      }
      return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
    });
  }
}
