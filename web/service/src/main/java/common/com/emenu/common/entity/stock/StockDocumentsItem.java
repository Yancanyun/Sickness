package com.emenu.common.entity.stock;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * StockDocumentsItem
 *
 * @author renhongshuai
 *         Created by admin.
 * @Time 2017/3/6 9:18.
 */
@Entity
@Table(name = "t_stock_documents_item")
public class StockDocumentsItem extends AbstractEntity{

    @Id
    private Integer id;

    //单据Id
    @Column(name = "documents_id")
    private Integer documentsId;

    //物品Id
    @Column(name = "item_id")
    private Integer itemId;

    //规格Id
    @Column(name = "specification_id")
    private Integer specificationId;

    //数量
    @Column(name = "quantity")
    private BigDecimal quantity;

    //单位Id
    @Column(name = "unit_id")
    private Integer unitId;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    //单价
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    //成本价=单价*数量
    @Column(name = "price")
    private BigDecimal price;

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

    public Integer getDocumentsId() {
        return documentsId;
    }

    public void setDocumentsId(Integer documentsId) {
        this.documentsId = documentsId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getSpecificationId() {
        return specificationId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
