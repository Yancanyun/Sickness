package com.emenu.common.entity.vip;

import com.pandawork.core.common.entity.AbstractEntity;
import com.pandawork.core.common.entity.EntityAnnotation;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PartyVipCount
 * 会员账户信息实体
 *
 * @author xubr
 * @date 2016/1/18.
 */
@Entity
@Table(name = "t_vip_account_info")
public class VipAccountInfo extends AbstractEntity {

    //会员账户id
    @Id
    private Integer id;

    //当事人id
    @Column(name = "party_id")
    private Integer partyId;

    //已挂账金额
    @Column(name = "used_credit_amount")
    private BigDecimal usedCreditAmount;

    //卡内余额
    @Column(name = "balance")
    private BigDecimal balance;

    //会员积分
    @Column(name = "integral")
    private Integer integral;

    //总消费额
    @Column(name = "total_consumption")
    private BigDecimal totalConsumption;

    //账户状态
    @Column(name = "status")
    private Integer status;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最后修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    /**
     * *********Getter And Setter*************
     */

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public BigDecimal getUsedCreditAmount() {
        return usedCreditAmount;
    }

    public void setUsedCreditAmount(BigDecimal usedCreditAmount) {
        this.usedCreditAmount = usedCreditAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public BigDecimal getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(BigDecimal totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) { this.lastModifiedTime = lastModifiedTime; }
}
