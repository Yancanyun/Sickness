package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.storage.Ingredient;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.mapper.storage.IngredientMapper;
import com.emenu.service.dish.UnitService;
import com.emenu.service.storage.IngredientService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * IngredientServiceImpl
 *
 * @author xiaozl
 * @date: 2016/5/14
 */
@Service("IngredientService")
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private UnitService unitService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public Ingredient newIngredient(Ingredient ingredient) throws SSException {
        if (!checkBeforeSave(ingredient)) {
            return null;
        }

        try {
            return commonDao.insert(ingredient);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.IngredientInserFailed, e);
        }
    }

    @Override
    public void updateIngredient(Ingredient ingredient) throws SSException {
        try {
            if (this.checkBeforeSave(ingredient)) {
                ingredientMapper.updateIngredient(ingredient);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.IngredientUpdateFailed, e);
        }
    }

    @Override
    public Ingredient queryById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)){
            return null;
        }
        try {
             return commonDao.queryById(Ingredient.class,id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public List<Ingredient> listBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException {
        List<Ingredient> ingredientList = Collections.emptyList();
        int pageNo = searchDto.getPageNo() <= 0 ? 0 : searchDto.getPageNo()-1;
        int offset = pageNo * searchDto.getPageSize();
        searchDto.setOffSet(offset);
        try {
            ingredientList = ingredientMapper.listBySearchDto(searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return ingredientList;
    }

    @Override
    public List<Ingredient> listAll() throws SSException {
        List<Ingredient> ingredientList = Collections.emptyList();
         try {
             ingredientList = ingredientMapper.listAll();

             //设置单位名称
             for (Ingredient ingredient : ingredientList){

                 Unit orderUnit = unitService.queryById(ingredient.getOrderUnitId());
                 if (Assert.isNotNull(orderUnit)){
                     ingredient.setOrderUnitName(orderUnit.getName());
                 } else {
                     throw SSException.get(EmenuException.QueryUnitFailed);
                 }

                 Unit storageUnit = unitService.queryById(ingredient.getStorageUnitId());
                 if (Assert.isNotNull(storageUnit)){
                     ingredient.setStorageUnitName(storageUnit.getName());
                 } else {
                     throw SSException.get(EmenuException.QueryUnitFailed);
                 }
                 ingredient.setStorageUnitName(storageUnit.getName());

                 Unit costCardUnit = unitService.queryById(ingredient.getCostCardUnitId());
                 if (Assert.isNotNull(costCardUnit)){
                     ingredient.setOrderUnitName(costCardUnit.getName());
                 } else {
                     throw SSException.get(EmenuException.QueryUnitFailed);
                 }
                 ingredient.setCostCardUnitName(costCardUnit.getName());
             }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return ingredientList;
    }


    private boolean checkBeforeSave(Ingredient ingredient) throws SSException {
        if (Assert.isNull(ingredient)) {
            return false;
        }

        Assert.isNotNull(ingredient.getTagId(), EmenuException.IngredientTagIdIsNotNull);
        Assert.isNotNull(ingredient.getName(), EmenuException.IngredientNameIsNotNull);
        Assert.isNotNull(ingredient.getIngredientNumber(), EmenuException.IngredientNumberIsNotNull);
        Assert.isNotNull(ingredient.getOrderUnitId(), EmenuException.IngredientOrderUnitIdIsNotNull);
        Assert.isNotNull(ingredient.getStorageUnitId(), EmenuException.IngredientStorageUnitIdIsNotNull);
        Assert.isNotNull(ingredient.getCostCardUnitId(), EmenuException.IngredientCostCardUnitIdIsNotNull);
        Assert.isNotNull(ingredient.getOrderToStorageRatio(), EmenuException.IngredientOrderToStorageRatioIsNotNull);
        Assert.isNotNull(ingredient.getStorageToCostCardRatio(), EmenuException.IngredientStorageToCostCardRatioIsNotNull);
        Assert.isNotNull(ingredient.getMaxStorageQuantity(), EmenuException.IngredientMaxStorageQuantityIsNotNull);
        Assert.isNotNull(ingredient.getMinStorageQuantity(), EmenuException.IngredientMinStorageQuantityIsNotNull);

        return true;
    }
}
