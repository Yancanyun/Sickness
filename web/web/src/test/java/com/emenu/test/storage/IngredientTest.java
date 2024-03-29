package com.emenu.test.storage;

import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.entity.storage.Ingredient;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.StringUtils;
import com.emenu.service.storage.IngredientService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
//
//        List<Integer> list = new ArrayList<Integer>();
//        List list1 = new ArrayList();
//        ingredientService.listAll();
        try {
            String str = StringUtils.str2Pinyin("糖醋排骨","headChar");
            System.out.println(str);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }


    }

    @Test
    public void exportExcel() throws SSException {
       ItemAndIngredientSearchDto searchDto=new ItemAndIngredientSearchDto();
       searchDto.setKeyword("辣椒");
       List<Integer> tagIdlist=new ArrayList<Integer>();
       tagIdlist.add(70);
       searchDto.setTagIdList(tagIdlist);
       ingredientService.exportExcel(searchDto,null);
   }

    @Test
    public void queryById() throws SSException{
        Integer a = null;
        ingredientService.queryById(67);
    }

}
