package com.emenu.service.stock;


import com.emenu.common.dto.stock.DocumentsDto;
import com.emenu.common.dto.stock.DocumentsSearchDto;
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

    /**
     * 更改单据结算状态
     *
     * @param documentsId
     * @param isSettled
     * @return
     * @throws SSException
     */
    public boolean updateIsSettled(int documentsId, int isSettled) throws SSException;

    /**
     * 更改单据审核状态
     *
     * @param documentsId
     * @param isAudited
     * @return
     * @throws SSException
     */
    public boolean updateIsAudited(int documentsId, int isAudited) throws SSException;



    /*************************************** by chenwenyan  ************************************************/

    /**
     * 获取所有单据List
     *
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listAll() throws SSException;

    /**
     * 根据查询条件dto获取单据
     *
     * @param documentsSearchDto
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listStockDocumentsBySearchDto(DocumentsSearchDto documentsSearchDto) throws SSException;

    /**
     * 通过id获取单据信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    public StockDocuments queryById(int id) throws SSException;

    /**
     * 根据Id删除对应单据
     *
     * @param id
     * @throws SSException
     */
    public boolean delDocumentsDtoById(int id) throws SSException;
}
