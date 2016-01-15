package com.emenu.common.entity.dish;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 成本卡物品实体
 *
 * @author: zhangteng
 * @time: 2015/12/14 18:13
 **/
@Entity
@Table(name = "t_cost_card_item")
public class CostCardItem extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 成本卡ID
    @Column(name = "cost_card_id")
    private Integer costCardId;

    // 库存物品ID
    @Column(name = "item_id")
    private Integer itemId;

    // 净料用量
    @Column(name = "net_item_quantity")
    private BigDecimal netItemQuantity;

    // 净料率
    @Column(name = "net_item_ratio")
    private BigDecimal netItemRatio;

    // 是否自动出库(0-否,1-是)
    @Column(name = "auto_stock_out")
    private Integer autoStockOut;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
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
    }

    public Integer getAutoStockOut() {
        return autoStockOut;
    }

    public void setAutoStockOut(Integer autoStockOut) {
        this.autoStockOut = autoStockOut;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
