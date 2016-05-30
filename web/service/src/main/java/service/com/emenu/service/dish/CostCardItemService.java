package com.emenu.service.dish;

import com.emenu.common.dto.dish.CostCardItemDto;
import com.emenu.common.entity.dish.CostCardItem;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * CostCardItemService
 *
 * @author xubaorong
 * @date 2016/5/17.
 */
public interface CostCardItemService {

    /**
     * 批量添加成本卡原料
     *
     * @param costCardItemList
     * @throws SSException
     */
    public void newCostCardItems(List<CostCardItem> costCardItemList) throws SSException;

    /**
     * 添加一个成本卡原料
     *
     * @param costCardItem
     * @throws SSException
     */
    public void newCostCardItem(CostCardItem costCardItem) throws SSException;

    /**
     * 根据成本卡原料id删除原料
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据成本卡id删除所有原料
     *
     * @param costCardId
     * @throws SSException
     */
    public void delByCostCardId(int costCardId) throws SSException;

    /**
     * 修改成本卡原料
     *
     * @param costCardItem
     * @throws SSException
     */
    public void updateCostCardItem(CostCardItem costCardItem) throws SSException;

    /**
     * 根据成本卡id查询原料列表
     * @param costCardId
     * @return
     * @throws SSException
     */
    public List<CostCardItemDto> listByCostCardId(int costCardId) throws SSException;

    /**
     * 修改升成本卡原料集合
     * @param costCardItems
     * @throws SSException
     */
    public void updateCostCardItemList(List<CostCardItem> costCardItems,int cardId) throws SSException;
}
