package com.emenu.service.dish.impl;

import com.emenu.common.dto.dish.CostCardItemDto;
import com.emenu.common.entity.dish.CostCardItem;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.CostCardItemMapper;
import com.emenu.service.dish.CostCardItemService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.Log;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.sun.org.apache.bcel.internal.generic.AASTORE;
import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper;
import org.apache.xpath.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 成本卡详细service层 CostCardItemImpl
 *
 * @author xubaorong
 * @date 2016/5/17.
 */
@Service("costCardItemService")
public class CostCardItemServiceImpl implements CostCardItemService {
    @Autowired
    @Qualifier("costCardItemMapper")
    private CostCardItemMapper costCardItemMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public List<CostCardItemDto> listByCostCardId(int costCardId) throws SSException {
        List<CostCardItemDto> costCardItemDtoList = Collections.emptyList();
        try {
            if (Assert.lessOrEqualZero(costCardId)) {
                throw SSException.get(EmenuException.CostCardIdError);
            }
            costCardItemDtoList = costCardItemMapper.listByCostCardId(costCardId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewCostcardItemsFailed);
        }
        return costCardItemDtoList;
    }

    @Override
    public void newCostCardItems(List<CostCardItem> costCardItemList) throws SSException {
        try {
            if (Assert.isNull(costCardItemList) ||costCardItemList.isEmpty()) {
                throw SSException.get(EmenuException.CostCardItemIsNotNUll);
            }
            for(CostCardItem costCardItem:costCardItemList) {
                if (!this.checkCostCardItem(costCardItem)){
                    return;
                }
            }
            costCardItemMapper.newCostCardItems(costCardItemList);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewCostcardItemsFailed);
        }
    }

    @Override
    public void newCostCardItem(CostCardItem costCardItem) throws SSException {
        try {
            if (!this.checkCostCardItem(costCardItem)) {
                return;
            }
            commonDao.insert(costCardItem);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewCostCardItemFailed);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        try{
            if(Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.CostCardItemIdError);
            }
            commonDao.deleteById(CostCardItem.class,id);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelCostCardItemByIdFailed);
        }
    }

    @Override
    public void delByCostCardId(int costCardId) throws SSException {
        try{
            if(Assert.lessOrEqualZero(costCardId)){
                throw SSException.get(EmenuException.CostCardIdError);
            }
            costCardItemMapper.delByCostCardId(costCardId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelCostCardItemByCostCardIdFailed);
        }
    }

    @Override
    public void updateCostCardItem(CostCardItem costCardItem) throws SSException {
        try {
            if(!checkCostCardItem(costCardItem)){
            return;
            }
            commonDao.update(costCardItem);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateCostCardItemFailed);
        }

    }

    private Boolean checkCostCardItem(CostCardItem costCardItem)throws SSException{
        if(Assert.isNull(costCardItem)){
            throw SSException.get(EmenuException.CostCardItemIsNotNUll);
        }
        if(Assert.isNull(costCardItem.getItemType())){
            throw SSException.get(EmenuException.CostCardItemTypeIsNotNull);
        }
        if(Assert.isNull(costCardItem.getIsAutoOut())){
            throw SSException.get(EmenuException.IsAutoOutIsNotNull);
        }
        if(Assert.isNull(costCardItem.getCostCardId())||Assert.lessOrEqualZero(costCardItem.getCostCardId())){
            throw SSException.get(EmenuException.CostCardIdError);
        }
        if(Assert.isNull(costCardItem.getIngredientId())||Assert.lessOrEqualZero(costCardItem.getIngredientId())){
            throw SSException.get(EmenuException.IngredientIdError);
        }
        if(Assert.isNull(costCardItem.getNetCount())|| costCardItem.getNetCount().equals(BigDecimal.ZERO)){
            throw SSException.get(EmenuException.NetCountError);
        }
        if(Assert.isNull(costCardItem.getTotalCount())||costCardItem.getTotalCount().equals(BigDecimal.ZERO)){
            throw SSException.get(EmenuException.TotalCountError);
        }
        return true;
    }
}
