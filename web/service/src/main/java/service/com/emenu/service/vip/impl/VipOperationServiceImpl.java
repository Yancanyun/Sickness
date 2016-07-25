package com.emenu.service.vip.impl;

import com.emenu.common.dto.vip.VipAccountInfoDto;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.entity.vip.ConsumptionActivity;
import com.emenu.common.entity.vip.VipAccountInfo;
import com.emenu.common.entity.vip.VipCard;
import com.emenu.common.dto.vip.VipRegisterDto;
import com.emenu.common.entity.vip.VipRechargePlan;
import com.emenu.common.enums.vip.ConsumptionActivityTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.service.party.group.vip.VipInfoService;
import com.emenu.service.vip.VipAccountInfoService;
import com.emenu.service.vip.VipCardService;
import com.emenu.service.vip.VipOperationService;
import com.emenu.service.vip.VipRechargePlanService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private VipRechargePlanService vipRechargePlanService;

    @Autowired
    private VipAccountInfoService vipAccountInfoService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CommonDao commonDao;

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public VipRegisterDto registerVip(String name, Integer sex, String phone, Date birthday, Date validityTime, Integer permanentlyEffective, Integer operatorPartyId) throws SSException {
        VipRegisterDto vipRegisterDto = new VipRegisterDto();
        try {
            // 会员基本信息
            VipInfo vipInfo = new VipInfo();
            vipInfo.setName(name);
            vipInfo.setSex(sex);
            vipInfo.setPhone(phone);
            vipInfo.setBirthday(birthday);
            VipInfo vipInfo1= vipInfoService.newVipInfo(operatorPartyId,vipInfo);
            Assert.isNotNull(vipInfo1,EmenuException.InsertVipInfoFail);
            vipRegisterDto.setVipInfo(vipInfo1);

            // 会员卡信息
            VipCard vipCard = new VipCard();
            vipCard.setValidityTime(validityTime);
            vipCard.setPermanentlyEffective(permanentlyEffective);
            vipCard.setOperatorPartyId(operatorPartyId);
            vipCard.setVipPartyId(vipInfo.getPartyId());
            VipCard vipCard1 = vipCardService.newVipCard(vipCard);
            Assert.isNotNull(vipCard1,EmenuException.InsertVipCardFail);
            vipRegisterDto.setVipCard(vipCard1);

            // 会员账户信息
            VipAccountInfo vipAccountInfo = new VipAccountInfo();
            vipAccountInfo.setPartyId(vipInfo.getPartyId());
            VipAccountInfo vipAccountInfo1 = vipAccountInfoService.newVipAccountInfo(vipAccountInfo);
            Assert.isNotNull(vipAccountInfo1,EmenuException.NewVipAccountInfoFailed);
            vipRegisterDto.setVipAccountInfo(vipAccountInfo1);

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.RejisterVipFail, e);
        }
        return vipRegisterDto;
    }

    @Override
    public VipRegisterDto queryByKeyword(String keyword) throws SSException {
        try {
            VipInfo vipInfo = vipInfoService.queryByKeyWord(keyword);
            Assert.isNotNull(vipInfo,EmenuException.VipInfoNotExist);
            VipCard vipCard = vipCardService.queryByPartyId(vipInfo.getPartyId());
            Assert.isNotNull(EmenuException.VipCardNotExist);
            VipAccountInfo vipAccountInfo = vipAccountInfoService.queryByPartyId(vipInfo.getPartyId());
            VipRegisterDto vipRegisterDto = new VipRegisterDto();
            vipRegisterDto.setVipInfo(vipInfo);
            vipRegisterDto.setVipCard(vipCard);
            vipRegisterDto.setVipAccountInfo(vipAccountInfo);
            return vipRegisterDto;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void rechargeByVipPartyId(Integer vipPartyId, Integer rechargePlanId, BigDecimal rechargeAmount, BigDecimal payAmount, Integer operator) throws SSException{
        try{
            if (Assert.lessOrEqualZero(vipPartyId)
                    || Assert.lessOrEqualZero(operator)){
                throw SSException.get(EmenuException.PartyIdError);
            }
            if (Assert.lessOrEqualZero(rechargePlanId)){
                throw SSException.get(EmenuException.RechargePlanIdError);
            }
            // 查询充值方案
            VipRechargePlan vipRechargePlan = vipRechargePlanService.queryById(rechargePlanId);
            // 查询会员账户信息
            VipAccountInfo vipAccountInfo = vipAccountInfoService.queryByPartyId(vipPartyId);

            // 添加充值记录
            ConsumptionActivity consumptionActivity = new ConsumptionActivity();
            consumptionActivity.setPartyId(vipPartyId);//partyId
            consumptionActivity.setRechargePlanId(rechargePlanId);// 充值方案id
            consumptionActivity.setOriginalAmount(vipAccountInfo.getBalance());//原有金额
            consumptionActivity.setResidualAmount(vipAccountInfo.getBalance().add(rechargeAmount));// 账户余额=原有金额+充值金额
            consumptionActivity.setActualPayment(payAmount);// 实际付款
            consumptionActivity.setType(ConsumptionActivityTypeEnums.Recharge.getId());//类型：充值
            consumptionActivity.setOperator(employeeService.queryByPartyId(operator).getName());// 操作人姓名
            commonDao.insert(consumptionActivity);

            // 修改账户信息
            vipAccountInfo.setBalance(vipAccountInfo.getBalance().add(rechargeAmount));// 卡内余额=原有金额+充值金额
            vipAccountInfo.setTotalConsumption(vipAccountInfo.getTotalConsumption().add(rechargeAmount));// 总消费金额=原有总消费金额+充值金额
            commonDao.update(vipAccountInfo);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }
}
