package com.emenu.common.dto.storage;

import com.emenu.common.entity.storage.StorageReportItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TestDto
 *
 * @author: zhangteng
 * @time: 2015/12/7 17:17
 **/
public class TestDto {

    private Integer createdPartyId;

    private Date date;

    private Integer depotId;

    private Integer handlerPartyId;

    private List<StorageReportItem> list;

    private BigDecimal price;

    private Integer type;

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }

    public Integer getHandlerPartyId() {
        return handlerPartyId;
    }

    public void setHandlerPartyId(Integer handlerPartyId) {
        this.handlerPartyId = handlerPartyId;
    }

    public List<StorageReportItem> getList() {
        return list;
    }

    public void setList(List<StorageReportItem> list) {
        this.list = list;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
