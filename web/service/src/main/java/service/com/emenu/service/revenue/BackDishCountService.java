
package com.emenu.service.revenue;

import com.emenu.common.dto.revenue.BackDishCountDto;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * 退项统计的Service
 * BackDishCountService
 *
 * @author guofr
 * @time 2016/8/3 10:51
 */

public interface BackDishCountService {

/**
     * 根据开始时间和结束时间查询这一段时间的BackDishCountDto
     * 包括开始时间和结束时间
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */

    public List<BackDishCountDto> queryBackDishCountDtoByTimePeriodAndtagIds (List<Integer> tagIds,Date startTime,Date endTime) throws SSException;


/**
     * 根据开始时间和结束时间查询这一段时间的退项清单总量
     * 包括开始时间和结束时间
     * @param tagIds
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */

    public int CountByTimePeriodAndtagIds(List<Integer> tagIds,Date startTime,Date endTime) throws SSException;


/**
     * 根据开始时间和结束时间查询这一段时间的BackDishCountDto
     * 包括开始时间和结束时间
     * 分页查询
     * @param pageNumber
     * @param pageSize
     * @param tagIds
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */

    public List<BackDishCountDto> queryPageBackDishCountDtoByTimePeriodAndtagIds (Integer pageNumber, Integer pageSize, List<Integer> tagIds,Date startTime,Date endTime) throws SSException;

    /**
     * 根据开始时间结束时间和大类id导出Excel表格
     * @param tagIds
     * @param startTime
     * @param endTime
     * @throws SSException
     */
    public void exportToExcel(List<Integer> tagIds,Date startTime,Date endTime,HttpServletResponse response) throws SSException;
}

