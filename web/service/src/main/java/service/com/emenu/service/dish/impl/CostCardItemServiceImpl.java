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
    @Qualifier("costCardItemMapper")
    private CostCardItemMapper costCardItemMapper;

    @Autowired
    @Qualifier("commonDao")
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
    public void updateCostCardItemList(List<CostCardItem> costCardItems,int cardId) throws SSException {
        List<CostCardItem> preItems = new ArrayList<CostCardItem>();//保存原来有而修改的集合中没有的原配料，即删除
        List<CostCardItem> preItems1 = new ArrayList<CostCardItem>();//将原来的原配料复制一份
        List<CostCardItem> updateItems1 = new ArrayList<CostCardItem>();//将修改的原配料复制一份
        List<CostCardItem> updateItems2 = new ArrayList<CostCardItem>();//将修改的原配料复制一份
        for(CostCardItem costCardItem:costCardItems){
            updateItems1.add(costCardItem);
            updateItems2.add(costCardItem);
        }
        try {
            if (Assert.isNull(costCardItems) ||costCardItems.isEmpty()) {
                throw SSException.get(EmenuException.CostCardItemIsNotNUll);
            }
            preItems = costCardItemMapper.listByCardId(cardId);
            if(preItems.isEmpty()){
                costCardItemMapper.newCostCardItems(costCardItems);
            }else {
                for(CostCardItem costCardItem:preItems){
                    preItems1.add(costCardItem);
                }
                preItems.removeAll(costCardItems);
                if(!preItems.isEmpty()) {
                    costCardItemMapper.delByCostCardItems(preItems);
                }
                updateItems1.retainAll(preItems1);//修改过的原配料
                for(CostCardItem costCardItem:updateItems1){
                  costCardItemMapper.updateByCardId(costCardItem);
                }
              /*  costCardItemMapper.updateCostCardItems(updateItems1,cardId);*/
                updateItems2.removeAll(preItems1);//新增的原配料
                if(!updateItems2.isEmpty()) {
                    costCardItemMapper.newCostCardItems(updateItems2);
                }
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
