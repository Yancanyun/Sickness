package com.emenu.common.dto.storage;

import com.emenu.common.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * StorageReportItemDto
 * 库存单据物品dto，供库存单据dto使用
 *
 * @author xiaozl
 * @date 2016/1/20 18:35
 */
public class StorageReportItemDto {

    private Integer id;

    //原料id
    private Integer itemId;

    //原料名称
    private String itemName;

    //助记码
    private String assistantCode;

    //入库数量(订货单位）
    private BigDecimal quantity;

    //订货单位名
    private String orderUnitName;

    //成本卡单位数量
    private BigDecimal costCardQuantity;

    //成本卡单位名
    private String costCardUnitName;

    //计数
    private BigDecimal count;

    //成本价
    private BigDecimal price;

    //单据详情备注
    private String comment;

    // 创建时间
    private Date createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAssistantCode() {
        return assistantCode;
    }

    public void setAssistantCode(String assistantCode) {
        this.assistantCode = assistantCode;
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

    public String getCreatedTime() {
        return DateUtils.yearMonthDayFormat(createdTime);
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getOrderUnitName() {
        return orderUnitName;
    }

    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    public BigDecimal getCostCardQuantity() {
        return costCardQuantity;
    }

    public void setCostCardQuantity(BigDecimal costCardQuantity) {
        this.costCardQuantity = costCardQuantity;
    }

    public String getCostCardUnitName() {
        return costCardUnitName;
    }

    public void setCostCardUnitName(String costCardUnitName) {
        this.costCardUnitName = costCardUnitName;
    }
}
