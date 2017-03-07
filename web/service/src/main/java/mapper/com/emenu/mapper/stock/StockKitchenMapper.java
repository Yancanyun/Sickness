package com.emenu.mapper.stock;

import com.emenu.common.entity.stock.StockKitchen;
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
     * @throws Exception
     */
    public List<StockKitchen> listStockKitchen()throws Exception;

    /**
     * 添加厨房
     *
     * @param stockKitchen
     * @throws Exception
     */
    public void addStockKitchen(@Param("stockKitchen") StockKitchen stockKitchen)throws Exception;

    /**
     * 修改厨房
     *
     * @param stockKitchen
     * @throws Exception
     */
    public void updateStockKitchen(@Param("id") int id,
                                   @Param("stockKitchen") StockKitchen stockKitchen)throws Exception;

    /**
     * 将厨房设为停用或重新启用厨房
     * @param id
     * @throws Exception
     */
    public void updateStockKitchenStatus(@Param("id") int id,
                                         @Param("status") int status)throws Exception;

    /**
     * 查看厨房明细信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    public StockKitchen queryStockKitchenDetails(@Param("id") int id)throws Exception;
}
