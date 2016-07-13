package com.emenu.common.entity.storage;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 单据详情实体
 * @author xiaozl
 * @date 2015/11/11
 * @time 15:03
 */
@Entity
@Table(name = "t_storage_report_item")
public class StorageReportItem extends AbstractEntity{

    //主键
    @Id
    private Integer id;

    //原料id
    @Column(name = "item_id")
    private Integer itemId;

    //库存物品
    @Transient
    private String itemName;

    @Transient
    private String itemNumber;

    //入库数量
    private BigDecimal quantity;

    //订货单位名
    @Transient
    private String orderUnitName;

    //成本卡单位数量
    @Transient
    private BigDecimal costCardUnitQuantity;

    //成本卡单位名
    @Transient
    private String costCardUnitName;

    //计数
    private BigDecimal count;

    //成本价
    private BigDecimal price;

    //单据详情备注
    private String comment;

    //单据id
    @Column(name = "report_id")
    private Integer reportId;

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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOrderUnitName() {
        return orderUnitName;
    }

    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public BigDecimal getCostCardUnitQuantity() {
        return costCardUnitQuantity;
    }

    public void setCostCardUnitQuantity(BigDecimal costCardUnitQuantity) {
        this.costCardUnitQuantity = costCardUnitQuantity;
    }

    public String getCostCardUnitName() {
        return costCardUnitName;
    }

    public void setCostCardUnitName(String costCardUnitName) {
        this.costCardUnitName = costCardUnitName;
    }
}
