package com.emenu.service.stock;


import com.emenu.common.dto.stock.DocumentsDto;
import com.emenu.common.dto.stock.DocumentsSearchDto;
import com.emenu.common.entity.stock.StockDocuments;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 单据管理Service
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

    /**
     * 修改单据Dto
     *
     * @param documentsDto
     * @throws SSException
     */
    public void updateDocumentsDto(DocumentsDto documentsDto) throws SSException;

    /**
     * 修改单据
     *
     * @param stockDocuments
     * @throws SSException
     */
    public void updateDocuments(StockDocuments stockDocuments) throws SSException;

    /**
     * 根据条件导出到Excel
     * @param stockDocuments
     * @param startTime
     * @param endTime
     * @param response
     * @throws SSException
     */
    public void exportToExcel(StockDocuments stockDocuments,Date startTime, Date endTime,
                               HttpServletResponse response) throws SSException;

    /**
     * 导出全部Excel
     * @param response
     * @throws SSException
     */
    public void exportToExcelAll(HttpServletResponse response)throws SSException;


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
     * 根据id获取单据以及单据详情
     *
     * @param id
     * @return
     * @throws SSException
     */
    public DocumentsDto queryDocumentsDtoById(int id) throws SSException;

    /**
     * 根据Id删除对应单据
     *
     * @param id
     * @throws SSException
     */
    public boolean delDocumentsDtoById(int id) throws SSException;

    /**
     * 根据查询条件获取单据和单据详情
     *
     * @param documentsSearchDto
     * @return
     * @throws SSException
     */
    public List<DocumentsDto> listDocumentsDtoBySearchDto(DocumentsSearchDto documentsSearchDto) throws SSException;

    /**
     * 获取时间段之间的单据，不包括开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listDocumentsByTime(Date startTime, Date endTime) throws SSException;

    /**
     * 获取时间段时间的单据以及 单据详情，不包括开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<DocumentsDto> listDocumentsDtoByTime(Date startTime,Date endTime) throws SSException;

    /**
     * 根据审核条件获取时间段之间的单据，包括开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     * @param isAudited
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listDocumentsByTimeAndIsAudited(Date startTime, Date endTime, int isAudited) throws SSException;


    /**
     * 根据审核条件获取时间段之间的单据以及单据详情，包括开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     * @param isAudited
     * @return
     * @throws SSException
     */
    public List<DocumentsDto> listDocumentsDtoByTimeAndIsAudited(Date startTime, Date endTime, int isAudited) throws SSException;


    /**
     * 根据审核条件获取时间段之间的单据，不包括开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     * @param isAudited
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listDocumentsByTimeAndIsAudited1(Date startTime, Date endTime, int isAudited) throws SSException;

    /**
     * 根据审核条件获取时间段之间的单据以及单据详情，不包括开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     * @param isAudited
     * @return
     * @throws SSException
     */
    public List<DocumentsDto> listDocumentsDtoByTimeAndIsAudited1(Date startTime, Date endTime, int isAudited) throws SSException;

    /**
     * 根据查询条件获取单据计数
     *
     * @param documentsSearchDto
     * @return
     * @throws SSException
     */
    public int countByDocumentsSearchDto(DocumentsSearchDto documentsSearchDto) throws SSException;

    /**
     * 获取指定时间之前审核通过且未结算的单据和单据详情,如果endTime为空，时间不作为查询条件
     *
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listUnsettleAndAuditedDocumentsByEndTime(Date endTime) throws SSException;

    /**
     * 根据页码获取单据以及单据详情
     *
     * @param page
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<DocumentsDto> listDocumentsDtoByPage(int page,int pageSize) throws SSException;

    /**
     * 根据时间、存放点、经手人、操作人分页单据详情获取单据和单据详情
     * @param stockDocuments
     * @param page
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<DocumentsDto> listDocumentsDtoByCondition(StockDocuments stockDocuments,
                                                           int page,
                                                           int pageSize,
                                                           Date startTime,
                                                           Date endTime) throws SSException;

    /**
     * 根据页码获取单据列表
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listByPage(int offset,int pageSize) throws SSException;

    /**
     * 统计单据数量
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;
}

