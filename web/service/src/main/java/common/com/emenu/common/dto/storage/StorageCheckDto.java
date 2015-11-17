package com.emenu.common.dto.storage;

import com.emenu.common.entity.storage.SettlementItem;
import com.emenu.common.entity.storage.StorageItem;

/**
 * StorageCheckDto
 * 库存盘点
 * @author dujuan
 * @date 2015/11/16
 */
public class StorageCheckDto {

    private StorageItem storageItem;

    private SettlementItem settlementItem;

    public StorageItem getStorageItem() {
        return storageItem;
    }

    public void setStorageItem(StorageItem storageItem) {
        this.storageItem = storageItem;
    }

    public SettlementItem getSettlementItem() {
        return settlementItem;
    }

    public void setSettlementItem(SettlementItem settlementItem) {
        this.settlementItem = settlementItem;
    }
}
