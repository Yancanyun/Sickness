package com.emenu.service.rank;

import com.emenu.common.dto.order.CheckOrderDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.entity.order.OrderDish;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 营业分析中的菜品销售排行
 * DishSaleRankService
 *
 *
 * @Author guofengrui
 * @Date 2016/7/26.
 */
public interface DishSaleRankService {
    /**
     * 查询一个时间段内的订单菜品情况,包括起始时间和结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<OrderDish> queryOrderDishByTimePeriod(Date startTime ,Date endTime) throws SSException;

    /**
     * 查询一个时间段的DishSaleRankDto，包括起始时间和结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeriod(Date startTime ,Date endTime) throws SSException;

    /**
     *
     * @return找到所有的菜品销售情况
     * @throws SSException
     */
    public List<DishSaleRankDto> listAll() throws SSException;

    /**
     * 根据起始时间和结束时间和菜品大类查询
     * 可用于导出Excel
     * @param startTime
     * @param endTime
     * @param tagId
     * @return
     * @throws SSException
     */
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeriodAndTagId(Date startTime,
                                                                          Date endTime,
                                                                          Integer tagId) throws SSException;

    /**
     * 导出Excel
     * @param startTime
     * @param endTime
     * @param tagIds
     * @throws SSException
     */
    public void exportToExcel(Date startTime ,Date endTime,List<Integer> tagIds,HttpServletResponse response) throws SSException;

    /**
     * 根据起始时间和结束时间和菜品大类查询
     * 分页查询
     * @param startTime
     * @param endTime
     * @param tagIds
     * @param pageSize
     * @param pageNumber
     * @return
     * @throws SSException
     */
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeriodAndTagIdAndPage(Date startTime,
                                                                                 Date endTime,
                                                                                 List<Integer> tagIds,
                                                                                 Integer pageSize,
                                                                                 Integer pageNumber) throws SSException;

    /**
     * 根据起始时间和结束时间和菜品大类Id找到数据条数
     * @param startTime
     * @param endTime
     * @param tagIds
     * @return
     * @throws SSException
     */
    public Integer countByTimePeriodAndTagId(Date startTime,Date endTime,List<Integer>tagIds) throws SSException;
}
