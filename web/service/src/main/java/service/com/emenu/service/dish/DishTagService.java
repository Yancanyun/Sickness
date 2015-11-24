package com.emenu.service.dish;

import com.emenu.common.entity.dish.DishTag;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 菜品-分类Service
 *
 * @author: zhangteng
 * @time: 2015/11/23 09:26
 **/
public interface DishTagService {

    /**
     * 添加dishTag
     *
     * @param dishTag
     * @throws SSException
     */
    public void newDishTag(DishTag dishTag) throws SSException;

    /**
     * 更新首要分类
     *
     * @param dishId
     * @param tagId
     * @throws SSException
     */
    public void updateFirstTag(int dishId,
                               int tagId) throws SSException;

    /**
     * 根据tagId添加
     *
     * @param tagId
     * @param dishIdList
     * @throws SSException
     */
    public void newByTagId(int tagId,
                           List<Integer> dishIdList) throws SSException;

    /**
     * 根据id删除
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;
}
