package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.DishPrice;

import java.util.List;

/**
 * 会员价mapper
 *
 * TODO
 *
 * @author chenyuting
 * @date 2015/11/11 9:25
 */
public interface DishPriceMapper {

    public List<DishPrice> listAll() throws Exception;
}
