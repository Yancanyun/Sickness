package com.emenu.mapper.stock;

import com.emenu.common.dto.stock.StockItemSearchDto;
import com.emenu.common.entity.stock.StockItem;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据SearchDto列举Item
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<StockItem> listBySearchDto(@Param("searchDto") StockItemSearchDto searchDto) throws SSException;
}
