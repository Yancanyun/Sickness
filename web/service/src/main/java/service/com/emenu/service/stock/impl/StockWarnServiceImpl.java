package com.emenu.service.stock.impl;

import com.emenu.common.dto.stock.StockWarnDto;
import com.emenu.common.entity.stock.StockWarn;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.stock.StockWarnMapper;
import com.emenu.service.stock.StockWarnService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * StockWarnServiceImpl
 *
 * @author yuzhengfei
 * @date 2017/3/15 15:44
 */
@Service("StockWarnService")
public class StockWarnServiceImpl implements StockWarnService {

    @Autowired
    public CommonDao commonDao;

    @Autowired
    public StockWarnMapper stockWarnMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public StockWarn newWarn(StockWarn stockWarn) throws Exception{
        try{
            return commonDao.insert(stockWarn);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.AddStockWarnFail,e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStateToResolvedByItemId(int itemId) throws Exception{
        try {
            if(Assert.isNull(itemId) || Assert.lessOrEqualZero(itemId)){
                throw SSException.get(EmenuException.UpdateStateToResolveFail);
            }
            stockWarnMapper.updateStateToResolvedByItemId(itemId);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStateToResolveFail,e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStateToIgnoredByItemId(int itemId) throws Exception{
        try{
            if(Assert.isNull(itemId) || Assert.lessOrEqualZero(itemId)){
                throw SSException.get(EmenuException.UpdateStateToIgnoreFail);
            }
            stockWarnMapper.updateStateToIgnoreByItemId(itemId);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStateToIgnoreFail,e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public List<StockWarnDto> queryAllUntreatedWarn(int kitchenId) throws Exception{
        try {
            if(Assert.isNull(kitchenId) || Assert.lessOrEqualZero(kitchenId)){
                throw SSException.get(EmenuException.QueryAllUntreatedWarnFail);
            }
            return stockWarnMapper.queryAllUntreatedWarn(kitchenId);

        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryAllUntreatedWarnFail,e);
        }

    }

}
