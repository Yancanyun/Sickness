package com.emenu.common.entity.vip;

import com.emenu.common.utils.DateUtils;
import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * MultipleIntegralPlan
 * 多倍积分方案实体
 *
 * @author WangLM
 * @date 2015/12/3 16:46
 */

@Entity
@Table(name = "t_multiple_integral_plan")
public class MultipleIntegralPlan extends AbstractEntity{

    @Id
    private Integer id;

    //积分类型名称
    private String name;

    //积分倍数
    @Column(name = "integral_multiples")
    private BigDecimal integralMultiples;

    //开始时间
    @Column(name = "start_time")
    private Date startTime;

    //结束时间
    @Column(name = "end_time")
    private Date endTime;

    //格式化后的开始时间的String
    private String startTimeString;

    //格式化后的结束时间的String
    private String endTimeString;

    //启用状态，0-未启用，1-启用
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getIntegralMultiples() {
        return integralMultiples;
    }

    public void setIntegralMultiples(BigDecimal integralMultiples) {
        this.integralMultiples = integralMultiples;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;

        if (startTime != null) {
            this.startTimeString = DateUtils.formatDate(startTime, "yyyy-MM-dd");
        } else {
            this.startTimeString = "";
        }
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;

        if (endTime != null) {
            this.endTimeString = DateUtils.formatDate(endTime, "yyyy-MM-dd");
        } else {
            this.endTimeString = "";
        }
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

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }
}
