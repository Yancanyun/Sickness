package com.emenu.service.stock;

import com.emenu.common.entity.stock.Specifications;
import com.emenu.common.entity.stock.StockItem;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * ItemService
 * 物品管理Service
 *
 * @author pengpeng
 * @time 2017/3/4 9:55
 */
public interface StockItemService {

    /**
     * 物品添加
     *
     * @param stockItem
     * @return
     * @throws SSException
     */
    public StockItem newItem(StockItem stockItem) throws SSException;

    /**
     * 检查物品是否存在（true-存在，false-不存在）
     *
     * @param name
     * @return
     * @throws SSException
     */
    public boolean checkIsExist(String name) throws SSException;

    /**
     * list转化成String
     *
     * @param list
     * @return
     * @throws SSException
     */
    public String listToString(List<Integer> list) throws SSException;

    /**
     * String转化成list
     *
     * @param string
     * @return
     * @throws SSException
     */
    public List<Integer> StringTolist(String string) throws SSException;
}
