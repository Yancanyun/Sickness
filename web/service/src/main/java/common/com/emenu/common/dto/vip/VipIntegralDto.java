package com.emenu.common.dto.vip;

import java.math.BigDecimal;

/**
 * @author chenyuting
 * @date 2016/1/20 10:46
 */
public class VipIntegralDto {

    // id
    private Integer id;

    // 兑换类型
    private Integer type;

    // 兑换类型名称
    private String integralType;

    // 兑换类型名称的name
    private String integralTypeName;

    // 兑换值
    private BigDecimal value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIntegralType() {
        return integralType;
    }

    public void setIntegralType(String integralType) {
        this.integralType = integralType;
    }

    public String getIntegralTypeName() {
        return integralTypeName;
    }

    public void setIntegralTypeName(String integralTypeName) {
        this.integralTypeName = integralTypeName;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}