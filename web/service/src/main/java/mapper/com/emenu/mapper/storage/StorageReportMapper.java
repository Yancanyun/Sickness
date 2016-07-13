package com.emenu.mapper.storage;

import com.emenu.common.dto.storage.ReportSerachDto;
import com.emenu.common.entity.storage.StorageReport;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * StorageReportMapper
 * @author xiaozl
 * @date 2015/11/12
 * @time 16:47
 */
public interface StorageReportMapper {

    /**
     * 新
     * 获取所有单据信息
     * @return
     * @throws Exception
     */
    public List<StorageReport> listAll() throws Exception;

    /**
     * 新
     * 根据查询条件获取单据信息
     * @param reportSerachDto
     * @return
     * @throws Exception
     */
    public List<StorageReport> listReportBySerachDto(ReportSerachDto reportSerachDto) throws Exception;

    /**
     * 新
     * 根据reportId 修改 审核状态
     * @param reportId
     * @param isAudited
     * @return
     * @throws Exception
     */
    public void updateIsAudited(@Param("reportId") int reportId, @Param("isAudited") int isAudited) throws Exception;

    /**
     * 新
     * 根据reportId 修改 结算状态
     * @param reportId
     * @param isAudited
     * @return
     * @throws SSException
     */
    public boolean updateIsSettlemented(@Param("reportId") int reportId, @Param("isSettlemented") int isSettlemented) throws Exception;

    /**
     * 新
     * 根据时间获取单据，不包括startTime和endTime
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<StorageReport> listStorageReportByTime(Date startTime, Date endTime) throws Exception;

    /**
     * 新
     * 根据审核条件获取时间段之间的单据，包括开始时间和结束时间
     * @param startTime
     * @param endTime
     * @param isAudited
     * @return
     * @throws Exception
     */
    public List<StorageReport> listReportByTimeAndIsAudited(Date startTime, Date endTime, int isAudited) throws Exception;

    /**
     * 根据查询条件统计记录数
     * @param reportSerachDto
     * @return
     * @throws Exception
     */
    public int countByReportSerachDto(ReportSerachDto reportSerachDto) throws Exception;

    /**
     * 分页获取单据信息
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<StorageReport> listByPage(@Param("offset")int offset,@Param("pageSize")int pageSize ) throws Exception;

    /**
     * 根据时间、存放点、经手人、操作人分页单据详情获取单据和单据详情
     * @param report
     * @param offset
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<StorageReport> listReportByCondition(@Param("report")StorageReport report,
                                                     @Param("offset")int offset,
                                                     @Param("pageSize")int pageSize,
                                                     @Param("startTime")Date startTime,
                                                     @Param("endTime")Date endTime) throws Exception;

    /**
     * 根据时间、存放点list、经手人、操作人分页单据详情获取单据和单据详情
     * @param report
     * @param offset
     * @param pageSize
     * @param depotIdList
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<StorageReport> listReportByCondition1(@Param("report")StorageReport report,
                                                      @Param("offset")Integer offset,
                                                      @Param("pageSize")Integer pageSize,
                                                      @Param("depotIdList")List<Integer> depotIdList,
                                                      @Param("startTime")Date startTime,
                                                      @Param("endTime")Date endTime) throws Exception;



    /**
     * 获取指定时间之前未结算的订单
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<StorageReport> listStorageReportUnsettled(@Param("endTime")Date endTime) throws Exception;

    /**
     * 获取指定时间之前未结算已经审核通过的单据
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<StorageReport> listUnsettleAndAuditedStorageReportByEndTime(@Param("endTime")Date endTime)throws Exception;

    /**
     * 根据单据id修改单据状态
     * @param id
     * @param status
     * @return
     * @throws Exception
     */
    public boolean updateIsSettlementedById(@Param("id")int id, @Param("status")int status) throws Exception;

    /**
     * 根据存放点IdList获取单据信息
     * @param depotIdList
     * @return
     * @throws Exception
     */
    public List<StorageReport> listByDepotIdList(@Param("startTime")Date startTime,
                                                 @Param("endTime")Date endTime,
                                                 @Param("depotIdList")List<Integer> depotIdList) throws Exception;

    /**
     * 新
     * 根据单据id删除单据
     * @param id
     * @return
     * @throws Exception
     */
    public boolean delById(@Param("id")int id) throws Exception;

    /**
     * 统计单据数量
     * @return
     * @throws Exception
     */
    public int count() throws Exception;

    /**
     * 根据条件查询记录数
     * @param reportSerachDto
     * @return
     * @throws Exception
     */
    public int countByCondition(ReportSerachDto reportSerachDto) throws Exception;

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<StorageReport> listByTime(@Param("startTime")Date startTime,
                                                 @Param("endTime")Date endTime) throws Exception;

}
