package com.emenu.mapper.stock;

import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

/**
 * StockItemMapper
 *
 * @author pengpeng
 * @time 2017/3/6 9:20
 */
public interface StockItemMapper {

    /**
     * 根据Name查询物品个数
     *
     * @param name
     * @return
     * @throws SSException
     */
    public int countByName(@Param("name") String name) throws SSException;
}
