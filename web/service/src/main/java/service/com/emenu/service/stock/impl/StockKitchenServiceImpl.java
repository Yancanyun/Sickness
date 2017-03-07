package com.emenu.service.stock.impl;

import com.emenu.common.entity.stock.StockKitchen;
import com.emenu.mapper.stock.StockKitchenMapper;
import com.emenu.service.stock.StockKitchenService;
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
@Service
public class StockKitchenServiceImpl implements StockKitchenService{
    @Autowired
    private StockKitchenMapper stockKitchenMapper;

    /**
     * 查看厨房列表
     * @return
     * @throws Exception
     */
    @Override
    public List<StockKitchen> listStockKitchen()throws Exception{
        List<StockKitchen> stockKitchenList = new ArrayList<StockKitchen>();
        try{
            stockKitchenList = stockKitchenMapper.listStockKitchen();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return stockKitchenList;
    }

    /**
     * 添加厨房
     *
     * @param stockKitchen
     * @throws Exception
     */
    @Override
    public void addStockKitchen(StockKitchen stockKitchen)throws Exception{
        try{
            stockKitchenMapper.addStockKitchen(stockKitchen);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 修改厨房
     *
     * @param stockKitchen
     * @throws Exception
     */
    @Override
    public void updateStockKitchen(int id,StockKitchen stockKitchen)throws Exception{
        try{
            stockKitchenMapper.updateStockKitchen(id,stockKitchen);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 删除厨房（将厨房设为停用）
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void updateStockKitchenStatus(int id,int status)throws Exception{
        try{
            stockKitchenMapper.updateStockKitchenStatus(id,status);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 查看厨房明细信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public StockKitchen queryStockKitchenDetails(int id)throws Exception{
        StockKitchen stockKitchen = new StockKitchen();
        try{
            stockKitchen = stockKitchenMapper.queryStockKitchenDetails(id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return stockKitchen;
    }
}
