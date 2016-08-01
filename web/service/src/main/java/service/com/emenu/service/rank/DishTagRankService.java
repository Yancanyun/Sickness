package com.emenu.service.rank;

import com.emenu.common.dto.rank.DishSaleRankDto;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * DishTagRankService
 *
 * @Author guofengrui
 * @Date 2016/7/27.
 */
public interface DishTagRankService {

    /**
     * 根据开始时间和结束时间查找大类
     * 存在DishSaleRankDto，此时里面的消费金额和数量对应的是大类的金额和数量
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeriod(Date startTime ,Date endTime) throws SSException;

    /**
     * 查找所有的菜品大类销售情况
     * @return
     * @throws SSException
     */
    public List<DishSaleRankDto> listAll() throws SSException;

    /**
     * 根据开始时间和结束时间查询菜品大类的销售情况
     * 分页查询
     * @param startTime
     * @param endTime
     * @param pageSize
     * @param pageNumber
     * @return
     * @throws SSException
     */
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeriodAndPage(Date startTime
                                                                        ,Date endTime
                                                                        ,Integer pageSize
                                                                        ,Integer pageNumber) throws SSException;

    /**
     * 根据开始时间和结束时间查询数据的条数
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public Integer countByTimePeroidAndTagId(Date startTime,Date endTime) throws SSException;

    /**
     * 根据开始时间和结束时间查询数据并导出表格
     * @param startTime
     * @param endTime
     * @throws SSException
     */
    public void exportToExcel(Date startTime,Date endTime,HttpServletResponse response) throws SSException;
}
