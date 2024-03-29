package com.emenu.common.entity.vip;

import com.emenu.common.enums.vip.VipCardPermanentlyEffectiveEnums;
import com.emenu.common.enums.vip.VipCardStatusEnums;
import com.emenu.common.utils.DateUtils;
import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * VipCard
 *
 * @author: yangch
 * @time: 2016/1/18 18:38
 */
@Entity
@Table(name = "t_vip_card")
public class VipCard extends AbstractEntity {
    //会员卡ID
    @Id
    private Integer id;

    //会员PartyID
    @Column(name = "vip_party_id")
    private Integer vipPartyId;

    //会员卡号
    @Column(name = "card_number")
    private String cardNumber;

    //物理卡号
    @Column(name = "physical_number")
    private String physicalNumber;

    //有效期
    @Column(name = "validity_time")
    private Date validityTime;

    //格式化后的有效期String
    @Transient
    private String validityTimeStr;

    //是否永久有效: 0-否, 1-是
    @Column(name = "permanently_effective")
    private Integer permanentlyEffective;

    //操作人PartyID
    @Column(name = "operator_party_id")
    private Integer operatorPartyId;

    //会员卡状态: 0-停用, 1-可用, 2-已挂失, 3-已删除
    private Integer status;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //格式化后的创建时间String
    @Transient
    private String createdTimeStr;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    //状态字符串
    @Transient
    private String statusStr;

    //是否永久有效字符串
    @Transient
    private String permanentlyEffectiveStr;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipPartyId() {
        return vipPartyId;
    }

    public void setVipPartyId(Integer vipPartyId) {
        this.vipPartyId = vipPartyId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPhysicalNumber() {
        return physicalNumber;
    }

    public void setPhysicalNumber(String physicalNumber) {
        this.physicalNumber = physicalNumber;
    }

    public Date getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Date validityTime) {
        this.validityTime = validityTime;

        if (validityTime != null) {
            this.validityTimeStr = DateUtils.formatDate(validityTime, "yyyy-MM-dd");
        } else {
            this.validityTimeStr = "";
        }
    }

    public Integer getPermanentlyEffective() {
        return permanentlyEffective;
    }

    public void setPermanentlyEffective(Integer permanentlyEffective) {
        this.permanentlyEffective = permanentlyEffective;
        VipCardPermanentlyEffectiveEnums vipCardPermanentlyEffectiveEnums = VipCardPermanentlyEffectiveEnums.valueOf(permanentlyEffective);
        this.setPermanentlyEffectiveStr(vipCardPermanentlyEffectiveEnums == null ? "" : vipCardPermanentlyEffectiveEnums.getType());
    }

    public Integer getOperatorPartyId() {
        return operatorPartyId;
    }

    public void setOperatorPartyId(Integer operatorPartyId) {
        this.operatorPartyId = operatorPartyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        VipCardStatusEnums statusEnums = VipCardStatusEnums.valueOf(status);
        this.setStatusStr(statusEnums == null ? "" : statusEnums.getType());
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;

        if (createdTime != null) {
            this.createdTimeStr = DateUtils.formatDate(createdTime, "yyyy-MM-dd");
        } else {
            this.createdTimeStr = "";
        }
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getValidityTimeStr() {
        return validityTimeStr;
    }

    public void setValidityTimeStr(String validityTimeStr) {
        this.validityTimeStr = validityTimeStr;
    }

    public String getCreatedTimeStr() {
        return createdTimeStr;
    }

    public void setCreatedTimeStr(String createdTimeStr) {
        this.createdTimeStr = createdTimeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getPermanentlyEffectiveStr() {
        return permanentlyEffectiveStr;
    }

    public void setPermanentlyEffectiveStr(String permanentlyEffectiveStr) {
        this.permanentlyEffectiveStr = permanentlyEffectiveStr;
    }
}
