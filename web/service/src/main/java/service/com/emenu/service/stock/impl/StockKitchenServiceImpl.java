package com.emenu.service.stock.impl;

import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.stock.StockKitchen;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.stock.StockKitchenMapper;
import com.emenu.service.dish.UnitService;
import com.emenu.service.stock.StockKitchenService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
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

    @Autowired
    private UnitService unitService;

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
     * 查询分页信息
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    @Override
    public List<StockKitchen> listByPage(int offset, int pageSize) throws SSException{
        try{
            if(Assert.isNull(offset)){
                throw SSException.get(EmenuException.OffsetIsNotNull);
            }
            List<StockKitchen> list = stockKitchenMapper.listByPage(offset,pageSize);
            return list;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListByPageFailed, e);
        }
    }

    /**
     * 添加厨房
     *
     * @param stockKitchen
     * @throws SSException
     */
    @Override
    public void addStockKitchen(StockKitchen stockKitchen)throws SSException{
        //检查存放点名称是否已存在
        if(checkNameIsExist(stockKitchen.getName())) {
            throw SSException.get(EmenuException.KitchenNameIsExist);
        }
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
        //检查厨房名称是否和其他厨房名称冲突
        if(checkNameIsExist(stockKitchen.getName())) {
            throw SSException.get(EmenuException.KitchenNameIsExist);
        }
        try{
            stockKitchenMapper.updateStockKitchen(id,stockKitchen);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateKitchenFailed);
        }
    }

    /**
     * 将厨房设为停用或重新启用厨房
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
     * 根据id查看厨房明细信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public StockKitchen queryById(int id)throws SSException{
        StockKitchen stockKitchen ;
        try{
            stockKitchen = stockKitchenMapper.queryStockKitchenDetails(id);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryKitchenDetailsFailed);
        }
        return stockKitchen;
    }

    /**
     * 检查厨房名称是否已存在
     *
     * @param name
     * @return
     * @throws SSException
     */
    @Override
    public Boolean checkNameIsExist(String name)throws SSException{
        int count = 0;
        try{
            count = stockKitchenMapper.checkNameIsExist(name);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckKitchenNameFailed);
        }
        return count == 0 ? false : true;
    }

    /**
     * 判断存放点是否是总库，1为总库
     * @param id
     * @return
     * @throws SSException
     */
    public Boolean  checkType(int id)throws SSException{
        int type;
        try{
            type = stockKitchenMapper.queryType(id);
            if(type == 1){
                return true;
            }
            else{
                return false;
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckTypeFailed);
        }
    }

    /**
     * 数据量（即有多少个厨房）
     * @return
     * @throws SSException
     */
    public Integer count()throws SSException {
        try{
            return stockKitchenMapper.count();
        }catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountError, e);
        }
    }
}
