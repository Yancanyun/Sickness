package com.emenu.service.storage.impl;

import com.emenu.common.entity.storage.StorageReportIngredient;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.storage.StorageReportIngredientMapper;
import com.emenu.service.storage.StorageReportIngredientService;
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
 * StorageReportIngredientServiceImpl
 *
 * @author xiaozl
 * @date: 2016/6/14
 */
@Service("storageReportIngredientService")
public class StorageReportIngredientServiceImpl implements StorageReportIngredientService{

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private StorageReportIngredientMapper storageReportIngredientMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public StorageReportIngredient newReportIngredient(StorageReportIngredient reportIngredient) throws SSException {
        StorageReportIngredient reportIngredientSave = null;
        try {
            if (checkBeforeSave(reportIngredient)){
                reportIngredientSave = commonDao.insert(reportIngredient);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertReportIngredientFail, e);
        }
        return reportIngredientSave;
    }

    @Override
    public List<StorageReportIngredient> listByReportId(Integer reportId) throws SSException {
        List<StorageReportIngredient> reportIngredientList = Collections.emptyList();
        try {
            if (Assert.isNull(reportId) ||
                    Assert.lessOrEqualZero(reportId)){
                throw SSException.get(EmenuException.ReportIdError);
            }
            reportIngredientList = storageReportIngredientMapper.listByReportId(reportId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return reportIngredientList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public boolean delByReportId(int reportId) throws SSException {
        try {
             if (Assert.lessOrEqualZero(reportId)){
                 throw SSException.get(EmenuException.ReportIdError);
             }
            storageReportIngredientMapper.delByReportId(reportId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public boolean delById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.ReportIdError);
            }
            commonDao.deleteById(StorageReportIngredient.class,id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateById(StorageReportIngredient reportIngredient) throws SSException {
        try {
            if(Assert.isNull(reportIngredient)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            } else {
                commonDao.update(reportIngredient);
            }
            } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStorageReportItemFail, e);
        }
    }

    private boolean checkBeforeSave(StorageReportIngredient reportIngredient) throws SSException{
        System.out.println("xiao");
        if (Assert.isNull(reportIngredient)){
            throw SSException.get(EmenuException.ReportIngredientIsNot);
        }
        if (Assert.isNull(reportIngredient.getIngredientId()) ||
                Assert.lessOrEqualZero(reportIngredient.getIngredientId())){
            throw SSException.get(EmenuException.IngredientIdError);
        }
        reportIngredient.setQuantity(reportIngredient.getCostCardQuantity());
        if (Assert.isNull(reportIngredient.getQuantity())){
            throw SSException.get(EmenuException.QuantityError);
        }
        if (Assert.isNull(reportIngredient.getReportId())){
            throw SSException.get(EmenuException.ReportIdError);
        }
        return true;
    }
}
