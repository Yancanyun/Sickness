package com.emenu.service.dish.impl;

import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.CostCardSearchDto;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.CostCardMapper;
import com.emenu.service.dish.CostCardService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * CostCardServiceImpl
 *
 * @author: zhangteng
 * @time: 2015/12/15 13:39
 **/
@Service("costCardService")
public class CostCardServiceImpl implements CostCardService {

    @Autowired
    private CostCardMapper costCardMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public List<CostCardDto> listDtoBySearchDto(CostCardSearchDto searchDto) throws SSException {
        List<CostCardDto> list = Collections.emptyList();
        int pageNo = searchDto.getPageNo() <= 0 ? 0 : searchDto.getPageNo() - 1;
        int offset = pageNo * searchDto.getPageSize();
        try {
            list = costCardMapper.listDtoBySearchDto(offset, searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }

        return list;
    }
}
