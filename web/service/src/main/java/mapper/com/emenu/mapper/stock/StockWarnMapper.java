package com.emenu.mapper.stock;

import com.emenu.common.entity.stock.StockWarn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StockWarnMapper
 *
 * @author Flying
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
    public List<StockWarn> queryAllUntreatedWarnByKitchenId(@Param("kitchenId") Integer kitchenId) throws Exception;

    /**
     * 查询所有未处理的预警信息
     *
     * @return
     * @throws Exception
     */
    public List<StockWarn> queryAllUntreatedWarn() throws Exception;

    /**
     *查询所有未处理的预警信息
     *
     * @return
     * @throws Exception
     */
    public List<StockWarn> queryAllWarn() throws Exception;

    /**
     * 获取所有未处理预警的数量
     *
     * @return
     * @throws Exception
     */
    public int countAllWarn() throws Exception;

    /**
     * 获取分页
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<StockWarn> listByPage(@Param("offset") int offset , @Param("pageSize") int pageSize) throws Exception;

}