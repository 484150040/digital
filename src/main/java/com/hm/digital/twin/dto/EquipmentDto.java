package com.hm.digital.twin.dto;

import java.util.List;

import com.hm.digital.inface.entity.Equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentDto {
  /**
   * 作业编号
   */
  private String id;

  /**
   * 监所编号
   */
  private String prisonId;

  /**
   * 设备品牌
   */
  private String brand;

  /**
   * 设备名称
   */
  private String name;

  /**
   * 是否有效
   */
  private Integer isvalid;

  /**
   * 设备code
   */
  private String code;

  /**
   * 父级设备id
   */
  private String parentId;

  /**
   * 子级数据
   */
  private List<Equipment> equipments;
}
