package com.emenu.service.vip.impl;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.vip.VipDishPriceDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.vip.VipDishPrice;
import com.emenu.common.enums.TrueEnums;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.VipDishPriceMapper;
import com.emenu.service.dish.DishService;
import com.emenu.service.vip.VipDishPriceService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 会员价Service实现类
 *
 * @author chenyuting
 * @date 2015/11/18 9:18
 */
@Service(value = "vipDishPriceService")
public class VipDishPriceServiceImpl implements VipDishPriceService{

    @Autowired
    private VipDishPriceMapper vipDishPriceMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private CommonDao commonDao;

    /*@Override
    public List<VipDishPriceDto> listByDishIdAndVipDishPricePlanId(List<Integer> dishIds, int vipDishPricePlanId) throws SSException{
        List<VipDishPriceDto> vipDishPriceDtoList = Collections.emptyList();
        try{
            //判断会员价方案id是否合法
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            for (Integer dishId: dishIds){
                //判断菜品id是否合法
                if (!Assert.isNull(dishId) && Assert.lessOrEqualZero(dishId)) {
                    throw SSException.get(EmenuException.DishIdNotNull);
                }
                DishDto dishDto = dishService.queryById(dishId);
            }
            return vipDishPriceDtoList;
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipDishPriceFail, e);
        }
    }*/

