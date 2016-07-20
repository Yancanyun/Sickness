package com.emenu.mapper.order;

import com.emenu.common.entity.order.BackDish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 退菜
 *
 * @author chenyuting
 * @date 2016/7/20 16:30
 */
public interface BackDishMapper {

    /**
     * 根据订单id查询退菜列表
     * @param orderId
     * @return
     * @throws Exception
     */
    public List<BackDish> queryBackDishListByOrderId(@Param("orderId")Integer orderId) throws Exception;
}
