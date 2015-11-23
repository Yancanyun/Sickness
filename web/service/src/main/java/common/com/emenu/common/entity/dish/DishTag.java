package com.emenu.common.entity.dish;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 菜品-分类关联表
 *
 * @author: zhangteng
 * @time: 2015/11/21 17:20
 **/
@Entity
@Table(name = "t_dish_tag")
public class DishTag extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 菜品ID
    @Column(name = "dish_id")
    private Integer dishId;

    // 分类ID
    @Column(name = "tag_id")
    private Integer tagId;

    // 是否首要分类
    @Column(name = "is_first_tag")
    private Integer isFirstTag;

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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getIsFirstTag() {
        return isFirstTag;
    }

    public void setIsFirstTag(Integer isFirstTag) {
        this.isFirstTag = isFirstTag;
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
