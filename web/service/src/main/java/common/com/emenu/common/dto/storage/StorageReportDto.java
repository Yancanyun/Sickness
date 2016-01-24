package com.emenu.common.dto.storage;

import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;

import java.util.List;

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
    private List<StorageReportItem> storageReportItemList;

    //单据详情dto，用于显示数据
    private List<StorageReportItemDto> storageReportItemDtoList;

    /********************getter and setter ********************/

    public StorageReport getStorageReport() {
        return storageReport;
    }

    public void setStorageReport(StorageReport storageReport) {
        this.storageReport = storageReport;
    }

    public List<StorageReportItem> getStorageReportItemList() {
        return storageReportItemList;
    }

    public void setStorageReportItemList(List<StorageReportItem> storageReportItemList) {
        this.storageReportItemList = storageReportItemList;
    }

    public List<StorageReportItemDto> getStorageReportItemDtoList() {
        return storageReportItemDtoList;
    }

    public void setStorageReportItemDtoList(List<StorageReportItemDto> storageReportItemDtoList) {
        this.storageReportItemDtoList = storageReportItemDtoList;
    }
}
