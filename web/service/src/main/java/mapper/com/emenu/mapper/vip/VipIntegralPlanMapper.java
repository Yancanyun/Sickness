package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.VipIntegralPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员积分方案mapper
 *
 * @author chenyuting
 * @date 2016/1/19 9:05
 */
public interface VipIntegralPlanMapper {

    public List<VipIntegralPlan> listByGradeId(@Param("gradeId") int gradeId) throws Exception;

    public void updateStatus(@Param("gradeId") int gradeId,
                             @Param("status") int status) throws Exception;

}
