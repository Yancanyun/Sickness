package com.emenu.mapper.stock;

import com.emenu.common.dto.stock.StockWarnDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StockWarnMapper
 *
 * @author yuzhengfei
 * @date 2017/3/15 15:56
 */
public interface StockWarnMapper  {

    /**
     * 根据物品id修改预警状态为已解决
     *
     * @param itemId
     * @throws Exception
     */
    public void updateStateToResolvedByItemId(@Param("itemId") Integer itemId) throws Exception;


    /**
     * 根据物品id修改预警状态为已忽略
     *
     * @param itemId
     * @throws Exception
     */
    public void updateStateToIgnoreByItemId(@Param("itemId") Integer itemId) throws Exception;


    /**
     * 根据厨房id查询出所有未处理的预警信息
     *
     * @param kitchenId
     * @return
     * @throws Exception
     */
    public List<StockWarnDto> queryAllUntreatedWarn(@Param("kitchenId") Integer kitchenId) throws Exception;


}
