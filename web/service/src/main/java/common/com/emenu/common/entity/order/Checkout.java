package com.emenu.common.entity.order;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结账单实体
 *
 * @author chenyuting
 * @date 2016/6/1 15:54
 */
@Entity
@Table(name = "t_checkout")
public class Checkout extends AbstractEntity{

    // 主键
    @Id
    private Integer id;

    // 桌号id
    @Column(name = "table_id")
    private Integer tableId;

    // 收款人partyId
    @Column(name = "checer_party_id")
    private Integer checkerPartyId;

    // 结账时间
    @Column(name = "checkout_time")
    private Date checkoutTime;

    // 消费金额
    @Column(name = "consumption_money")
    private BigDecimal consumptionMoney;

    // 抹零金额
    @Column(name = "wipe_zero_money")
    private BigDecimal wipeZeroMoney;

    // 实付金额（实付金额=消费金额-抹零金额）
    @Column(name = "should_pay_money")
    private BigDecimal shouldPayMoney;

    // 宾客付款
    @Column(name = "total_pay_money")
    private BigDecimal totalPayMoney;

    // 找零金额（找零金额=宾客付款/预付金额-实付金额）
    @Column(name = "change_money")
    private BigDecimal changeMoney;

    // 预付金额
    @Column(name = "prepay_money")
    private BigDecimal prepayMoney;

    // 状态：0-未结账；1-已结账
    private Integer status;

    // 消费类型：1-第一次消费；2-非第一次消费
    @Column(name = "consumption_type")
    private Integer consumptionType;

    // 是否开发票：0-不开发票；1-开发票
    @Column(name = "is_invoiced")
    private Integer isInvoiced;

    // 是否免单：0-非免单；1-免单
    @Column(name = "is_free_order")
    private Integer isFreeOrder;

    // 免单备注
    @Column(name = "free_remark_id")
    private Integer freeRemarkId;


    // setter、getter
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getCheckerPartyId() {
        return checkerPartyId;
    }

    public void setCheckerPartyId(Integer checkerPartyId) {
        this.checkerPartyId = checkerPartyId;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public BigDecimal getConsumptionMoney() {
        return consumptionMoney;
    }

    public void setConsumptionMoney(BigDecimal consumptionMoney) {
        this.consumptionMoney = consumptionMoney;
    }

    public BigDecimal getWipeZeroMoney() {
        return wipeZeroMoney;
    }

    public void setWipeZeroMoney(BigDecimal wipeZeroMoney) {
        this.wipeZeroMoney = wipeZeroMoney;
    }

    public BigDecimal getShouldPayMoney() {
        return shouldPayMoney;
    }

    public void setShouldPayMoney(BigDecimal shouldPayMoney) {
        this.shouldPayMoney = shouldPayMoney;
    }

    public BigDecimal getTotalPayMoney() {
        return totalPayMoney;
    }

    public void setTotalPayMoney(BigDecimal totalPayMoney) {
        this.totalPayMoney = totalPayMoney;
    }

    public BigDecimal getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(BigDecimal changeMoney) {
        this.changeMoney = changeMoney;
    }

    public BigDecimal getPrepayMoney() {
        return prepayMoney;
    }

    public void setPrepayMoney(BigDecimal prepayMoney) {
        this.prepayMoney = prepayMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(Integer consumptionType) {
        this.consumptionType = consumptionType;
    }

    public Integer getIsInvoiced() {
        return isInvoiced;
    }

    public void setIsInvoiced(Integer isInvoiced) {
        this.isInvoiced = isInvoiced;
    }

    public Integer getIsFreeOrder() {
        return isFreeOrder;
    }

    public void setIsFreeOrder(Integer isFreeOrder) {
        this.isFreeOrder = isFreeOrder;
    }

    public Integer getFreeRemarkId() {
        return freeRemarkId;
    }

    public void setFreeRemarkId(Integer freeRemarkId) {
        this.freeRemarkId = freeRemarkId;
    }
}