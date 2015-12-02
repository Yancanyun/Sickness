package com.emenu.service.storage;

import com.emenu.common.entity.storage.StorageDepot;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 存放点Service
 *
 * @author xubr
 * @date 2015/11/10.
 */
public interface StorageDepotService {

    /**
     * 获取全部存放点
     *
     * @return
     * @throws SSException
     */
    public List<StorageDepot> listAll() throws SSException;

    /**
     * 计算存放点总数
     *
     * @return
     * @throws SSException
     */
    public int countAll() throws SSException;

    /**
     * 根据名称查找存放点
     *
     * @param name
     * @return
     * @throws SSException
     */
    public StorageDepot queryByName(String name) throws SSException;

    /**
     * 根据id查找存放点
     *
     * @param id
     * @return
     * @throws SSException
     */
    public StorageDepot queryById(int id) throws SSException;
    /**
     * 添加一个storageDepot
     *
     * @param storageDepot
     * @return
     * @throws SSException
     */
    public StorageDepot newStorageDepot(StorageDepot storageDepot) throws SSException;

    /**
     * 根据id删除存放点
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 修改存放点
     *
     * @param storageDepot
     * @throws SSException
     */
    public void updateStorageDepot(StorageDepot storageDepot) throws SSException;

}
