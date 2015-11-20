package com.emenu.service.storage;

import com.emenu.common.dto.storage.StorageCheckDto;
import com.emenu.common.dto.storage.StorageSupplierDto;
import com.pandawork.core.common.exception.SSException;

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
     * 库存盘点
     * @param startDate
     * @param endDate
     * @param depotIds
     * @param tagIds
     * @param keyword
     * @return List<StorageCheckDto>
     * @throws SSException
     */
    //套页的时候注意前端传过来的时间是什么格式的
    public List<StorageCheckDto> listSettlementCheck(Date startDate,
                                                     Date endDate,
                                                     List<Integer> depotIds,
                                                     List<Integer> tagIds,
                                                     String keyword) throws Exception;
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
}
