package com.emenu.mapper.dish;

import com.emenu.common.entity.dish.DishImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜品图片Mapper
 *
 * @author: zhangteng
 * @time: 2015/11/16 11:00
 **/
public interface DishImgMapper {

    /**
     * 根据菜品id和类型获取图片
     *
     * @param dishId
     * @param type
     * @return
     * @throws Exception
     */
    public List<DishImg> listByDishIdAndType(@Param("dishId") int dishId,
                                             @Param("type") int type) throws Exception;

    /**
     * 根据菜品id和类型删除图片
     *
     * @param dishId
     * @param type
     * @throws Exception
     */
    public void delByDishIdAndType(@Param("dishId") int dishId,
                                   @Param("type") int type) throws Exception;
}
