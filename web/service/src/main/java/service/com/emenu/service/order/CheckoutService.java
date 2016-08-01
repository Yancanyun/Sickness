package com.emenu.service.order;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.rank.CheckoutDto;
import com.emenu.common.dto.rank.CheckoutEachItemSumDto;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.CheckoutPay;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
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
     * 打印结账单
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
     *
     * @author yangch
     */
    public List<Checkout> checkout(int tableId, int partyId, BigDecimal consumptionMoney,
                                   BigDecimal wipeZeroMoney, BigDecimal totalPayMoney,
                                   int checkoutType, String serialNum, int isInvoiced) throws SSException;

    /**
     * 根据餐台ID计算出该餐台若结账，消费金额是多少
     * @param tableId
     * @throws SSException
     *
     * @author yangch
     */
    public BigDecimal calcConsumptionMoney(int tableId) throws SSException;

    /**
     * 根据餐台ID计算出该餐台若结账，房间费用
     * @param tableId
     * @throws SSException
     *
     * @author yangch
     */
    public BigDecimal calcTableMoney(int tableId) throws SSException;

    /**
     * 根据餐台ID、结账人PartyID、消费金额、免单备注对餐台进行免单
     * @param tableId
     * @param partyId
     * @param consumptionMoney
     * @param freeRemark
     * @throws SSException
     *
     * @author yangch
     */
    public List<Checkout> freeOrder(int tableId, int partyId, BigDecimal consumptionMoney,
                                    String freeRemark) throws SSException;

    /**
     * 检查打印机是否可用
     * @throws SSException
     *
     * @author yangch
     */
    public Boolean isPrinterOk() throws SSException;

    /**
     * 结账单的状态为已结账后打印
     * 吧台打印结账单(并桌的话则会将多个结账单合并到一起打印)
     * @throws SSException
     * @param checkouts
     *
     * @author quanyibo
     */
    public void printCheckOut(List<Checkout> checkouts) throws SSException;

    /**
     * 把餐台改成"占用已结账"状态
     * @param tableId
     * @throws SSException
     */
    public void setTableStatusToCheckouted(int tableId) throws SSException;

    /**
     * 根据时间段返回该时间段的所有结账单信息(已结账的账单)
     * @param startDate
     * @param endDate
     * @return
     *
     * @author pengpeng
     */
    public List<CheckoutDto> queryCheckoutByTimePeriod(Date startDate, Date endDate) throws SSException;

    /**
     * 求出时间段里的所有账单单项的金钱总和
     * @param checkoutDtoList
     * @return
     * @throws SSException
     *
     * @author pengpeng
     */
    public CheckoutEachItemSumDto sumCheckoutEachItem(List<CheckoutDto> checkoutDtoList) throws SSException;
}
