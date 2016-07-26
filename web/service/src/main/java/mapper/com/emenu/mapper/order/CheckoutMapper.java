package com.emenu.mapper.order;

import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.Order;
import org.apache.ibatis.annotations.Param;

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

}
