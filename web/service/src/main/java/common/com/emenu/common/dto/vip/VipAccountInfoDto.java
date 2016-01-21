package com.emenu.common.dto.vip;

import java.math.BigDecimal;

/**
 * 会员账户信息Dto
 *
 * @author xubr
 * @date 2016/1/18.
 */
public class VipAccountInfoDto {

    //会员账户Id
    private Integer id;

    //会员等级
    private String vipGrade;

    //当事人姓名
    private String name;

    //电话
    private String phone;

    //卡号
    private String cardNumber;

    //卡内余额
    private BigDecimal balance;

    //会员积分
    private Integer integral;

    //最低消费额
    private BigDecimal minConsumption;

    //总消费额
    private BigDecimal totalConsumption;

    //已挂账金额
    private BigDecimal usedCreditAmount;

    //会员账户状态
    private Integer status;

    /***********************Getter And Setter***********************/

    public BigDecimal getUsedCreditAmount() { return usedCreditAmount; }

    public void setUsedCreditAmount(BigDecimal usedCreditAmount) { this.usedCreditAmount = usedCreditAmount; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getVipGrade() { return vipGrade; }

    public void setVipGrade(String vipGrade) { this.vipGrade = vipGrade; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getCardNumber() { return cardNumber; }

    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public BigDecimal getBalance() { return balance; }

    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public Integer getIntegral() { return integral; }

    public void setIntegral(Integer integral) { this.integral = integral; }

    public BigDecimal getMinConsumption() { return minConsumption; }

    public void setMinConsumption(BigDecimal minConsumption) { this.minConsumption = minConsumption; }

    public BigDecimal getTotalConsumption() { return totalConsumption; }

    public void setTotalConsumption(BigDecimal totalConsumption) { this.totalConsumption = totalConsumption; }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }
}
