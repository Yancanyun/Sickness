package com.emenu.service.storage;

import com.emenu.common.dto.storage.StorageItemSearchDto;
import com.emenu.common.entity.storage.StorageItem;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 库存物品Service
 *
 * @author: zhangteng
 * @time: 2015/11/12 8:48
 **/
public interface StorageItemService {

    /**
     * 根据分页和搜索查询
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<StorageItem> listBySearchDto(StorageItemSearchDto searchDto) throws SSException;

    /**
     * 根据搜索查询数量
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countBySearchDto(StorageItemSearchDto searchDto) throws SSException;

    /**
     * 查询所有
     *
     * @return
     * @throws SSException
     */
    public List<StorageItem> listAll() throws SSException;

    /**
     * 添加
     *
     * @param storageItem
     * @throws SSException
     */
    public void newStorageItem(StorageItem storageItem) throws SSException;

    /**
     * 更新
     *
     * @param storageItem
     * @throws SSException
     */
    public void updateStorageItem(StorageItem storageItem) throws SSException;

    /**
     * 根据id删除
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据分类ID查询数量
     *
     * @param tagId
     * @return
     * @throws SSException
     */
    public int countByTagId(int tagId) throws SSException;

    /**
     * 根据ID进行查询
     *
     * @param id
     * @return
     * @throws SSException
     */
    public StorageItem queryById(int id) throws SSException;


}
