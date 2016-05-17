package com.emenu.service.dish;

import com.emenu.common.dto.dish.CostCardSearchDto;
import com.emenu.common.entity.dish.CostCard;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成本卡Service
 *
 * @author: quanyibo
 * @time: 2016/5/16 9:09
 **/
public interface CostCardService {

    /**
     * 新增成本卡
     *
     * @param costCard
     * @return int
     * @throws SSException
     */
    public int newCostCard(CostCard costCard) throws SSException;

    /**
     * 删除成本卡
     *
     * @param id
     * @return int
     * @throws SSException
     */
    public int delCostCardById(Integer id) throws SSException;

    /**
     * 更新成本卡
     *
     * @param costCard
     * @return int
     * @throws SSException
     */

    public int updateCostCard(CostCard costCard) throws SSException;
}