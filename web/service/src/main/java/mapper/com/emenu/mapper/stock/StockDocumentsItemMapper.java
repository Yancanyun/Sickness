package com.emenu.mapper.stock;

import com.emenu.common.entity.stock.StockDocumentsItem;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StockDocumentsItemMapper
 *
 * @author: wychen
 * @time: 2017/3/9 17:16
 */
public interface StockDocumentsItemMapper {

    /**
     * 根据单据id删除单据条目
     * @param documentsId
     * @throws SSException
     */
    public void delByDocumentsId(@Param("documentsId") int documentsId) throws SSException;

    /**
     * 根据单据id查询该单据下的物品列表
     *
     * @param documentsId
     * @return
     * @throws SSException
     */
    public List<StockDocumentsItem> queryByDocumentsId(@Param("documentsId") int documentsId) throws SSException;
}
