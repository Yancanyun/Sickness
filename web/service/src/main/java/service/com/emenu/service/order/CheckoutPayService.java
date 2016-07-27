package com.emenu.service.order;

import com.emenu.common.entity.order.CheckoutPay;
import com.pandawork.core.common.exception.SSException;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CheckoutPayService
 *
 * @author: yangch
 * @time: 2016/7/19 19:22
 */
public interface CheckoutPayService {
    /**
     * 添加结账-支付信息
     * @param checkoutPay
     * @throws SSException
     */
    public void newCheckoutPay (CheckoutPay checkoutPay) throws SSException;

    /**
     * 修改结账-支付信息
     * @param checkoutPay
     * @throws SSException
     */
    public void updateCheckoutPay (CheckoutPay checkoutPay) throws SSException;

    /**
     * 根据结账单ID查询结账-支付信息
     * @param checkoutId
     * @return
     * @throws SSException
     */
    public CheckoutPay queryByCheckoutId (int checkoutId) throws SSException;

    /**
     * 查询该时间之后的所有现金收入
     * @param startTime
     * @return
     * @throws SSException
     */
    public BigDecimal queryCashIncomeFromDate(Date startTime) throws SSException;
}
