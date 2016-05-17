package com.emenu.mapper.dish;

import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.CostCardSearchDto;
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
     * 根据searchDto查询成本卡dto
     * @author: zhangteng
     * @param offset
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<CostCardDto> listDtoBySearchDto(@Param("offset") int offset,
                                                CostCardSearchDto searchDto) throws SSException;

    /**
     * 新增成本卡
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


}
