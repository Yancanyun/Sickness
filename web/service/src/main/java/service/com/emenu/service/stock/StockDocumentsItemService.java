package com.emenu.service.stock;

import com.emenu.common.entity.stock.StockDocumentsItem;
import com.pandawork.core.common.exception.SSException;

/**
 * StockDocumentsItemService
 *
 * @author renhongshuai
 * @Time 2017/3/8 15:09.
 */
public interface StockDocumentsItemService{

    public StockDocumentsItem newDocumentsItem(StockDocumentsItem stockDocumentsItem) throws SSException;
}
