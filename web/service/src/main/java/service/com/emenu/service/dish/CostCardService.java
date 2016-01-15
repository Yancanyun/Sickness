package com.emenu.service.dish;

import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.CostCardSearchDto;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 成本卡Service
 *
 * @author: zhangteng
 * @time: 2015/12/15 11:19
 **/
public interface CostCardService {

    /**
     * 根据searchDto查询Dto
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<CostCardDto> listDtoBySearchDto(CostCardSearchDto searchDto) throws SSException;
}
