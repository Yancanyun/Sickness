package com.emenu.service.vip;

import com.emenu.common.dto.vip.VipRegisterDto;
import com.pandawork.core.common.exception.SSException;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员管理service
 * VipOperationService
 * @author xiaozl
 * @date: 2016/7/23
 */
public interface VipOperationService {

    /**
     * 注册会员
     * @param name
     * @param sex
     * @param phone
     * @param birthday
     * @param validityTime
     * @param permanentlyEffective
     * @param operatorPartyId
     * @return
     * @throws SSException
     */
    public VipRegisterDto registerVip(String name,
                                      Integer sex,
                                      String phone,
                                      Date birthday,
                                      Date validityTime,
                                      Integer permanentlyEffective,
                                      Integer operatorPartyId) throws SSException;

    /**
     * 根据会员电话、物理卡号、会员号搜索
     * @param keyword
     * @return
     * @throws SSException
     */
    public VipRegisterDto queryByKeyword(String keyword) throws SSException;

    /**
     * 会员充值
     * @param vipPartyId
     * @param rechargePlanId
     * @param rechargeAmount
     * @param payAmount
     * @param operator
     * @param paymentType
     * @throws SSException
     */
    public void rechargeByVipPartyId(Integer vipPartyId,
                                     Integer rechargePlanId,
                                     BigDecimal rechargeAmount,
                                     BigDecimal payAmount,
                                     Integer operator,
                                     Integer paymentType) throws SSException;

}
