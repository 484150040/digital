package com.hm.digital.twin.vo;

import org.springframework.data.jpa.domain.Specification;

import com.hm.digital.common.config.QueryCondition;
import com.hm.digital.common.enums.MatchType;
import com.hm.digital.common.query.BaseQuery;
import com.hm.digital.inface.entity.RoleMenu;

import lombok.Data;

@Data
public class RoleMenuVO  extends BaseQuery<RoleMenu> {

  /**
   * 作业编号
   */
  @QueryCondition(func = MatchType.equal)
  private String id;


  /**
   * 菜单权限id
   */
  @QueryCondition(func = MatchType.equal)
  private String menuId;

  /**
   * 角色id
   */
  @QueryCondition(func = MatchType.equal)
  private String roleId;

  @Override
  public Specification<RoleMenu> toSpec() {
    return super.toSpecWithAnd();
  }
}
