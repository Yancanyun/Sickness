package com.emenu.common.dto.order;

import java.util.List;

/**
 * PrintOrderDishDto
 * 菜品打印信息
 * @author quanyibo
 * @date 2016/6/24.
 */
public class PrintOrderDishDto {

    //订单菜品Id
    private Integer orderDishId;

    //桌子名称
    private String tableName;

    //菜品名称
    private String dishName;

    // 菜品大类名称
    private String  dishBigTagName;

    //点餐数量
    private float num;

    //备注
    private String remark;

    //口味
    private List<String> taste;

    // 上菜方式
    private String serverType;

    //打印机Ip
    private String printerIp;

    public Integer getOrderDishId() {
        return orderDishId;
    }

    public void setOrderDishId(Integer orderDishId) {
        this.orderDishId = orderDishId;
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


    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getTaste() {
        return taste;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getDishBigTagName() {
        return dishBigTagName;
    }

    public void setDishBigTagName(String dishBigTagName) {
        this.dishBigTagName = dishBigTagName;
    }

    public void setTaste(List<String> taste) {
        this.taste = taste;
    }

    public String getPrinterIp() {
        return printerIp;
    }

    public void setPrinterIp(String printerIp) {
        this.printerIp = printerIp;
    }
}
