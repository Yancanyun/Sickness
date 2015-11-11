package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.DishPricePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员价方案mapper
 *
 * @author chenyuting
 * @date 2015/11/11 9:25
 */
public interface DishPricePlanMapper {

    public List<DishPricePlan> listAll() throws Exception;

    public DishPricePlan queryById(@Param("id") int id) throws Exception;

}
