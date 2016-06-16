package com.emenu.common.dto.storage;

import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportIngredient;
import com.emenu.common.entity.storage.StorageReportItem;

import java.util.List;

/**
 * 单据Dto
 * @author xiaozl
 * @date 2015/11/11
 * @time 14:37
 */
public class StorageReportDto {

    // 单据实体
    private StorageReport storageReport;

    // 单据物品详情
    private List<StorageReportItem> storageReportItemList;

    // 单据原配料
    private List<StorageReportIngredient> storageReportIngredientList;

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

    public List<StorageReportIngredient> getStorageReportIngredientList() {
        return storageReportIngredientList;
    }

    public void setStorageReportIngredientList(List<StorageReportIngredient> storageReportIngredientList) {
        this.storageReportIngredientList = storageReportIngredientList;
    }
}
