package com.emenu.service.stock.impl;

import com.emenu.common.entity.stock.StockKitchen;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.stock.StockKitchenMapper;
import com.emenu.service.stock.StockKitchenService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * StockKitchenServiceImpl
 *
 * @author nigbo
 * @date 2017/3/6 15:55
 */
@Service("stockKitchenService")
public class StockKitchenServiceImpl implements StockKitchenService{
    @Autowired
    private StockKitchenMapper stockKitchenMapper;

    /**
     * 查看厨房列表
     * @return
     * @throws SSException
     */
    @Override
    public List<StockKitchen> listStockKitchen()throws SSException {
        List<StockKitchen> stockKitchenList = new ArrayList<StockKitchen>();
        try{
            stockKitchenList = stockKitchenMapper.listStockKitchen();
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListKitchenFailed);
        }
        return stockKitchenList;
    }

    /**
     * 添加厨房
     *
     * @param stockKitchen
     * @throws SSException
     */
    @Override
    public void addStockKitchen(StockKitchen stockKitchen)throws SSException{
        try{
            stockKitchenMapper.addStockKitchen(stockKitchen);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.AddKitchenFailed);
        }
    }

    /**
     * 修改厨房
     *
     * @param stockKitchen
     * @throws SSException
     */
    @Override
    public void updateStockKitchen(int id,StockKitchen stockKitchen)throws SSException{
        try{
            stockKitchenMapper.updateStockKitchen(id,stockKitchen);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateKitchenFailed);
        }
    }

    /**
     * 删除厨房（将厨房设为停用）
     *
     * @param id
     * @throws SSException
     */
    @Override
    public void updateStockKitchenStatus(int id,int status)throws SSException{
        try{
            stockKitchenMapper.updateStockKitchenStatus(id,status);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteKitchenFailed);
        }
    }

    /**
     * 查看厨房明细信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public StockKitchen queryStockKitchenDetails(int id)throws SSException{
        StockKitchen stockKitchen = new StockKitchen();
        try{
            stockKitchen = stockKitchenMapper.queryStockKitchenDetails(id);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryKitchenDetailsFailed);
        }
        return stockKitchen;
    }
}
