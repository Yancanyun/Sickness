package com.emenu.common.entity.vip;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 会员积分方案
 *
 * @author chenyuting
 * @date 2016/1/19 8:54
 */
@Entity
@Table(name = "t_vip_integral_plan")
public class VipIntegralPlan extends AbstractEntity{

    // 会员积分方案id
    @Id
    private Integer id;

    // 等级id
    @Column(name = "grade_id")
    private Integer gradeId;

    // 兑换类型
    private Integer type;

    // 兑换值
    private BigDecimal value;

    // 状态
    private Integer status;

    // get、set方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}