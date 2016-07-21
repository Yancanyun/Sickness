package com.emenu.service.storage;

import com.emenu.common.dto.storage.ReportSearchDto;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.enums.storage.StorageReportStatusEnum;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 单据操作service
 * @author xiaozl
 * @date 2015/11/11
 * @time 14:32
 */
public interface StorageReportService {

    /**
     * 添加单据和单据详情 新
     * @param reportDto
     * @throws SSException
     */
    public void newReportDto(StorageReportDto reportDto) throws SSException;

    /**
     * 获取所有单据信息 新
     * @return
     * @throws SSException
     */
    public List<StorageReport> listAll() throws SSException;

    /**
     * 新
     * 根据查询条件获取单据
     * @param reportSearchDto
     * @return
     * @throws SSException
     */
    public List<StorageReport> listReportBySerachDto(ReportSearchDto reportSearchDto) throws SSException;

    /**
     * 新
     * 根据查询条件获取单据和单据详情
     * @param reportSearchDto
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listReportDtoBySerachDto(ReportSearchDto reportSearchDto) throws SSException;

    /**
     * 新
     * 获取时间段之间的单据和单据详情，不包括开始时间和结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listReportDtoByTime(Date startTime,Date endTime) throws SSException;

    /**
     * 新
     * 根据审核条件获取时间段之间的单据和单据详情，包括开始时间和结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listReportDtoByTimeAndIsAudited(Date startTime,Date endTime,int isAudited) throws SSException;

    /**
     * 新
     * 根据审核条件获取时间段之间的单据，包括开始时间和结束时间
     * @param startTime
     * @param endTime
     * @param isAudited
     * @return
     * @throws SSException
     */
    public List<StorageReport> listReportByTimeAndIsAudited(Date startTime,Date endTime,int isAudited) throws SSException;


    /**
     * 新
     * 根据审核条件获取时间段之间的单据和单据详情，包括开始时间和结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listReportDtoByTimeAndIsAudited1(Date startTime,Date endTime,int isAudited) throws SSException;


    /**
     * 新
     * 根据审核条件获取时间段之间的单据，不包括开始时间和结束时间
     * @param startTime
     * @param endTime
     * @param isAudited
     * @return
     * @throws SSException
     */
    public List<StorageReport> listReportByTimeAndIsAudited1(Date startTime,Date endTime,int isAudited) throws SSException;



    /**
     * 新
     * 获取时间段之间的单据，不包括开始时间和结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<StorageReport> listStorageReportByTime(Date startTime,Date endTime) throws SSException;

    /**
     * 新
     * 根据id获取单据信息
     * @param id
     * @return
     * @throws SSException
     */
    public StorageReport queryById(int id) throws SSException;

    /**
     * 新
     * 根据reportId获取单据和单据详情
     * @param id
     * @return
     * @throws SSException
     */
    public StorageReportDto queryReportDtoById(int id) throws SSException;

    /**
     * 新
     * 根据reportId删除单据和单据详情
     * @param id
     * @throws SSException
     */
    public boolean delReportDtoById(int id) throws SSException;

    /**
     * 新
     * 根据reportId 修改 审核状态
     * @param reportId
     * @param isAudited
     * @return
     * @throws SSException
     */
    public boolean updateIsAudited(int reportId,int isAudited) throws SSException;

    /**
     * 新
     * 根据reportId 修改 结算状态
     * @param reportId
     * @param isSettlemented
     * @return
     * @throws SSException
     */
    public boolean updateIsSettlemented(int reportId,int isSettlemented) throws SSException;

    /**
     * 新
     * 根据查询条件统计记录数
     * @param reportSearchDto
     * @return
     * @throws SSException
     */
    public int countByReportSerachDto(ReportSearchDto reportSearchDto) throws SSException;

    /**
     * 新
     * 获取所有单据和单据详情
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listReportDto() throws  SSException;

    /**
     * 新
     * 获取指定时间之前审核通过且未结算的单据和单据详情,如果endTime为空，时间不作为查询条件
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listUnsettleAndAuditedStorageReportByEndTime(Date endTime) throws SSException;


    /**
     * 分页获取单据和单据详情
     * @param page
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listReportDtoByPage(int page, int pageSize) throws SSException;

    /**
     * 根据时间、存放点、经手人、操作人分页单据详情获取单据和单据详情
     * @param report
     * @param page
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listReportDtoByCondition(StorageReport report,
                                                           int page,
                                                           int pageSize,
                                                           Date startTime,
                                                           Date endTime) throws SSException;

    /**
     * 根据id修改单据
     * @param report
     * @return
     * @throws SSException
     */
    public void updateById(StorageReport report) throws SSException;

    /**
     * 根据单据id修改单据结算状态
     * @param id
     * @param storageReportStatusEnum
     * @return
     * @throws SSException
     */
    public void updateIsSettlementedById(int id, StorageReportStatusEnum storageReportStatusEnum) throws SSException;



    /**
     * 根据时间、存放点idlist，分类idlist获取单据和单据详情信息
     * @param startTime
     * @param endTime
     * @param depotIdList
     * @param tagIdList
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listReportDtoByCondition(Date startTime,
                                                           Date endTime,
                                                           List<Integer> depotIdList,
                                                           List<Integer> tagIdList) throws SSException;

    /**
     * 根据时间，deportList，handlerPartyId,createdPartyId查询单据和单据详情
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listReportDtoByCondition1(StorageReport report,
                                                            Integer page,
                                                            Integer pageSize,
                                                            List<Integer> depotIdList,
                                                            Date startTime,
                                                            Date endTime) throws SSException;



    /**
     * 修改单据和单据详情
     * @param reportDto
     * @return
     */
    public void updateReportDto(StorageReportDto reportDto) throws SSException;

    /**
     * 统计单据总数量
     * @return
     * @throws SSException
     */
    public int count() throws SSException;



    /**
     * 导出到Excel
     *
     * @param startTime
     * @param endTime
     * @param handlerPartyId
     * @param createdPartyId
     * @throws SSException
     */
    public void exportToExcel(StorageReport report,Date startTime, Date endTime,List<Integer> deports, Integer handlerPartyId, Integer createdPartyId, HttpServletResponse response) throws SSException;

    /**
     * 全部的单据导出
     * @throws SSException
     */
    public void exportToExcelAll(HttpServletResponse response) throws SSException;

}
