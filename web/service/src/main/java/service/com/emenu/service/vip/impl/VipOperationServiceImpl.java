package com.emenu.service.vip.impl;

import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.entity.vip.VipCard;
import com.emenu.common.entity.vip.VipRejisterDto;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.party.group.vip.VipInfoService;
import com.emenu.service.vip.VipCardService;
import com.emenu.service.vip.VipOperationService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
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

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public VipRejisterDto registerVip(String name, Integer sex, String phone, Date birthday, Date validityTime, Integer permanentlyEffective, Integer operatorPartyId) throws SSException {
        VipRejisterDto vipRejisterDto = new VipRejisterDto();
        try {
            // 会员基本信息
            VipInfo vipInfo = new VipInfo();
            vipInfo.setName(name);
            vipInfo.setSex(sex);
            vipInfo.setPhone(phone);
            vipInfo.setBirthday(birthday);
            vipRejisterDto.setVipInfo(vipInfoService.newVipInfo(operatorPartyId,vipInfo));

            // 会员卡信息
            VipCard vipCard = new VipCard();
            vipCard.setValidityTime(validityTime);
            vipCard.setPermanentlyEffective(permanentlyEffective);
            vipCard.setOperatorPartyId(operatorPartyId);
            vipCard.setVipPartyId(vipInfo.getPartyId());
            vipRejisterDto.setVipCard(vipCardService.newVipCard(vipCard));
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.RejisterVipFail, e);
        }
        return vipRejisterDto;
    }
}
