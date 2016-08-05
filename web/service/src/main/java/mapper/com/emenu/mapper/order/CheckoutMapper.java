package com.emenu.mapper.order;

import com.emenu.common.dto.revenue.CheckoutDto;
import com.emenu.common.entity.order.Checkout;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * CheckoutMapper
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public interface CheckoutMapper {
    /**
     * 根据餐台ID及结账单状态查询结账单
     * @param tableId
     * @return
     * @throws Exception
     */
    public Checkout queryByTableIdAndStatus(@Param("tableId")int tableId,
                                            @Param("status")int status) throws Exception;

    /**
     * 返回时间段里已结账的账单数
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public List<Checkout> countCheckoutByTimePeriod(@Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate) throws Exception;

    /**
     * 根据时间段返回该时间段的所有结账单信息(已结账的账单)
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public List<Checkout> queryCheckoutByTime(@Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate) throws Exception;

    /**
     * 根据id查询账单信息
     * @param id
     * @return
     * @throws Exception
     */
    public Checkout queryById(@Param("id") Integer id) throws Exception;
}
