package com.emenu.service.storage;

import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.tag.Tag;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 库存分类Service
 *
 * @author: zhangteng
 * @time: 2015/11/10 19:25
 **/
public interface StorageTagService {

    /**
     * 查询所有的库存分类
     *
     * @return
     * @throws SSException
     */
    public List<TagDto> listAll() throws SSException;

    /**
     * 添加库存分类
     *
     * @param pId
     * @param name
     * @return
     * @throws SSException
     */
    public Tag newStorageTag(int pId,
                             String name) throws SSException;

    /**
     * 更新库存分类
     *
     * @param id
     * @param pId
     *@param name  @throws SSException
     */
    public void updateStorageTag(int id,
                                 int pId,
                                 String name) throws SSException;

    /**
     * 根据id删除库存分类
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;
}
