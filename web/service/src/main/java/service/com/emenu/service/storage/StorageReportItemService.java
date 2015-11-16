package com.emenu.service.storage;

import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 单据详情Service
 * @author xiaozl
 * @date 2015/11/12
 * @time 14:30
 */
public interface StorageReportItemService {

    /**
     * 添加单据详情
     * @param storageReportItem
     * @return
     * @throws SSException
     */
    public StorageReportItem newStorageReportItem(StorageReportItem storageReportItem) throws SSException;

    /**
     * 获取所有单据详情信息
     * @return
     * @throws SSException
     */
    public List<StorageReportItem> listAll() throws SSException;

    /**
     * 根据单据ID获取单据详情信息
     * @return
     * @throws SSException
     */
    public StorageReportItem queryByReportId(int reportId) throws SSException;


    /**
     * 获取所有相同reportId的单据详情
     * @param reportId
     * @return
     * @throws SSException
     */
    public List<StorageReportItem> listByReportId(int reportId) throws SSException;

    /**
     * 根据id修改单据详情
     * @param storageReportItem
     * @return
     * @throws SSException
     */
    boolean updateById(StorageReportItem storageReportItem) throws SSException;


}
