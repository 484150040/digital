package com.hm.digital.twin.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hm.digital.inface.entity.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDto {

  /**
   * 作业编号
   */
  private String id;


  /**
   * 菜单名称
   */
  private String menuName;

  /**
   * 父级菜单id
   */
  private String menuParentId;

  /**
   * 菜单描述
   */
  private String description;


  /**
   * 权限CODE
   */
  private String authorityCode;

  /**
   * 请求地址
   */
  private String authorityPath;

  /**
   * 类型platform/menu/function
   */
  private String action;

  /**
   * 显示图片所在路径
   */
  private String icon;

  /**
   * 排序
   */
  private String sortOrder;
  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date modifyTime;

  /**
   * 子类
   */
  private List<Menu> child;

  /**
   * function集合
   */
  private List<Menu> function;
}
