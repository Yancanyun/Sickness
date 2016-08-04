package com.emenu.service.order;

import com.emenu.common.dto.revenue.BackDishCountDto;
import com.emenu.common.entity.order.BackDish;
import com.pandawork.core.common.exception.SSException;

import java.util.Date;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/7/18 11:26
 */
public interface BackDishService {

    /**
     * 根据订单菜品id和数量退菜
     * @param orderDishId
     * @throws SSException
     */
    public void backDishByOrderDishId(Integer orderDishId, Float backNumber, String backRemarks, Integer partyId) throws SSException;

    /**
     * 根据订单id查询退菜
     * @param orderId
     * @return
     * @throws SSException
     */
    public List<BackDish> queryBackDishListByOrderId(Integer orderId) throws SSException;

    /**
     * 根据开始时间和结束时间查询这一段时间的退菜详情
     * 包括开始时间和结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<BackDish> queryOrderByTimePeriod(Date startTime,Date endTime) throws SSException;

    /**
     * 根据开始时间和结束时间查询这一段时间的BackDishCountDto
     * 包括开始时间和结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<BackDishCountDto>queryBackDishCountDtoByTimePeriod (Date startTime,Date endTime) throws SSException;

    /**
     * 根据d查询退菜信息
     * @param id
     * @return
     * @author quanyibo
     * @throws SSException
     */
    public BackDish queryBackDishById(Integer id) throws SSException;

    /**
     * 根据orderDishId查询退菜信息
     * @param orderDishId
     * @return
     * @author quanyibo
     * @throws SSException
     */
    public BackDish queryBackDishByOrderDishId(Integer orderDishId) throws SSException;
}
