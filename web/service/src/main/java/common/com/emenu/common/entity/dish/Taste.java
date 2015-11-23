package com.emenu.common.entity.dish;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Taste
 * 口味
 * @author dujuan
 * @date 2015/11/23
 */
@Entity
@Table(name = "t_taste")
public class Taste extends AbstractEntity{

    private static final long serialVersionUID = -8230204769370128667L;

    //主键
    @Id
    private Integer id;

    //名称
    private String name;

    //关联收费
    @Column(name = "related_charge")
    private BigDecimal relatedCharge;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最后修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

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

    public BigDecimal getRelatedCharge() {
        return relatedCharge;
    }

    public void setRelatedCharge(BigDecimal relatedCharge) {
        this.relatedCharge = relatedCharge;
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
