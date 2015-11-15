package com.emenu.service.storage.impl;

import com.emenu.common.entity.storage.Settlement;
import com.emenu.common.entity.storage.SettlementItem;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.storage.SettlementItemService;
import com.emenu.service.storage.StorageItemService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * SettlementItemServiceImpl
 * 库存结算
 * @author dujuan
 * @date 2015/11/15
 */
public class SettlementItemServiceImpl implements SettlementItemService{

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private SerialNumService serialNumService;

    @Autowired
    private StorageItemService storageItemService;

    @Override
    public void newSettlementItem() throws SSException {
        try {
            //添加一条结算数据
            //第一步：t_storage_settlement
            Settlement settlement = new Settlement();
            settlement.setSerialNumber(serialNumService.generateSerialNum(SerialNumTemplateEnums.SettlementSerialNum));
            Integer settlementId = commonDao.insert(settlement).getId();
            //第二步：t_storage_settlement_item
            List<StorageItem> storageItemList = storageItemService.listAll();
            SettlementItem settlementItem = new SettlementItem();



            //第三步：把结算过的单据状态改为已结算

        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertStorageSettlementItemFailed, e);
        }

    }
}
