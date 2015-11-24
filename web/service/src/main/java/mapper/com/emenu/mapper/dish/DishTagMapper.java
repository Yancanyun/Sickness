package com.emenu.mapper.dish;

import org.apache.ibatis.annotations.Param;

/**
 * 菜品-分类Mapper
 *
 * @author: zhangteng
 * @time: 2015/11/23 09:47
 **/
public interface DishTagMapper {

    /**
     * 更新菜品的首要分类
     *
     * @param dishId
     * @param tagId
     * @throws Exception
     */
    public void updateFirstTag(@Param("dishId") int dishId,
                               @Param("tagId") int tagId) throws Exception;
}
