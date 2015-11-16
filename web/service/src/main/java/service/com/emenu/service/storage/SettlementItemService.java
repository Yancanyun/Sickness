package com.emenu.service.storage;

import com.pandawork.core.common.exception.SSException;

/**
 * SettlementItemService
 * 库存结算
 * @author dujuan
 * @date 2015/11/15
 */
public interface SettlementItemService {

    /**
     * 添加结算
     * 根据结算周期数，定期结算
     * @throws SSException
     */
    public void newSettlementItem() throws SSException;


}
