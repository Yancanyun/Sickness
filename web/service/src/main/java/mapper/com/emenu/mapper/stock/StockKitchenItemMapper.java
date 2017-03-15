package com.emenu.mapper.stock;

import com.pandawork.core.common.exception.SSException;
import com.emenu.common.entity.stock.StockKitchenItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StockKitchenItemMapper
 * 厨房物品表Mapper
 * @author yaojf
 * @date 2017/3/6 14:48
 */
public interface StockKitchenItemMapper {
    /**
     * 厨房物品表中添加新的物品
     * @param stockKitchenItem
     * @throws SSException
     */
    public void newStockKitchenItem(@Param("stockKitchenItem") StockKitchenItem stockKitchenItem)throws SSException;

    /**
     * 根据id删除物品
     * @param id
     * @throws SSException
     */
    public void delStockKitchenItem(@Param("id") Integer id) throws SSException;

    /**
     * 编辑物品
     * @param id
     * @throws SSException
     */
    public void updateStockKitchenItem(@Param("id") Integer id) throws SSException;

    /**
     * 列出所有物品
     * @return
     * @throws SSException
     */
    public List<StockKitchenItem> queryAllItem() throws SSException;

    /**
     * 根据id查询物品
     * @param id
     * @return
     * @throws SSException
     */
    public StockKitchenItem queryById(@Param("id") Integer id) throws SSException;

    /**
     * 根据item_id查询物品
     * @param id
     * @return
     * @throws SSException
     */
    public StockKitchenItem queryByItemId(@Param("id") Integer id) throws SSException;
}
