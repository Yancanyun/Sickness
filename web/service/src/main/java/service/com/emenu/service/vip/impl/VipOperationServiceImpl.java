package com.emenu.service.vip.impl;

import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.entity.vip.VipAccountInfo;
import com.emenu.common.entity.vip.VipCard;
import com.emenu.common.entity.vip.VipRegisterDto;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.party.group.vip.VipInfoService;
import com.emenu.service.vip.VipAccountInfoService;
import com.emenu.service.vip.VipCardService;
import com.emenu.service.vip.VipOperationService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.bean.StaticAutoWire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * VipOperationServiceImpl
 *
 * @author xiaozl
 * @date: 2016/7/23
 */
@Service("vipOperationService")
public class VipOperationServiceImpl implements VipOperationService{


    @Autowired
    private VipInfoService vipInfoService;

    @Autowired
    private VipCardService vipCardService;

    @Autowired
    private VipAccountInfoService vipAccountInfoService;

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public VipRegisterDto registerVip(String name, Integer sex, String phone, Date birthday, Date validityTime, Integer permanentlyEffective, Integer operatorPartyId) throws SSException {
        VipRegisterDto vipRejisterDto = new VipRegisterDto();
        try {
            // 会员基本信息
            VipInfo vipInfo = new VipInfo();
            vipInfo.setName(name);
            vipInfo.setSex(sex);
            vipInfo.setPhone(phone);
            vipInfo.setBirthday(birthday);
            VipInfo vipInfo1= vipInfoService.newVipInfo(operatorPartyId,vipInfo);
            Assert.isNotNull(vipInfo1,EmenuException.InsertVipInfoFail);
            vipRejisterDto.setVipInfo(vipInfo1);

            // 会员卡信息
            VipCard vipCard = new VipCard();
            vipCard.setValidityTime(validityTime);
            vipCard.setPermanentlyEffective(permanentlyEffective);
            vipCard.setOperatorPartyId(operatorPartyId);
            vipCard.setVipPartyId(vipInfo.getPartyId());
            VipCard vipCard1 = vipCardService.newVipCard(vipCard);
            Assert.isNotNull(vipCard1,EmenuException.InsertVipCardFail);
            vipRejisterDto.setVipCard(vipCard1);

            // 会员账户信息
            VipAccountInfo vipAccountInfo = new VipAccountInfo();
            vipAccountInfo.setPartyId(vipInfo.getPartyId());
            VipAccountInfo vipAccountInfo1 = vipAccountInfoService.newVipAccountInfo(vipAccountInfo);
            Assert.isNotNull(vipAccountInfo1,EmenuException.NewVipAccountInfoFailed);
            vipRejisterDto.setVipAccountInfo(vipAccountInfo1);

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.RejisterVipFail, e);
        }
        return vipRejisterDto;
    }
}
