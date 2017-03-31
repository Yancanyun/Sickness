package com.emenu.service.stock;

import com.emenu.common.dto.stock.StockItemSearchDto;
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
    public List<Integer> stringTolist(String string) throws SSException;

    /**
     * 根据Id返回实体
     *
     * @param id
     * @return
     * @throws SSException
     */
    public StockItem queryById(int id) throws SSException;

    /**
     * 修改stockItem
     *
     * @param stockItem
     * @return
     * @throws SSException
     */
    public void updateStockItem(StockItem stockItem) throws SSException;

    /**
     * 列举Item
     * （searchDto可为空）
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<StockItem> listItem(StockItemSearchDto searchDto)throws SSException;


    /**
     * 根据id修改物品的状态
     *
     * @param itemId
     * @param status
     * @throws SSException
     */
    public void updateStockItemStatusById(int itemId,int status) throws SSException;

    /**
     * 分页显示库存物品列表
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<StockItem> listByPage(int offset,int pageSize) throws SSException;

    /**
     * 统计物品数量
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;
}
