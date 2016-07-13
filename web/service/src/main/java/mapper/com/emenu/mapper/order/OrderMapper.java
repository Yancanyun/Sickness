package com.emenu.mapper.order;

import com.emenu.common.entity.order.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    /**
     * 根据订单状态,盘点状态和当前时间之前查询订单列表
     * @param status,isCheck,date
     * @return
     * @throws Exception
     */
    public List<Order> listOrderByStatusAndIsCheckAndDate(@Param("status")Integer status
            ,@Param("isSettlemented")Integer isSettlemented
            ,@Param("date")Date date) throws Exception;

    /**
     * 根据订单Id查询订单
     * @param id
     * @return
     * @throws Exception
     */

    public Order queryOrderById(@Param("id")Integer id) throws Exception;

    /**
     * 查询一个时间段内的订单,不包活起始时间和结束时间
     * @param startTime,endTime
     * @return
     * @throws Exception
     */
    public List<Order> queryOrderByTimePeroid1(@Param("startTime") Date startTime
            ,@Param("endTime") Date endTime) throws Exception;

    /**
     * 查询一个时间段内的订单,包活起始时间和结束时间
     * @param startTime,endTime
     * @return
     * @throws Exception
     */
    public List<Order> queryOrderByTimePeroid2(@Param("startTime") Date startTime
            ,@Param("endTime") Date endTime) throws Exception;
}
