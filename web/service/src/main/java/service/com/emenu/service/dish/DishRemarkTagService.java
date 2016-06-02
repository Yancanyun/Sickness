package com.emenu.service.dish;

import com.emenu.common.entity.dish.DishRemarkTag;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DishRemarkTagService
 *
 * @author xubaorong
 * @date 2016/6/2.
 */
public interface DishRemarkTagService {
    /**
     * 根据菜品分类id删除菜品分类与备注关联表
     * @param tagId
     * @throws Exception
     */
    public void delBytTagId(int tagId) throws SSException;

    /**
     * 增加菜品分类与备注分类关联表
     * @param dishRemarkTags
     * @throws Exception
     */
    public void newDishRemarkTags(List<DishRemarkTag> dishRemarkTags) throws SSException;
}
