package com.emenu.common.dto.rank;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账单稽查
 * @author pengpeng
 * @time 2016/7/26 11:06
 */
public class CheckoutDto {
    // 主键
    private Integer id;

    // 结账单号
    private Integer checkoutId;

    // 餐台号
    private Integer tableId;

    // 餐台名
    private String tableName;

    // 收款人partyId
    private Integer checkerPartyId;

    // 结账时间
    private Date checkoutTime;

    // 支付类型
    private Integer checkoutType;

    // 消费金额
    private BigDecimal consumptionMoney;

    // 抹零金额
    private BigDecimal wipeZeroMoney;

    // 实付金额
    private BigDecimal shouldPayMoney;

    // 宾客付款
    private BigDecimal totalPayMoney;

    // 找零金额
    private BigDecimal changeMoney;

    // 消费类型
    private Integer consumptionType;

    // 是否开发票
    private Integer isInvoiced;

    public BigDecimal getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(BigDecimal changeMoney) {
        this.changeMoney = changeMoney;
    }

    public Integer getCheckerPartyId() {
        return checkerPartyId;
    }

    public void setCheckerPartyId(Integer checkerPartyId) {
        this.checkerPartyId = checkerPartyId;
    }

    public Integer getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(Integer checkoutId) {
        this.checkoutId = checkoutId;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Integer getCheckoutType() {
        return checkoutType;
    }

    public void setCheckoutType(Integer checkoutType) {
        this.checkoutType = checkoutType;
    }

    public BigDecimal getConsumptionMoney() {
        return consumptionMoney;
    }

    public void setConsumptionMoney(BigDecimal consumptionMoney) {
        this.consumptionMoney = consumptionMoney;
    }

    public Integer getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(Integer consumptionType) {
        this.consumptionType = consumptionType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsInvoiced() {
        return isInvoiced;
    }

    public void setIsInvoiced(Integer isInvoiced) {
        this.isInvoiced = isInvoiced;
    }

    public BigDecimal getShouldPayMoney() {
        return shouldPayMoney;
    }

    public void setShouldPayMoney(BigDecimal shouldPayMoney) {
        this.shouldPayMoney = shouldPayMoney;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public BigDecimal getTotalPayMoney() {
        return totalPayMoney;
    }

    public void setTotalPayMoney(BigDecimal totalPayMoney) {
        this.totalPayMoney = totalPayMoney;
    }

    public BigDecimal getWipeZeroMoney() {
        return wipeZeroMoney;
    }

    public void setWipeZeroMoney(BigDecimal wipeZeroMoney) {
        this.wipeZeroMoney = wipeZeroMoney;
    }
}
