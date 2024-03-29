package com.emenu.mapper.storage;

import com.emenu.common.dto.storage.StorageItemDto;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.entity.storage.StorageSettlement;
import com.emenu.common.entity.storage.StorageSettlementIngredient;
import com.emenu.common.entity.storage.StorageSettlementItem;
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
     * 根据结算id获取结算详情
     * @param settlementId
     * @return
     * @throws Exception
     */
    public List<StorageSettlementIngredient> listSettlementIngredientBySettlementId(@Param("settlementId") int settlementId) throws Exception;

    /**
     * 根据时间取出结算表最新的一次结算库存数据
     * @param settlementDate
     * @return
     * @throws SSException
     */
    public List<StorageSettlementItem> listByDate(@Param("settlementDate") Date settlementDate) throws Exception;

    /**
     * 根据时间取出某一物品在结算表最新的一次结算库存数据
     * @param settlementDate
     * @param itemId
     * @return
     * @throws SSException
     */
    public StorageSettlementItem queryByDateAndItemId(@Param("settlementDate") Date settlementDate,
                                                      @Param("itemId") int itemId) throws Exception;

    /**
     * 新
     * 根据时间取出某一原配料在结算表最新的一次结算库存数据
     * @param settlementId
     * @param ingredientId
     * @return
     * @throws Exception
     */
    public StorageSettlementIngredient queryBySettlementIdAndIngredientId(@Param("settlementId") int settlementId,
                                                                  @Param("ingredientId") int ingredientId) throws Exception;


    /**
     * 根据供货商ID和时间段取出这个时间段入库物品
     * @param supplierId
     * @param startDate
     * @param endDate
     * @return
     * @throws SSException
     */
    public List<StorageItemDto> listItemByDateAndSupplierId(@Param("supplierId") Integer supplierId,
                                                            @Param("startDate")Date startDate,
                                                            @Param("endDate")Date endDate) throws Exception;


    /**
     * 获取某个时间之前最后一次结算
     * @return
     * @throws SSException
     */
    public StorageSettlement queryLastSettlement(@Param("settlementDate") Date settlementDate) throws Exception;

    /**
     * 根据条件获取库存物品列表
     * @param supplierPartyId
     * @param depotIds
     * @param tagIds
     * @param keyword
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<StorageItem> listStorageItemByDepotAndTag(@Param("supplierPartyId") Integer supplierPartyId,
                                                          @Param("depotIds") List<Integer> depotIds,
                                                          @Param("tagIds") List<Integer> tagIds,
                                                          @Param("keyword") String keyword,
                                                          @Param("offset") Integer offset,
                                                          @Param("pageSize") Integer pageSize)throws Exception;

    /**
     * 根据条件获取库存物品总数
     * @param supplierPartyId
     * @param depotIds
     * @param tagIds
     * @param keyword
     * @return
     * @throws SSException
     */
    public int countStorageItemByDepotAndTag(@Param("supplierPartyId") Integer supplierPartyId,
                                             @Param("depotIds") List<Integer> depotIds,
                                             @Param("tagIds") List<Integer> tagIds,
                                             @Param("keyword") String keyword)throws SSException;
}
