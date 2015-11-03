package com.emenu.common.entity.dish.unit;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Unit
 *
 * @author xubaorong
 * @date 2015/10/23.
 */
@Entity
@Table(name = "t_unit")
public class Unit extends AbstractEntity{

    private static final long serialVersionUID = -4348318293591069581L;

    //主键id
    @Id
    private Integer id;
    //单位名称
    private String name;
    //量度类型，1代表重量单位，2代表数量单位
    private Integer type;
    //创建时间
    @Column(name = "created_time")
    private Date createdTime;
    //最后修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    /********************getter and setter ********************/
    public Integer getId() { return id; }

    @Override
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getType() { return type; }

    public void setType(Integer type) { this.type = type; }

    public Date getLastModifiedTime() { return lastModifiedTime; }

    public void setLastModifiedTime(Date lastModifiedTime) { this.lastModifiedTime = lastModifiedTime; }

    public Date getCreatedTime() { return createdTime; }

    public void setCreatedTime(Date createdTime) { this.createdTime = createdTime; }
}
