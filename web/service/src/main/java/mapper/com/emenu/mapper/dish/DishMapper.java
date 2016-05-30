package com.emenu.mapper.dish;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishSmallDto;
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

    /**
     * 根据ID进行查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    public DishDto queryById(@Param("id") int id) throws Exception;

    /**
     * 根据菜品名或助记码模糊查询DishSmallDto
     *
     * @param keyword
     * @return
     * @throws Exception
     */
    public List<DishSmallDto> listByKeyword(@Param("keyword")String keyword) throws Exception;

    /**
     * 顾客端-根据关键字，分页显示菜品列表
     * @author: chenyuting
     *
     * @param keyword
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<DishDto> listBySearchDtoInMobile(@Param("dishSearchDto") DishSearchDto dishSearchDto) throws Exception;
}
