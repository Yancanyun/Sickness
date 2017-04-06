package com.emenu.service.stock;

import com.emenu.common.entity.stock.StockKitchenItem;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * StockKitchenItemService
 *
 * @author yaojf
 * @date 2017/3/13 10:15
 */
public interface StockKitchenItemService {

    /**
     * 添加新的厨房物品
     * @param stockKitchenItem
     * @throws SSException
     */
    public void newStockKitchenItem(StockKitchenItem stockKitchenItem)throws SSException;

    /**
     * 删除厨房的某个物品
     * @param id
     * @throws SSException
     */
    public void delStockKitchenItem(int id) throws SSException;

    /**
     * 更新库存物品列表
     * @param stockKitchenItem
     * @throws SSException
     */
    public void updateStockKitchenItem(StockKitchenItem stockKitchenItem) throws SSException;


    /**
     * 修改厨房物品的备注
     * @param id
     * @throws SSException
     */
    public void editRemark(int id) throws SSException;

    /**
     * 列出所有的物品
     * @return
     * @throws SSException
     */
    public List<StockKitchenItem> queryAllItem() throws SSException;

    /**
     * 根据id查询某个物品
     * @param id
     * @return
     * @throws SSException
     */
    public StockKitchenItem queryById(int id) throws SSException;

    /**
     * 根据itemId（物品表中的物品id）来查询物品
     * @param itemId
     * @param kitchenId
     * @return
     * @throws SSException
     */
    public StockKitchenItem queryByItemId(int itemId,int kitchenId) throws SSException;
}
