package com.emenu.service.stock;


import com.emenu.common.dto.stock.DocumentsDto;
import com.emenu.common.entity.stock.StockDocuments;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 单据管理Srvice
 * StockDocumentsService
 *
 * @author renhongshuai
 *         Created by admin.
 * @Time 2017/3/7 15:33.
 */
public interface StockDocumentsService {

    /**
     * 新增单据Dto
     *
     * @param documentsDto
     * @return
     * @throws SSException
     */
    public void newDocumentsDto(DocumentsDto documentsDto) throws SSException;



    /*************************************** by chenwenyan  ************************************************/

    /**
     * 获取所有单据List
     *
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listAll() throws SSException;

}
