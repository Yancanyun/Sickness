package com.emenu.common.dto.revenue;

import java.math.BigDecimal;

/**
 * @author pengpeng
 * @time 2016/7/28 16:41
 */
public class CheckoutEachItemSumDto {
    // 主键
    private Integer id;

    // 账单总数
    private Integer checkSum;

    // 现金支付总和
    private BigDecimal cashSum;

    // 会员卡支付总和
    private BigDecimal vipCardSum;

    // 银行卡支付总和
    private BigDecimal bankCardSum;

    // 支付宝支付总和
    private BigDecimal alipaySum;

    // 微信支付总和
    private BigDecimal weChatSum;

    // 消费金额总和
    private BigDecimal consumptionMoneySum;

    // 抹零金额总和
    private BigDecimal wipeZeroMoneySum;

    // 实付金额总和
    private BigDecimal shouldPayMoneySum;

    // 宾客付款总和
    private BigDecimal totalPayMoneySum;

    // 找零金额总和
    private BigDecimal changeMoneySum;

    // 发票总和
    private Integer invoiceSum;

    public CheckoutEachItemSumDto(){
        this.checkSum = 0;
        this.cashSum = new BigDecimal(0);
        this.vipCardSum = new BigDecimal(0);
        this.bankCardSum = new BigDecimal(0);
        this.alipaySum = new BigDecimal(0);
        this.weChatSum = new BigDecimal(0);
        this.consumptionMoneySum = new BigDecimal(0);
        this.wipeZeroMoneySum = new BigDecimal(0);
        this.shouldPayMoneySum = new BigDecimal(0);
        this.totalPayMoneySum = new BigDecimal(0);
        this.changeMoneySum = new BigDecimal(0);
        this.invoiceSum = 0;
    }

    public Integer getInvoiceSum() {
        return invoiceSum;
    }

    public void setInvoiceSum(Integer invoiceSum) {
        this.invoiceSum = invoiceSum;
    }

    public BigDecimal getAlipaySum() {
        return alipaySum;
    }

    public void setAlipaySum(BigDecimal alipaySum) {
        this.alipaySum = alipaySum;
    }

    public BigDecimal getBankCardSum() {
        return bankCardSum;
    }

    public void setBankCardSum(BigDecimal bankCardSum) {
        this.bankCardSum = bankCardSum;
    }

    public BigDecimal getCashSum() {
        return cashSum;
    }

    public void setCashSum(BigDecimal cashSum) {
        this.cashSum = cashSum;
    }

    public BigDecimal getChangeMoneySum() {
        return changeMoneySum;
    }

    public void setChangeMoneySum(BigDecimal changeMoneySum) {
        this.changeMoneySum = changeMoneySum;
    }

    public Integer getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(Integer checkSum) {
        this.checkSum = checkSum;
    }

    public BigDecimal getConsumptionMoneySum() {
        return consumptionMoneySum;
    }

    public void setConsumptionMoneySum(BigDecimal consumptionMoneySum) {
        this.consumptionMoneySum = consumptionMoneySum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getShouldPayMoneySum() {
        return shouldPayMoneySum;
    }

    public void setShouldPayMoneySum(BigDecimal shouldPayMoneySum) {
        this.shouldPayMoneySum = shouldPayMoneySum;
    }

    public BigDecimal getTotalPayMoneySum() {
        return totalPayMoneySum;
    }

    public void setTotalPayMoneySum(BigDecimal totalPayMoneySum) {
        this.totalPayMoneySum = totalPayMoneySum;
    }

    public BigDecimal getVipCardSum() {
        return vipCardSum;
    }

    public void setVipCardSum(BigDecimal vipCardSum) {
        this.vipCardSum = vipCardSum;
    }

    public BigDecimal getWeChatSum() {
        return weChatSum;
    }

    public void setWeChatSum(BigDecimal weChatSum) {
        this.weChatSum = weChatSum;
    }

    public BigDecimal getWipeZeroMoneySum() {
        return wipeZeroMoneySum;
    }

    public void setWipeZeroMoneySum(BigDecimal wipeZeroMoneySum) {
        this.wipeZeroMoneySum = wipeZeroMoneySum;
    }
}
