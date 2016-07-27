package com.emenu.service.dish;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishTagDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.DishTag;
import com.emenu.common.entity.dish.Tag;
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
     * @param dishIds
     * @param tagId
     * @param dishIds
     * @throws SSException
     */
    public void newByTagId(int tagId,
                           Integer[] dishIds) throws SSException;

    /**
     * 根据id删除
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据tagId获取菜品列表
     *
     * @return
     * @throws SSException
     */
    public List<Dish> listDishByTagId(int tagId) throws SSException;

    /**
     * 根据tagId获取dto
     *
     * @param tagId
     * @return
     * @throws SSException
     */
    public List<DishTagDto> listDtoByTagId(int tagId) throws SSException;

    /**
     * 获取根据分类搜索出来的不在tagId分类下的菜品
     *
     * @param tagId
     * @param searchTagIdList
     * @return
     * @throws SSException
     */
    public List<Dish> listNotSelectedByTagId(int tagId,
                                             List<Integer> searchTagIdList) throws SSException;

    /**
     * 根据当前节点分页获取DishTagDto列表
     * @param tagId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<DishTagDto> listByTagIdAndPage(int tagId,int pageNo,int pageSize,String keyword) throws SSException;

    /**
     * 计算当前节点的DishTag数
     * @param tagId
     * @param keyword
     * @return
     * @throws Exception
     */
    public int countByTagId(int tagId,String keyword) throws SSException;


}
