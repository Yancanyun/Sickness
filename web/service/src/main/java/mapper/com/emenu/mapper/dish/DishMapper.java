package com.emenu.mapper.dish;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.Dish;
import com.pandawork.core.common.exception.SSException;
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
     * 顾客端-根据关键字，分页显示菜品列表
     * @author: chenyuting
     *
     * @param dishSearchDto
     * @return
     * @throws Exception
     */
    public List<DishDto> listBySearchDtoInMobile(@Param("dishSearchDto") DishSearchDto dishSearchDto) throws Exception;

    /**
     * 根据ID对菜品/套餐点赞
     * @param id
     * @return
     * @throws Exception
     */
    public int likeThisDish(@Param("id")int id) throws Exception;

    /**
     * 根据ID对菜品/套餐取消点赞
     * @param id
     * @return
     * @throws Exception
     */
    public int dislikeThisDish(@Param("id")int id) throws Exception;

    /**
     * 根据tagid获取dish
     * @param tagIdList
     * @return
     * @throws Exception
     */
    public List<Dish> listByTagIdList(@Param("tagIdList") List<Integer> tagIdList) throws Exception;

    /**
     * 根据关键字搜索和状态搜索酒水
     * @param keyword
     * @param status
     * @return
     */
    public List<Dish> listByKeywordAndStatus(@Param("keyword") String keyword, @Param("status") int status) throws Exception;

}
