package com.emenu.common.entity.stock;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * StockKitchen
 *
 * @author yuzhengfei
 * @date 2017/3/6 9:09
 */
@Entity
@Table(name="t_stock_kitchen")
public class StockKitchen extends AbstractEntity{

    @Id
    private Integer id;

    //厨房名称
    private String name;

    //厨房简介
    private String introduction;

    //厨房负责人
    private String principal;

    //厨房状态（状态(1-正常使用,2-已删除)）
    private Integer status;

    //创建时间
    @Column(name="created_time")
    private String createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private String lastModifiedTime;


    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
