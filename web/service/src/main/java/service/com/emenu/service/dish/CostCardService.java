package com.emenu.service.dish;

import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.CostCardSearchDto;
import com.emenu.common.entity.dish.CostCard;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 成本卡Service
 *
 * @author: quanyibo
 * @time: 2016/5/16 9:09
 **/
public interface CostCardService {

    /**
     * 根据searchDto查询Dto
     * @author: zhangteng
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<CostCardDto> listDtoBySearchDto(CostCardSearchDto searchDto) throws SSException;

}
