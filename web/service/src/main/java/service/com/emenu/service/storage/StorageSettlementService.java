package com.emenu.service.storage;

import com.emenu.common.dto.storage.StorageCheckDto;
import com.emenu.common.dto.storage.StorageSupplierDto;
import com.emenu.common.entity.storage.StorageSettlementItem;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * StorageSettlementService
 * 库存结算
 * @author xiaozl
 * @date 2015/11/15
 */
public interface StorageSettlementService {

    /**
     * 新
     * 添加结算
     * 根据结算周期数，定期结算
     * @throws SSException
     */
    public void newSettlement() throws SSException;

    /**
     * 修改盘点缓存
     * @param key
     * @param quantity
     * @throws SSException
     */
    public void updateSettlementCache(Integer key, BigDecimal quantity) throws SSException;

    /**
     * 查询盘点缓存中的原配料库存
     * @param key
     * @return
     * @throws SSException
     */
    public BigDecimal queryCache(Integer key) throws SSException;

    /**
     * 新
     * 库存盘点（库存物品）获取截止时间时的库存统计结果
     * @param startTime
     * @param endTime
     * @param tagIds
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<StorageCheckDto>  listSettlementCheck(Date startTime,
                                                      Date endTime,
                                                      List<Integer> tagIds,
                                                      String keyword,
                                                      Integer curPage,
                                                      Integer pageSize) throws SSException;

    /**
     * 获取导出数据
     * @return
     * @throws SSException
     */
    public List<StorageCheckDto> listExportData() throws SSException;

    /**
     * 新
     * 计算库存盘点总数
     * @param tagIds
     * @param keyword
     * @return
     * @throws SSException
     */
    public int countSettlementCheck(List<Integer> tagIds, String keyword) throws SSException;

    /**
     * 新
     * 结算中心
     * @param supplierId
     * @param startDate
     * @param endDate
     * @return
     * @throws SSException
     */
    //套页的时候注意前端传过来的时间是什么格式的
    public List<StorageSupplierDto> listSettlementSupplier(Integer supplierId,
                                                           Date startDate,
                                                           Date endDate) throws SSException;

    /**
     * 库存盘点导出EXCEL
     * @param response
     * @throws SSException
     */
    public void exportSettlementCheckToExcel(HttpServletResponse response) throws SSException;

    /**
     * 结算中心导出Excel
     * @param supplierId
     * @param startDate
     * @param endDate
     * @throws SSException
     */
    public void exportSettlementSupplierToExcel(Integer supplierId,
                                                Date startDate,
                                                Date endDate,
                                                HttpServletResponse response)throws SSException;

    public void test();
}
