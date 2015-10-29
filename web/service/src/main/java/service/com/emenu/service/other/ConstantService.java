package com.emenu.service.other;

import com.pandawork.core.common.exception.SSException;

/** 常量表管理service
 * Created with IntelliJ IDEA.
 * User: gaoyang
 * Date: 13-8-16
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public interface ConstantService {

    /**
     * 根据key查询value的值
     * @param key
     * @return
     * @throws SSException
     */
    public String queryValueByKey(String key) throws SSException;

    /**
     * 根据key更新value的值
     * @param key
     * @param value
     * @throws SSException
     */
    public void updateValueByKey(String key, String value) throws SSException;
}
