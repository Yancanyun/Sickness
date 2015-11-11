package com.emenu.common.dto.storage;

import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;

/**
 * 单据Dto
 * @author xiaozl
 * @date 2015/11/11
 * @time 14:37
 */
public class StorageReportDto {

    //单据实体
    private StorageReport storageReport;

    //单据详情
    private StorageReportItem storageReportItem;

    public StorageReport getStorageReport() {
        return storageReport;
    }

    public void setStorageReport(StorageReport storageReport) {
        this.storageReport = storageReport;
    }

    public StorageReportItem getStorageReportItem() {
        return storageReportItem;
    }

    public void setStorageReportItem(StorageReportItem storageReportItem) {
        this.storageReportItem = storageReportItem;
    }
}
