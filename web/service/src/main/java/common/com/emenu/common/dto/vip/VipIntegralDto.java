package com.emenu.common.dto.vip;

import java.math.BigDecimal;

/**
 * @author chenyuting
 * @date 2016/1/20 10:46
 */
public class VipIntegralDto {

    // 兑换类型
    private String IntegralType;

    // 兑换值
    private BigDecimal value;

    public String getIntegralType() {
        return IntegralType;
    }

    public void setIntegralType(String integralType) {
        IntegralType = integralType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}