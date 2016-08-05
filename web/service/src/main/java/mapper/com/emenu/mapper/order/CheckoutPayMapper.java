package com.emenu.mapper.order;

import com.emenu.common.dto.revenue.CheckoutDto;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.CheckoutPay;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    /**
     * 查询该时间之后的所有现金收入
     * @param startTime
     * @return
     * @throws Exception
     */
    public BigDecimal queryCashIncomeFromDate(@Param("startTime") Date startTime) throws Exception;

    /**
     * 根据时间段返回该时间段的所有结账单信息(已结账的账单)
     * 分页查询
     * @param startDate
     * @param endDate
     * @param offset
     * @param checkoutDto
     * @return
     * @throws Exception
     */
    public List<CheckoutPay> queryCheckoutPayByTimePeriod(@Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate,
                                                    @Param("offset") Integer offset,
                                                    @Param("checkoutDto") CheckoutDto checkoutDto) throws Exception;
}
