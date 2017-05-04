package com.emenu.service.stock;

import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.Tag;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * StockTagService
 *
 * @author renhongshuai
 * @Time 2017/5/4 16:12.
 */
public interface StockTagService {
    /**
     * 查询所有的库存分类
     *
     * @return
     * @throws SSException
     */
    public List<TagDto> listAll() throws SSException;

    /**
     * 获取所有的小类
     *
     * @return
     * @throws SSException
     */
    public List<Tag> listAllSmallTag() throws SSException;

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
