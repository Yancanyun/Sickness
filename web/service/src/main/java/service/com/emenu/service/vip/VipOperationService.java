package com.emenu.service.vip;

import com.emenu.common.entity.vip.VipRejisterDto;
import com.pandawork.core.common.exception.SSException;

import java.util.Date;

/**
 * VipOperationService
 *
 * @author xiaozl
 * @date: 2016/7/23
 */
public interface VipOperationService {


    public VipRejisterDto registerVip(String name,
                                      Integer sex,
                                      String phone,
                                      Date birthday,
                                      Date validityTime,
                                      Integer permanentlyEffective,
                                      Integer operatorPartyId) throws SSException;

}
