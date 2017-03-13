package com.emenu.common.entity.stock;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * StockItemDetail
 *
 * @author renhongshuai
 * @Time 2017/3/9 17:18.
 */
@Entity
@Table(name = "t_stock_item_detail")
public class StockItemDetail extends AbstractEntity {
    @Id
    private Integer id;

    // 物品Id
    @Column(name = "item_id")
    private Integer itemId;

    //规格Id
    @Column(name = "specification_id")
    private Integer specificationId;

    // 存放点Id
    @Column(name = "kitchen_id")
    private Integer kitchenId;

    // 数量
    @Column(name = "quantity")
    private BigDecimal quantity;

    // 单位Id
    @Column(name = "unit_id")
    private Integer unitId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public Integer getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
}
