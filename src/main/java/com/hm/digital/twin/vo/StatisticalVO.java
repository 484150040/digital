package com.hm.digital.twin.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.hm.digital.common.enums.InputParameterEnum;
import com.hm.digital.common.query.BaseQuery;
import com.hm.digital.inface.entity.Statistical;

import lombok.Data;

@Data
public class StatisticalVO extends BaseQuery<Statistical> {

  /**
   * 开始时间
   */
  private String startTime;

  /**
   * 结束时间
   */
  private String endTime;

  /**
   * 监所编号
   */
  private String prisonId;

  /**
   * 请求参数
   */
  private String item;

  @Override
  public Specification<Statistical> toSpec() {

    Specification<Statistical> spec = super.toSpecWithAnd();
    return ((root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicatesList = new ArrayList<>();
      predicatesList.add(spec.toPredicate(root, criteriaQuery, criteriaBuilder));
      if (!StringUtils.isEmpty(item) && (
          item.equals(InputParameterEnum.QUARTER_CIVILIAN_POLICE_IN_PRISONB_CHAR.getKey())
              || item.equals(InputParameterEnum.QUARTER_INSPECTION_DURATION_CHAR.getKey())
              || item.equals(InputParameterEnum.QUARTER_MEDICAL_APPOINTMENT.getKey())
              || item.equals(InputParameterEnum.QUARTER_DORM_CODE_IN_PRISON.getKey())
              || item.equals(InputParameterEnum.DORMCODE_QUARTER_CIVILIAN_POLICE_IN_PRISONB_CHAR.getKey())
              || item.equals(InputParameterEnum.DORMCODE_QUARTER_MEDICAL_APPOINTMENT.getKey())
              || item.equals(InputParameterEnum.DORMCODE_QUARTER_DORM_CODE_IN_PRISON.getKey())
              || item.equals(InputParameterEnum.DORMAREA_QUARTER_CIVILIAN_POLICE_IN_PRISONB_CHAR.getKey())
              || item.equals(InputParameterEnum.DORMAREA_QUARTER_MEDICAL_APPOINTMENT.getKey())
              || item.equals(InputParameterEnum.DORMAREA_QUARTER_DORM_CODE_IN_PRISON.getKey())
              || item.equals("1"))) {
        if (startTime != null) {
          predicatesList.add(
              criteriaBuilder.and(
                  criteriaBuilder.greaterThanOrEqualTo(
                      root.get("year"), startTime)));
        }
        if (endTime != null) {
          predicatesList.add(
              criteriaBuilder.and(
                  criteriaBuilder.lessThanOrEqualTo(
                      root.get("year"), endTime)));
        }
      } else {
        if (startTime != null) {
          predicatesList.add(
              criteriaBuilder.and(
                  criteriaBuilder.greaterThanOrEqualTo(
                      root.get("date"), startTime)));
        }
        if (endTime != null) {
          predicatesList.add(
              criteriaBuilder.and(
                  criteriaBuilder.lessThanOrEqualTo(
                      root.get("date"), endTime)));
        }
      }
      if (prisonId != null) {
        predicatesList.add(
            criteriaBuilder.and(
                criteriaBuilder.equal(
                    root.get("prisonId"), prisonId)));
      }
      if (item != null) {
        predicatesList.add(
            criteriaBuilder.and(
                criteriaBuilder.equal(
                    root.get("requiredParameter"), item)));
      }

      return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
    });
  }

}
