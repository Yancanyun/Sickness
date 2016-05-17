package com.emenu.mapper.dish;

import com.emenu.common.entity.dish.CostCard;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成本卡Mapper
 *
 * @author: quanyibo
 * @time: 2016/5/16 9:04
 **/
public interface CostCardMapper {

    /**
     * 新建成本卡
     *
     * @param costCard
     * @return
     * @throws SSException
     */
    public void newCostCard(@Param("costCard")CostCard costCard) throws SSException;

    /**
     * 删除成本卡
     *
     * @param id
     * @return
     * @throws SSException
     */
    public void delCostCardById(@Param("id")Integer id) throws SSException;

    /**
     * 更新成本卡
     *
     * @param costCard
     * @return
     * @throws SSException
     */

    public void updateCostCard(@Param("costCard")CostCard costCard) throws SSException;
}
