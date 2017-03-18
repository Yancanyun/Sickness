package com.emenu.mapper.stock;

import com.emenu.common.entity.stock.StockItemDetail;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StockItemDetailMapper
 *
 * @author renhongshuai
 * @Time 2017/3/16 13:37.
 */
public interface StockItemDetailMapper {

    /**
     * 根据物品id与厨房id查询物品明细表
     *
     * @param itemId
     * @param kitchenId
     * @return
     * @throws SSException
     */
    public List<StockItemDetail> queryDetailById(@Param("itemId") Integer itemId,@Param("kitchenId") Integer kitchenId) throws SSException;
}
