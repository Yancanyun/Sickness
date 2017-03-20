package com.emenu.mapper.stock;

import com.emenu.common.entity.stock.StockKitchen;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StockKitchenMapper
 *
 * @author nigbo
 * @date 2017/3/6 15:24
 */
public interface StockKitchenMapper {
    /**
     * 查看厨房列表
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
    public void addStockKitchen(@Param("stockKitchen") StockKitchen stockKitchen)throws SSException;

    /**
     * 修改厨房
     *
     * @param stockKitchen
     * @throws SSException
     */
    public void updateStockKitchen(@Param("id") int id,
                                   @Param("stockKitchen") StockKitchen stockKitchen)throws SSException;

    /**
     * 将厨房设为停用或重新启用厨房
     * @param id
     * @throws SSException
     */
    public void updateStockKitchenStatus(@Param("id") int id,
                                         @Param("status") int status)throws SSException;

    /**
     * 查看厨房明细信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    public StockKitchen queryStockKitchenDetails(@Param("id") int id)throws SSException;

    /**
     * 检查厨房名称是否已存在
     *
     * @param name
     * @return
     * @throws SSException
     */
    public Integer checkNameIsExist(@Param("name") String name)throws SSException;

    /**
     * 查询是否是总库
     * @param id
     * @return
     * @throws SSException
     */
    public Integer queryIsItem(@Param("id") Integer id) throws SSException;
}
