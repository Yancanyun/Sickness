package com.emenu.service.dish.impl;

import com.emenu.common.dto.dish.CostCardSearchDto;
import com.emenu.common.entity.dish.CostCard;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.CostCardMapper;
import com.emenu.service.dish.CostCardService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * CostCardServiceImpl
 *
 * @author: quanyibo
 * @time: 2016/5/16 9:13
 **/
@Service("costCardService")
public class CostCardServiceImpl implements CostCardService {

    @Autowired
    private CostCardMapper costCardMapper;

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

}
