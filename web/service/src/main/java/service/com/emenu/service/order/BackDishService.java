package com.emenu.service.order;

import com.emenu.common.entity.order.BackDish;
import com.pandawork.core.common.exception.SSException;

/**
 * @author chenyuting
 * @date 2016/7/18 11:26
 */
public interface BackDishService {

    /**
     * 根据订单菜品id和数量退菜
     * @param orderDishId
     * @throws SSException
     */
    public void newBackDishByOrderDishId(Integer orderDishId, Float backNumber, String backRemarks, Integer partyId) throws SSException;
}
