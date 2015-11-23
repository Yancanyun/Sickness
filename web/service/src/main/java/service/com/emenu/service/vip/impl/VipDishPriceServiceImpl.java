package com.emenu.service.vip.impl;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.vip.VipDishPriceDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.vip.VipDishPrice;
import com.emenu.common.enums.TrueEnums;
import com.emenu.common.enums.vip.AutoGeneratePlanEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.VipDishPriceMapper;
import com.emenu.service.dish.DishService;
import com.emenu.service.vip.VipDishPriceService;
import com.pandawork.core.common.exception.ExceptionMes;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<VipDishPrice> listAll() throws SSException{
        List<VipDishPrice> vipDishPriceList = Collections.emptyList();
        try{
            vipDishPriceList = vipDishPriceMapper.listAll();
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipDishPriceFail, e);
        }
        return vipDishPriceList;
    }

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
    public List<VipDishPriceDto> listVipDishPriceDtosByPage(int curPage, int pageSize, int vipDishPricePlanId) throws SSException{
        List<VipDishPriceDto> vipDishPriceDtoList  = Collections.emptyList();
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)){
            return vipDishPriceDtoList;
        }
        try{
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            vipDishPriceDtoList = vipDishPriceMapper.listDishPriceDtosByPage(offset, pageSize, vipDishPricePlanId);
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipDishPriceFail, e);
        }
        return vipDishPriceDtoList;
    }

    @Override
    public int countAllByVipDishPricePlanId(int vipDishPricePlanId) throws SSException{
        Integer count = 0;
        try{
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            count = vipDishPriceMapper.countAllByVipDishPricePlanId(vipDishPricePlanId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public List<VipDishPriceDto> listDishPriceDtosByKeyword(int vipDishPricePlanId, String keyword, int curPage, int pageSize) throws SSException{
        List<VipDishPriceDto> vipDishPriceDtoList = Collections.emptyList();
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)){
            return vipDishPriceDtoList;
        }
        try{
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            Assert.isNotNull(keyword, EmenuException.VipDishPriceKeywordNotNull);
            return vipDishPriceMapper.listDishPriceDtosByKeyword(keyword, vipDishPricePlanId, offset, pageSize);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipDishPriceFail);
        }
    }

    @Override
    public void newVipDishPrice(VipDishPrice vipDishPrice) throws SSException{
        try{
            if (!checkBeforeSave(vipDishPrice)){
                throw SSException.get(EmenuException.InsertVipDishPriceFail);
            }
            commonDao.insert(vipDishPrice);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertVipDishPriceFail);
        }
    }

    @Override
    public void updateVipDishPrice(VipDishPrice vipDishPrice) throws SSException{
        try{
            if (!checkBeforeSave(vipDishPrice)){
                throw SSException.get(EmenuException.InsertVipDishPriceFail);
            }
            commonDao.update(vipDishPrice);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }
    }

    @Override
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
    }

    @Override
    public int countByDishId(int dishId) throws SSException{
        Integer count = 0;
        try{
            if (!Assert.isNull(dishId) && Assert.lessOrEqualZero(dishId)){
                throw SSException.get(EmenuException.DishIdNotNull);
            }
            count = vipDishPriceMapper.countByDishId(dishId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }
        return count == null ? 0 : count;
    }

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
    public void generateVipDishPrice(List<Integer> ids,
                                     Integer discount,
                                     BigDecimal difference,
                                     BigDecimal lowPrice,
                                     TrueEnums includeDrinks,
                                     TrueEnums cover,
                                     int vipDishPricePlanId) throws SSException{
        try{
            if (ids.size() == 0 && difference == null){
                generateAllByDiscount(discount, lowPrice, includeDrinks, cover, vipDishPricePlanId);
            }else if (difference == null){
                generateByDiscount(ids, discount, lowPrice, includeDrinks);
            }else if (ids.size() == 0 && discount == null){
                generateAllByDifference(difference, lowPrice, includeDrinks, cover, vipDishPricePlanId);
            }else if (discount == null){
                generateByDifference(ids, difference, lowPrice, includeDrinks);
            }
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }

    }


    /**
     * 私有方法 1
     * 根据折扣生成所有会员价记录
     *
     * @param discount
     * @param lowPrice
     * @param includeDrinks
     * @param cover
     * @param vipDishPricePlanId
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private void generateAllByDiscount(Integer discount,
                                       BigDecimal lowPrice,
                                       TrueEnums includeDrinks,
                                       TrueEnums cover,
                                       int vipDishPricePlanId) throws SSException{
        //将discount转为BigDecimal类型，方便计算
        BigDecimal bigDecimalDiscount = new BigDecimal(discount);
        BigDecimal zero = new BigDecimal("0.00");
        BigDecimal divisor = new BigDecimal("100.00");
        //定义map用于循环判断
        Map<Integer,BigDecimal> map = new HashMap<Integer, BigDecimal>();
        try{
            //判断折扣是否为空
            if( Assert.isNull(discount) ){
                throw SSException.get(EmenuException.InputDiscountOrDifferenceNotNull);
            }
            //判断折扣是否合法
            if ( discount < 0 && discount > 100 ){
                throw SSException.get(EmenuException.InputDiscountOrDifferenceError);
            }
            //判断会员价方案id是否合法
            if (!Assert.isNull(vipDishPricePlanId) && Assert.lessOrEqualZero(vipDishPricePlanId)) {
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            //如果最低价格为空，则设为0
            if (lowPrice == null){
                lowPrice = zero;
            }

            List<VipDishPrice> vipDishPriceList = this.listByVipDishPricePlanId(vipDishPricePlanId);
            List<Dish> dishList = dishService.listAll();
            //将会员价记录放入map中
            for (VipDishPrice vipDishPrice: vipDishPriceList){
                map.put(vipDishPrice.getDishId(), vipDishPrice.getVipDishPrice());
            }

            //循环菜品id，判断map中是否含有dishId为id的菜品
            //含有则更新，不含有则添加
            for (Dish dish: dishList){
                //不包含酒水时，是酒水（categoryId == 5）则跳出本次循环
                if (includeDrinks.getId() == 0 && dish.getCategoryId() == 5){
                    continue;
                }

                //计算会员价，菜品售价*折扣/100.00；如果会员价小于最低价，则将会员价设为最低价
                BigDecimal vipDishPrice = dishService.queryById(dish.getId()).getPrice().multiply(bigDecimalDiscount).divide(divisor);
                Integer i = vipDishPrice.compareTo(lowPrice);
                if (i < 0) {
                    vipDishPrice = lowPrice;
                }
                //覆盖已有会员价项目，则更新所有的；map中含有的菜品进行更新，不含有的菜品进行添加;
                //否则只插入会员价记录
                if (cover.getId() == 1){
                    if (map.containsKey(dish.getId())){
                        VipDishPrice vipDishPriceRecord = queryById(this.queryByDishIdAndVipDishPricePlanId(dish.getId(), vipDishPricePlanId).getId());
                        vipDishPriceRecord.setVipDishPrice(vipDishPrice);//写入会员价
                        this.updateVipDishPrice(vipDishPriceRecord);
                    } else {
                        VipDishPrice vipDishPriceRecord = new VipDishPrice();
                        vipDishPriceRecord.setDishId(dish.getId());
                        vipDishPriceRecord.setVipDishPricePlanId(vipDishPricePlanId);
                        vipDishPriceRecord.setVipDishPrice(vipDishPrice);
                        this.newVipDishPrice(vipDishPriceRecord);
                    }
                }else{
                    if (!map.containsKey(dish.getId())){
                        VipDishPrice vipDishPriceRecord = new VipDishPrice();
                        vipDishPriceRecord.setDishId(dish.getId());
                        vipDishPriceRecord.setVipDishPricePlanId(vipDishPricePlanId);
                        vipDishPriceRecord.setVipDishPrice(vipDishPrice);
                        this.newVipDishPrice(vipDishPriceRecord);
                    }else {
                        continue;
                    }
                }
            }
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }
    }

    /**
     * 私有方法 2
     * 根据折扣更新所选菜品会员价
     *
     * @param ids
     * @param discount
     * @param lowPrice
     * @param includeDrinks
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private void generateByDiscount(List<Integer> ids,
                                    Integer discount,
                                    BigDecimal lowPrice,
                                    TrueEnums includeDrinks) throws SSException{
        //将discount转为BigDecimal类型，方便计算
        BigDecimal bigDecimalDiscount = new BigDecimal(discount);
        BigDecimal zero = new BigDecimal("0.00");
        BigDecimal divisor = new BigDecimal("100.00");
        try{
            //判断折扣是否为空
            if( Assert.isNull(discount) ){
                throw SSException.get(EmenuException.InputDiscountOrDifferenceNotNull);
            }
            //判断折扣是否合法
            if ( discount < 0 && discount > 100 ){
                throw SSException.get(EmenuException.InputDiscountOrDifferenceError);
            }
            //如果最低价格为空，则设为0
            if (lowPrice == null){
                lowPrice = zero;
            }

            //根据id和折扣更新会员价
            for (int id: ids) {
                //判断ids是否合法
                if (!Assert.isNull(id) && Assert.lessOrEqualZero(id)) {
                    throw SSException.get(EmenuException.VipDishPriceIdError);
                }

                VipDishPrice vipDishPriceRecord = this.queryById(id);
                //不包含酒水且当前菜品为酒水则跳出当前循环
                if (includeDrinks.getId() == 0 && dishService.queryById(vipDishPriceRecord.getDishId()).getCategoryId() == 5){
                    continue;
                }
                BigDecimal vipDishPrice = dishService.queryById(vipDishPriceRecord.getDishId()).getPrice().multiply(bigDecimalDiscount).divide(divisor);
                //判断是否低于最低价
                Integer i = vipDishPrice.compareTo(lowPrice);
                if (i < 0) {
                    vipDishPrice = lowPrice;
                }
                vipDishPriceRecord.setVipDishPrice(vipDishPrice);
                this.updateVipDishPrice(vipDishPriceRecord);
            }
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }
    }


    /**
     * 私有方法 3
     * 根据差价生成所有会员价记录
     *
     * @param difference
     * @param lowPrice
     * @param includeDrinks
     * @param cover
     * @param vipDishPricePlanId
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private void generateAllByDifference(BigDecimal difference,
                                         BigDecimal lowPrice,
                                         TrueEnums includeDrinks,
                                         TrueEnums cover,
                                         int vipDishPricePlanId) throws SSException{
        //定义map用于循环判断
        Map<Integer,BigDecimal> map = new HashMap<Integer, BigDecimal>();
        BigDecimal zero = new BigDecimal("0.00");
        try {
            //判断差价是否为空
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

            List<VipDishPrice> vipDishPriceList = this.listByVipDishPricePlanId(vipDishPricePlanId);
            List<Dish> dishList = dishService.listAll();
            //将会员价记录放入map中
            for (VipDishPrice vipDishPrice: vipDishPriceList){
                map.put(vipDishPrice.getDishId(), vipDishPrice.getVipDishPrice());
            }

            //循环菜品id，判断map中是否含有dishId为id的菜品
            //含有则更新，不含有则添加
            for (Dish dish: dishList){
                //不包含酒水时，是酒水（categoryId == 5）则跳出本次循环
                if (includeDrinks.getId() == 0 && dish.getCategoryId() == 5){
                    continue;
                }

                //计算会员价，菜品售价-差价；如果会员价小于最低价，则将最低价设为会员价
                BigDecimal vipDishPrice = dishService.queryById(dish.getId()).getPrice().subtract(difference);
                Integer i = vipDishPrice.compareTo(lowPrice);
                if (i < 0) {
                    vipDishPrice = lowPrice;
                }
                //覆盖已有会员价项目，则更新所有的；map中含有的菜品进行更新，不含有的菜品进行添加;
                //否则只插入会员价记录
                if (cover.getId() == 1){
                    if (map.containsKey(dish.getId())){
                        VipDishPrice vipDishPriceRecord = queryById(this.queryByDishIdAndVipDishPricePlanId(dish.getId(),vipDishPricePlanId).getId());
                        vipDishPriceRecord.setVipDishPrice(vipDishPrice);//写入会员价
                        this.updateVipDishPrice(vipDishPriceRecord);
                    } else {
                        VipDishPrice vipDishPriceRecord = new VipDishPrice();
                        vipDishPriceRecord.setDishId(dish.getId());
                        vipDishPriceRecord.setVipDishPricePlanId(vipDishPricePlanId);
                        vipDishPriceRecord.setVipDishPrice(vipDishPrice);
                        this.newVipDishPrice(vipDishPriceRecord);
                    }
                }else{
                    if (!map.containsKey(dish.getId())){
                        VipDishPrice vipDishPriceRecord = new VipDishPrice();
                        vipDishPriceRecord.setDishId(dish.getId());
                        vipDishPriceRecord.setVipDishPricePlanId(vipDishPricePlanId);
                        vipDishPriceRecord.setVipDishPrice(vipDishPrice);
                        this.newVipDishPrice(vipDishPriceRecord);
                    }else {
                        continue;
                    }
                }
            }
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipDishPriceFail);
        }
    }

    /**
     * 私有方法 4
     * 根据差价更新所选会员价记录
     *
     * @param ids
     * @param difference
     * @param lowPrice
     * @param includeDrinks
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private void generateByDifference(List<Integer> ids,
                                      BigDecimal difference,
                                      BigDecimal lowPrice,
                                      TrueEnums includeDrinks) throws SSException{
        BigDecimal zero = new BigDecimal("0.00");
        try{
            //判断折扣是否为空
            if( Assert.isNull(difference) ){
                throw SSException.get(EmenuException.InputDiscountOrDifferenceNotNull);
            }

            //如果最低价格为空，则设为0
            if (lowPrice == null){
                lowPrice = zero;
            }

            //根据id和差价更新会员价
            for (int id: ids) {
                //判断ids是否合法
                if (!Assert.isNull(id) && Assert.lessOrEqualZero(id)) {
                    throw SSException.get(EmenuException.VipDishPriceIdError);
                }

                VipDishPrice vipDishPriceRecord = this.queryById(id);
                //不包含酒水且当前菜品为酒水则跳出当前循环
                if (includeDrinks.getId() == 0 && dishService.queryById(vipDishPriceRecord.getDishId()).getCategoryId() == 5){
                    continue;
                }
                BigDecimal vipDishPrice = dishService.queryById(vipDishPriceRecord.getDishId()).getPrice().subtract(difference);
                //判断是否低于最低价
                Integer i = vipDishPrice.compareTo(lowPrice);
                if (i < 0) {
                    vipDishPrice = lowPrice;
                }
                vipDishPriceRecord.setVipDishPrice(vipDishPrice);
                this.updateVipDishPrice(vipDishPriceRecord);
            }
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