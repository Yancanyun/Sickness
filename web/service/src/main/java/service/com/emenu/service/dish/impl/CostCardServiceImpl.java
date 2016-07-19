package com.emenu.service.dish.impl;

import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.CostCard;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.CostCardItemMapper;
import com.emenu.mapper.dish.CostCardMapper;
import com.emenu.service.dish.CostCardService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CostCardServiceImpl
 *
 * @author:  quanyibo
 * @time: 2016/5/16 9:13
 **/
@Service("costCardService")
public class CostCardServiceImpl implements CostCardService {

    @Autowired
    @Qualifier("costCardMapper")
    private CostCardMapper costCardMapper;

    @Autowired
    @Qualifier("costCardItemMapper")
    private CostCardItemMapper costCardItemMapper;

    @Override
    public CostCardDto queryById(int id) throws SSException {
        CostCardDto costCardDto = null;
        try{
            if(Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.QueryCostCardByIdFailed);
            }
            costCardDto = costCardMapper.queryById(id);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCostCardByIdFailed, e);
        }
        return costCardDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public int newCostCard(CostCard costCard) throws SSException
    {
        try {
            if(!Assert.lessOrEqualZero(costCard.getDishId())&&Assert.isNotNull(costCard.getCostCardNumber())
                    &&Assert.isNotNull(costCard.getMainCost())&&Assert.isNotNull(costCard.getAssistCost())
                    &&Assert.isNotNull(costCard.getDeliciousCost())&&Assert.isNotNull(costCard.getStandardCost()))
            {
                costCardMapper.newCostCard(costCard);
                return 1;//添加成功
            }
            else
            {
                return 0;//添加失败
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CostCardInsertFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public int delCostCardById(Integer id) throws SSException
    {
        try {
            if(!Assert.lessOrEqualZero(id))
            {
                costCardMapper.delCostCardById(id);
                costCardItemMapper.delByCostCardId(id);
                return 1;//删除成功
            }
            else
                return 0;//删除失败
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CostCardDeleteFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public int updateCostCard(CostCard costCard) throws SSException
    {
        try {
            if(!Assert.lessOrEqualZero(costCard.getDishId())&&Assert.isNotNull(costCard.getCostCardNumber())
                    &&Assert.isNotNull(costCard.getMainCost())&&Assert.isNotNull(costCard.getAssistCost())
                    &&Assert.isNotNull(costCard.getDeliciousCost())&&Assert.isNotNull(costCard.getStandardCost()))
            {
                costCardMapper.updateCostCard(costCard);
                return 1;//更新成功
            }
            else
            {
                return 0;//更新失败
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CostCardUpdateFailed, e);
        }
    }

    @Override
    public List<CostCardDto> queryCostCardDto(DishSearchDto searchDto) throws SSException {
        List<CostCardDto> list = Collections.emptyList();
        int pageNo = searchDto.getPageNo() <= 0 ? 0 : searchDto.getPageNo() - 1;
        int offset = pageNo * searchDto.getPageSize();
        try{
            list = costCardMapper.queryCostCardDto(offset,searchDto);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCostCardFailed, e);
        }
        return list;
    }

    @Override
    public int countBySearchDto(DishSearchDto searchDto) throws SSException {
        int count = 0;
        try {
            count = costCardMapper.countBySearchDto(searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCostCardFailed, e);
        }
        return count;
    }

    @Override
    public CostCard queryCostCardByDishId(Integer dishId) throws SSException
    {
        CostCard costCard = new CostCard();
        try {
          if(!Assert.lessOrEqualZero(dishId))
          {
              costCard = costCardMapper.queryCostCardByDishId(dishId);
          }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCostCardFailed, e);
        }
        return costCard;
    }

    @Override
    public List<CostCardDto> listAllCostCardDto()throws SSException
    {
        List<CostCardDto> costCardDtos = new ArrayList<CostCardDto>();
        List<CostCard> costCards = new ArrayList<CostCard>();
        try {
            costCards = costCardMapper.listAllCostCard();
            for(CostCard dto : costCards)
            {
                CostCardDto costCardDto = new CostCardDto();
                costCardDto = costCardMapper.queryById(dto.getId());
                costCardDto.setCostCardItemDtos(costCardItemMapper.listByCostCardId(dto.getId()));
                if(costCardDto!=null)
                    costCardDtos.add(costCardDto);
            }

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException. ListAllCostCardDtoFail, e);
        }
        return costCardDtos;
    }
}
