package com.emenu.common.entity.dish;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * DishRemarkTag
 *
 * @author xubaorong
 * @date 2016/6/2.
 */
@Entity
@Table(name="t_dish_remark_tag")
public class DishRemarkTag extends AbstractEntity{
    //主键id
    @Id
    private Integer id;

    //菜品分类id
    @Column(name = "tag_id")
    private Integer tagId;

    //备注分类id
    @Column(name = "remark_tag_id")
    private Integer remarkTagId;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最后修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    /*************getter getter and setter*************/

    @Override
    public void setId(Integer id) {
       this.id = id;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getId() {
        return id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getRemarkTagId() {
        return remarkTagId;
    }

    public void setRemarkTagId(Integer remarkTagId) {
        this.remarkTagId = remarkTagId;
    }


}
