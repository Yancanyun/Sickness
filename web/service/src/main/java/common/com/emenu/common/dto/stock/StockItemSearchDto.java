package com.emenu.common.dto.stock;

import java.math.BigDecimal;
import java.util.List;

/**
 * StockItemSearchDto
 *
 * @author Karl_SC
 * @Time 2017/3/8 16:25
 */
public class StockItemSearchDto {

    // 关键字（名称/编号/助记码）
    private String keyword;

    // 页码
    private Integer pageNo;

    // 分页大小
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    // 物品ID
    private Integer itemId;

    // 物品编号
    private String itemNumber;

    // 物品名称
    private String name;

    // 助记码
    private String assistantCode;

    // 分类ID
    private Integer tagId;

    // 分类ID列表
    private List<Integer> tagIdList;

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }

    // 规格
    private String specifications;

    // 库存量
    private BigDecimal storageQuantity;

    // 成本卡单位id
    private Integer costCardUnitId;

    // 预警上限
    private BigDecimal upperQuantity;

    // 预警下限
    private BigDecimal lowerQuantity;

    // 备注
    private String remark;

    // 出库方式(1-加权平均，2-手动)
    private Integer stockOutType;

    // 状态(1-正常使用,2-已删除)
    private Integer status;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
