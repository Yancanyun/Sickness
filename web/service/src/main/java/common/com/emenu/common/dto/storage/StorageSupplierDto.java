package com.emenu.common.dto.storage;

import java.math.BigDecimal;
import java.util.List;

/**
 * StorageSupplierDto
 * 供货商和涉及物品关联
 * 库存结算中心使用
 * @author dujuan
 * @date 2015/11/19
 */
public class StorageSupplierDto {

    //供货商名称
    private String supplierName;

    //总金额
    private BigDecimal totalMoney;

    List<StorageItemDto> storageItemDtoList;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<StorageItemDto> getStorageItemDtoList() {
        return storageItemDtoList;
    }

    public void setStorageItemDtoList(List<StorageItemDto> storageItemDtoList) {
        this.storageItemDtoList = storageItemDtoList;
    }
}
