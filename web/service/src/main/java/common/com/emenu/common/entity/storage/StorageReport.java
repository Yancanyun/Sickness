package com.emenu.common.entity.storage;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
public class StorageReport extends AbstractEntity{

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

        //经手人
        @Column(name = "handler_party_id")
        private Integer handlerPartyId;

        //当事人id/操作人id
        @Column(name = "created_party_id")
        private Integer createdPartyId;

        //单据金额
        private BigDecimal money;

        //单据状态：0-未结算、1-已结算
        private Integer status;

        //单据类型：1-入库单、2-出库单、3-盘盈单、4-盘亏单
        private Integer type;

        // 创建时间
        @Column(name = "created_time")
        private Date createdTime;

        // 最近修改时间
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

    public Integer getHandlerPartyId() {
        return handlerPartyId;
    }

    public void setHandlerPartyId(Integer handlerPartyId) {
        this.handlerPartyId = handlerPartyId;
    }

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
