package com.emenu.mapper.storage;

import com.emenu.common.entity.storage.SettlementItem;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * StorageSettlementMapper
 * 库存结算
 * @author dujuan
 * @date 2015/11/16
 */
public interface StorageSettlementMapper {

    /**
     * 根据时间取出结算表最新的一次结算库存数据
     * @param settlementDate
     * @return
     * @throws SSException
     */
    public List<SettlementItem> listByDate(@Param("settlementDate") Date settlementDate) throws SSException;

    /**
     * 根据时间取出某一物品在结算表最新的一次结算库存数据
     * @param settlementDate
     * @param itemId
     * @return
     * @throws SSException
     */
    public SettlementItem queryByDate(@Param("settlementDate") Date settlementDate,
                                      @Param("itemId") int itemId) throws SSException;
}
