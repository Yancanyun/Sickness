package com.emenu.service.stock;

import com.emenu.common.entity.stock.StockKitchen;
import com.pandawork.core.common.exception.SSException;

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
     * @throws SSException
     */
    public List<StockKitchen> listStockKitchen()throws SSException;

    /**
     * 添加厨房
     *
     * @param stockKitchen
     * @throws SSException
     */
    public void addStockKitchen(StockKitchen stockKitchen)throws SSException;

    /**
     * 修改厨房
     *
     * @param stockKitchen
     * @throws SSException
     */
    public void updateStockKitchen(int id,StockKitchen stockKitchen)throws SSException;

    /**
     * 将厨房设为停用或重新启用厨房
     * @param id
     * @throws SSException
     */
    public void updateStockKitchenStatus(int id,int status)throws SSException;

    /**
     * 查看厨房明细信息
     * @param id
     * @return
     * @throws SSException
     */
    public StockKitchen queryStockKitchenDetails(int id)throws SSException;

    /**
     * 检查厨房名称是否存在
     *
     * @param name
     * @return
     * @throws SSException
     */
    public Boolean checkNameIsExist(String name)throws SSException;

    public Boolean checkIsItem(Integer id)throws SSException;
}
