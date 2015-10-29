package com.emenu.common.entity.party.group.employee;

import com.pandawork.core.common.entity.AbstractEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 员工信息实体
 * @author xiaozl
 * @time 17:14
 */
@Entity
@Table(name = "t_party_employee")
public class Employee extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    //抽象用户id
    @Column(name = "party_id")
    private Integer partyId;

    // 员工真实姓名
    private String name;

    // 员工电话
    private  String phone;

    //员工状态：0代表禁用，1代表启用，2代表已删除
    private  Integer status;

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

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
