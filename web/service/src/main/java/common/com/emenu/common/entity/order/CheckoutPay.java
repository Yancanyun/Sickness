package com.emenu.common.entity.order;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CheckoutPay
 *
 * @author: yangch
 * @time: 2016/7/19 18:55
 */
@Entity
@Table(name = "t_checkout_pay")
public class CheckoutPay extends AbstractEntity {
    // 主键
    @Id
    private Integer id;

    // 结账单ID
    @Column(name = "checkout_id")
    private Integer checkoutId;

    // 支付类型(1-现金, 2-会员卡, 3-银行卡, 4-支付宝, 5-微信支付)
    @Column(name = "checkout_type")
    private Integer checkoutType;

    // 支付金额
    @Column(name = "pay_money")
    private BigDecimal payMoney;

    // 流水号
    @Column(name = "serial_num")
    private String serialNum;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

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

    public Integer getCheckoutType() {
        return checkoutType;
    }

    public void setCheckoutType(Integer checkoutType) {
        this.checkoutType = checkoutType;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
