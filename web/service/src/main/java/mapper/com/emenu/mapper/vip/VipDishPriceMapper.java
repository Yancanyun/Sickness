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
     * 获取所有会员价记录
     * @return
     * @throws Exception
     */
    public List<VipDishPrice> listAll() throws Exception;

    /**
     * 根据会员价方案id查询会员价记录
     * @param vipDishPlanId
     * @return
     * @throws Exception
     */
    public List<VipDishPrice> listByVipDishPricePlanId(int vipDishPlanId) throws Exception;

    /**
     * 查询会员价Dto列表
     * @param offset
     * @param pageSize
     * @param vipDishPricePlanId
     * @return
     * @throws Exception
     */
    public List<VipDishPriceDto> listDishPriceDtosByPage(@Param("offset") int offset,
                                                         @Param("pageSize") int pageSize,
                                                         @Param("vipDishPricePlanId") int vipDishPricePlanId) throws Exception;

    /**
     * 查询数据总量
     * @param vipDishPricePlanId
     * @return
     * @throws Exception
     */
    public int countAllByVipDishPricePlanId(@Param("vipDishPricePlanId") int vipDishPricePlanId) throws Exception;

    /**
     * 根据关键词查找菜品会员价
     * @param keyword
     * @param vipDishPricePlanId
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<VipDishPriceDto> listDishPriceDtosByKeyword(@Param("keyword") String keyword,
                                                            @Param("vipDishPricePlanId") int vipDishPricePlanId,
                                                            @Param("offset") int offset,
                                                            @Param("pageSize") int pageSize) throws Exception;

    /**
     * 根据id查询菜品会员价
     * @param id
     * @return
     * @throws Exception
     */
    public VipDishPrice queryById(@Param("id") int id) throws Exception;

    /**
     * 根据dishId判断是否存在id为dishId的菜品
     * @param dishId
     * @return
     * @throws Exception
     */
    public int countByDishId(int dishId) throws Exception;

    /**
     * 根据菜品id和会员价方案id获取会员价记录
     * @param dishId
     * @param vipDishPricePlanId
     * @return
     * @throws Exception
     */
    public VipDishPrice queryByDishIdAndVipDishPricePlanId(@Param("dishId")int dishId,
                                                           @Param("vipDishPricePlanId")int vipDishPricePlanId) throws Exception;

}
