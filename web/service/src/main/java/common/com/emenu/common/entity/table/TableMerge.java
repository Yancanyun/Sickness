package com.emenu.common.entity.table;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * TableMerge
 *
 * @author: yangch
 * @time: 2016/6/23 9:10
 */
@Entity
@javax.persistence.Table(name = "t_table_merge")
public class TableMerge extends AbstractEntity {
    // 主键
    @Id
    private Integer id;

    // 并台序号ID
    @Column(name = "merge_id")
    private Integer mergeId;

    // 餐台ID
    @Column(name = "table_id")
    private Integer tableId;

    // 并台前的餐台状态(1-可用, 2-占用已结账, 3-占用未结账, 4-已并桌)
    @Column(name = "last_table_status")
    private Integer lastTableStatus;

    // 状态(1-并台中, 2-已结束并台)
    private Integer status;

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

    public Integer getMergeId() {
        return mergeId;
    }

    public void setMergeId(Integer mergeId) {
        this.mergeId = mergeId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getLastTableStatus() {
        return lastTableStatus;
    }

    public void setLastTableStatus(Integer lastTableStatus) {
        this.lastTableStatus = lastTableStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
