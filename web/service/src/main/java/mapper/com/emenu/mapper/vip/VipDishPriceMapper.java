package com.emenu.mapper.vip;

import com.emenu.common.dto.vip.VipDishPriceDto;
import com.emenu.common.entity.vip.VipDishPrice;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员价mapper
 *
 * @author chenyuting
 * @date 2015/11/11 9:25
 */
public interface VipDishPriceMapper {

    /**
     * 根据会员价方案id查询会员价记录
     * @param vipDishPlanId
     * @return
     * @throws Exception
     */
    public List<VipDishPrice> listByVipDishPricePlanId(int vipDishPlanId) throws Exception;

    /**
     * 查询会员价Dto列表
     * @param vipDishPricePlanId
     * @return
     * @throws Exception
     */
    public List<VipDishPriceDto> listDishPriceDtos(@Param("vipDishPricePlanId") int vipDishPricePlanId) throws Exception;

    /**
     * 根据关键词查找菜品会员价
     * @param keyword
     * @param vipDishPricePlanId
     * @return
     * @throws Exception
     */
    public List<VipDishPriceDto> listDishPriceDtosByKeyword(@Param("keyword") String keyword,
                                                            @Param("vipDishPricePlanId") int vipDishPricePlanId) throws Exception;

    /**
     * 根据id查询菜品会员价
     * @param id
     * @return
     * @throws Exception
     *//*
    public VipDishPrice queryById(@Param("id") int id) throws Exception;

    *//**
     * 根据菜品id和会员价方案id获取会员价记录
     * @param dishId
     * @param vipDishPricePlanId
     * @return
     * @throws Exception
     */
    public VipDishPrice queryByDishIdAndVipDishPricePlanId(@Param("dishId")int dishId,
                                                           @Param("vipDishPricePlanId")int vipDishPricePlanId) throws Exception;
    /**
     * 批量添加会员价
     *
     * @param vipDishPriceList
     * @throws Exception
     */
    public void insertAll(@Param("vipDishPriceList") List<VipDishPrice> vipDishPriceList) throws Exception;
}
