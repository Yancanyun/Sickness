package com.emenu.service.stock;

import com.emenu.common.entity.stock.StockDocumentsItem;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * StockDocumentsItemService
 *
 * @author renhongshuai
 * @Time 2017/3/8 15:09.
 */
public interface StockDocumentsItemService{

    /**
     * 添加单据物品明细
     * @param stockDocumentsItem
     * @return
     * @throws SSException
     */
    public StockDocumentsItem newDocumentsItem(StockDocumentsItem stockDocumentsItem) throws SSException;

    /**
     * 根据id删除单据item
     *
     * @param documentsId
     * @throws SSException
     */
    public boolean delByDocumentsId(int documentsId) throws SSException;

    /**
     * 根据单据id获取该单据下的物品列表
     *
     * @param documentsId
     * @return
     * @throws SSException
     */
    public List<StockDocumentsItem> queryByDocumentsId(int documentsId) throws SSException;

    /**
     * 修改单据明细信息
     *
     * @param stockDocumentsItem
     * @throws SSException
     */
    public void updateDocumentsItem(StockDocumentsItem stockDocumentsItem)throws SSException;

    /**
     * 根据单据物品明细Id删除
     *
     * @param id
     * @throws SSException
     */
    public void delDocumentsItemById(int id)throws SSException;
}
