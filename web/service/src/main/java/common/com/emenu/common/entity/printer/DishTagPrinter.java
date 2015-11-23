package com.emenu.common.entity.printer;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * DishTagPrinter
 * 菜品与打印机关联表
 * @author dujuan
 * @date 2015/11/14
 */
@Entity
@Table(name = "t_dish_tag_printer")
public class DishTagPrinter extends AbstractEntity{

    private static final long serialVersionUID = -2925475617462686714L;

    //关联表ID
    @Id
    private Integer id;

    //菜品ID或菜品分类ID
    @Column(name = "dish_tag_id")
    private Integer dishId;

    //打印机ID
    @Column(name = "printer_id")
    private Integer printerId;

    //类型：1-菜品分类，2-具体某一个
    private Integer type;

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

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getPrinterId() {
        return printerId;
    }

    public void setPrinterId(Integer printerId) {
        this.printerId = printerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
