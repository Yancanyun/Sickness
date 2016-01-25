package com.emenu.common.dto.vip;

/**
 * @author chenyuting
 * @date 2016/1/25 9:25
 */
public class VipIntegralTypeDto {

    //完善信息赠送积分
    private Integer completeInfoIntegral;

    //积分兑换现金
    private Integer integralToMoney;

    // 消费时现金兑换积分
    private Integer conCashToIntegral;

    // 消费时刷卡兑换积分
    private Integer conCardToIntegral;

    // 消费时在线支付兑换积分
    private Integer conOnlineToIntegral;

    // 储值时现金兑换积分
    private Integer recCashToIntegral;

    // 储值时刷卡兑换积分
    private Integer recCardToIntegral;

    // 储值时在线支付兑换积分
    private Integer recOnlineToIntegral;


    //************getter、setter***************
    public Integer getCompleteInfoIntegral() {
        return completeInfoIntegral;
    }

    public void setCompleteInfoIntegral(Integer completeInfoIntegral) {
        this.completeInfoIntegral = completeInfoIntegral;
    }

    public Integer getIntegralToMoney() {
        return integralToMoney;
    }

    public void setIntegralToMoney(Integer integralToMoney) {
        this.integralToMoney = integralToMoney;
    }

    public Integer getConCashToIntegral() {
        return conCashToIntegral;
    }

    public void setConCashToIntegral(Integer conCashToIntegral) {
        this.conCashToIntegral = conCashToIntegral;
    }

    public Integer getConCardToIntegral() {
        return conCardToIntegral;
    }

    public void setConCardToIntegral(Integer conCardToIntegral) {
        this.conCardToIntegral = conCardToIntegral;
    }

    public Integer getConOnlineToIntegral() {
        return conOnlineToIntegral;
    }

    public void setConOnlineToIntegral(Integer conOnlineToIntegral) {
        this.conOnlineToIntegral = conOnlineToIntegral;
    }

    public Integer getRecCashToIntegral() {
        return recCashToIntegral;
    }

    public void setRecCashToIntegral(Integer recCashToIntegral) {
        this.recCashToIntegral = recCashToIntegral;
    }

    public Integer getRecCardToIntegral() {
        return recCardToIntegral;
    }

    public void setRecCardToIntegral(Integer recCardToIntegral) {
        this.recCardToIntegral = recCardToIntegral;
    }

    public Integer getRecOnlineToIntegral() {
        return recOnlineToIntegral;
    }

    public void setRecOnlineToIntegral(Integer recOnlineToIntegral) {
        this.recOnlineToIntegral = recOnlineToIntegral;
    }
}