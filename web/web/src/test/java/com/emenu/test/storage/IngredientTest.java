package com.emenu.test.storage;

import com.emenu.common.entity.storage.Ingredient;
import com.emenu.service.storage.IngredientService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * IngredientTest
 *
 * @author xiaozl
 * @date: 2016/5/16
 */
public class IngredientTest extends AbstractTestCase{

    @Autowired
    IngredientService ingredientService;

    @Test
    public void newIngredient() throws SSException{
        Ingredient ingredient = new Ingredient();
        ingredient.setName("牛肉");
        ingredient.setTagId(33);
        ingredient.setIngredientNumber("NR_88");
        ingredient.setAssistantCode("nr");
        ingredient.setOrderUnitId(1);
        ingredient.setStorageUnitId(1);
        ingredient.setOrderToStorageRatio(new BigDecimal(1));
        ingredient.setCostCardUnitId(1);
        ingredient.setStorageToCostCardRatio(new BigDecimal(1));
        ingredient.setMaxStorageQuantity(new BigDecimal(1));
        ingredient.setMinStorageQuantity(new BigDecimal(1));
        ingredientService.newIngredient(ingredient);

    }

    @Test
    public void listAll() throws SSException{
        ingredientService.listAll();
    }

}
