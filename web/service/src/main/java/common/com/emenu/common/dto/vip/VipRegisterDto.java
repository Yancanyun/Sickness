package com.emenu.common.dto.vip;

import com.emenu.common.dto.vip.VipCardDto;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.entity.vip.VipAccountInfo;
import com.emenu.common.entity.vip.VipCard;

/**
 * VipRejisterDto
 *
 * @author xiaozl
 * @date: 2016/7/23
 */
public class VipRegisterDto {

    // 会员基本信息
    private VipInfo vipInfo;

    // 会员卡信息
    private VipCard vipCard;

    // 会员账户信息
    private VipAccountInfo vipAccountInfo;

    // 会员等级
    private VipGradeDto vipGradeDto;

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

    public VipAccountInfo getVipAccountInfo() {
        return vipAccountInfo;
    }

    public void setVipAccountInfo(VipAccountInfo vipAccountInfo) {
        this.vipAccountInfo = vipAccountInfo;
    }

    public VipGradeDto getVipGradeDto() {
        return vipGradeDto;
    }

    public void setVipGradeDto(VipGradeDto vipGradeDto) {
        this.vipGradeDto = vipGradeDto;
    }
}
