package com.emenu.common.cache.call;

import java.util.Date;

/**
 * CallCache
 * 呼叫服务缓存
 * @author quanyibo
 * @date 2016/5/18.
 */
public class CallCache {

    //呼叫服务主键
    private Integer id;

    // 缓存主键
    private Integer cacheId;

    //服务名称
    private String name;

    //呼叫发出时间
    private Date callTime;

    //服务员响应时间
    private Date responseTime;

    //对应的桌子Id
    private Integer tableId;

    //服务应答状态  0为应答了 1为未应答
    private  Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCacheId() {
        return cacheId;
    }

    public void setCacheId(Integer cacheId) {
        this.cacheId = cacheId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }
}
