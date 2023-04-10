package com.hm.digital.twin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.hm.digital.common.config.QueryCondition;
import com.hm.digital.common.enums.MatchType;
import com.hm.digital.common.query.BaseQuery;
import com.hm.digital.inface.entity.Menu;

import lombok.Data;

@Data
public class MenuVO extends BaseQuery<Menu> {
  /**
   * 作业编号
   */
  @QueryCondition(func = MatchType.equal)
  private String id;
  
  /**
   * 菜单名称
   */
  @QueryCondition(func = MatchType.like)
  private String menuName;


  /**
   * 父级菜单id
   */
  @QueryCondition(func = MatchType.equal)
  private String menuParentId;

  /**
   * 菜单描述
   */
  @QueryCondition(func = MatchType.equal)
  private String description;


  /**
   * 权限CODE
   */
  @QueryCondition(func = MatchType.equal)
  private String authorityCode;

  /**
   * 请求地址
   */
  @QueryCondition(func = MatchType.equal)
  private String authorityPath;

  /**
   * 类型platform/menu/function
   */
  @QueryCondition(func = MatchType.equal)
  private String action;

  /**
   * 显示图片所在路径
   */
  @QueryCondition(func = MatchType.equal)
  private String icon;

  /**
   *
   */
  private List<String> menuList;

  /**
   * 开始时间
   */
  private Date startTime;

  /**
   * 结束时间
   */
  private Date endDate;

  @Override
  public Specification<Menu> toSpec() {
    Specification<Menu> spec = super.toSpecWithAnd();
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

      if (!CollectionUtils.isEmpty(menuList)) {
        Path<Object> path = root.get("id");  //定义查询的字段
        CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
        for (int i = 0; i <menuList.size() ; i++) {
          in.value(menuList.get(i));//存入值
        }
        predicatesList.add(criteriaBuilder.and(criteriaBuilder.and(in)));
      }
      return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
    });
  }
}
