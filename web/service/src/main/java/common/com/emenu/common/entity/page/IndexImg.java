package com.emenu.common.entity.page;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 点餐平台首页图片实体
 *
 * @author Wang LiMing
 * @date 2015/10/23 14:17
 */

@Entity
@Table(name = "t_index_img")
public class IndexImg extends AbstractEntity{

    private static final long serialVersionUID = 1190613292739642980L;

    @Id
    private Integer id;

    //图片存储路径
    @Column(name = "img_path")
    private String imgPath;

    //图片使用状态，0表示未使用，1表示正在使用
    private Integer state;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_moedified_timelili")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
