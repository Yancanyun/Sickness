package com.emenu.common.entity.bar;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 吧台对账
 *
 * @author: yangch
 * @time: 2016/7/27 15:34
 */
@Entity
@Table(name = "t_bar_contrast")
public class BarContrast extends AbstractEntity {
    @Id
    private Integer id;

    // 操作人PartyId
    @Column(name = "party_id")
    private Integer partyId;

    // 原有金额
    @Column(name = "original_amount")
    private BigDecimal originalAmount;

    // 营业收入金额
    @Column(name = "income_amount")
    private BigDecimal incomeAmount;

    // 吧台应有金额
    @Column(name = "should_have_amount")
    private BigDecimal shouldHaveAmount;

    // 提取现金
    @Column(name = "extract_amount")
    private BigDecimal extractAmount;

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

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public BigDecimal getShouldHaveAmount() {
        return shouldHaveAmount;
    }

    public void setShouldHaveAmount(BigDecimal shouldHaveAmount) {
        this.shouldHaveAmount = shouldHaveAmount;
    }

    public BigDecimal getExtractAmount() {
        return extractAmount;
    }

    public void setExtractAmount(BigDecimal extractAmount) {
        this.extractAmount = extractAmount;
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
