package com.emenu.common.entity.order;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单实体
 *
 * @author chenyuting
 * @date 2016/6/1 15:55
 */
@Entity
@Table(name = "t_order")
public class Order extends AbstractEntity {

    // 主键id
    @Id
    private Integer id;

    // 结账单id
    @Column(name = "checkout_id")
    private Integer checkoutId;

    // 餐台id
    @Column(name = "table_id")
    private Integer tableId;

    // 服务员partyId
    @Column(name = "employee_party_id")
    private Integer employeePartyId;

    // 会员partyId
    @Column(name = "vip_party_id")
    private Integer vipPartyId;

    // 状态：1-已下单；2-已结账
    private Integer status;

    // 整单备注
    @Column(name = "order_remark")
    private String orderRemark;

    // 整单上菜方式：1-即起；2-叫起
    @Column(name = "order_serve_type")
    private Integer orderServeType;

    // 登录下单方式：1-微信下单；2-其他登录方式下单
    @Column(name = "login_type")
    private Integer loginType;


    // setter、getter
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(Integer checkoutId) {
        this.checkoutId = checkoutId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getEmployeePartyId() {
        return employeePartyId;
    }

    public void setEmployeePartyId(Integer employeePartyId) {
        this.employeePartyId = employeePartyId;
    }

    public Integer getVipPartyId() {
        return vipPartyId;
    }

    public void setVipPartyId(Integer vipPartyId) {
        this.vipPartyId = vipPartyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public Integer getOrderServeType() {
        return orderServeType;
    }

    public void setOrderServeType(Integer orderServeType) {
        this.orderServeType = orderServeType;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }
}