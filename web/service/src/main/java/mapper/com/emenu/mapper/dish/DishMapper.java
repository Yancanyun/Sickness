package com.emenu.mapper.dish;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.Dish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜品Mapper
 *
 * @author: zhangteng
 * @time: 2015/11/16 11:00
 **/
public interface DishMapper {

    /**
     * 查询全部
     *
     * @return
     * @throws Exception
     */
    public List<Dish> listAll() throws Exception;

    /**
     * 根据searchDto查询
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<Dish> listBySearchDto(@Param("offset") int offset,
                                      @Param("searchDto") DishSearchDto searchDto) throws Exception;

    /**
     * 根据searchDto查询数量
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public int countBySearchDto(@Param("searchDto") DishSearchDto searchDto) throws Exception;

    /**
     * 根据id更改状态
     *
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatusById(@Param("id") int id,
                                 @Param("status") int status) throws Exception;
}
