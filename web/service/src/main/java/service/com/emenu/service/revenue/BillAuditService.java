package com.emenu.service.revenue;

import com.emenu.common.dto.revenue.CheckoutDto;
import com.emenu.common.dto.revenue.CheckoutEachItemSumDto;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**账单稽查
 * @athor pengpeng
 * @time 2016/8/4  10:25
 */
public interface BillAuditService {
    /**
     * 统计该时间段里的账单数
     * @param startDate
     * @param endDate
     * @return
     * @throws SSException
     */
    public Integer countCheckoutByTimePeriod(Date startDate, Date endDate) throws SSException;

    /**
     * 根据时间段返回该时间段的所有结账单信息(已结账的账单)
     * @param startDate
     * @param endDate
     * @return
     * @throws SSException
     */

    public List<CheckoutDto> queryCheckoutByTime(Date startDate, Date endDate) throws SSException;

    /**
     * 求出时间段里的所有账单单项的金钱总和
     * @param checkoutDtoList
     * @return
     * @throws SSException
     *
     * @author pengpeng
     */
    public CheckoutEachItemSumDto sumCheckoutEachItem(List<CheckoutDto> checkoutDtoList) throws SSException;

    /**
     * 根据时间段返回该时间段的所有结账单信息(已结账的账单)
     * 分页查询
     * @param startDate
     * @param endDate
     * @return
     *
     * @author pengpeng
     */
    public List<CheckoutDto> queryCheckoutByTimePeriod(Date startDate, Date endDate, CheckoutDto checkoutDto) throws SSException;

    /**
     * 根据开始时间和结束时间查询数据并导出表格
     * @param startTime
     * @param endTime
     * @throws SSException
     */
    public void exportToExcel(Date startTime,Date endTime,HttpServletResponse response) throws SSException;

}
