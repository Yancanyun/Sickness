package com.emenu.common.cache.call;

import java.util.Date;

/**
 * CallCache
 * 呼叫服务缓存
 * @author quanyibo
 * @date 2016/5/18.
 */
public class CallCache {

    //服务名称
    private String name;

    //呼叫发出时间
    private Date callTime;

    //服务员响应时间
    private Date responseTime;

    //服务应答状态  0为应答了 1为未应答
    private  Integer status;

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
