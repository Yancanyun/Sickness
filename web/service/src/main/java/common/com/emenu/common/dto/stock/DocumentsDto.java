package com.emenu.common.dto.stock;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 新库存单据Dto
 *
 * @author renhongshuai
 *         Created by admin.
 * @Time 2017/3/6 9:50.
 */
public class DocumentsDto {

    // 主键
    private Integer id;

    // 供货商id
    private Integer supplierId;

    //厨房（存放点）Id
    private Integer kitchenId;

    // 单据编号
    private String serialNumber;

    // 备注
    private String comment;

    // 当事人partyId
    private Integer createdPartyId;

    // 当事人姓名
    private String createdName;

    // 经手人partyId
    private Integer handlerPartyId;

    // 经手人姓名
    private String handlerName;

    // 审核人partyId
    private Integer auditPartyId;

    // 审核人姓名
    private String auditName;

    // 金额
    private BigDecimal money;

    // 结算状态：0-未结算、1-已结算
    private Integer isSettled;

    // 结算状态字符串
    private String isSettledStr;

    // 审核状态：0-未审核、1-代表已通过、2-代表未通过
    private Integer isAudited;

    // 审核状态字符串
    private String isAuditedStr;

    // 单据类型：1-入库单、2-领用单、3-回库单、4-盘盈单、5-盘亏单
    private Integer type;

    // 创建时间
    private Date createdTime;

    // 创建时间字符串
    private String createdTimeStr;

    // 单据明细
    private List<DocumentsItemDto> documentsItemDtoList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
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

    public Integer getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(Integer isSettled) {
        this.isSettled = isSettled;
    }

    public String getIsSettledStr() {
        return isSettledStr;
    }

    public void setIsSettledStr(String isSettledStr) {
        this.isSettledStr = isSettledStr;
    }

    public Integer getIsAudited() {
        return isAudited;
    }

    public void setIsAudited(Integer isAudited) {
        this.isAudited = isAudited;
    }

    public String getIsAuditedStr() {
        return isAuditedStr;
    }

    public void setIsAuditedStr(String isAuditedStr) {
        this.isAuditedStr = isAuditedStr;
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

    public String getCreatedTimeStr() {
        return createdTimeStr;
    }

    public void setCreatedTimeStr(String createdTimeStr) {
        this.createdTimeStr = createdTimeStr;
    }

    public List<DocumentsItemDto> getDocumentsItemDtoList() {
        return documentsItemDtoList;
    }

    public void setDocumentsItemDtoList(List<DocumentsItemDto> documentsItemDtoList) {
        this.documentsItemDtoList = documentsItemDtoList;
    }
}
