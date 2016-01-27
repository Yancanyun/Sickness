package com.emenu.common.dto.vip;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/1/25 9:25
 */
public class VipIntegralTypeDto {

    //完善信息赠送积分
    private BigDecimal completeInfoIntegral;

    //积分兑换现金
    private BigDecimal integralToMoney;

    // 消费时现金兑换积分
    private BigDecimal conCashToIntegral;

    // 消费时刷卡兑换积分
    private BigDecimal conCardToIntegral;

    // 消费时在线支付兑换积分
    private BigDecimal conOnlineToIntegral;

    // 储值时现金兑换积分
    private BigDecimal recCashToIntegral;

    // 储值时刷卡兑换积分
    private BigDecimal recCardToIntegral;

    // 储值时在线支付兑换积分
    private BigDecimal recOnlineToIntegral;

    // 兑换类型的name
    private List<String> typeName;

    // 利用java反射获取name
    public List<String> getTypeName(Object obj)
    {
        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            String name = fields[i].getName();
            typeName.add(name);
        }
        return typeName;
    }

    //************getter、setter***************

    public BigDecimal getCompleteInfoIntegral() {
        return completeInfoIntegral;
    }

    public void setCompleteInfoIntegral(BigDecimal completeInfoIntegral) {
        this.completeInfoIntegral = completeInfoIntegral;
    }

    public BigDecimal getIntegralToMoney() {
        return integralToMoney;
    }

    public void setIntegralToMoney(BigDecimal integralToMoney) {
        this.integralToMoney = integralToMoney;
    }

    public BigDecimal getConCashToIntegral() {
        return conCashToIntegral;
    }

    public void setConCashToIntegral(BigDecimal conCashToIntegral) {
        this.conCashToIntegral = conCashToIntegral;
    }

    public BigDecimal getConCardToIntegral() {
        return conCardToIntegral;
    }

    public void setConCardToIntegral(BigDecimal conCardToIntegral) {
        this.conCardToIntegral = conCardToIntegral;
    }

    public BigDecimal getConOnlineToIntegral() {
        return conOnlineToIntegral;
    }

    public void setConOnlineToIntegral(BigDecimal conOnlineToIntegral) {
        this.conOnlineToIntegral = conOnlineToIntegral;
    }

    public BigDecimal getRecCashToIntegral() {
        return recCashToIntegral;
    }

    public void setRecCashToIntegral(BigDecimal recCashToIntegral) {
        this.recCashToIntegral = recCashToIntegral;
    }

    public BigDecimal getRecCardToIntegral() {
        return recCardToIntegral;
    }

    public void setRecCardToIntegral(BigDecimal recCardToIntegral) {
        this.recCardToIntegral = recCardToIntegral;
    }

    public BigDecimal getRecOnlineToIntegral() {
        return recOnlineToIntegral;
    }

    public void setRecOnlineToIntegral(BigDecimal recOnlineToIntegral) {
        this.recOnlineToIntegral = recOnlineToIntegral;
    }
}