package com.emenu.common.dto.stock;

import java.util.Date;

/**
 * StockWarnDto
 *
 * @author yuzhengfei
 * @date 2017/3/20 15:02
 */
public class StockWarnDto {

    private Integer id;

    //物品id
    private Integer itemId;

    //厨房id
    private Integer kitchenId;

    //物品名称
    private String itemName;

    //说明
    private String comment;

    //状态
    private int state;

    //时间
    private Date time;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


}
