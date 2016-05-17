package com.emenu.mapper.storage;

import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.entity.storage.Ingredient;
import org.apache.ibatis.annotations.Param;

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
     * 获取所有没有删除的原配料
     * @return
     * @throws Exception
     */
    public List<Ingredient> listAll() throws Exception;

    /**
     * 根据条件检索原配料
     * @param itemSearchDto
     * @return
     * @throws Exception
     */
    List<Ingredient> listBySearchDto(@Param("searchDto") ItemAndIngredientSearchDto searchDto) throws Exception;
}
