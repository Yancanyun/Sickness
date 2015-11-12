package com.emenu.common.entity.storage;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * SettlementItem
 * 结算原料详细表
 * @author dujuan
 * @date 2015/11/11
 */
@Entity
@Table(name = "t_storage_settlement_item")
public class SettlementItem extends AbstractEntity{

    private static final long serialVersionUID = 4562512793101730984L;

    @Id
    private Integer id;

    //原料ID
    @Column(name = "item_id")
    private Integer itemId;

    //入库数量
    @Column(name = "stock_in_quantity")
    private BigDecimal stockInQuantity;

    //入库金额
    @Column(name = "stock_in_money")
    private BigDecimal stockInMoney;

    //出库数量
    @Column(name = "stock_out_quantity")
    private BigDecimal stockOutQuantity;

    //出库金额
    @Column(name = "stock_out_money")
    private BigDecimal stockOutMoney;

    //盘盈数量
    @Column(name = "income_on_quantity")
    private BigDecimal incomeOnQuantity;

    //盘盈金额
    @Column(name = "income_on_money")
    private BigDecimal incomeOnMoney;

    //盘亏数量
    @Column(name = "loss_on_quantity")
    private BigDecimal lossOnQuantity;

    //盘亏金额
    @Column(name = "loss_on_money")
    private BigDecimal lossOnMoney;

    //真实数量
    @Column(name = "real_quantity")
    private BigDecimal realQuantity;

    //真实金额
    @Column(name = "real_money")
    private BigDecimal realMoney;

    //总数量
    @Column(name = "total_quantity")
    private BigDecimal totalQuantity;

    //总金额
    @Column(name = "total_money")
    private BigDecimal totalMoney;

    //月结ID
    @Column(name = "settlement_id")
    private Integer settlementId;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最后修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    /********************getter and setter ********************/

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getStockInQuantity() {
        return stockInQuantity;
    }

    public void setStockInQuantity(BigDecimal stockInQuantity) {
        this.stockInQuantity = stockInQuantity;
    }

    public BigDecimal getStockInMoney() {
        return stockInMoney;
    }

    public void setStockInMoney(BigDecimal stockInMoney) {
        this.stockInMoney = stockInMoney;
    }

    public BigDecimal getStockOutQuantity() {
        return stockOutQuantity;
    }

    public void setStockOutQuantity(BigDecimal stockOutQuantity) {
        this.stockOutQuantity = stockOutQuantity;
    }

    public BigDecimal getStockOutMoney() {
        return stockOutMoney;
    }

    public void setStockOutMoney(BigDecimal stockOutMoney) {
        this.stockOutMoney = stockOutMoney;
    }

    public BigDecimal getIncomeOnQuantity() {
        return incomeOnQuantity;
    }

    public void setIncomeOnQuantity(BigDecimal incomeOnQuantity) {
        this.incomeOnQuantity = incomeOnQuantity;
    }

    public BigDecimal getIncomeOnMoney() {
        return incomeOnMoney;
    }

    public void setIncomeOnMoney(BigDecimal incomeOnMoney) {
        this.incomeOnMoney = incomeOnMoney;
    }

    public BigDecimal getLossOnQuantity() {
        return lossOnQuantity;
    }

    public void setLossOnQuantity(BigDecimal lossOnQuantity) {
        this.lossOnQuantity = lossOnQuantity;
    }

    public BigDecimal getLossOnMoney() {
        return lossOnMoney;
    }

    public void setLossOnMoney(BigDecimal lossOnMoney) {
        this.lossOnMoney = lossOnMoney;
    }

    public BigDecimal getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(BigDecimal realQuantity) {
        this.realQuantity = realQuantity;
    }

    public BigDecimal getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(BigDecimal realMoney) {
        this.realMoney = realMoney;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Integer settlementId) {
        this.settlementId = settlementId;
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
