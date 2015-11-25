package com.emenu.common.entity.remark;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 备注实体类
 *
 * @author: yangch
 * @time: 2015/11/7 13:35
 */
@Entity
@Table(name = "t_remark")
public class Remark extends AbstractEntity {
    //备注ID
    @Id
    private Integer id;

    //备注名称
    private String name;

    //备注分类ID
    @Column(name = "remark_tag_id")
    private Integer remarkTagId;

    //权重
    private Integer weight;

    //是否为常用备注: 0-否; 1-是
    @Column(name = "is_common")
    private Integer isCommon;

    //关联收费
    @Column(name = "related_charge")
    private BigDecimal relatedCharge;

    //状态(1-可用, 2-已删除)
    private Integer status;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
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

    public Integer getRemarkTagId() {
        return remarkTagId;
    }

    public void setRemarkTagId(Integer remarkTagId) {
        this.remarkTagId = remarkTagId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getIsCommon() {
        return isCommon;
    }

    public void setIsCommon(Integer isCommon) {
        this.isCommon = isCommon;
    }

    public BigDecimal getRelatedCharge() {
        return relatedCharge;
    }

    public void setRelatedCharge(BigDecimal relatedCharge) {
        this.relatedCharge = relatedCharge;
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
