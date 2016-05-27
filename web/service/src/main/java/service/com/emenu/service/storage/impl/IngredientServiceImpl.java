package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.storage.Ingredient;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.StringUtils;
import com.emenu.mapper.storage.IngredientMapper;
import com.emenu.service.dish.UnitService;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.storage.IngredientService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IngredientServiceImpl
 *
 * @author xiaozl
 * @date: 2016/5/14
 */
@Service("ingredientService")
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private UnitService unitService;

    @Autowired
    private SerialNumService serialNumService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public Ingredient newIngredient(Ingredient ingredient) throws SSException {
        if (!checkBeforeSave(ingredient)) {
            return null;
        }
        // 设置物品编号和助记码
        try {
            // 原配料编号
            String serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.IngredientNum);
            ingredient.setIngredientNumber(serialNumber);
            ingredient.setMaxStorageQuantity(ingredient.getMaxStorageQuantity().multiply(ingredient.getStorageToCostCardRatio()));
            ingredient.setMinStorageQuantity(ingredient.getMinStorageQuantity().multiply(ingredient.getStorageToCostCardRatio()));
            // 助记码
            if (Assert.isNull(ingredient.getAssistantCode())
                    || ingredient.getAssistantCode().equals("")){
                String assistantCode = StringUtils.str2Pinyin(ingredient.getName(),"headChar");
                ingredient.setAssistantCode(assistantCode);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
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
                ingredient.setMaxStorageQuantity(ingredient.getMaxStorageQuantity().multiply(ingredient.getStorageToCostCardRatio()));
                ingredient.setMinStorageQuantity(ingredient.getMinStorageQuantity().multiply(ingredient.getStorageToCostCardRatio()));
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
        Ingredient ingredient;
        try {
            ingredient = commonDao.queryById(Ingredient.class,id);
            if (Assert.isNull(ingredient)){
                return null;
            }
            List<Unit> unitList = unitService.listAll();
            Map<Integer, String> unitMap = new HashMap<Integer, String>();
            for (Unit unit : unitList) {
                unitMap.put(unit.getId(), unit.getName());
            }
            unitMap.put(0, "");
            ingredient.setOrderUnitName(unitMap.get(ingredient.getOrderUnitId()));
            ingredient.setStorageUnitName(unitMap.get(ingredient.getStorageUnitId()));
            ingredient.setCostCardUnitName(unitMap.get(ingredient.getCostCardUnitId()));
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return ingredient;
    }

    @Override
    public List<Ingredient> listBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException {
        List<Ingredient> ingredientList = Collections.emptyList();
        int pageNo = searchDto.getPageNo() <= 0 ? 0 : searchDto.getPageNo()-1;
        int offset = pageNo * searchDto.getPageSize();
        searchDto.setOffset(offset);
        try {
            ingredientList = ingredientMapper.listBySearchDto(searchDto);
            //设置单位名称
            setUnitName(ingredientList);
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
             setUnitName(ingredientList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return ingredientList;
    }

    @Override
    public int countBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException {
        Integer count = 0;
        try {
            count = ingredientMapper.countBySearchDto(searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return count == null ? 0 : count;
    }

    public boolean checkIngredientNameIsExist(String name) throws SSException{
        int count = 0;
        try {
            count = ingredientMapper.coutByName(name);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return count > 0 ? true:false;
    }

    @Override
    public void exportExcel(String keyword, List<Integer> tagIdList, HttpServletResponse response) throws SSException {

    }


    private boolean checkBeforeSave(Ingredient ingredient) throws SSException {
        if (Assert.isNull(ingredient)) {
            return false;
        }

        Assert.isNotNull(ingredient.getTagId(), EmenuException.IngredientTagIdIsNotNull);
        Assert.isNotNull(ingredient.getName(), EmenuException.IngredientNameIsNotNull);
        checkIngredientNameIsExist(ingredient.getName());
        Assert.isNotNull(ingredient.getOrderUnitId(), EmenuException.IngredientOrderUnitIdIsNotNull);
        Assert.isNotNull(ingredient.getStorageUnitId(), EmenuException.IngredientStorageUnitIdIsNotNull);
        Assert.isNotNull(ingredient.getCostCardUnitId(), EmenuException.IngredientCostCardUnitIdIsNotNull);
        Assert.isNotNull(ingredient.getOrderToStorageRatio(), EmenuException.IngredientOrderToStorageRatioIsNotNull);
        Assert.isNotNull(ingredient.getStorageToCostCardRatio(), EmenuException.IngredientStorageToCostCardRatioIsNotNull);
        Assert.isNotNull(ingredient.getMaxStorageQuantity(), EmenuException.IngredientMaxStorageQuantityIsNotNull);
        Assert.isNotNull(ingredient.getMinStorageQuantity(), EmenuException.IngredientMinStorageQuantityIsNotNull);

        return true;
    }

    private void setUnitName(List<Ingredient> ingredientList) throws SSException {
        List<Unit> unitList = unitService.listAll();
        Map<Integer, String> unitMap = new HashMap<Integer, String>();
        for (Unit unit : unitList) {
            unitMap.put(unit.getId(), unit.getName());
        }
        unitMap.put(0, "");
        for (Ingredient ingredient : ingredientList) {
            ingredient.setOrderUnitName(unitMap.get(ingredient.getOrderUnitId()));
            ingredient.setStorageUnitName(unitMap.get(ingredient.getStorageUnitId()));
            ingredient.setCostCardUnitName(unitMap.get(ingredient.getCostCardUnitId()));
        }
    }
}
