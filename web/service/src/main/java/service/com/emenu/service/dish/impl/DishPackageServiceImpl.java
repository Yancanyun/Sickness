package com.emenu.service.dish.impl;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.DishMealPeriod;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.DishPackageMapper;
import com.emenu.service.dish.DishPackageService;
import com.emenu.service.dish.DishService;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.other.ConstantService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DishPackageServiceImpl
 * 套餐Service实现
 * @author dujuan
 * @date: 2015/12/11
 */
@Service("dishPackageService")
public class DishPackageServiceImpl implements DishPackageService{

    @Autowired
    private DishService dishService;

    @Autowired
    private TagFacadeService tagFacadeService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private DishPackageMapper dishPackageMapper;

    @Autowired
    private CommonDao commonDao;

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void newDishPackage(DishDto dishDto, List<DishPackage> dishPackageList) throws SSException {
        try {
            // 添加一个菜品(此"菜品"其实就是套餐)
            DishDto dishDtoNew = dishService.newDish(dishDto);
            Integer packageId = dishDtoNew.getId();
            for(DishPackage dishPackage : dishPackageList){
                dishPackage.setPackageId(packageId);
            }
            // 添加关联表
            this.newListDishPackage(dishPackageList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewDishPackageFailed, e);
        }
    }

    @Override
    public void updateDishPackage(DishDto dishDto, List<DishPackage> dishPackageList) throws SSException {
        try{
            // 修改菜品
            dishService.updateDish(dishDto);
            Integer packageId = dishDto.getId();
            for(DishPackage dishPackage : dishPackageList){
                dishPackage.setPackageId(packageId);
            }
            // 修改关联表
            this.updateDishPackage(dishPackageList, packageId);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateDishPackageFailed, e);
        }
    }

    @Override
    public void delDishPackage(int packageId) throws SSException {
        try{
            // 先删除菜品
            dishService.delById(packageId);
            // 再删除关联表
            this.delByPackageId(packageId);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteDishPackageFailed, e);
        }
    }

    @Override
    public DishPackageDto queryDishPackageById(int packageId) throws SSException {
        if(Assert.isNull(packageId)){
            throw SSException.get(EmenuException.DishPackageIdError);
        }
        DishPackageDto dishPackageDto = new DishPackageDto();
        try{
            // 先获得套餐菜品基本信息
            DishDto dishDto = dishService.queryById(packageId);
            dishPackageDto.setDishDto(dishDto);
            // 再获得具体菜列表
            List<DishDto> childDishDtoList = new ArrayList<DishDto>();
            // 获得关联表列表
            List<DishPackage> dishPackageList = dishPackageMapper.listDishPackage(packageId);
            for(DishPackage dishPackage : dishPackageList){
                childDishDtoList.add(dishService.queryById(dishPackage.getDishId()));
                // 依次添加套餐下的菜品的DishPackage至childDishDtoList中的DishDto
                childDishDtoList.get(childDishDtoList.size()-1).setDishPackage(dishPackage);
                //底下这个是学姐写的方法,有错误,二重循环设置的DishPackage的话最后全部都一样（就相当于最后一次操作把前面已经设置的值全部覆盖了）
                // 所有的DishPackage都是最后一次设置的同一个对象
                /*for (DishDto childDishDto : childDishDtoList) {
                    childDishDto.setDishPackage(dishPackage);
                }*/
            }
            dishPackageDto.setChildDishDtoList(childDishDtoList);
            return dishPackageDto;
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListDishPackageFailed, e);
        }
    }

    @Override
    public void updateDishPackageStatus(int packageId, DishStatusEnums dishStatusEnums) throws SSException {
        try{
            if(Assert.isNull(packageId)){
                throw SSException.get(EmenuException.DishPackageIdError);
            }
            if (Assert.isNull(dishStatusEnums)){
                throw SSException.get(EmenuException.DishStatusIllegal);
            }
            dishService.updateStatusById(packageId,dishStatusEnums);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateDishPackageFailed, e);
        }
    }

    @Override
    public int countByPackageId(int packageId) throws SSException {
        Integer count = 0;
        try{
            if(Assert.isNull(packageId)){
                throw SSException.get(EmenuException.DishPackageIdError);
            }
            count = dishPackageMapper.countByPackageId(packageId);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDishPackageFailed, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public BigDecimal countPriceByPackageId(int packageId) throws SSException {
        BigDecimal countPrice = new BigDecimal(0.00);
        try{
            if(Assert.isNull(packageId)){
                throw SSException.get(EmenuException.DishPackageIdError);
            }
            List<DishPackage> dishPackageList  = dishPackageMapper.listDishPackage(packageId);
            for(DishPackage dishPackage : dishPackageList){
                Integer dishId = dishPackage.getDishId();
                Integer dishQuantity = dishPackage.getDishQuantity();
                BigDecimal dishQuantityDecimal = BigDecimal.valueOf(dishQuantity);
                countPrice = countPrice.add(dishService.queryById(dishId).getPrice().multiply(dishQuantityDecimal));
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDishPackageFailed, e);
        }
        return countPrice;
    }

    /**
     * 根据PackageId查询关联表列表
     * @param packageId
     * @return
     * @throws SSException
     */
    private List<DishPackage> listDishPackage(int packageId) throws SSException {
        List<DishPackage> dishPackageList = Collections.emptyList();
        try{
            if(Assert.isNull(packageId)){
                throw SSException.get(EmenuException.DishPackageIdError);
            }
            dishPackageList = dishPackageMapper.listDishPackage(packageId);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListDishPackageFailed, e);
        }
        return dishPackageList;
    }

    /**
     * 新建套餐关联的菜品
     * @param dishPackageList
     * @return
     * @throws SSException
     */
    private void newListDishPackage(List<DishPackage> dishPackageList) throws SSException{
        if(Assert.isEmpty(dishPackageList)){
            throw SSException.get(EmenuException.ListChildDishFailed);
        }
        for(DishPackage dishPackage: dishPackageList){
            this.checkDishPackage(dishPackage);
            try {
                commonDao.insert(dishPackage);
            }catch (Exception e){
                LogClerk.errLog.error(e);
                throw SSException.get(EmenuException.NewDishPackageFailed, e);
            }
        }
    }

    /**
     * 根据PackageID删除关联表
     * @param packageId
     * @throws SSException
     */
    private void delByPackageId(int packageId) throws SSException{
        try{
            if(Assert.isNull(packageId)){
                throw SSException.get(EmenuException.DishPackageIdError);
            }
            dishPackageMapper.delByPackageId(packageId);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteDishPackageFailed, e);
        }
    }

    /**
     * 修改套餐关联的菜品列表
     * @param dishPackageList
     * @throws SSException
     */
    private void updateDishPackage(List<DishPackage> dishPackageList, Integer packageId) throws SSException{
        if(Assert.isEmpty(dishPackageList)){
            throw SSException.get(EmenuException.ListChildDishFailed);
        }
        try{
            //先删除原来的
            this.delByPackageId(packageId);
            //再重新添加
            this.newListDishPackage(dishPackageList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateDishPackageFailed, e);
        }
    }

    /**
     * 判断套餐关联表必填字段是否为空
     * @param dishPackage
     * @return
     */
    private void checkDishPackage(DishPackage dishPackage) throws SSException{
        if(Assert.isNull(dishPackage)){
            throw SSException.get(EmenuException.DishPackageIsNull);
        }
        if(Assert.isNull(dishPackage.getPackageId())){
            throw SSException.get(EmenuException.DishPackageIdError);
        }
        if(Assert.isNull(dishPackage.getDishId())){
            throw SSException.get(EmenuException.DishIdError);
        }
        if(Assert.isNull(dishPackage.getDishQuantity())){
            throw SSException.get(EmenuException.DishQuantityIsNull);
        }
    }

    @Override
    public List<Dish> listAll() throws SSException {
        List<Dish> list = Collections.emptyList();
        try {
            list = dishPackageMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishQueryFailed, e);
        }
        return list;
    }

    @Override
    public int judgeIsOrNotPackage(int dishId)throws  SSException
    {
        try {
            if(dishPackageMapper.judgeIsOrNotPackage(dishId)>0)
                return 1;
            else
                return 0;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishQueryFailed, e);
        }
    }
}
