package com.emenu.common.entity.storage;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 单据实体
 * @author xiaozl
 * @date 2015/11/11
 * @time 15:05
 */
@Entity
@Table(name = "t_storage_report")
public class StorageReport extends AbstractEntity {

    //主键
    @Id
    private Integer id;

    //单据编号
    @Column(name = "serial_number")
    private String serialNumber;

    //单据备注
    private String comment;

    //存放点id
    @Column(name = "depot_id")
    private Integer depotId;

    //存放点名
    @Transient
    private String depotName;

    //经手人
    @Column(name = "handler_party_id")
    private Integer handlerPartyId;

    //经手人姓名
    @Transient
    private String handlerName;

    //当事人id/操作人id
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    //当事人姓名
    @Transient
    private String createdName;

    // 审核人partyid
    @Column(name = "audit_party_id")
    private Integer auditPartyId;

    // 审核人姓名
    @Transient
    private String auditName;

    //单据金额
    private BigDecimal money;

    //单据状态：0-未结算、1-已结算
    @Column(name = "is_settlemented")
    private Integer isSettlemented;

    //单据状态：单据状态：0-未审核、1-代表已通过、2-代表未通过
    @Column(name = "is_audited")
    private Integer isAudited;

    //单据类型：1-入库单、2-出库单、3-盘盈单、4-盘亏单
    private Integer type;

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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public Integer getHandlerPartyId() {
        return handlerPartyId;
    }

    public void setHandlerPartyId(Integer handlerPartyId) {
        this.handlerPartyId = handlerPartyId;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public Integer getAuditPartyId() {
        return auditPartyId;
    }

    public void setAuditPartyId(Integer auditPartyId) {
        this.auditPartyId = auditPartyId;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getIsSettlemented() {
        return isSettlemented;
    }

    public void setIsSettlemented(Integer isSettlemented) {
        this.isSettlemented = isSettlemented;
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