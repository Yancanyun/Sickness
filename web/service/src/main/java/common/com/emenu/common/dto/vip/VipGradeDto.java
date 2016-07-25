package com.emenu.common.dto.vip;

import com.emenu.common.entity.vip.VipGrade;

/**
 * VipGradeDto
 *
 * @author: yangch
 * @time: 2015/12/30 11:12
 */
public class VipGradeDto {

    private VipGrade vipGrade;

    private String vipDishPricePlanName;

    public VipGrade getVipGrade() {
        return vipGrade;
    }

    public void setVipGrade(VipGrade vipGrade) {
        this.vipGrade = vipGrade;
    }

    public String getVipDishPricePlanName() {
        return vipDishPricePlanName;
    }

    public void setVipDishPricePlanName(String vipDishPricePlan) {
        this.vipDishPricePlanName = vipDishPricePlan;
    }
}
