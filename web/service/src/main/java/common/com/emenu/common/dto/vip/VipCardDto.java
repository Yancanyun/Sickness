package com.emenu.common.dto.vip;

import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.entity.vip.VipCard;

/**
 * VipCardDto
 *
 * @author: yangch
 * @time: 2016/1/19 13:34
 */
public class VipCardDto {
    private VipCard vipCard;
    private VipInfo vipInfo;
    private String operator;

    public VipCard getVipCard() {
        return vipCard;
    }

    public void setVipCard(VipCard vipCard) {
        this.vipCard = vipCard;
    }

    public VipInfo getVipInfo() {
        return vipInfo;
    }

    public void setVipInfo(VipInfo vipInfo) {
        this.vipInfo = vipInfo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
