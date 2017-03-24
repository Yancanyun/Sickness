package com.emenu.service.stock;

import com.emenu.common.dto.stock.StockWarnDto;
import com.emenu.common.entity.stock.StockWarn;

import java.util.List;

/**
 * StockWarnService
 * 库存预警
 *
 * @author yuzhengfei
 * @date 2017/3/13 16:05
 */
public interface StockWarnService {

    /**
     * 添加预警
     *
     * @param stockWarn
     * @return
     * @throws Exception
     */
    public StockWarn newWarn(StockWarn stockWarn) throws Exception;

    /**
     * 根据物品id修改预警状态为已解决
     *
     * @param itemId
     * @throws Exception
     */
    public void updateStateToResolvedByItemId(int itemId) throws Exception;

    /**
     * 根据物品id修改预警状态为已忽略
     *
     * @param itemId
     * @throws Exception
     */
    public void updateStateToIgnoredByItemId(int itemId) throws Exception;

    /**
     * 根据厨房id查询出当前所有未处理的预警信息
     *
     * @param kitchenId
     * @return
     * @throws Exception
     */
    public List<StockWarnDto> queryAllUntreatedWarn(int kitchenId) throws Exception;


}
