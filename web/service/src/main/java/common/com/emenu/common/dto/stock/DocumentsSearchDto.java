package com.emenu.common.dto.stock;

import java.util.Date;

/**
 * 库存单据搜索dto
 *
 * @author renhongshuai
 *         Created by admin.
 * @Time 2017/3/6 9:46.
 */
public class DocumentsSearchDto {

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    // 页码
    private Integer pageNo;

    // 分页size
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    // 当事人partyId
    private Integer createdPartyId;

    // 经手人partyId
    private Integer handlerPartyId;

    // 审核人partyId
    private Integer auditPartyId;

    // 审核状态：0-未审核、1-代表已通过、2-代表未通过
    private Integer isAudited;

    // 结算状态：0-未结算、1-已结算
    private Integer isSettled;

    // 单据类型：1-入库单、2-领用单、3-回库单、4-盘盈单、5-盘亏单
    private Integer type;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
    }

    public Integer getHandlerPartyId() {
        return handlerPartyId;
    }

    public void setHandlerPartyId(Integer handlerPartyId) {
        this.handlerPartyId = handlerPartyId;
    }

    public Integer getAuditPartyId() {
        return auditPartyId;
    }

    public void setAuditPartyId(Integer auditPartyId) {
        this.auditPartyId = auditPartyId;
    }

    public Integer getIsAudited() {
        return isAudited;
    }

    public void setIsAudited(Integer isAudited) {
        this.isAudited = isAudited;
    }

    public Integer getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(Integer isSettled) {
        this.isSettled = isSettled;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
