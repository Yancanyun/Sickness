package com.emenu.service.stock;

import com.emenu.common.dto.stock.StockWarnDto;
import com.emenu.common.entity.stock.Specifications;
import com.emenu.common.entity.stock.StockWarn;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * StockWarnService
 * 库存预警
 *
 * @author yuzhengfei
 * @date 2017/3/13 16:05
 */
public interface StockWarnService {

    /**
     * 添加预警
     *
     * @param stockWarn
     * @return
     * @throws Exception
     */
    public StockWarn newWarn(StockWarn stockWarn) throws SSException;

    /**
     * 根据物品id修改预警状态为已解决
     *
     * @param itemId
     * @throws Exception
     */
    public void updateStateToResolvedByItemId(int itemId) throws SSException;

    /**
     * 根据物品id修改预警状态为已忽略
     *
     * @param itemId
     * @throws Exception
     */
    public void updateStateToIgnoredByItemId(int itemId) throws SSException;

    /**
     * 根据厨房id查询出当前所有未处理的预警信息
     *
     * @param kitchenId
     * @return
     * @throws Exception
     */
    public List<StockWarn> queryAllUntreatedWarn(int kitchenId) throws SSException;

    /**
     * 查询所有未处理的预警信息
     *
     * @return
     * @throws Exception
     */
    public List<StockWarn> queryAllWarn() throws SSException;

    /**
     * 获取所有未处理的预警的数量
     *
     * @return
     * @throws Exception
     */
    public int countAllWarn() throws SSException;

    /**
     * 获取分页信息
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<StockWarn> listByPage(int offset, int pageSize) throws SSException;

    /**
     * 导出Excel表格
     *
     * @param httpServletResponse
     * @throws SSException
     */
    public void exportExcel(HttpServletResponse httpServletResponse) throws SSException;


}
