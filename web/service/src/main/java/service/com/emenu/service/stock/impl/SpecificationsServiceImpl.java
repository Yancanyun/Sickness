package com.emenu.service.stock.impl;

import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.stock.Specifications;
import com.emenu.common.entity.stock.StockItem;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.stock.SpecificationsMapper;
import com.emenu.service.dish.UnitService;
import com.emenu.service.stock.SpecificationsService;
import com.emenu.service.stock.StockItemService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.Log;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by apple on 17/2/27.
 */
@Service("specificationsService")
public class SpecificationsServiceImpl implements SpecificationsService {

    @Autowired
    private SpecificationsMapper specificationsMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private UnitService unitService;

    @Autowired
    private StockItemService stockItemService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void add(Specifications specifications) throws SSException {
        try {
            if (Assert.isNull(specifications)) {
                throw SSException.get(EmenuException.SpecificationsIsNull);
            }
            commonDao.insert(specifications);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.AddSpecificationsFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void deleteById(int id) throws SSException {
        if (id <= 0) return;
        try {
            Specifications specifications = queryById(id);
            if(!checkBeforeUpdateOrDelete(specifications)){
                throw SSException.get(EmenuException.SpecificationIsUsed);
            }
            commonDao.deleteById(Specifications.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteSpecificationsFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void update(Specifications specifications) throws SSException {
        try {
            if (Assert.isNull(specifications)) {
                throw SSException.get(EmenuException.SpecificationsIsNull);
            }
            if(!checkBeforeUpdateOrDelete(specifications)){
                throw SSException.get(EmenuException.SpecificationIsUsed);
            }
            commonDao.update(specifications);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateSpecificationsFail, e);
        }
    }

    private boolean checkBeforeUpdateOrDelete(Specifications specifications) throws SSException{
            List<StockItem> stockItemList = stockItemService.listAll();
            for(StockItem stockItem:stockItemList){
                List<Integer> specificationList = stockItemService.stringTolist(stockItem.getSpecifications());
                for(Integer specificationId:specificationList){
                    if(specificationId == specifications.getId()){
                        return false;
                    }
                }
            }
            return true;
    }

    @Override
    public Specifications queryById(int id) throws SSException {
        Specifications specifications = new Specifications();
        if (id <= 0) return null;
        try {
            specifications = commonDao.queryById(specifications.getClass(), id);
            return specifications;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QuerySpecificationsByIdFail, e);
        }
    }

    @Override
    public List<Specifications> listAll() throws SSException {
        try {
            List<Specifications> specificationsList = specificationsMapper.listAll();
            return specificationsList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListAllSpecificationsFail, e);
        }
    }

    @Override
    public List<Specifications> listByPage(int offset,int pageSize) throws SSException{
        try{
            if(Assert.isNull(offset)){
                throw SSException.get(EmenuException.OffsetIsNotNull);
            }
            List<Specifications> list = specificationsMapper.listByPage(offset,pageSize);
            //遍历规格立刻表查询单位名称
            for(Specifications specifications:list){
                Unit unit = unitService.queryById(specifications.getOrderUnitId());
                specifications.setOrderUnitName(unit.getName());
                unit = unitService.queryById(specifications.getStorageUnitId());
                specifications.setStorageUnitName(unit.getName());
                unit = unitService.queryById(specifications.getCostCardUnitId());
                specifications.setCostCardUnitName(unit.getName());
            }
            return list;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListByPageFailed, e);
        }
    }

    @Override
    public int count()throws SSException{
        try{
            return specificationsMapper.count();
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountError,e);
        }
    }
}
