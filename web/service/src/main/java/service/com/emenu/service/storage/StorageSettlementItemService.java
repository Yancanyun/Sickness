package com.emenu.service.storage;

import com.emenu.common.dto.storage.StorageCheckDto;
import com.pandawork.core.common.exception.SSException;

import java.util.Date;
import java.util.List;

/**
 * StorageSettlementItemService
 * 库存结算
 * @author dujuan
 * @date 2015/11/15
 */
public interface StorageSettlementItemService {

    /**
     * 添加结算
     * 根据结算周期数，定期结算
     * @throws SSException
     */
    public void newSettlementItem() throws SSException;

    /**
     * 库存盘点
     * @param startDate
     * @param endDate
     * @param depotIds
     * @param tagIds
     * @param itemId
     * @return List<StorageCheckDto>
     * @throws SSException
     */
    public List<StorageCheckDto> listStorageItemCheck(Date startDate,
                                                      Date endDate,
                                                      List<Integer> depotIds,
                                                      List<Integer> tagIds,
                                                      Integer itemId) throws Exception;


}
