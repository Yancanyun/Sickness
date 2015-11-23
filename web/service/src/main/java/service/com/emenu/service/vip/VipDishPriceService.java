package com.emenu.service.vip;

import com.emenu.common.dto.vip.VipDishPriceDto;
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
     * 获取所有会员价记录
     * @return
     * @throws SSException
     */
    public List<VipDishPrice> listAll() throws SSException;

    /**
     * 根据会员价方案id获取会员价记录
     * @param vipDishPricePlanId
     * @return
     * @throws SSException
     */
    public List<VipDishPrice> listByVipDishPricePlanId(int vipDishPricePlanId) throws SSException;

    /**
     * 分页显示会员价记录Dto
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipDishPriceDto> listVipDishPriceDtosByPage(int curPage, int pageSize, int vipDishPricePlanId) throws SSException;

    /**
     * 查询数据总量
     * @return
     * @throws SSException
     */
    public int countAll() throws SSException;

    /**
     * 根据关键字查询会员价记录Dto
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipDishPriceDto> listDishPriceDtosByKeyword(String keyword, int curPage, int pageSize) throws SSException;

    /**
     * 添加会员价记录
     * @param vipDishPrice
     * @throws SSException
     */
    public void newVipDishPrice(VipDishPrice vipDishPrice) throws SSException;

    /**
     * 更新会员价记录
     * @param vipDishPrice
     * @throws SSException
     */
    public void updateVipDishPrice(VipDishPrice vipDishPrice) throws SSException;

    /**
     * 根据id查找会员价记录
     * @param id
     * @return
     * @throws SSException
     */
    public VipDishPrice queryById(int id) throws SSException;

    /**
     * 根据dishId判断是否存在id为dishId的菜品
     * @param dishId
     * @return
     * @throws SSException
     */
    public int countByDishId(int dishId) throws SSException;

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
     * @param ids
     * @param discount
     * @param difference
     * @param lowPrice
     * @param includeDrinks
     * @param cover
     * @param vipDishPricePlanId
     * @throws SSException
     */
    public void generateVipDishPrice(List<Integer> ids,
                                     Integer discount,
                                     BigDecimal difference,
                                     BigDecimal lowPrice,
                                     TrueEnums includeDrinks,
                                     TrueEnums cover,
                                     int vipDishPricePlanId) throws SSException;

    /**
     * 根据折扣自动生成全部会员价记录
     * @param ids
     * @param discount
     * @param lowPrice
     * @param includeDrinks
     * @param cover
     * @param vipDishPricePlanId
     * @throws SSException
     *//*
    public void generateAllByDiscount(List<Integer> ids, int discount, BigDecimal lowPrice, TrueEnums includeDrinks, TrueEnums cover,int vipDishPricePlanId) throws SSException;


    *//**
     * 根据固定减少金额生成全部会员价记录
     * @param ids
     * @param difference
     * @param lowPrice
     * @param includeDrinks
     * @param cover
     * @param vipDishPricePlanId
     * @throws SSException
     *//*
    public void generateAllByDifference(List<Integer> ids, BigDecimal difference, BigDecimal lowPrice, TrueEnums includeDrinks, TrueEnums cover,int vipDishPricePlanId) throws SSException;

    *//**
     * 单选或多选根据差价生成会员价记录
     * @param ids
     * @param difference
     * @throws SSException
     *//*
    public void generateByDifference(List<Integer> ids, BigDecimal difference) throws SSException;*/


    //public void generateAllByDifference(List<Integer> ids, int discount, int includeWine, BigDecimal lowPrice) throws SSException;

}
