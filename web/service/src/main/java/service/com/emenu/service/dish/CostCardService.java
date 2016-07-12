package com.emenu.service.dish;

import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.CostCardSearchDto;
import com.emenu.common.dto.dish.DishSearchDto;
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
     * 根据id查询成本卡
     * @param id
     * @return
     * @throws SSException
     */
    public CostCardDto queryById(int id) throws SSException;

    /**
     * 新建成本卡
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

    /**
     * 根据searchDto查询成本卡
     *
     * @return List<CostCardDto>
     * @throws SSException
     */
    public List<CostCardDto> queryCostCardDto(DishSearchDto searchDto)throws SSException;

    /**
     * 根据searchDto查询成本卡数量
     *
     * @return
     * @throws SSException
     */
    public int countBySearchDto(DishSearchDto searchDto) throws SSException;

    /**
     * 根据dishId获取成本卡信息
     *
     * @return
     * @throws SSException
     */
    public CostCard queryCostCardByDishId(Integer dishId) throws SSException;
}