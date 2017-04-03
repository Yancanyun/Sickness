package com.emenu.mapper.stock;

import com.emenu.common.dto.stock.DocumentsSearchDto;
import com.emenu.common.entity.stock.StockDocuments;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Date;

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

    /**
     * 通过查询条件获取单据
     *
     * @param documentsSearchDto
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listBySearchDto(@Param("documentsSearchDto") DocumentsSearchDto documentsSearchDto) throws SSException;

    /**
     * 根据Id删除单据
     *
     * @param id
     * @return
     * @throws SSException
     */
    public boolean delById(@Param("id") Integer id) throws SSException;

    /**
     * 修改单据审核状态
     *
     * @param documentsId
     * @param isAudited
     * @throws Exception
     */
    public void updateIsAudited(@Param("documentsId") Integer documentsId, @Param("isAudited") Integer isAudited) throws Exception;

    /**
     * 修改入库单据结算状态
     *
     * @param documentsId
     * @param isSettled
     * @return
     * @throws Exception
     */
    public boolean updateIsSettled(@Param("documentsId") Integer documentsId, @Param("isSettled") Integer isSettled) throws Exception;

    /**
     * 获取时间段之间的单据和单据详情，不包括开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<StockDocuments> listByTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime) throws Exception;

    /**
     * 根据审核条件获取时间段之间的单据，包括开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     * @param isAudited
     * @return
     * @throws Exception
     */
    public List<StockDocuments> listByTimeAndIsAudited(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("isAudited") Integer isAudited) throws Exception;

    /**
     * 根据审核条件获取时间段之间的单据，不包括开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     * @param isAudited
     * @return
     * @throws Exception
     */
    public List<StockDocuments> listByTimeAndIsAudited1(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("isAudited") Integer isAudited) throws Exception;

    /**
     * 根据查询条件获取单据计数
     *
     * @param documentsSearchDto
     * @return
     * @throws Exception
     */
    public Integer countBySearchDto(@Param("documentsSearchDto") DocumentsSearchDto documentsSearchDto) throws Exception;

    /**
     * 获取指定时间之前审核通过且未结算的单据和单据详情,如果endTime为空，时间不作为查询条件
     *
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<StockDocuments> listUnsettleAndAuditedDocumentsByEndTime(@Param("endTime") Date endTime) throws Exception;

    /**
     * 根据页码获取单据
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<StockDocuments> listByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 根据时间、存放点、经手人、操作人分页单据详情获取单据和单据详情
     *
     * @param stockDocuments
     * @param offset
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<StockDocuments> listDocumentsByCondition(@Param("stockDocument") StockDocuments stockDocuments, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize, @Param("startTime") Date startTime, @Param("endTime") Date endTime) throws Exception;

    /**
     * 统计单据数量
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;
}

