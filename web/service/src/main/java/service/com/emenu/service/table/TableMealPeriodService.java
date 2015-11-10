package com.emenu.service.table;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.TableMealPeriod;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 餐台-餐段Service
 *
 * @author: yangch
 * @time: 2015/11/9 13:57
 */
public interface TableMealPeriodService {
    /**
     * 根据餐台ID查询餐段ID
     * @param tableId
     * @return
     * @throws SSException
     */
    public List<Integer> listMealPeriodIdByTableId (int tableId) throws SSException;

    /**
     * 添加餐台-餐段信息
     * @param tableMealPeriodList
     * @throws SSException
     */
    public void newTableMealPeriod (List<TableMealPeriod> tableMealPeriodList) throws SSException;

    /**
     * 修改餐台-餐段信息
     * @param tableMealPeriodList
     * @throws SSException
     */
    public void updateTableMealPeriod (List<TableMealPeriod> tableMealPeriodList) throws SSException;

    /**
     * 根据餐台ID删除餐台-餐段信息
     * @param tableId
     * @throws SSException
     */
    public void delByTableId (int tableId) throws SSException;
}
