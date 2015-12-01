package com.emenu.service.vip;

import com.emenu.common.dto.vip.VipDishPriceDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.vip.VipDishPrice;
import com.emenu.common.enums.TrueEnums;
import com.pandawork.core.common.exception.SSException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 会员价Service
 *
 * @author chenyuting
 * @date 2015/11/13 13:51
 */
public interface VipDishPriceService {

    /**
     * 根据菜品id和会员价方案id查询菜品
     * @param dishIds
     * @param vipDishPricePlanId
     * @return
     * @throws SSException
     */
    //public List<VipDishPriceDto> listByDishIdAndVipDishPricePlanId(List<Integer> dishIds, int vipDishPricePlanId) throws SSException;

    /**
     * 根据会员价方案id获取会员价记录
     * @param vipDishPricePlanId
     * @return
     * @throws SSException
     */
    public List<VipDishPrice> listByVipDishPricePlanId(int vipDishPricePlanId) throws SSException;

    /**
     * 根据会员价方案id获取会员价记录Dto
     * @param vipDishPricePlanId
     * @return
     * @throws SSException
     */
    public List<VipDishPriceDto> listVipDishPriceDtos(int vipDishPricePlanId) throws SSException;

    /**
     * 根据关键字查询会员价记录Dto
     * @param vipDishPricePlanId
     * @param keyword
     * @return
     * @throws SSException
     */
    public List<VipDishPriceDto> listVipDishPriceDtosByKeyword(int vipDishPricePlanId, String keyword) throws SSException;

    /**
     * 根据id查找会员价记录
     * @param id
     * @return
     * @throws SSException
     */
    //public VipDishPrice queryById(int id) throws SSException;

    /**
     * 根据菜品id和会员价方案id查询菜品
     * @param dishId
     * @param vipDishPricePlanId
     * @return
     * @throws SSException
     */
    public VipDishPrice queryByDishIdAndVipDishPricePlanId(int dishId, int vipDishPricePlanId) throws SSException;


    /**
     * 生成会员价通用接口
     * @param dishIds
     * @param discount
     * @param difference
     * @param lowPrice
     * @param includeDrinks
     * @param cover
     * @param vipDishPricePlanId
     * @throws SSException
     */
    public void generateVipDishPrice(List<Integer> dishIds,
                                     BigDecimal discount,
                                     BigDecimal difference,
                                     BigDecimal lowPrice,
                                     TrueEnums includeDrinks,
                                     TrueEnums cover,
                                     int vipDishPricePlanId) throws SSException;

    /**
     * 根据dishId和会员价方案id更新会员价
     * @param dishId
     * @param vipDishPricePlanId
     * @param vipDishPrice
     * @throws SSException
     */
    public void updateVipDishPrice(int dishId, int vipDishPricePlanId, BigDecimal vipDishPrice) throws SSException;
}
