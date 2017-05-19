package com.emenu.common.entity.stock;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * StockWarn
 *
 * @author Flying
 * @date 2017/3/13 14:53
 */

@Entity
@Table(name = "t_stock_warn")
public class StockWarn extends AbstractEntity {
    @Id
    private Integer id;

    //物品id
    @Column(name = "item_id")
    private Integer itemId;

    //物品名称
    private String itemName;

    //厨房id
    @Column(name = "kitchen_id")
    private Integer kitchenId;

    //厨房名称
    private String kitchenName;

    //预警内容
    private String content;

    //预警状态 0：未处理，1：已忽略，2：已解决
    private Integer state;

    //预警生成时间
    private Date time;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

}
