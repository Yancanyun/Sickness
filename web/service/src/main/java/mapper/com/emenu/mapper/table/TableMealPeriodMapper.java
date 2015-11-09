package com.emenu.mapper.table;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TableMealPeriodMapper
 *
 * @author: yangch
 * @time: 2015/11/9 16:16
 */
public interface TableMealPeriodMapper {
    /**
     * 根据餐台ID查询餐台-餐段信息
     * @param tableId
     * @return
     * @throws Exception
     */
    public List<Integer> listByTableId(@Param("tableId") int tableId) throws Exception;

    /**
     * 根据餐台ID删除餐台-餐段信息
     * @param tableId
     * @throws Exception
     */
    public void delByTableId (@Param("tableId")int tableId) throws Exception;
}
