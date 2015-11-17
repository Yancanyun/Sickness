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
     *
     * @throws SSException
     */
    public List<StorageCheckDto> listStorageItemCheck(Date startDate,
                                                      Date endDate,
                                                      List<Integer> depotId,
                                                      List<Integer> tagId,
                                                      String itemName) throws SSException;


}
