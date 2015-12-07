package com.emenu.service.vip.impl;

import com.emenu.common.entity.vip.MultipleIntegralPlan;
import com.emenu.common.enums.vip.MultipleIntegralPlanStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.MultipleIntegralPlanMapper;
import com.emenu.service.vip.MultipleIntegralPlanService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * MultipleIntegralPlanServiceImpl
 *
 * @author WangLM
 * @date 2015/12/7 14:17
 */

@Service(value = "multipleIntegralPlanService")
public class MultipleIntegralPlanServiceImpl implements MultipleIntegralPlanService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private MultipleIntegralPlanMapper multipleIntegralPlanMapper;

    @Override
    public List<MultipleIntegralPlan> listAll() throws SSException {
        List<MultipleIntegralPlan> list = Collections.emptyList();
        try {
            list = multipleIntegralPlanMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMultipleIntegralPlanFail, e);
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public MultipleIntegralPlan newMultipleIntegralPlan(MultipleIntegralPlan multipleIntegralPlan) throws SSException {
        try {
            if(!checkBeforeSave(multipleIntegralPlan)){
                return null;
            }
            return commonDao.insert(multipleIntegralPlan);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertMultipleIntegralPlanFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateById(MultipleIntegralPlan multipleIntegralPlan) throws SSException {

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        Assert.lessOrEqualZero(id, EmenuException.MultipleIntegralPlanIdIllegal);
        try {
            commonDao.deleteById(MultipleIntegralPlan.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteMultipleIntegralPlanFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStatusById(int id, MultipleIntegralPlanStatusEnums status) throws SSException {

        Assert.lessOrEqualZero(id, EmenuException.MultipleIntegralPlanIdIllegal);
        Assert.isNotNull(status, EmenuException.MultipleIntegralPlanStatusNotNull);
        try {
            multipleIntegralPlanMapper.updateStatusById(id, status.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateMultipleIntegralPlanFail, e);
        }
    }

    @Override
    public List<MultipleIntegralPlan> listEnabledPlan() throws SSException {
        List<MultipleIntegralPlan> list = Collections.emptyList();
        try {
            list = multipleIntegralPlanMapper.listEnabledPlan();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMultipleIntegralPlanFail, e);
        }
        return list;
    }

    private boolean checkBeforeSave(MultipleIntegralPlan multipleIntegralPlan) throws SSException {
        if (Assert.isNull(multipleIntegralPlan)){
            return false;
        }

        Assert.isNotNull(multipleIntegralPlan.getName(), EmenuException.MultipleIntegralPlanNameNotNull);
        Assert.isNotNull(MultipleIntegralPlanStatusEnums.valueOf(multipleIntegralPlan.getStatus()), EmenuException.MultipleIntegralPlanStatusNotNull);
        Assert.isNotNull(multipleIntegralPlan.getIntegralMultiples(), EmenuException.IntegralMultiplesNotNull);
        if (checkNameExist(multipleIntegralPlan.getName())){
            throw SSException.get(EmenuException.MultipleIntegralPlanNameExist);
        }
        return true;
    }

    private boolean checkNameExist(String name) throws SSException{
        int num = 0;
        try {
            num = multipleIntegralPlanMapper.countByName(name);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return num > 0;
    }
}
