package com.emenu.common.entity.storage;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Settlement
 * 结算总表
 * @author dujuan
 * @date 2015/11/11
 */
@Entity
@Table(name = "t_storage_settlement")
public class Settlement extends AbstractEntity{

    private static final long serialVersionUID = -1429067854524059412L;

    //主键id
    @Id
    private Integer id;

    //编号
    @Column(name = "serial_number")
    private String serialNumber;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最后修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    /********************getter and setter ********************/

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
