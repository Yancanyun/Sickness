package com.emenu.service.stock;

import com.emenu.common.entity.stock.StockItemDetail;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * StockItemDetailService
 *
 * @author renhongshuai
 * @Time 2017/3/10 8:43.
 */
public interface StockItemDetailService {

    /**
     * 添加物品明细
     *
     * @param stockItemDetail
     * @throws SSException
     */
    public void newStockItemDetail(StockItemDetail stockItemDetail) throws SSException;

    /**
     * 根据Id删除物品明细
     *
     * @param id
     * @throws SSException
     */
    public void deleteStockItemDetailById(int id) throws SSException;

    /**
     * 根据物品id与存放点id查询对应的物品明细
     *
     * @param itemId
     * @param kitchenId
     * @return
     * @throws SSException
     */
    public List<StockItemDetail> queryDetailById(int itemId,int kitchenId) throws SSException;

    /**
     * 修改单据明细表
     * @param stockItemDetail
     * @return
     * @throws SSException
     */
    public void updateStockItemDetail(StockItemDetail stockItemDetail) throws SSException;
}
