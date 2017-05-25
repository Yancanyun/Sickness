package com.emenu.common.entity.stock;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * StockItem
 *
 * @author pengpeng
 * @time 2017/3/4 9:11
 */
@Entity
@Table(name ="t_stock_item")
public class StockItem extends AbstractEntity {

    @Id
    private Integer id;

    // 物品编号
    @Column(name = "item_number")
    private String itemNumber;

    // 名称
    @Column(name = "name")
    private String name;

    // 助记码
    @Column(name = "assistant_code")
    private String assistantCode;

    // 所属分类Id
    @Column(name = "tag_id")
    private Integer tagId;

    // 分类名
    @Transient
    private String tagName;

    // 规格Id（所有规格Id，以字符串存在该字段里）
    @Column(name = "specifications")
    private String specifications;

    // 刷出所有规格,规格管理表还没写
//    @Transient
//    private List<Specifications> specificationsList;


//    public List<Specifications> getSpecificationsList() {
//        return specificationsList;
//    }
//
//    public void setSpecificationsList(List<Specifications> specificationsList) {
//        this.specificationsList = specificationsList;
//    }


    // 库存量
    @Column(name = "storage_quantity")
    private BigDecimal storageQuantity;

    // 成本卡单位id
    @Column(name = "cost_card_unit_id")
    private Integer costCardUnitId;

    // 成本卡单位
    @Transient
    private String costCardName;

    // 预警上限
    @Column(name = "upper_quantity")
    private BigDecimal upperQuantity;

    // 预警下限
    @Column(name = "lower_quantity")
    private BigDecimal lowerQuantity;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 出库方式(1-加权平均，2-手动)
    @Column(name = "stock_out_type")
    private Integer stockOutType;

    @Transient
    private String stockOutTypeName;

    // 状态
    private Integer status;

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

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssistantCode() {
        return assistantCode;
    }

    public void setAssistantCode(String assistantCode) {
        this.assistantCode = assistantCode;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public BigDecimal getStorageQuantity() {
        return storageQuantity;
    }

    public void setStorageQuantity(BigDecimal storageQuantity) {
        this.storageQuantity = storageQuantity;
    }

    public Integer getCostCardUnitId() {
        return costCardUnitId;
    }

    public void setCostCardUnitId(Integer costCardUnitId) {
        this.costCardUnitId = costCardUnitId;
    }

    public String getCostCardName() {
        return costCardName;
    }

    public void setCostCardName(String costCardName) {
        this.costCardName = costCardName;
    }

    public BigDecimal getUpperQuantity() {
        return upperQuantity;
    }

    public void setUpperQuantity(BigDecimal upperQuantity) {
        this.upperQuantity = upperQuantity;
    }

    public BigDecimal getLowerQuantity() {
        return lowerQuantity;
    }

    public void setLowerQuantity(BigDecimal lowerQuantity) {
        this.lowerQuantity = lowerQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStockOutType() {
        return stockOutType;
    }

    public void setStockOutType(Integer stockOutType) {
        this.stockOutType = stockOutType;
    }

    public String getStockOutTypeName() {
        return stockOutTypeName;
    }

    public void setStockOutTypeName(String stockOutTypeName) {
        this.stockOutTypeName = stockOutTypeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
