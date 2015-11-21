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

    public List<VipDishPricePlan> listAll() throws Exception;

    public VipDishPricePlan queryById(@Param("id") int id) throws Exception;

}
