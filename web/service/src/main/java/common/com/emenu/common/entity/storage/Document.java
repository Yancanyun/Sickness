package com.emenu.common.entity.storage;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 单据类
 * @author xiaozl
 * @date 2015/11/10
 * @time 17:40
 */
@Entity
@Table(name = "t_storage_document")
public class Document extends AbstractEntity {

    //主键
    @Id
    private Integer id;

    //单据编号
    private String number;

    //单据备注
    private String comment;

    //存放点id
    @Column(name = "depots_id")
    private Integer depotsId;

    //经手人
    private String handler;

    //当事人id
    @Column(name = "party_id")
    private Integer partyId;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getDepotsId() {
        return depotsId;
    }

    public void setDepotsId(Integer depotsId) {
        this.depotsId = depotsId;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
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
