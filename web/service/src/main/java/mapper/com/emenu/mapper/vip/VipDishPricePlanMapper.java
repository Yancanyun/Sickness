package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.VipDishPricePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员价方案mapper
 *
 * @author chenyuting
 * @date 2015/11/11 9:25
 */
public interface VipDishPricePlanMapper {

    /**
     * 获取所有会员价方案
     * @return
     * @throws Exception
     */
    public List<VipDishPricePlan> listAll() throws Exception;

    /**
     * 根据id查询会员价方案
     * @param id
     * @return
     * @throws Exception
     */
    public VipDishPricePlan queryById(@Param("id") int id) throws Exception;

}
