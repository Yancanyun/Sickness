package com.emenu.service.bar;

import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;

/**
 * BarOrderDishNewService
 * 吧台端新增消费Service
 * @author quanyibo
 * @date: 2016/7/21
 */

public interface BarOrderDishNewService {

    /**
     * 增加消费-获取总分类(一级分类)和总分类下的大类(二级分类)
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public JSONObject queryTag() throws SSException;

    /**
     * 增加消费-选择二级分类(大类)返回对应的所有菜品信息
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public JSONObject queryAllDishByBigTag(Integer bigTagId) throws SSException;

    /**
     * 增加消费-按照菜品大类和关键字搜索菜品
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public JSONObject queryDishByBigTagAndKey(Integer bigTagId,String key) throws SSException;
}
