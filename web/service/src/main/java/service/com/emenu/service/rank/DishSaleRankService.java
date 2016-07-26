package com.emenu.service.rank;

import com.emenu.common.dto.order.CheckOrderDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.order.OrderDish;
import com.pandawork.core.common.exception.SSException;

import java.util.Date;
import java.util.List;

/**
 * DishSaleRankService
 *
 * @author guofengrui
 * @date 2016/7/26.
 */
public interface DishSaleRankService {
    /**
     * 查询一个时间段内的订单菜品情况,包括起始时间和结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<OrderDish> queryOrderDishByTimePeroid(Date startTime ,Date endTime) throws SSException;
}
