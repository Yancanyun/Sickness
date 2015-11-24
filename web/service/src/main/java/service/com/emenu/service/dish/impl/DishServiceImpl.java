package com.emenu.service.dish.impl;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.printer.DishTagPrinter;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.emenu.common.enums.dish.SaleTypeEnums;
import com.emenu.common.enums.printer.PrinterDishEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.DishMapper;
import com.emenu.service.dish.DishMealPeriodService;
import com.emenu.service.dish.DishService;
import com.emenu.service.dish.DishTasteService;
import com.emenu.service.printer.DishTagPrinterService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private DishTagPrinterService dishTagPrinterService;

    @Autowired
    private DishTasteService dishTasteService;

    @Autowired
    private DishMealPeriodService dishMealPeriodService;

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
    public DishDto newDish(DishDto dishDto) throws SSException {
        try {
            if (!checkBeforeSave(dishDto)) {
                return null;
            }
            if (Assert.isNull(dishDto.getCreatedPartyId())
                    || Assert.lessOrEqualZero(dishDto.getCreatedPartyId())) {
                throw SSException.get(EmenuException.DishCreatedPartyIdNotNull);
            }
            // 1. 添加菜品
            Dish dish = (new Dish()).constructFromDto(dishDto);
            SaleTypeEnums saleTypeEnums = SaleTypeEnums.valueOf(dish.getSaleType());
            BigDecimal salePrice = dish.getPrice();
            // 如果是折扣，售价需要计算
            if (SaleTypeEnums.Discount.equals(saleTypeEnums)) {
                Integer discount = dish.getDiscount();
                salePrice = dish.getPrice().multiply(new BigDecimal(discount.toString()))
                                            .divide(new BigDecimal("100.00"));
                salePrice = salePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (SaleTypeEnums.SalePrice.equals(saleTypeEnums)) {
                salePrice = dish.getSalePrice();
            }
            dish.setSalePrice(salePrice);
            // 设置状态
            dish.setStatus(DishStatusEnums.OnSale.getId());
            dish = commonDao.insert(dish);

            // 2. 添加口味
            dishTasteService.newDishTaste(dish.getId(), dishDto.getTasteIdList());

            // 3. 添加餐段
            dishMealPeriodService.newDishMealPeriod(dish.getId(), dishDto.getMealPeriodIdList());

            // 4. 添加打印机
            if (Assert.isNotNull(dishDto.getPrinterId())
                    && !Assert.lessOrEqualZero(dishDto.getPrinterId())) {
                DishTagPrinter dishTagPrinter = new DishTagPrinter();
                dishTagPrinter.setDishId(dish.getId());
                dishTagPrinter.setPrinterId(dishDto.getPrinterId());
                dishTagPrinter.setType(PrinterDishEnum.DishPrinter.getId());
                dishTagPrinterService.newPrinterDish(dishTagPrinter);
            }

            dishDto.setId(dish.getId());
            return dishDto;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishInsertFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateDish(DishDto dishDto) throws SSException {
        try {
            if (!checkBeforeSave(dishDto)) {
                return ;
            }
            if (Assert.isNull(dishDto.getId())
                    || Assert.lessOrEqualZero(dishDto.getId())) {
                throw SSException.get(EmenuException.DishIdError);
            }
            // 1. 更新菜品
            Dish dish = (new Dish()).constructFromDto(dishDto);
            SaleTypeEnums saleTypeEnums = SaleTypeEnums.valueOf(dish.getSaleType());
            BigDecimal salePrice = dish.getPrice();
            // 如果是折扣，售价需要计算
            if (SaleTypeEnums.Discount.equals(saleTypeEnums)) {
                Integer discount = dish.getDiscount();
                salePrice = dish.getPrice().multiply(new BigDecimal(discount.toString()))
                        .divide(new BigDecimal("100.00"));
                salePrice = salePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (SaleTypeEnums.SalePrice.equals(saleTypeEnums)) {
                salePrice = dish.getSalePrice();
            }
            dish.setSalePrice(salePrice);
            commonDao.update(dish);

            // 2. 更新口味
            dishTasteService.updateDishTaste(dish.getId(), dishDto.getTasteIdList());

            // 3. 更新餐段
            dishMealPeriodService.updateDishMealPeriod(dish.getId(), dishDto.getMealPeriodIdList());

            // 4. 更新打印机
            DishTagPrinter dishTagPrinter = new DishTagPrinter();
            dishTagPrinter.setDishId(dish.getId());
            dishTagPrinter.setPrinterId(dishDto.getPrinterId());
            dishTagPrinter.setType(PrinterDishEnum.DishPrinter.getId());
            dishTagPrinterService.updatePrinterDish(dishTagPrinter);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishUpdateFailed, e);
        }

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
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return dishMapper.queryById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishQueryFailed, e);
        }
    }

    private boolean checkBeforeSave(DishDto dishDto) throws SSException {
        if (Assert.isNull(dishDto)) {
            return false;
        }

        Assert.isNotNull(dishDto.getCategoryId(), EmenuException.DishCategoryNotNull);
        Assert.isNotNull(dishDto.getTagId(), EmenuException.DishTagNotNull);
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
