package com.emenu.service.order;

import com.emenu.common.entity.order.Checkout;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

/**
 * CheckoutService
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public interface CheckoutService {
    /**
     * 根据桌号id查询结账单
     * @param tableId
     * @return
     * @throws Exception
     */
    public Checkout queryByTableId(Integer tableId,Integer status) throws SSException;

    /**
     * 添加结账单
     * @param checkout
     * @throws SSException
     */
    public Checkout newCheckout(Checkout checkout) throws SSException;

    /**
     * 修改结账单
     * @param checkout
     * @throws SSException
     */
    public void updateCheckout(Checkout checkout) throws SSException;
}
