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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private CostCardItemMapper costCardItemMapper;

    @Autowired
    private CommonDao commonDao;

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
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
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updateCostCardItemList(List<CostCardItem> updateCostCardItems,int cardId) throws SSException {
        try {
            if (Assert.isNull(updateCostCardItems)){
                throw SSException.get(EmenuException.CostCardItemIsNotNUll);
            }
            // 获取修改之前原配料详情
            Assert.lessOrEqualZero(cardId,EmenuException.CostCardIdError);
            List<CostCardItem> oldCostCardItemList = Collections.emptyList();
            oldCostCardItemList = costCardItemMapper.listByCardId(cardId);

            // 如果编辑后原配料详情数量为0，则清除原来原配料中的内容
            if (updateCostCardItems.isEmpty()){
                Assert.lessOrEqualZero(cardId,EmenuException.CostCardIdError);
                // 如果编辑之前的的成本卡有成本卡详情，删除
                if (Assert.isNotNull(oldCostCardItemList)
                        && oldCostCardItemList.size()>0){
                    costCardItemMapper.delByCostCardId(cardId);
                    return;
                }
            }
            // 如果编辑之前成本卡详情为空或者没有，添加成本卡详情
            if(oldCostCardItemList.isEmpty()){
                if (updateCostCardItems.size() > 0) {
                    costCardItemMapper.newCostCardItems(updateCostCardItems);
                    return;
                }
            }
            // 如果编辑前有成本卡详情，编辑后也有成本卡详情，则先删除
            if (updateCostCardItems.size()>0
                    && oldCostCardItemList.size()>0){
                Assert.lessOrEqualZero(cardId,EmenuException.CostCardIdError);
                costCardItemMapper.delByCostCardId(cardId);
                costCardItemMapper.newCostCardItems(updateCostCardItems);
                return;
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateCostCardItemFailed);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
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
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
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
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
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
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
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
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
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
     /*   if(Assert.isNull(costCardItem.getNetCount())|| costCardItem.getNetCount().equals(BigDecimal.ZERO)){
                throw SSException.get(EmenuException.NetCountError);
            }
            if(Assert.isNull(costCardItem.getOtherCount())||costCardItem.getOtherCount().equals(BigDecimal.ZERO)){
                throw SSException.get(EmenuException.TotalCountError);
        }*/
        return true;
    }
}
