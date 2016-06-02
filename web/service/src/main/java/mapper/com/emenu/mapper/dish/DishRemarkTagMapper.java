package com.emenu.mapper.dish;

import com.emenu.common.entity.dish.DishRemarkTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DishRemarkTagMapper
 *
 * @author xubaorong
 * @date 2016/6/2.
 */
public interface DishRemarkTagMapper {

    /**
     * 根据菜品分类id删除菜品分类与备注关联表
     * @param tagId
     * @throws Exception
     */
    public void delByTagId(@Param("tagId")int tagId) throws Exception;

    /**
     * 增加菜品分类与备注分类关联表
     * @param dishRemarkTags
     * @throws Exception
     */
    public void newDishRemarkTag(@Param("dishRemarkTags")List<DishRemarkTag> dishRemarkTags) throws Exception;


}
