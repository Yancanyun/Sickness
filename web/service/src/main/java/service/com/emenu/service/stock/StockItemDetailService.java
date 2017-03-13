package com.emenu.service.stock;

import com.emenu.common.entity.stock.StockItemDetail;
import com.pandawork.core.common.exception.SSException;

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
}
