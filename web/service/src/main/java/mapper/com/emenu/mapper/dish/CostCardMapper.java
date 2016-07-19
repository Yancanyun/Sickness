package com.emenu.mapper.dish;

import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.CostCard;
import com.emenu.common.entity.dish.Dish;
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
     * 根据id查询成本卡
     * @param id
     * @return
     * @throws Exception
     */
    public CostCardDto queryById(@Param("id")int id) throws Exception;

    /**
     * 新建成本卡
     *
     * @param costCard
     * @return
     * @throws SSException
     */
    public void newCostCard(@Param("costCard")CostCard costCard) throws Exception;

    /**
     * 删除成本卡
     *
     * @param id
     * @return
     * @throws SSException
     */
    public void delCostCardById(@Param("id")Integer id) throws Exception;

    /**
     * 更新成本卡
     *
     * @param costCard
     * @return
     * @throws SSException
     */

    public void updateCostCard(@Param("costCard")CostCard costCard)throws Exception;

    /**
     * 根据searchDto查询成本卡
     *
     * @param offset
     * @param searchDto
     * @return List<CostCardDto>
     * @throws SSException
     */
    public List<CostCardDto> queryCostCardDto(@Param("offset")Integer offset,
                                              @Param("searchDto")DishSearchDto searchDto)throws Exception;

    /**
     * 根据searchDto查询成本卡数量
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countBySearchDto(@Param("searchDto")DishSearchDto searchDto) throws Exception;

    /**
     * 根据dishId获取成本卡
     *
     * @param dishId
     * @return
     * @throws SSException
     */
    public CostCard queryCostCardByDishId(@Param("dishId") Integer dishId) throws Exception;

    /**
     * 查询出所有的成本卡
     *
     * @param
     * @return
     * @throws SSException
     */
    public List<CostCard> listAllCostCard() throws Exception;
}
