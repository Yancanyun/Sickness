package com.emenu.common.entity.table;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 服务员-餐桌关联表
 * @author xiaozl
 * @date 2015/10/27
 * @time 16:21
 */
@Entity

@Table(name = "t_party_employee_waiter_table")

public class WaiterTable extends AbstractEntity {

    @Id
    private Integer id;

    //服务员id
    @Column(name = "party_id")
    private Integer waiterId;

    //餐桌id
    @Column(name = "table_id")
    private Integer tableId;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
}
