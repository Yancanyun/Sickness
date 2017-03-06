package com.emenu.service.stock.impl;

import com.emenu.common.entity.stock.StockItem;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.StringUtils;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.stock.ItemService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * ItemServiceImpl
 *
 * @author pengpeng
 * @time 2017/3/4 9:55
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService{

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SerialNumService serialNumService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public StockItem newItem(StockItem stockItem) throws SSException{
        // 设置物品编号和助记码
        try {
            // 物品编号
            String serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockItemNum);
            stockItem.setItemNumber(serialNumber);
            // 助记码
            if (Assert.isNull(stockItem.getAssistantCode())
                    || stockItem.getAssistantCode().equals("")){
                String assistantCode = StringUtils.str2Pinyin(stockItem.getName(),"headChar");
                stockItem.setAssistantCode(assistantCode);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        try {
            return commonDao.insert(stockItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StockItemInsertFailed, e);
        }
    }
}
