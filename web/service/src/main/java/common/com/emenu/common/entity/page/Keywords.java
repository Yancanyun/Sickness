package com.emenu.common.entity.page;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 搜索风向标-关键字实体
 *
 * @author Wang LiMing
 * @date 2015/10/22 17:36
 */

@Entity
@Table(name = "t_keywords")
public class Keywords extends AbstractEntity{

    private static final long serialVersionUID = 8291379421771771510L;

    @Id
    private Integer id;

    // 关键字
    private String key;

    // 0代表点餐平台的关键字，1代表服务员系统的关键字
    private Integer type;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
