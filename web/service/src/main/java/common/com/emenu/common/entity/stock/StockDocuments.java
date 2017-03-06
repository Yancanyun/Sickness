package com.emenu.common.entity.stock;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


/**
 * StockDocuments
 *
 * @author renhongshuai
 *         Created by admin on 8:53.
 */
@Entity
@Table(name = "t_stock_documents")
public class StockDocuments extends AbstractEntity {

    @Id
    private Integer id;

    //单据编号
    @Column(name = "serial_number")
    private String serialNumber;

    //供货商id
    @Column(name = "supplier_id")
    private Integer supplierId;

    //厨房Id
    @Column(name = "kitchen_id")
    private Integer kitchenId;

    //单据备注
    @Column(name = "comment")
    private String comment;

    //当事人Id
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    //经手人Id
    @Column(name = "handler_party_id")
    private Integer handlerPartyId;

    //审核人Id
    @Column(name = "audit_party_id")
    private Integer auditPartyId;

    //金额
    @Column(name = "money")
    private BigDecimal money;

    //结算状态(0-未结算、1-已结算)
    @Column(name = "is_settled")
    private Integer isSettled;

    //审核状态(0-未审核、1-代表已通过、2-代表未通过)
    @Column(name = "is_audited")
    private Integer isAudited;

    //单据类型
    @Column(name = "type")
    private Integer type;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
    }

    public Integer getHandlerPartyId() {
        return handlerPartyId;
    }

    public void setHandlerPartyId(Integer handlerPartyId) {
        this.handlerPartyId = handlerPartyId;
    }

    public Integer getAuditPartyId() {
        return auditPartyId;
    }

    public void setAuditPartyId(Integer auditPartyId) {
        this.auditPartyId = auditPartyId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(Integer isSettled) {
        this.isSettled = isSettled;
    }

    public Integer getIsAudited() {
        return isAudited;
    }

    public void setIsAudited(Integer isAudited) {
        this.isAudited = isAudited;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
