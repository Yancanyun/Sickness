package com.emenu.service.vip.impl;

import com.emenu.common.dto.vip.VipConfigDto;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.service.other.ConstantService;
import com.emenu.service.vip.VipConfigService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员基本配置ServiceImpl
 *
 * @author chenyuting
 * @date 2015/11/16 16:20
 */
@Service("vipConfigService")
public class VipConfigServiceImpl implements VipConfigService {

    @Autowired
    private ConstantService constantService;

    public void updateVipConfig(VipConfigDto vipConfigDto) throws SSException{
        //现金兑换积分
        if (!Assert.isNull(vipConfigDto.getCashToIntegral())){
            constantService.updateValueByKey(ConstantEnum.VipIntegralRuleCashToIntegral.getKey(),
                    vipConfigDto.getCashToIntegral().toString());
        }

        //刷卡兑换积分
        if (!Assert.isNull(vipConfigDto.getCardToIntegral())){
            constantService.updateValueByKey(ConstantEnum.VipIntegralRuleCardToIntegral.getKey(),
                    vipConfigDto.getCardToIntegral().toString());
        }

        //在线支付兑换积分
        if (!Assert.isNull(vipConfigDto.getOnlineToIntegral())){
            constantService.updateValueByKey(ConstantEnum.VipIntegralRuleOnlineToIntegral.getKey(),
                    vipConfigDto.getOnlineToIntegral().toString());
        }

        //积分兑换现金
        if (!Assert.isNull(vipConfigDto.getIntegralToMoney())){
            constantService.updateValueByKey(ConstantEnum.VipIntegralRuleIntegralToMoney.getKey(),
                    vipConfigDto.getIntegralToMoney().toString());
        }

        //完善信息赠送积分
        if (!Assert.isNull(vipConfigDto.getCompleteInfoIntegral())){
            constantService.updateValueByKey(ConstantEnum.VipIntegralRuleIntegralToMoney.getKey(),
                    vipConfigDto.getIntegralToMoney().toString());
        }

        //启动多倍积分方式:1-自动；2-手动
        if (!Assert.isNull(vipConfigDto.getMeansOfStartIntegral())){
            constantService.updateValueByKey(ConstantEnum.VipIntegralRuleMeansOfStartIntegral.getKey(),
                    vipConfigDto.getMeansOfStartIntegral().toString());
        }

        //是否自动升级会员等级：0-否；1-是
        if (!Assert.isNull(vipConfigDto.getVipAutoUpgradeState())){
            constantService.updateValueByKey(ConstantEnum.VipGradeVipAutoUpgradeState.getKey(),
                    vipConfigDto.getVipAutoUpgradeState().toString());
        }
    }

    public VipConfigDto queryVipConfig() throws SSException{

        VipConfigDto vipConfigDto = new VipConfigDto();

        //现金兑换积分
        Integer cashToIntegral = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.VipIntegralRuleCashToIntegral.getKey()));
        vipConfigDto.setCashToIntegral(cashToIntegral);

        //刷卡兑换积分
        Integer cardToIntegral = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.VipIntegralRuleCardToIntegral.getKey()));
        vipConfigDto.setCardToIntegral(cardToIntegral);

        //在线支付兑换积分
        Integer onlineToIntegral = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.VipIntegralRuleOnlineToIntegral.getKey()));
        vipConfigDto.setOnlineToIntegral(onlineToIntegral);

        //积分兑换现金
        Integer integralToMoney = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.VipIntegralRuleIntegralToMoney.getKey()));
        vipConfigDto.setIntegralToMoney(integralToMoney);

        //完善信息赠送积分
        Integer completeInfoIntegral = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.VipIntegralRuleCompleteInfoIntegral.getKey()));
        vipConfigDto.setCompleteInfoIntegral(completeInfoIntegral);

        //启动多倍积分方式:1-自动；2-手动
        Integer meansOfStartIntegral = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.VipIntegralRuleMeansOfStartIntegral.getKey()));
        vipConfigDto.setMeansOfStartIntegral(meansOfStartIntegral);

        //是否自动升级会员等级：0-否；1-是
        Integer vipAutoUpgradeState = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.VipGradeVipAutoUpgradeState.getKey()));
        vipConfigDto.setVipAutoUpgradeState(vipAutoUpgradeState);

        return vipConfigDto;
    }
}