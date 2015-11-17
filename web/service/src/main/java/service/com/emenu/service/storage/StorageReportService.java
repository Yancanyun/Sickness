package com.emenu.service.storage;

import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.enums.storage.StorageReportStatusEnum;
import com.pandawork.core.common.exception.SSException;

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
     * 添加单据和单据详情
     * @param reportDto
     * @throws SSException
     */
    public boolean newReportAndReportItem(StorageReportDto reportDto) throws SSException;


    /**
     * 添加单据
     * @param storageReport
     * @return
     * @throws SSException
     */
    public StorageReport newReport(StorageReport storageReport) throws SSException;

    /**
     * 获取所有单据信息
     * @return
     * @throws SSException
     */
    public List<StorageReport> listAll() throws SSException;

    /**
     * 获取所有单据和单据详情
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listStorageReportDto() throws  SSException;

    /**
     * 获取指定时间之前未结算的单据和单据详情
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> ListStorageReportDtoUnsettled(Date endTime) throws SSException;

    /**
     * 分页获取单据和单据详情
     * @param page
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listStorageReportDtoByPage(int page, int pageSize) throws SSException;

    /**
     * 时间、存放点、经手人、操作人分页单据详情获取单据和单据详情
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listStorageReportDtoByCondition(Date startTime,
                                                                  Date endTime,
                                                                  String serialNumber,
                                                                  int depotId,
                                                                  int handlerPartyId,
                                                                  int createdPartyId,
                                                                  int page,
                                                                  int pageSize) throws SSException;

    /**
     * 根据经手人id、操作人id、单据id分页单据详情获取单据和单据详情
     * @param id
     * @param depotId
     * @param handlerPartyId
     * @param createdPartyId
     * @param page
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<StorageReportDto> listStorageReportDtoByCondition1(int id,
                                                                     int depotId,
                                                                     int handlerPartyId,
                                                                     int createdPartyId,
                                                                     int page,
                                                                     int pageSize) throws SSException;

    /**
     * 根据id修改单据
     * @param storageReport
     * @return
     * @throws SSException
     */
    boolean updateById(StorageReport storageReport) throws SSException;

    /**
     * 根据单据id修改单据状态
     * @param id
     * @param storageReportStatusEnum
     * @return
     * @throws SSException
     */
    boolean updateStatusById(int id,StorageReportStatusEnum storageReportStatusEnum) throws SSException;

    /**
     * 根据id获取单据信息
     * @param id
     * @return
     * @throws SSException
     */
    public StorageReport queryById(int id) throws SSException;

}
