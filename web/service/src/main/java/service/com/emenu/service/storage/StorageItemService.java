package com.emenu.service.storage;

import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.dto.storage.StorageItemSearchDto;
import com.emenu.common.entity.storage.StorageItem;
import com.pandawork.core.common.exception.SSException;
import com.sun.scenario.effect.impl.sw.sse.SSESepiaTonePeer;

import javax.servlet.http.HttpServletResponse;
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
    public List<StorageItem> listBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException;

    /**
     * 根据条件将 storageItem 的各个字段导出到excel
     * @param searchDto
     * @param response
     * @throws SSException
     */
    public void exportExcelBySearchDto(ItemAndIngredientSearchDto searchDto,HttpServletResponse response) throws SSException;

    /**
     * 根据搜索查询数量
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException;

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
     * 更新各个单位
     *
     * @param storageItem
     * @throws SSException
     */
    public void updateUnit(StorageItem storageItem) throws SSException;

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

    /**
     * 根据关键字进行查询
     * 此方法只用于根据助记码或名字查询
     * 返回的实体中只有id、name、assistantCode三个字段有值
     * 其余查询请使用{@link #listBySearchDto(ItemAndIngredientSearchDto)}
     *
     * @param keyword
     * @return
     * @throws SSException
     */
    public List<StorageItem> listByKeyword(String keyword) throws SSException;

    /**
     * 根据原配料id获取库存物品
     * @param IngredientId
     * @return
     * @throws SSException
     */
    public List<Integer> listIdsByIngredientId(Integer IngredientId) throws SSException;

    /**
     * 设置数量格式
     * @param storageItemList
     * @throws SSException
     */
    public void setQuantityFormat(List<StorageItem> storageItemList) throws SSException;

    /**
     * 设置数量格式
     * @param storageItem
     * @throws SSException
     */
    public void setQuantityFormat(StorageItem storageItem) throws SSException;

}
