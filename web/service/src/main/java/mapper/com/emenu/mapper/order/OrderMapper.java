package com.emenu.mapper.order;

import com.emenu.common.entity.order.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OrderMapper
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public interface OrderMapper {

    /**
     * 根据桌号和订单状态查询订单列表
     * @param tableId
     * @return
     * @throws Exception
     */
    public List<Order> listByTableIdAndStatus(@Param("tableId")int tableId,@Param("status")int status) throws Exception;
}
