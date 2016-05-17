package com.emenu.mapper.dish;

import com.emenu.common.dto.dish.CostCardItemDto;
import com.emenu.common.entity.dish.CostCardItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成本卡详细Mapper
 *
 * @author xubaorong
 * @date 2016/5/17.
 */
public interface CostCardItemMapper {

    /**
     * 根据成本卡id查询原料详细
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<CostCardItemDto> listByCostCardId(@Param("costCardId")int id) throws Exception;

    /**
     * 批量添加成本卡原料
     *
     * @param costCardItemList
     * @throws Exception
     */
    public void newCostCardItems(@Param("costCardItemList")List<CostCardItem> costCardItemList) throws Exception;

    /**
     * 根据成本卡id删除其所有原料
     *
     * @throws Exception
     */
    public void delByCostCardId(@Param("costCardId")int costCardId) throws Exception;
}
