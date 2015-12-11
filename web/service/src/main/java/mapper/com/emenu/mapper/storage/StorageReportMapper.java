package com.emenu.mapper.storage;

import com.emenu.common.dto.storage.StorageReportDto;
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
     * 获取所有单据信息
     * @return
     * @throws Exception
     */
    public List<StorageReport> listAll() throws Exception;

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
                                                      @Param("offset")int offset,
                                                      @Param("pageSize")int pageSize,
                                                      @Param("depotIdList")List<Integer> depotIdList,
                                                      @Param("startTime")Date startTime,
                                                      @Param("endTime")Date endTime) throws Exception;


 /*   *//**
     *
     * @param report
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     *//*
    public List<StorageReport> listStorageReportByCondition1(@Param("report")StorageReport report,
                                                             @Param("offset")int offset,
                                                             @Param("pageSize")int pageSize) throws Exception;
*/
    /**
     * 获取指定时间之前未结算的订单
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<StorageReport> ListStorageReportUnsettled(@Param("endTime")Date endTime) throws Exception;

    /**
     * 根据单据id修改单据状态
     * @param id
     * @param status
     * @return
     * @throws Exception
     */
    public boolean updateStatusById(@Param("id")int id, @Param("status")int status) throws Exception;

    /**
     * 根据存放点IdList获取单据信息
     * @param deportIdList
     * @return
     * @throws Exception
     */
    public List<StorageReport> listByDepotIdList(@Param("startTime")Date startTime,
                                                 @Param("endTime")Date endTime,
                                                 @Param("depotIdList")List<Integer> depotIdList) throws Exception;

    /**
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
     * @param report
     * @param depots
     * @param endTime
     * @param startTime
     * @return
     */
    public int countByContition(@Param("report")StorageReport report,
                                @Param("depotIdList")List<Integer> depotIdList,
                                @Param("startTime")Date startTime,
                                @Param("endTime")Date endTime) throws Exception;

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
