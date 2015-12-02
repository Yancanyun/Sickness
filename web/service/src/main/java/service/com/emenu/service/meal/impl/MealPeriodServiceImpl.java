package com.emenu.service.meal.impl;

import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.meal.MealPeriodStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.meal.MealPeriodMapper;
import com.emenu.service.meal.MealPeriodService;
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
 * MealPeriodServiceImpl
 *
 * @author Wang Liming
 * @date 2015/11/10 11:13
 */

@Service(value = "mealPeriodService")
public class MealPeriodServiceImpl implements MealPeriodService{

    @Autowired
    CommonDao commonDao;

    @Autowired
    MealPeriodMapper mealPeriodMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public MealPeriod newMealPeriod(MealPeriod mealPeriod) throws SSException {

        try {
            if(!checkBeforeSave(mealPeriod)){
                return null;
            }
            return commonDao.insert(mealPeriod);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertMealPeriodFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateMealPeriod(MealPeriod mealPeriod) throws SSException {

        try {
            MealPeriod mealPeriod1 = queryById(mealPeriod.getId());
            if (Assert.isNull(mealPeriod.getId())){
                throw SSException.get(EmenuException.MealPeriodInfoIllegal);
            }
            if (!Assert.isNull(mealPeriod.getName())){
                if (!mealPeriod.getName().equals(mealPeriod1.getName())) {
                    if (checkNameExist(mealPeriod.getName())) {
                        throw SSException.get(EmenuException.MealPeriodNameExist);
                    }
                }
            }
            if (!Assert.isNull(mealPeriod.getStatus())){
                if (Assert.isNull(MealPeriodIsCurrentEnums.valueOf(mealPeriod.getStatus()))){
                    throw SSException.get(EmenuException.MealPeriodInfoIllegal);
                }
                if (mealPeriod.getStatus().equals(MealPeriodStatusEnums.Disabled.getId())){
                    if (mealPeriodMapper.countById(mealPeriod.getStatus()) > 0){
                        throw SSException.get(EmenuException.MealPeriodIsUsing);
                    }
                    if (mealPeriod1.getCurrentPeriod().equals(MealPeriodIsCurrentEnums.Using.getId())){
                        throw SSException.get(EmenuException.MealPeriodIsUsing);
                    }
                }
            }
            if (!Assert.isNull(mealPeriod.getWeight())){
                if (Assert.lessOrEqualZero(mealPeriod.getWeight())){
                    throw SSException.get(EmenuException.MealPeriodInfoIllegal);
                }
            }
            commonDao.update(mealPeriod);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateMealPeriodFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.MealPeriodInfoIllegal);
            }
            if (mealPeriodMapper.countById(id) > 0){
                throw SSException.get(EmenuException.MealPeriodIsUsing);
            }
            if (queryById(id).getCurrentPeriod().equals(MealPeriodIsCurrentEnums.Using.getId())){
                throw SSException.get(EmenuException.MealPeriodIsUsing);
            }
            commonDao.deleteById(MealPeriod.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteMealPeriodFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStatusById(int id, MealPeriodStatusEnums status) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id) || Assert.isNull(status)){
                throw SSException.get(EmenuException.MealPeriodInfoIllegal);
            }
            MealPeriod mealPeriod = queryById(id);
            if (mealPeriodMapper.countById(id) > 0){
                throw SSException.get(EmenuException.MealPeriodIsUsing);
            }
            if (mealPeriod.getCurrentPeriod().equals(MealPeriodIsCurrentEnums.Using.getId())){
                throw SSException.get(EmenuException.MealPeriodIsUsing);
            }
            mealPeriodMapper.updateStatusById(id, status.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateMealPeriodFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateCurrentMealPeriod(int id, MealPeriodIsCurrentEnums isCurrent) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id) || Assert.isNull(isCurrent)){
                throw SSException.get(EmenuException.MealPeriodInfoIllegal);
            }
            MealPeriod mealPeriod = queryById(id);
            MealPeriod currentMealPeriod = mealPeriodMapper.queryByCurrentPeriod(MealPeriodIsCurrentEnums.Using.getId());

            //验证要修改为当前餐段的餐段是否已被删除或停用
            if (!Assert.isNull(mealPeriod)){
                if (mealPeriod.getStatus().equals(MealPeriodStatusEnums.Disabled.getId())){
                    throw SSException.get(EmenuException.MealPeriodDisabled);
                }
            } else {
                throw SSException.get(EmenuException.MealPeriodDeleted);
            }

            //验证是否存在当前餐段。如果存在，就将其修改为非当前餐段
            if (!Assert.isNull(currentMealPeriod)){
                if (!Assert.isNull(currentMealPeriod.getId())){
                    mealPeriodMapper.updateCurrentMealPeriod(currentMealPeriod.getId(), MealPeriodIsCurrentEnums.UnUsing.getId());
                }
            }
            mealPeriodMapper.updateCurrentMealPeriod(id, isCurrent.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateMealPeriodFail, e);
        }
    }

    @Override
    public List<MealPeriod> listAll() throws SSException {
        List<MealPeriod> list = Collections.emptyList();
        try {
            list = mealPeriodMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMealPeriodFail, e);
        }
        return list;
    }

    @Override
    public List<MealPeriod> listEnabledMealPeriod() throws SSException {
        List<MealPeriod> list = Collections.emptyList();
        try {
            list = mealPeriodMapper.listEnabledMealPeriod();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMealPeriodFail, e);
        }
        return list;
    }

    @Override
    public MealPeriod queryById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.MealPeriodInfoIllegal);
            }
            return commonDao.queryById(MealPeriod.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMealPeriodFail, e);
        }
    }

    @Override
    public MealPeriod queryByCurrentPeriod(MealPeriodIsCurrentEnums isCurrent) throws SSException {
        try {
            if (Assert.isNull(isCurrent)){
                return null;
            }
            return mealPeriodMapper.queryByCurrentPeriod(isCurrent.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMealPeriodFail, e);
        }
    }

    /**
     * 判断同名餐段是否存在
     *
     * @param name
     * @return
     * @throws SSException
     */
    private boolean checkNameExist(String name) throws SSException{
        int num = 0;
        try {
            num = mealPeriodMapper.countByName(name);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return num > 0;
    }

    /**
     * 检查实体及其关键字段是否为空
     *
     * @param mealPeriod
     * @return
     * @throws SSException
     */
    private boolean checkBeforeSave(MealPeriod mealPeriod) throws SSException {
        if (Assert.isNull(mealPeriod)){
            return false;
        }

        Assert.isNotNull(mealPeriod.getName(), EmenuException.MealPeriodNameNotNull);
        Assert.isNotNull(MealPeriodIsCurrentEnums.valueOf(mealPeriod.getStatus()), EmenuException.MealPeriodStatusIllegal);
        if (Assert.isNull(mealPeriod.getWeight()) || Assert.lessOrEqualZero(mealPeriod.getWeight())){
            throw SSException.get(EmenuException.MealPeriodWeightIllegal);
        }
        if (checkNameExist(mealPeriod.getName())){
            throw SSException.get(EmenuException.MealPeriodNameExist);
        }
        return true;
    }
}
