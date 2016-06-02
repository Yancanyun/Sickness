package com.emenu.test.order;

import com.emenu.common.dto.order.OrderDishCache;
import com.emenu.service.order.OrderDishCacheService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * OrderDishCacheTest
 *
 * @author: yangch
 * @time: 2016/6/1 15:19
 */
public class OrderDishCacheTest extends AbstractTestCase {
    @Autowired
    private OrderDishCacheService orderDishCacheService;

    @Test
    public void newDish() throws SSException {
        OrderDishCache orderDishCache = new OrderDishCache();
        orderDishCache.setId(0);
        orderDishCache.setDishId(41);
        orderDishCache.setQuantity(5);
        orderDishCache.setRemark("没有备注");
        orderDishCacheService.newDish(1, orderDishCache);
    }
}
