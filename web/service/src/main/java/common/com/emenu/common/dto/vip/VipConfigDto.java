package com.emenu.common.dto.vip;

/**
 * 会员基本信息配置dto
 *
 * @author chenyuting
 * @date 2015/11/16 16:04
 */
public class VipConfigDto {

    //现金兑换积分
    private Integer cashToIntegral;

    //刷卡兑换积分
    private Integer cardToIntegral;

    //在线支付兑换积分
    private Integer onlineToIntegral;

    //积分兑换现金
    private Integer integralToMoney;

    //完善信息赠送积分
    private Integer completeInfoIntegral;

    //启动多倍积分方式:1-自动；2-手动
    private Integer meansOfStartIntegral;

    //是否自动升级会员等级：0-否；1-是
    private Integer vipAutoUpgradeState;

    //get、set方法
    public Integer getCashToIntegral() {
        return cashToIntegral;
    }

    public void setCashToIntegral(Integer cashToIntegral) {
        this.cashToIntegral = cashToIntegral;
    }

    public Integer getCardToIntegral() {
        return cardToIntegral;
    }

    public void setCardToIntegral(Integer cardToIntegral) {
        this.cardToIntegral = cardToIntegral;
    }

    public Integer getOnlineToIntegral() {
        return onlineToIntegral;
    }

    public void setOnlineToIntegral(Integer onlineToIntegral) {
        this.onlineToIntegral = onlineToIntegral;
    }

    public Integer getIntegralToMoney() {
        return integralToMoney;
    }

    public void setIntegralToMoney(Integer integralToMoney) {
        this.integralToMoney = integralToMoney;
    }

    public Integer getCompleteInfoIntegral() {
        return completeInfoIntegral;
    }

    public void setCompleteInfoIntegral(Integer completeInfoIntegral) {
        this.completeInfoIntegral = completeInfoIntegral;
    }

    public Integer getMeansOfStartIntegral() {
        return meansOfStartIntegral;
    }

    public void setMeansOfStartIntegral(Integer meansOfStartIntegral) {
        this.meansOfStartIntegral = meansOfStartIntegral;
    }

    public Integer getVipAutoUpgradeState() {
        return vipAutoUpgradeState;
    }

    public void setVipAutoUpgradeState(Integer vipAutoUpgradeState) {
        this.vipAutoUpgradeState = vipAutoUpgradeState;
    }
}