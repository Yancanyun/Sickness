package com.emenu.mapper.dish;

import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.CostCardSearchDto;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成本卡Mapper
 *
 * @author: zhangteng
 * @time: 2015/12/14 18:32
 **/
public interface CostCardMapper {

    /**
     * 根据searchDto查询成本卡dto
     *
     * @param offset
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<CostCardDto> listDtoBySearchDto(@Param("offset") int offset,
                                                CostCardSearchDto searchDto) throws SSException;
}
