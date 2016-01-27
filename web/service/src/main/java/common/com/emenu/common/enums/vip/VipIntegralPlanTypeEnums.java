package com.emenu.common.enums.vip;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyuting
 * @date 2016/1/18 19:20
 */
public enum VipIntegralPlanTypeEnums {

    CompleteInfoIntegral(0,"完善信息赠送积分","completeInfoIntegral"),
    IntegralToMoney(1,"积分兑换现金","integralToMoney"),
    ConCashToIntegral(2,"消费时现金兑换积分","conCashToIntegral"),
    ConCardToIntegral(3,"消费时刷卡兑换积分","conCardToIntegral"),
    ConOnlineToIntegral(4,"消费时在线支付兑换积分","conOnlineToIntegral"),
    RecCashToIntegral(5,"储值时现金兑换积分","recCashToIntegral"),
    RecCardToIntegral(6,"储值时刷卡兑换积分","recCardToIntegral"),
    RecOnlineToIntegral(7,"储值时在线支付兑换积分","recOnlineToIntegral");

    private Integer id;
    private String type;
    private String typeName;

    VipIntegralPlanTypeEnums(Integer id, String type, String typeName) {
        this.id = id;
        this.type = type;
        this.typeName = typeName;
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

    public String getTypeName() { return typeName; }
}
