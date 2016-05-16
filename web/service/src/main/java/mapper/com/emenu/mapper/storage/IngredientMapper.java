package com.emenu.mapper.storage;

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

    public void updateIngredient(@Param("ingredient") Ingredient ingredient) throws Exception;

   // public List<Ingredient> listAll() throws Exception;
}
