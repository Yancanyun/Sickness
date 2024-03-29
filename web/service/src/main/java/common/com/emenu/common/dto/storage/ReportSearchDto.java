package com.emenu.common.dto.storage;

import javax.persistence.Column;
import java.util.Date;

/**
 * 查询单据的Dto
 * ReportSearchDto
 * @author xiaozl
 * @date: 2016/6/14
 */
public class ReportSearchDto {

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

    // 存放点
    private Integer depotId;

    // 操作人
    private Integer handlerPartyId;

    // 审核人
    private Integer auditPartyId;

    // 经手人
    private Integer createdPartyId;

    // 单据状态：0-未结算、1-已结算
    private Integer isSettlemented;

    // 单据状态：单据状态：0-未审核、1-代表已通过、2-代表未通过
    private Integer isAudited;

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

    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
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

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
    }

    public Integer getIsSettlemented() {
        return isSettlemented;
    }

    public void setIsSettlemented(Integer isSettlemented) {
        this.isSettlemented = isSettlemented;
    }

    public Integer getIsAudited() {
        return isAudited;
    }

    public void setIsAudited(Integer isAudited) {
        this.isAudited = isAudited;
    }
}
