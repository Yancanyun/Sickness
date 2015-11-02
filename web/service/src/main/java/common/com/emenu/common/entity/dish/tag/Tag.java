package com.emenu.common.entity.dish.tag;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Tag
 * 菜品和原料类别实体类
 * @author dujuan
 * @date 2015/10/22
 */
@Entity
@Table(name = "t_tag")
public class Tag extends AbstractEntity implements Cloneable ,Comparable<Tag>{

    private static final long serialVersionUID = -7116994168670878216L;

    //主键ID
    @Id
    private Integer id;
    //类别名称
    private String name;
    //类别的父类别ID
    @Column(name = "p_id")
    private Integer pId;
    //权重
    private Integer weight;
    //类别类型，如果为1则为菜品类别，2为原料类别
    private Integer type;
    //下单之后是否立即打印(0-不立即打印,1-立即打印)
    @Column(name = "print_after_confirm_order")
    private Integer printAfterConfirmOrder;
    //分类下的菜同时最多可以做的数量
    @Column(name = "max_print_num")
    private Integer maxPrintNum;
    // 创建时间
    @Column(name = "created_time")
    private Date createTime;
    // 最后修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object object = null;
        try {
            object = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return object;
    }

    /********************* getter and setter ***********************/
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

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPrintAfterConfirmOrder() {
        return printAfterConfirmOrder;
    }

    public void setPrintAfterConfirmOrder(Integer printAfterConfirmOrder) {
        this.printAfterConfirmOrder = printAfterConfirmOrder;
    }

    public Integer getMaxPrintNum() {
        return maxPrintNum;
    }

    public void setMaxPrintNum(Integer maxPrintNum) {
        this.maxPrintNum = maxPrintNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public int compareTo(Tag tag) {
        if (!this.weight.equals(tag.weight)) {
            return this.weight - tag.weight;
        }else {
            return this.id - tag.id;
        }
    }
}
