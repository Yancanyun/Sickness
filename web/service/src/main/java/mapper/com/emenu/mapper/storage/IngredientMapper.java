package com.emenu.mapper.storage;

import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.entity.storage.Ingredient;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * IngredientMapper
 * 原配料Mapper
 * @author xiaozl
 * @date: 2016/5/14
 */

public interface IngredientMapper {

    /**
     * 更新原配料
     * @param ingredient
     * @throws Exception
     */
    public void updateIngredient(@Param("ingredient") Ingredient ingredient) throws Exception;

    /**
     * 根据id修改原配料助记码和库存上下限
     * @param ingredient
     * @throws Exception
     */
    public void updateIngredientCodeAndLimitById(@Param("ingredient") Ingredient ingredient) throws Exception;

    /**
     * 根据原配料id修改原配料状态
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateIngredientStatusById(@Param("id") int id, @Param("status") int status) throws Exception;

    /**
     * 获取所有没有删除的原配料
     * @return
     * @throws Exception
     */
    public List<Ingredient> listAll() throws Exception;

    /**
     * 根据条件检索原配料
     * @param searchDto
     * @return
     * @throws Exception
     */
    List<Ingredient> listBySearchDto(@Param("searchDto") ItemAndIngredientSearchDto searchDto) throws Exception;
    /**
     * 根据条件检索原配料
     * @param searchDto
     * @return
     * @throws Exception
     */
    List<Ingredient> listDetailsBySearchDto(@Param("searchDto") ItemAndIngredientSearchDto searchDto) throws Exception;
    /**
     * 根据搜索查询数量
     * @param searchDto
     * @return
     * @throws Exception
     */
    public int countBySearchDto(@Param("searchDto") ItemAndIngredientSearchDto searchDto) throws Exception;

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    public int coutByName(@Param("name") String name) throws Exception;

    /**
     *
     */
    public List<Ingredient> listByKeywordAndTagids(String keyword, List<Integer> tagIdList)throws Exception;

    /**
     * 根据keyword获取原配料
     * @param keyword
     * @return
     * @throws SSException
     */
    public List<Ingredient> listByKeyword(@Param("keyword") String keyword) throws Exception;
}
