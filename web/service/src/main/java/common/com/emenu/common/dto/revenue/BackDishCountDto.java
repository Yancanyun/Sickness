package com.emenu.common.dto.revenue;

import com.emenu.common.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 营收统计中的退项统计的Dto
 * @author guofr
 * @time 2016/8/3 10:55
 */
public class BackDishCountDto {
    // 订单编号(前台页面显示的编号)
    private int orderId;

    // 数据库退菜时间
    private Date backDish;

    // 数据库点菜时间
    private Date orderDish;

    // 日期(几年几月几天)
    private String day;

    //前台显示点菜时间(几时几分几秒)
    private String orderDishTime;

    // 前台显示退菜时间(几时几分几秒)
    private String backDishTime;

    // 前台显示间隔时间(几时几分几秒)
    private String intervalTime;

    // 退菜人
    private String backMan;

    // 餐台
    private String tableName;

    // 菜品名称
    private String dishName;

    // 菜品（套餐）单价
    private BigDecimal salePrice;

    // 菜品（套餐）会员价
    private BigDecimal vipSalePrice;

    // 退菜数量
    private Integer num;

    // 套餐Id
    private int packageId;

    // 是否为套餐：0-非套餐；1-套餐
    private int isPackage;

    // 套餐标识,用来标记哪些菜品属于同一个套餐
    private int packageFlag;

    // 菜品小类Id
    private int tagId;

    // 金额
    private BigDecimal allPrice;

    // 退菜原因
    private String reason;

    // orderDishId
    private Integer orderDishId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getBackDish() {
        return backDish;
    }

    public void setBackDish(Date backDish) {
        this.backDish = backDish;
    }

    public Date getOrderDish() {
        return orderDish;
    }

    public void setOrderDish(Date orderDish) {
        this.orderDish = orderDish;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getVipSalePrice() {
        return vipSalePrice;
    }

    public void setVipSalePrice(BigDecimal vipSalePrice) {
        this.vipSalePrice = vipSalePrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(int isPackage) {
        this.isPackage = isPackage;
    }

    public int getPackageFlag() {
        return packageFlag;
    }

    public void setPackageFlag(int packageFlag) {
        this.packageFlag = packageFlag;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
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

    public Integer getOrderDishId() {
        return orderDishId;
    }

    public void setOrderDishId(Integer orderDishId) {
        this.orderDishId = orderDishId;
    }
}
