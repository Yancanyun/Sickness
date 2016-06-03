package com.emenu.mapper.order;

import com.emenu.common.entity.order.Checkout;
import org.apache.ibatis.annotations.Param;

/**
 * CheckoutMapper
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public interface CheckoutMapper {

    /**
     * 根据桌号id查询结账单
     * @param tableId
     * @return
     * @throws Exception
     */
    public Checkout queryByTableId(@Param("tableId")int tableId,@Param("status")int status) throws Exception;

}
