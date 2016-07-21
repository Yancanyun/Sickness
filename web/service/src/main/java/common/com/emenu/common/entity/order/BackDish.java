package com.emenu.common.entity.order;

import com.emenu.common.utils.DateUtils;
import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chenyuting
 * @date 2016/7/18 10:59
 */
@Entity
@Table(name = "t_back_dish")
public class BackDish extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 订单id
    @Column(name = "order_id")
    private Integer orderId;

    // 订单菜品id
    @Column(name = "order_dish_id")
    private Integer orderDishId;

    // 退菜口味id
    @Column(name = "taste_id")
    private Integer tasteId;

    // 退菜数量
    @Column(name = "back_number")
    private Float backNumber;

    // 退菜备注
    @Column(name = "back_remarks")
    private String backRemarks;

    // 退菜时间
    @Column(name = "back_time")
    private Date backTime;

    // 操作人partyId
    @Column(name = "employee_party_id")
    private Integer employeePartyId;

    @Transient
    private String backTimeStr;

    // getter、setter
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderDishId() {
        return orderDishId;
    }

    public void setOrderDishId(Integer orderDishId) {
        this.orderDishId = orderDishId;
    }

    public Integer getTasteId() {
        return tasteId;
    }

    public void setTasteId(Integer tasteId) {
        this.tasteId = tasteId;
    }

    public Float getBackNumber() {
        return backNumber;
    }

    public void setBackNumber(Float backNumber) {
        this.backNumber = backNumber;
    }

    public String getBackRemarks() {
        return backRemarks;
    }

    public void setBackRemarks(String backRemarks) {
        this.backRemarks = backRemarks;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
        if (backTime != null) {
            this.backTimeStr = DateUtils.formatDate(backTime, "yyyy-MM-dd HH:mm:ss");
        } else {
            this.backTimeStr = "";
        }
    }

    public Integer getEmployeePartyId() {
        return employeePartyId;
    }

    public void setEmployeePartyId(Integer employeePartyId) {
        this.employeePartyId = employeePartyId;
    }

    public String getBackTimeStr() {
        return backTimeStr;
    }

    public void setBackTimeStr(String backTimeStr) {
        this.backTimeStr = backTimeStr;
    }
}