    @Override
    public List<VipDishPrice> listByVipDishPricePlanId(int vipDishPricePlanId) throws SSException{
        List<VipDishPrice> vipDishPriceList = Collections.emptyList();
        try {
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            vipDishPriceList = vipDishPriceMapper.listByVipDishPricePlanId(vipDishPricePlanId);
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipDishPriceFail, e);
        }
        return vipDishPriceList;
    }

    @Override
    public List<VipDishPriceDto> listVipDishPriceDtos(int vipDishPricePlanId) throws SSException{
        List<VipDishPriceDto> vipDishPriceDtoList  = Collections.emptyList();
        BigDecimal zero = new BigDecimal("0.00");
        Map<Integer,BigDecimal> map = new HashMap<Integer, BigDecimal>();
        try{
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            vipDishPriceDtoList = vipDishPriceMapper.listDishPriceDtos(vipDishPricePlanId);
            if (Assert.isNotEmpty(vipDishPriceDtoList)){
                for (VipDishPriceDto vipDishPriceDto: vipDishPriceDtoList){
                    BigDecimal difference = vipDishPriceDto.getPrice().subtract(vipDishPriceDto.getVipDishPrice());
                    vipDishPriceDto.setDifference(difference == null ? zero : difference);
                    map.put(vipDishPriceDto.getDishId(), vipDishPriceDto.getPrice());
                }
            }
            List<Dish> dishList = dishService.listAll();
            for (Dish dish: dishList){
                if (Assert.isNull(map)
                        || !map.containsKey(dish.getId())){
                    VipDishPriceDto vipDishPriceDto = new VipDishPriceDto();
                    vipDishPriceDto.setDishId(dish.getId());
                    vipDishPriceDto.setDishName(dish.getName());
                    vipDishPriceDto.setDishNumber(dish.getDishNumber());
                    vipDishPriceDto.setPrice(dish.getPrice());
                    vipDishPriceDto.setSalePrice(dish.getSalePrice());
                    vipDishPriceDtoList.add(vipDishPriceDto);
                }
            }
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipDishPriceFail, e);
        }
        return vipDishPriceDtoList;
    }

    @Override
    public List<VipDishPriceDto> listVipDishPriceDtosByKeyword(int vipDishPricePlanId, String keyword) throws SSException{
        List<VipDishPriceDto> vipDishPriceDtoList = Collections.emptyList();
        try{
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            Assert.isNotNull(keyword, EmenuException.VipDishPriceKeywordNotNull);
            return vipDishPriceMapper.listDishPriceDtosByKeyword(keyword, vipDishPricePlanId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipDishPriceFail);
        }
    }

    /*@Override
    public VipDishPrice queryById(int id) throws SSException{
        try{
            if (!Assert.isNull(id) && Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.VipDishPriceIdError);
            }
            return commonDao.queryById(VipDishPrice.class, id);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }
    }*/

    @Override
    public VipDishPrice queryByDishIdAndVipDishPricePlanId(int dishId, int vipDishPricePlanId) throws SSException{
        VipDishPrice vipDishPrice = new VipDishPrice();
        try{
            if (!Assert.isNull(dishId) && Assert.lessOrEqualZero(dishId)){
                throw SSException.get(EmenuException.DishIdNotNull);
            }
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)){
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            vipDishPrice = vipDishPriceMapper.queryByDishIdAndVipDishPricePlanId(dishId, vipDishPricePlanId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipDishPriceFail);
        }
        return vipDishPrice;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void generateVipDishPrice(List<Integer> dishIds,
                                     BigDecimal discount,
                                     BigDecimal difference,
                                     BigDecimal lowPrice,
                                     TrueEnums includeDrinks,
                                     TrueEnums cover,
                                     int vipDishPricePlanId) throws SSException{
        List<Dish> dishList = Collections.emptyList();
        List<VipDishPrice> vipDishPriceList = Collections.emptyList();
        List<VipDishPrice> vipDishPriceListExist = Collections.emptyList();
        Map<Integer,BigDecimal> map = new HashMap<Integer, BigDecimal>();
        try{
            //判断dishIds是否为空，为空则选中所有菜品
            if (dishIds.size() == 0){
                dishList = dishService.listAll();
            } else {
                DishSearchDto searchDto = new DishSearchDto();
                searchDto.setDishIdList(dishIds);
                dishList = dishService.listBySearchDto(searchDto);
            }

            // 覆盖处理，如果不覆盖原有会员价，则把存在的会员价信息放入map中
            if(cover == TrueEnums.False) {
                vipDishPriceListExist = vipDishPriceMapper.listByVipDishPricePlanId(vipDishPricePlanId);
                for (VipDishPrice vipDishPrice : vipDishPriceListExist) {
                    map.put(vipDishPrice.getDishId(), vipDishPrice.getVipDishPrice());
                }
            }

            // 迭代，从菜品列表中删除不需要生成的菜品
            Iterator<Dish> dishIterator = dishList.iterator();
            while (dishIterator.hasNext()) {
                Dish dish = dishIterator.next();
                // 酒水处理。如果不包含酒水，则去除菜品列表中的酒水
                if (TagEnum.Drinks.getId().equals(dish.getCategoryId())
                        && includeDrinks == TrueEnums.False) {
                    dishIterator.remove();
                }
                // 覆盖处理。如果不覆盖，将dish中已存在会员价的菜品去除
                if (cover == TrueEnums.False
                        && map.containsKey(dish.getId())) {
                    dishIterator.remove();
                }
            }

            if (Assert.isNull(difference)
                    && Assert.isNotNull(discount)) {
                vipDishPriceList = generateByDiscount(dishList, discount, lowPrice, vipDishPricePlanId);
            }else if (Assert.isNull(discount)
                    && Assert.isNotNull(difference)){
                vipDishPriceList = generateByDifference(dishList, difference, lowPrice, vipDishPricePlanId);
            }else {
                throw SSException.get(EmenuException.UpdateVipDishPriceFail);
            }

            vipDishPriceMapper.insertAll(vipDishPriceList);

        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }

    }

    /**
     * 私有方法1：根据折扣生成会员价
     * @param discount
     * @param lowPrice
     * @param vipDishPricePlanId
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private List<VipDishPrice> generateByDiscount(List<Dish> dishList,
                                                  BigDecimal discount,
                                                  BigDecimal lowPrice,
                                                  int vipDishPricePlanId) throws SSException{
        BigDecimal zero = new BigDecimal("0.00");
        BigDecimal divisor = new BigDecimal("10.00");
        List<VipDishPrice> vipDishPriceList = new ArrayList<VipDishPrice>();
        try {
            //判断折扣是否为空
            if (Assert.isNull(discount)) {
                throw SSException.get(EmenuException.InputDiscountOrDifferenceNotNull);
            }
            //判断折扣是否合法(折扣小于0或大于10不合法)
            if (discount.compareTo(zero) < 0
                    && discount.compareTo(divisor) > 0) {
                throw SSException.get(EmenuException.InputDiscountOrDifferenceError);
            }
            //判断会员价方案id是否合法
            if (!Assert.isNull(vipDishPricePlanId)
                    && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            //如果最低价格为空，则设为0
            if (lowPrice == null) {
                lowPrice = zero;
            }

            //根据菜品id更新/添加会员价;
            for (Dish dish : dishList) {
                VipDishPrice vipDishPriceRecord = new VipDishPrice();
                BigDecimal vipDishPrice = dish.getPrice().multiply(discount).divide(divisor);
                //如果会员价小于最低价，则将会员价设为最低价
                vipDishPrice = vipDishPrice.compareTo(lowPrice) < 0 ? lowPrice :vipDishPrice;
                vipDishPriceRecord.setDishId(dish.getId());
                vipDishPriceRecord.setVipDishPricePlanId(vipDishPricePlanId);
                vipDishPriceRecord.setVipDishPrice(vipDishPrice);
                vipDishPriceList.add(vipDishPriceRecord);
            }
            return vipDishPriceList;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }
    }

    /**
     * 私有方法2：根据差价生成会员价
     * @param difference
     * @param lowPrice
     * @param vipDishPricePlanId
     * @return
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private List<VipDishPrice> generateByDifference(List<Dish> dishList,
                                                    BigDecimal difference,
                                                    BigDecimal lowPrice,
                                                    int vipDishPricePlanId) throws SSException{
        BigDecimal zero = new BigDecimal("0.00");
        List<VipDishPrice> vipDishPriceList = new ArrayList<VipDishPrice>();
        try {
            //判断差价否为空
            if (Assert.isNull(difference)) {
                throw SSException.get(EmenuException.InputDiscountOrDifferenceNotNull);
            }
            //判断会员价方案id是否合法
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            //如果最低价格为空，则设为0
            if (lowPrice == null) {
                lowPrice = zero;
            }

            //根据菜品id更新/添加会员价
            for (Dish dish : dishList) {
                VipDishPrice vipDishPriceRecord = new VipDishPrice();
                BigDecimal vipDishPrice = dish.getPrice().subtract(difference);
                //如果会员价小于最低价，则将会员价设为最低价
                vipDishPrice = vipDishPrice.compareTo(lowPrice) < 0 ? lowPrice :vipDishPrice;
                vipDishPriceRecord.setDishId(dish.getId());
                vipDishPriceRecord.setVipDishPricePlanId(vipDishPricePlanId);
                vipDishPriceRecord.setVipDishPrice(vipDishPrice);
                vipDishPriceList.add(vipDishPriceRecord);
            }
            return vipDishPriceList;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }
    }

    public void updateVipDishPrice(int dishId, int vipDishPricePlanId, BigDecimal vipDishPrice) throws SSException{
        BigDecimal zero = new BigDecimal("0.00");
        try{
            //判断菜品id是否合法
            if (!Assert.isNull(dishId) && Assert.lessOrEqualZero(dishId)) {
                throw SSException.get(EmenuException.DishIdNotNull);
            }
            //判断会员价方案id是否合法
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            vipDishPrice = vipDishPrice.compareTo(zero) < 0 ? zero : vipDishPrice;
            vipDishPriceMapper.updateVipDishPrice(dishId, vipDishPricePlanId, vipDishPrice);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }
    }

    /**
     * 保存前检查菜品id、会员价方案id、会员价是否为空
     * @param vipDishPrice
     * @return
     * @throws SSException
     */
    private boolean checkBeforeSave(VipDishPrice vipDishPrice) throws SSException{
        if (Assert.isNull(vipDishPrice)){
            return false;
        }

        Assert.isNotNull(vipDishPrice.getDishId(), EmenuException.DishIdNotNull);
        Assert.isNotNull(vipDishPrice.getVipDishPricePlanId(), EmenuException.VipDishPricePlanIdError);
        Assert.isNotNull(vipDishPrice.getVipDishPrice(), EmenuException.VipDishPriceNotNull);
        return true;
    }

}