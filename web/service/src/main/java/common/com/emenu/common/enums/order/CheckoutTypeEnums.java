package com.emenu.common.enums.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付类型枚举
 *
 * @author: yangch
 * @time: 2016/7/19 20:08
 */
public enum CheckoutTypeEnums {
    Cash(1, "现金"),
    VipCard(2, "会员卡"),
    BankCard(3, "银行卡"),
    Alipay(4, "支付宝"),
    WeChat(5, "微信支付");

    private Integer id;
    private String status;

    CheckoutTypeEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, CheckoutTypeEnums> map = new HashMap<Integer, CheckoutTypeEnums>();

    static {
        for (CheckoutTypeEnums enums : CheckoutTypeEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static CheckoutTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static CheckoutTypeEnums valueOf(int id, CheckoutTypeEnums defaultValue) {
        CheckoutTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }
}
