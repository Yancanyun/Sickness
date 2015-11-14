package com.emenu.common.dto.printer;

/**
 * PrinterDishDto
 * 菜品与打印机关联表DTO
 * @author dujuan
 * @date 2015/11/14
 */
public class PrinterDishDto {
    //关联表ID
    private Integer id;

    //菜品ID或菜品分类ID
    private Integer dishId;

    //打印机ID
    private Integer printerId;

    //类型：1-菜品分类，2-具体某一个
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getPrinterId() {
        return printerId;
    }

    public void setPrinterId(Integer printerId) {
        this.printerId = printerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
