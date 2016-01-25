package com.emenu.common.enums.vip;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyuting
 * @date 2016/1/18 19:20
 */
public enum VipIntegralPlanTypeEnums {

    CompleteInfoIntegral(0,"完善信息赠送积分"),
    IntegralToMoney(1,"积分兑换现金"),
    ConCashToIntegral(2,"消费时现金兑换积分"),
    ConCardToIntegral(3,"消费时刷卡兑换积分"),
    ConOnlineToIntegral(4,"消费时在线支付兑换积分"),
    RecCashToIntegral(5,"储值时现金兑换积分"),
    RecCardToIntegral(6,"储值时刷卡兑换积分"),
    RecOnlineToIntegral(7,"储值时在线支付兑换积分");

    private Integer id;
    private String type;

    VipIntegralPlanTypeEnums(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, VipIntegralPlanTypeEnums> map = new HashMap<Integer, VipIntegralPlanTypeEnums>();

    static {
        for (VipIntegralPlanTypeEnums vipIntegralPlanTypeEnums : VipIntegralPlanTypeEnums.values()) {
            map.put(vipIntegralPlanTypeEnums.getId(), vipIntegralPlanTypeEnums);
        }
    }

    public static VipIntegralPlanTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static VipIntegralPlanTypeEnums valueOf(int id, VipIntegralPlanTypeEnums defaultValue) {
        VipIntegralPlanTypeEnums vipIntegralPlanTypeEnums = map.get(id);
        return vipIntegralPlanTypeEnums == null ? defaultValue : vipIntegralPlanTypeEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
