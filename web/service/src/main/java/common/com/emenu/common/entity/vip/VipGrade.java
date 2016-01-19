package com.emenu.common.entity.vip;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * VipGrade
 * 会员等级方案实体
 *
 * @author Wang Liming
 * @date 2015/12/14 15:04
 */

@Entity
@Table(name = "t_vip_grade")
public class VipGrade extends AbstractEntity{

    @Id
    private Integer id;

    //会员等级方案名称
    private String name;

    //会员价方案id
    @Column(name = "vip_dish_price_plan_id")
    private Integer vipDishPricePlanId;

    //最低消费金额
    @Column(name = "min_consumption")
    private BigDecimal minConsumption;

    //信用额度
    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    //结算周期（按月）
    @Column(name = "settlement_cycle")
    private Integer settlementCycle;

    //升级预提醒额度
    @Column(name = "pre_reminder_amount")
    private BigDecimal preReminderAmount;

    //积分启用状态,0-停用,1-启用
    @Column(name = "integral_enable_state")
    private Integer integralEnableState;

    //卡片政策
    @Column(name = "card_policy")
    private String cardPolicy;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVipDishPricePlanId() {
        return vipDishPricePlanId;
    }

    public void setVipDishPricePlanId(Integer vipDishPricePlanId) {
        this.vipDishPricePlanId = vipDishPricePlanId;
    }

    public BigDecimal getMinConsumption() {
        return minConsumption;
    }

    public void setMinConsumption(BigDecimal minConsumption) {
        this.minConsumption = minConsumption;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Integer getSettlementCycle() {
        return settlementCycle;
    }

    public void setSettlementCycle(Integer settlementCycle) {
        this.settlementCycle = settlementCycle;
    }

    public BigDecimal getPreReminderAmount() {
        return preReminderAmount;
    }

    public void setPreReminderAmount(BigDecimal preReminderAmount) {
        this.preReminderAmount = preReminderAmount;
    }

    public Integer getIntegralEnableState() {
        return integralEnableState;
    }

    public void setIntegralEnableState(Integer integralEnableState) {
        this.integralEnableState = integralEnableState;
    }

    public String getCardPolicy() {
        return cardPolicy;
    }

    public void setCardPolicy(String cardPolicy) {
        this.cardPolicy = cardPolicy;
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
