package com.emenu.common.dto.storage;

import com.emenu.common.entity.storage.StorageSettlement;
import com.emenu.common.entity.storage.StorageSettlementIngredient;

/**
 * 库存盘点和盘点详情
 * StorageSettlementDto
 *
 * @author xiaozl
 * @date: 2016/7/11
 */
public class StorageSettlementDto {

    // 盘点
    StorageSettlement storageSettlement;

    // 原配料盘点详情
    StorageSettlementIngredient storageSettlementIngredient;

    public StorageSettlement getStorageSettlement() {
        return storageSettlement;
    }

    public void setStorageSettlement(StorageSettlement storageSettlement) {
        this.storageSettlement = storageSettlement;
    }

    public StorageSettlementIngredient getStorageSettlementIngredient() {
        return storageSettlementIngredient;
    }

    public void setStorageSettlementIngredient(StorageSettlementIngredient storageSettlementIngredient) {
        this.storageSettlementIngredient = storageSettlementIngredient;
    }
}
