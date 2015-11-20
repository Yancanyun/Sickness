package com.emenu.service.dish.impl;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.emenu.common.enums.dish.SaleTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.DishMapper;
import com.emenu.service.dish.DishService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 菜品Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/17 16:27
 **/
@Service("dishService")
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public List<Dish> listAll() throws SSException {
        List<Dish> list = Collections.emptyList();
        try {
            list = dishMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishQueryFailed, e);
        }
        return list;
    }

    @Override
    public List<Dish> listBySearchDto(DishSearchDto searchDto) throws SSException {
        List<Dish> list = Collections.emptyList();
        int pageNo = searchDto.getPageNo() <= 0 ? 0 : searchDto.getPageNo() - 1;
        int offset = pageNo * searchDto.getPageSize();

        try {
            list = dishMapper.listBySearchDto(offset, searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishQueryFailed, e);
        }
        return list;
    }

    @Override
    public int countBySearchDto(DishSearchDto searchDto) throws SSException {
        Integer count = 0;
        try {
            count = dishMapper.countBySearchDto(searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishQueryFailed, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newDish(DishDto dishDto) throws SSException {

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateDish(DishDto dishDto) throws SSException {

    }

    @Override
    public void updateStatusById(int id, DishStatusEnums statusEnums) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.DishIdError);
            }
            Assert.isNotNull(statusEnums, EmenuException.DishStatusIllegal);

            dishMapper.updateStatusById(id, statusEnums.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishUpdateFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            updateStatusById(id, DishStatusEnums.Deleted);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public DishDto queryById(int id) throws SSException {
        return null;
    }

    private boolean checkBeforeSave(DishDto dishDto) throws SSException {
        if (Assert.isNull(dishDto)) {
            return false;
        }

        Assert.isNotNull(dishDto.getCategoryId(), EmenuException.DishCategoryNotNull);
        Assert.isNotNull(dishDto.getName(), EmenuException.DishNameNotNull);
        Assert.isNotNull(dishDto.getUnitId(), EmenuException.DishUnitNotNull);
        Assert.isNotNull(dishDto.getPrice(), EmenuException.DishPriceNotNUll);
        // 促销方式
        SaleTypeEnums saleTypeEnums = SaleTypeEnums.valueOf(dishDto.getSaleType());
        Assert.isNotNull(saleTypeEnums, EmenuException.DishSaleTypeNotNull);
        if (SaleTypeEnums.Discount.equals(saleTypeEnums)) {
            Assert.isNotNull(dishDto.getDiscount(), EmenuException.DishDiscountNotNull);
        } else if (SaleTypeEnums.SalePrice.equals(saleTypeEnums)) {
            Assert.isNotNull(dishDto.getSaleType(), EmenuException.DishSalePriceNotNull);
        }

        return true;
    }
}
