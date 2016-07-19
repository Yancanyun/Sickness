package com.emenu.service.order;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.CheckoutPay;
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
     * 根据餐台ID及结账单状态查询结账单
     * @param tableId
     * @param status
     * @return
     * @throws SSException
     */
    public Checkout queryByTableIdAndStatus(int tableId, int status) throws SSException;

    /**
     * 根据餐台ID查询结账单
     * @param tableId
     * @return
     * @throws SSException
     */
    public Checkout queryByTableId(int tableId) throws SSException;

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

    /**
     * 修改结账单
     * @author quanyibo
     * @param tableId
     * @throws SSException
     */
    public JSONObject printCheckOutByTableId(Integer tableId) throws SSException;

    /**
     * 根据订单ID、结账单、结账-支付信息结账
     * @param orderId
     * @param checkout
     * @throws SSException
     */
    public void checkout(int orderId, Checkout checkout, CheckoutPay checkoutPay) throws SSException;
}
