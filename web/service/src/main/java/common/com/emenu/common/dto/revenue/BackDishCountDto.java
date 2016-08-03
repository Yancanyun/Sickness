package com.emenu.common.dto.revenue;

import java.math.BigDecimal;

/**
 * 营收统计中的退项统计的Dto
 * @author guofr
 * @time 2016/8/3 10:55
 */
public class BackDishCountDto {
    // 菜品的Id(前台页面显示的编号)
    private int dishId;

    // 日期(几年几月几天)
    private String day;

    //点菜时间(几时几分几秒)
    private String orderDishTime;

    // 退菜时间(几时几分几秒)
    private String backDishTime;

    // 间隔时间(几时几分几秒)
    private String intervalTime;

    // 退菜人
    private String backMan;

    // 餐台
    private String table;

    // 菜品名称
    private String dishName;

    // 菜品单价
    private BigDecimal unitPrice;

    // 退菜数量
    private Integer num;

    // 金额
    private BigDecimal allPrice;

    // 退菜原因
    private String reason;

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOrderDishTime() {
        return orderDishTime;
    }

    public void setOrderDishTime(String orderDishTime) {
        this.orderDishTime = orderDishTime;
    }

    public String getBackDishTime() {
        return backDishTime;
    }

    public void setBackDishTime(String backDishTime) {
        this.backDishTime = backDishTime;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getBackMan() {
        return backMan;
    }

    public void setBackMan(String backMan) {
        this.backMan = backMan;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(BigDecimal allPrice) {
        this.allPrice = allPrice;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
