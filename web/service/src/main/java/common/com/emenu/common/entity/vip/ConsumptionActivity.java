package com.emenu.common.entity.vip;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ConsumptionActivity
 * 充值消费记录实体
 *
 * @author Wang LM
 * @date 2016/1/18 20:07
 */
@Entity
@Table(name = "t_vip_consumption_activity")
public class ConsumptionActivity extends AbstractEntity{

    @Id
    private Integer id;

    //会员partyId
    @Column(name = "partyId")
    private Integer partyId;

    //充值方案id
    @Column(name = "recharge_plan_id")
    private Integer rechargePlanId;

    //多倍积分方案id
    @Column(name = "multiple_integral_plan_id")
    private Integer multipleIntegralPlanId;

    //原有金额
    @Column(name = "original_amount")
    private BigDecimal originalAmount;

    //卡内余额
    @Column(name = "residual_amount")
    private BigDecimal residualAmount;

    //消费金额
    @Column(name = "consumption_amount")
    private BigDecimal consumptionAmount;

    //实付金额
    @Column(name = "actual_payment")
    private BigDecimal actualPayment;

    //原有积分
    @Column(name = "original_integral")
    private Integer originalIntegral;

    //积分变化
    @Column(name = "integral_change")
    private Integer integralChange;

    //操作人
    private String operator;

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

    public Integer getRechargePlanId() {
        return rechargePlanId;
    }

    public void setRechargePlanId(Integer rechargePlanId) {
        this.rechargePlanId = rechargePlanId;
    }

    public Integer getMultipleIntegralPlanId() {
        return multipleIntegralPlanId;
    }

    public void setMultipleIntegralPlanId(Integer multipleIntegralPlanId) {
        this.multipleIntegralPlanId = multipleIntegralPlanId;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getResidualAmount() {
        return residualAmount;
    }

    public void setResidualAmount(BigDecimal residualAmount) {
        this.residualAmount = residualAmount;
    }

    public BigDecimal getConsumptionAmount() {
        return consumptionAmount;
    }

    public void setConsumptionAmount(BigDecimal consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
    }

    public BigDecimal getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(BigDecimal actualPayment) {
        this.actualPayment = actualPayment;
    }

    public Integer getOriginalIntegral() {
        return originalIntegral;
    }

    public void setOriginalIntegral(Integer originalIntegral) {
        this.originalIntegral = originalIntegral;
    }

    public Integer getIntegralChange() {
        return integralChange;
    }

    public void setIntegralChange(Integer integralChange) {
        this.integralChange = integralChange;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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
