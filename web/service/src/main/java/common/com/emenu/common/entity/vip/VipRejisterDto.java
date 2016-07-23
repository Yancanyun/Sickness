package com.emenu.common.entity.vip;

import com.emenu.common.dto.vip.VipCardDto;
import com.emenu.common.entity.party.group.vip.VipInfo;

/**
 * VipRejisterDto
 *
 * @author xiaozl
 * @date: 2016/7/23
 */
public class VipRejisterDto {

    // 会员基本信息
    private VipInfo vipInfo;

    // 会员卡信息
    private VipCard vipCard;

    public VipInfo getVipInfo() {
        return vipInfo;
    }

    public void setVipInfo(VipInfo vipInfo) {
        this.vipInfo = vipInfo;
    }

    public VipCard getVipCard() {
        return vipCard;
    }

    public void setVipCard(VipCard vipCard) {
        this.vipCard = vipCard;
    }
}
