package com.emenu.common.dto.stock;

import com.emenu.common.entity.stock.StockDocuments;
import com.emenu.common.entity.stock.StockDocumentsItem;
import com.emenu.common.entity.stock.StockItem;
import java.util.List;

/**
 * 新库存单据Dto
 *
 * @author renhongshuai
 *         Created by admin.
 * @Time 2017/3/6 9:50.
 */
public class DocumentsDto {
    // 单据实体
    private StockDocuments stockDocuments;

    // 单据物品详情
    private List<StockDocumentsItem> stockDocumentsItemList;

    // 单据物品
    private List<StockItem> stockItemList;

    public StockDocuments getStockDocuments() {
        return stockDocuments;
    }

    public void setStockDocuments(StockDocuments stockDocuments) {
        this.stockDocuments = stockDocuments;
    }

    public List<StockDocumentsItem> getStockDocumentsItemList() {
        return stockDocumentsItemList;
    }

    public void setStockDocumentsItemList(List<StockDocumentsItem> stockDocumentsItemList) {
        this.stockDocumentsItemList = stockDocumentsItemList;
    }

    public List<StockItem> getStockItemList() {
        return stockItemList;
    }

    public void setStockItemList(List<StockItem> stockItemList) {
        this.stockItemList = stockItemList;
    }
}
