package com.emenu.common.dto.dish;

import com.pandawork.core.common.util.Assert;

import java.math.BigDecimal;

/**
 * 成本卡物品Dto
 *
 * @author: zhangteng
 * @time: 2015/12/14 18:37
 **/
public class CostCardItemDto {

    private Integer id;

    // 成本卡ID
    private Integer costCardId;

    // 库存物品ID
    private Integer itemId;

    // 物品编号
    private String itemNumber;

    // 物品名称
    private String itemName;

    // 物品总入库量
    private BigDecimal itemTotalStockInQuantity;

    // 物品入库总金额
    private BigDecimal itemTotalStockInMoney;

    // 库存单位名称
    private String itemStorageUnitName;

    // 成本卡单位名称
    private String itemCostCardUnitName;

    // 库存单位到成本卡单位的换算比例
    private BigDecimal itemStorageToCostCardRatio;

    // 净料用量
    private BigDecimal netItemQuantity;

    // 净料率
    private BigDecimal netItemRatio;

    // 是否自动出库
    private Integer autoStockOut;

    // 均价
    private BigDecimal averagePrice;

    // 原料用量
    private BigDecimal rawMaterialQuantity;

    // 原料金额
    private BigDecimal rawMaterialMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCostCardId() {
        return costCardId;
    }

    public void setCostCardId(Integer costCardId) {
        this.costCardId = costCardId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemTotalStockInQuantity() {
        return itemTotalStockInQuantity;
    }

    public void setItemTotalStockInQuantity(BigDecimal itemTotalStockInQuantity) {
        this.itemTotalStockInQuantity = itemTotalStockInQuantity;

        // 计算库存均价
        calcAveragePrice();
    }

    public BigDecimal getItemTotalStockInMoney() {
        return itemTotalStockInMoney;
    }

    public void setItemTotalStockInMoney(BigDecimal itemTotalStockInMoney) {
        this.itemTotalStockInMoney = itemTotalStockInMoney;

        // 计算库存均价
        calcAveragePrice();
    }

    public String getItemStorageUnitName() {
        return itemStorageUnitName;
    }

    public void setItemStorageUnitName(String itemStorageUnitName) {
        this.itemStorageUnitName = itemStorageUnitName;
    }

    public String getItemCostCardUnitName() {
        return itemCostCardUnitName;
    }

    public void setItemCostCardUnitName(String itemCostCardUnitName) {
        this.itemCostCardUnitName = itemCostCardUnitName;
    }

    public BigDecimal getItemStorageToCostCardRatio() {
        return itemStorageToCostCardRatio;
    }

    public void setItemStorageToCostCardRatio(BigDecimal itemStorageToCostCardRatio) {
        this.itemStorageToCostCardRatio = itemStorageToCostCardRatio;
    }

    public BigDecimal getNetItemQuantity() {
        return netItemQuantity;
    }

    public void setNetItemQuantity(BigDecimal netItemQuantity) {
        this.netItemQuantity = netItemQuantity;
    }

    public BigDecimal getNetItemRatio() {
        return netItemRatio;
    }

    public void setNetItemRatio(BigDecimal netItemRatio) {
        this.netItemRatio = netItemRatio;

        // 计算原料用量和金额
        calcRawMaterialQuantityAndMoney();
    }

    public Integer getAutoStockOut() {
        return autoStockOut;
    }

    public void setAutoStockOut(Integer autoStockOut) {
        this.autoStockOut = autoStockOut;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getRawMaterialQuantity() {
        return rawMaterialQuantity;
    }

    public void setRawMaterialQuantity(BigDecimal rawMaterialQuantity) {
        this.rawMaterialQuantity = rawMaterialQuantity;
    }

    public BigDecimal getRawMaterialMoney() {
        return rawMaterialMoney;
    }

    public void setRawMaterialMoney(BigDecimal rawMaterialMoney) {
        this.rawMaterialMoney = rawMaterialMoney;
    }

    /**
     * 调用这个方法时,需要先确保itemTotalStockInMoney和itemTotalStockInQuantity
     *
     */
    public void calcAveragePrice() {
        if (Assert.isNotNull(this.itemTotalStockInMoney)
                && Assert.isNotNull(this.itemTotalStockInQuantity)
                && !this.itemTotalStockInQuantity.equals(BigDecimal.ZERO)) {
            this.averagePrice = this.itemTotalStockInMoney.divide(itemTotalStockInQuantity, 10, BigDecimal.ROUND_DOWN);
            this.averagePrice = this.averagePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     * 调用这个方法时,需要先确保netItemQuantity和netItemRatio有值
     *
     */
    public void calcRawMaterialQuantityAndMoney() {
        if (Assert.isNotNull(this.netItemQuantity)
                && Assert.isNotNull(this.netItemRatio)
                && !this.netItemRatio.equals(BigDecimal.ZERO)) {
            this.rawMaterialQuantity = this.netItemQuantity.divide(this.netItemRatio, 10, BigDecimal.ROUND_DOWN);
            this.rawMaterialQuantity = this.rawMaterialQuantity.setScale(2, BigDecimal.ROUND_HALF_UP);

            this.rawMaterialMoney = this.averagePrice.divide(this.itemStorageToCostCardRatio, 10, BigDecimal.ROUND_DOWN)
                                        .multiply(this.rawMaterialQuantity);
            this.rawMaterialMoney = this.rawMaterialMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }
}
