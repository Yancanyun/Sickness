package com.emenu.service.order;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.CheckoutPay;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

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
     * 根据餐台ID、结账人PartyID、消费金额、抹零金额、宾客付款金额、付款方式、流水号、是否开发票对餐台进行结账
     * @param tableId
     * @param partyId
     * @param consumptionMoney
     * @param wipeZeroMoney
     * @param totalPayMoney
     * @param checkoutType
     * @param serialNum
     * @param isInvoiced
     * @throws SSException
     */
    public void checkout(int tableId, int partyId, BigDecimal consumptionMoney,
                         BigDecimal wipeZeroMoney, BigDecimal totalPayMoney,
                         int checkoutType, String serialNum, int isInvoiced) throws SSException;

    /**
     * 根据餐台ID计算出该餐台若结账，消费金额是多少
     * @param tableId
     * @throws SSException
     */
    public BigDecimal calcConsumptionMoney(int tableId) throws SSException;

    /**
     * 根据餐台ID计算出该餐台若结账，房间费用
     * @param tableId
     * @throws SSException
     */
    public BigDecimal calcTableMoney(int tableId) throws SSException;

    /**
     * 根据餐台ID、结账人PartyID、消费金额、免单备注对餐台进行免单
     * @param tableId
     * @param partyId
     * @param consumptionMoney
     * @param freeRemark
     * @throws SSException
     */
    public void freeOrder(int tableId, int partyId, BigDecimal consumptionMoney,
                          String freeRemark) throws SSException;
}
