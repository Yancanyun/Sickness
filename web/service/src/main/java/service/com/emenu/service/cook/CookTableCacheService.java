package com.emenu.service.cook;

import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;

/**
 * CookTableCacheService
 *
 * @author 权一博
 * @date 2016/6/22.
 */
public interface CookTableCacheService {

    /**
     * 获取所有可用且已经设置版本号的桌子的版本号
     * @return
     * @throws com.pandawork.core.common.exception.SSException
     */
    public JSONObject getAllTableVersion() throws SSException;

    /**
     * 根据桌号查询版本号
     * @param tableId
     * @return
     * @throws Exception
     */
    public Long getVersionByTableId(int tableId)throws SSException;

    /**
     * 更新桌子的版本号
     * @param tableId
     * @throws SSException
     */
    public void updateTableVersion(int tableId) throws SSException;

    /**
     * 去掉缓存中的桌子
     * @param tableId
     * @throws SSException
     */
    public void deleteTable(int tableId) throws SSException;

}
