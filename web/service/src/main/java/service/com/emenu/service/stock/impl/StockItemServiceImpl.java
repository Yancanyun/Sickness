package com.emenu.service.stock.impl;

import com.emenu.common.entity.stock.StockItem;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.StringUtils;
import com.emenu.mapper.stock.StockItemMapper;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.stock.StockItemService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * ItemServiceImpl
 *
 * @author pengpeng
 * @time 2017/3/4 9:55
 */
@Service("itemService")
public class StockItemServiceImpl implements StockItemService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SerialNumService serialNumService;

    @Autowired
    private StockItemMapper stockItemMapper;

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

    @Override
    public boolean checkIsExist(String name) throws SSException{
        try{
            return stockItemMapper.countByName(name) > 0 ? true : false;
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public String listToString(List<Integer> list) throws SSException{
        String string = new String();
        string = "";
        try{
            for(int i = 0; i < list.size(); i++)
            {
                if(i==0)
                    string += list.get(i).toString();
                else
                    string += ',' + list.get(i).toString();
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return string;
    }

    @Override
    public List<Integer> stringTolist(String string) throws SSException{
        List<Integer> list = new ArrayList<Integer>();
        try{
            int num = 0;
            for(int i=0;i<string.length();i++) {
                char a = string.charAt(i);
                if(a!=','){
                    num = num * 10 +  a - '0';
                }else{
                    list.add(num);
                    num = 0;
                }
                if(i==string.length()-1){
                    list.add(num);
                }
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return list;
    }

    @Override
    public StockItem queryById(int id) throws SSException{
        StockItem stockItem = new StockItem();
        try{
            stockItem = commonDao.queryById(stockItem.getClass(),id);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryStockItemByIdFailed, e);
        }
        return stockItem;
    }

    @Override
    public void updateStockItem(StockItem stockItem) throws SSException{
        try{
            commonDao.update(stockItem);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStockItemFailed, e);
        }
    }
}
