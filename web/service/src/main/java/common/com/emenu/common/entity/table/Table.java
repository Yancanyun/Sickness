package com.emenu.common.entity.table;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Table
 *
 * @author: yangch
 * @time: 2015/10/23 9:36
 */

@Entity
@javax.persistence.Table(name = "t_table")
public class Table extends AbstractEntity {
    //主键
    @Id
    private Integer id;

    //所属区域ID
    @Column(name = "area_id")
    private Integer areaId;

    //餐台名称
    private String name;

    //标准座位数
    @Column(name = "seat_num")
    private Integer seatNum;

    //餐位费用/人
    @Column(name = "seat_fee")
    private BigDecimal seatFee;

    //餐台费用
    @Column(name = "table_fee")
    private BigDecimal tableFee;

    //最低消费
    @Column(name = "min_cost")
    private BigDecimal minCost;

    //二维码地址
    @Column(name = "qrcode_path")
    private String qrCodePath;

    //状态(0-停用, 1-可用, 2-占用已结账, 3-占用未结账, 4-已并桌, 5-已预订, 6-已删除)
    private Integer state;

    //实际人数
    @Column(name = "person_num")
    private Integer personNum;

    //开台时间
    @Column(name = "open_time")
    private Date openTime;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public BigDecimal getSeatFee() {
        return seatFee;
    }

    public void setSeatFee(BigDecimal seatFee) {
        this.seatFee = seatFee;
    }

    public BigDecimal getTableFee() {
        return tableFee;
    }

    public void setTableFee(BigDecimal tableFee) {
        this.tableFee = tableFee;
    }

    public BigDecimal getMinCost() {
        return minCost;
    }

    public void setMinCost(BigDecimal minCost) {
        this.minCost = minCost;
    }

    public String getQrCodePath() {
        return qrCodePath;
    }

    public void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
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
