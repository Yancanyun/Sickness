package com.emenu.service.stock;

import com.emenu.common.entity.stock.StockKitchen;

import java.util.List;

/**
 * StockKitchenService
 *
 * @author nigbo
 * @date 2017/3/6 15:53
 */
public interface StockKitchenService {
    /**
     * 查看厨房管理列表
     *
     * @return
     * @throws Exception
     */
    public List<StockKitchen> listStockKitchen()throws Exception;

    /**
     * 添加厨房
     *
     * @param stockKitchen
     * @throws Exception
     */
    public void addStockKitchen(StockKitchen stockKitchen)throws Exception;

    /**
     * 修改厨房
     *
     * @param stockKitchen
     * @throws Exception
     */
    public void updateStockKitchen(int id,StockKitchen stockKitchen)throws Exception;

    /**
     * 将厨房设为停用或重新启用厨房
     * @param id
     * @throws Exception
     */
    public void updateStockKitchenStatus(int id,int status)throws Exception;

    /**
     * 查看厨房明细信息
     * @param id
     * @return
     * @throws Exception
     */
    public StockKitchen queryStockKitchenDetails(int id)throws Exception;
}
