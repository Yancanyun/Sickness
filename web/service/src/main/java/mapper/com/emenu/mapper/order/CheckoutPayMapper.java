package com.emenu.mapper.order;

import com.emenu.common.entity.order.CheckoutPay;
import org.apache.ibatis.annotations.Param;

/**
 * CheckoutPayMapper
 *
 * @author: yangch
 * @time: 2016/7/19 19:36
 */
public interface CheckoutPayMapper {
    /**
     * 根据结账单ID查询结账-支付信息
     * @param checkoutId
     * @return
     * @throws Exception
     */
    public CheckoutPay queryByCheckoutId(@Param("checkoutId") int checkoutId) throws Exception;
}
