package com.emenu.service.stock.impl;

import com.emenu.common.entity.stock.StockDocumentsItem;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.stock.StockDocumentsItemMapper;
import com.emenu.service.stock.StockDocumentsItemService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * StockDocumentsItemServiceImpl
 * 单据明细实现类
 *
 * @author renhongshuai
 * @Time 2017/3/8 15:10.
 */
@Service("stockDocumentsItemService")
public class StockDocumentsItemServiceImpl implements StockDocumentsItemService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private StockDocumentsItemMapper stockDocumentsItemMapper;

    /**
     * 添加单据明细
     *
     * @param stockDocumentsItem
     * @return
     * @throws SSException
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public StockDocumentsItem newDocumentsItem(StockDocumentsItem stockDocumentsItem) throws SSException{
        try {
            if(Assert.isNull(stockDocumentsItem)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            if(!checkStockDocumentsItemBeforeSave(stockDocumentsItem)){
                return null;
            }
            return commonDao.insert(stockDocumentsItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertReportItemFail, e);
        }
    }

    /**
     * 保存StorageReportItem检查关键字段是否合法
     *
     * @param stockDocumentsItem
     * @return
     * @throws SSException
     */

    private boolean checkStockDocumentsItemBeforeSave(StockDocumentsItem stockDocumentsItem) throws SSException{
        if (Assert.isNull(stockDocumentsItem)){
            return false;
        }
        //单据详情数量字段
        Assert.isNotNull(stockDocumentsItem.getQuantity(),EmenuException.QuantityError);
        //单据详情成本价
        Assert.isNotNull(stockDocumentsItem.getPrice(), EmenuException.PriceError);
        if (!Assert.isNull(stockDocumentsItem.getDocumentsId())){
            Assert.lessOrEqualZero(stockDocumentsItem.getDocumentsId(),EmenuException.ReportIdError);
        }
        return true;
    }

    /**
     * 根据单据id删除单据详情
     *
     * @param documentsId
     * @return
     * @throws SSException
     */
    @Override
    public boolean delByDocumentsId(int documentsId) throws SSException{
        try{
            if(Assert.lessOrEqualZero(documentsId)){
                throw SSException.get(EmenuException.DocumentsIdError);
            }
            stockDocumentsItemMapper.delByDocumentsId(documentsId);
            return true;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelStockDocumentsItemFailed, e);
        }
    }

    /**
     * 根据单据id获取该单据下的所有物品详情
     *
     * @param documentsId
     * @return
     * @throws SSException
     */
    @Override
    public List<StockDocumentsItem> queryByDocumentsId(int documentsId) throws SSException{
        List<StockDocumentsItem> stockDocumentsItems = new ArrayList<StockDocumentsItem>();
        try{
            if(Assert.lessOrEqualZero(documentsId)){
                throw SSException.get(EmenuException.DocumentsIdError);
            }
            stockDocumentsItems = stockDocumentsItemMapper.queryByDocumentsId(documentsId);
            return stockDocumentsItems;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw  SSException.get(EmenuException.QueryDocumentsItemByDocumentsIdError,e);
        }
    }
}
