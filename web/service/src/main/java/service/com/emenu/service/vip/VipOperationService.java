package com.emenu.service.vip;

import com.emenu.common.entity.vip.VipRegisterDto;
import com.pandawork.core.common.exception.SSException;

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

}
