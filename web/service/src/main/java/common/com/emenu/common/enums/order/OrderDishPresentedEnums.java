package com.emenu.common.enums.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单菜品赠送状态枚举
 *
 * @author quanyibo
 * @date 2016/7/19.
 */
public enum OrderDishPresentedEnums {

    IsPresentedDish(1,"赠送菜品"),
    IsNotPresentedDish(0,"非赠送菜品")
    ;
    private Integer id;
    private String desc;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    OrderDishPresentedEnums(Integer id,String desc){
        this.id = id;
        this.desc = desc;
    }

    private static Map<Integer,OrderDishPresentedEnums> map = new HashMap<Integer,OrderDishPresentedEnums>();
    static {
        for(OrderDishPresentedEnums autoOutEnums:OrderDishPresentedEnums.values()){
            map.put(autoOutEnums.getId(),autoOutEnums);
        }
    }
    public static OrderDishPresentedEnums valueOf(int id){
        return valueOf(id,null);
    }
    public static OrderDishPresentedEnums valueOf(int id,OrderDishPresentedEnums defaultValue){
        OrderDishPresentedEnums autoOutEnums = map.get(id);
        return autoOutEnums == null? defaultValue:autoOutEnums;
    }
}
