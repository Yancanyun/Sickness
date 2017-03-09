package com.emenu.mapper.stock;

import com.emenu.common.entity.stock.StockDocuments;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * StockDocumentsMapper
 *
 * @author: wychen
 * @time: 2017/3/8 20:39
 */
public interface StockDocumentsMapper {

    /**
     * 获取单据list
     *
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listAll() throws SSException;
}
