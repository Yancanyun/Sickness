package com.emenu.service.stock.impl;


import com.emenu.common.entity.stock.StockItemDetail;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.stock.StockItemDetailService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * StockItemDetailServiceImpl
 *
 * @author renhongshuai
 * @Time 2017/3/10 8:47.
 */
@Service("stockItemDetailService")
public class StockItemDetailServiceImpl implements StockItemDetailService{

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newStockItemDetail(StockItemDetail stockItemDetail) throws SSException{
        try{
            if(Assert.isNotNull(stockItemDetail)){
              commonDao.insert(stockItemDetail);
            }else{
                throw SSException.get(EmenuException.ItemDetailIsNotNull);
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertItemDetailFail);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void deleteStockItemDetailById(int id)throws SSException{
        try{
            StockItemDetail stockItemDetail = new StockItemDetail();
            if(!Assert.lessOrEqualZero(id)){
                commonDao.deleteById(stockItemDetail.getClass(),id);
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelItemDetailFail);
        }
    }
}
