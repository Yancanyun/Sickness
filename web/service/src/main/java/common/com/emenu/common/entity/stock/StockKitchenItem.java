package com.emenu.common.entity.stock;

import com.pandawork.core.common.entity.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * StockKitchenItem
 *
 * @author yaojf
 * @date 2017/3/6 13:40
 */
@Entity
@Table(name = "t_stock_kitchen_item")
public class StockKitchenItem extends AbstractEntity{
    @Id
    private Integer id;

    //厨房id
    @Column(name = "kitchen_id")
    private Integer kitchenId;

    //物品编号
    @Column(name = "item_number")
    private Integer itemNumber;

    //物品id
    @Column(name = "item_id")
    private Integer itemId;

    //助记码
    @Column(name = "assistant_code")
    private String assistantCode;

    //规格id
    @Column(name = "specifications")
    private String specifications;

    //库存量
    @Column(name = "storage_quantity")
    private BigDecimal storageQuantity;

    //成本卡单位
    @Column(name = "cost_card_unit_id")
    private Integer costCardUnitId;

    //备注
    private String remark;

    //物品状态(1-正常使用,2-标缺，3-删除)
    private Integer status;

    //创建时间
    @Column(name="created_time")
    private String createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private String lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getAssistantCode() {
        return assistantCode;
    }

    public void setAssistantCode(String assistantCode) {
        this.assistantCode = assistantCode;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
