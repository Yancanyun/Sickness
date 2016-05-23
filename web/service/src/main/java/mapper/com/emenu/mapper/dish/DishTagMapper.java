package com.emenu.mapper.dish;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishTagDto;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据tagId查询菜品ID
     *
     * @param tagId
     * @return
     * @throws Exception
     */
    public List<Integer> listDishIdByTagId(@Param("tagId") int tagId) throws Exception;

    /**
     * 根据tagId查询dto
     *
     * @param tagId
     * @return
     * @throws SSException
     */
    public List<DishTagDto> listDtoByTagId(@Param("tagId") int tagId) throws SSException;
}
