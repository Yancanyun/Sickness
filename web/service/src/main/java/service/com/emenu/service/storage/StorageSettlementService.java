package com.emenu.service.storage;

import com.emenu.common.dto.storage.StorageCheckDto;
import com.emenu.common.dto.storage.StorageSupplierDto;
import com.emenu.common.entity.storage.StorageSettlementItem;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * StorageSettlementService
 * 库存结算
 * @author dujuan
 * @date 2015/11/15
 */
public interface StorageSettlementService {

    /**
     * 添加结算
     * 根据结算周期数，定期结算
     * @throws SSException
     */
    public void newSettlement() throws SSException;

    /**
     * 库存盘点（库存物品）
     * @param startDate
     * @param endDate
     * @Param supplierId
     * @param depotIds
     * @param tagIds
     * @param keyword
     * @return List<StorageCheckDto>
     * @throws SSException
     */
    //套页的时候注意前端传过来的时间是什么格式的
    public List<StorageCheckDto> listSettlementCheck(Date startDate,
                                                     Date endDate,
                                                     Integer supplierId,
                                                     List<Integer> depotIds,
                                                     List<Integer> tagIds,
                                                     String keyword,
                                                     Integer curPage,
                                                     Integer pageSize) throws SSException;

    /**
     * 库存盘点（原配料）
     * @param startDate
     * @param endDate
     * @param tagIds
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<StorageCheckDto> listSettlementIngredientCheck(Date startDate,
                                                     Date endDate,
                                                     List<Integer> tagIds,
                                                     String keyword,
                                                     Integer curPage,
                                                     Integer pageSize) throws SSException;

    /**
     * 计算库存盘点总数
     * @param startDate
     * @param endDate
     * @Param supplierId
     * @param depotIds
     * @param tagIds
     * @param keyword
     * @return
     * @throws SSException
     */
    public int countSettlementCheck(Date startDate,
                                    Date endDate,
                                    Integer supplierId,
                                    List<Integer> depotIds,
                                    List<Integer> tagIds,
                                    String keyword) throws SSException;

    /**
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
     * @param startDate
     * @param endDate
     * @param supplierId
     * @param depotIds
     * @param tagIds
     * @param keyword
     * @param response
     * @throws SSException
     */
    public void exportSettlementCheckToExcel(Date startDate,
                                             Date endDate,
                                             Integer supplierId,
                                             List<Integer> depotIds,
                                             List<Integer> tagIds,
                                             String keyword,
                                             HttpServletResponse response) throws SSException;

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
