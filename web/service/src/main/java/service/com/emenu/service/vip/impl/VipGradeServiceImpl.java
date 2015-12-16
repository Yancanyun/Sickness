package com.emenu.service.vip.impl;

import com.emenu.common.entity.vip.VipGrade;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.VipGradeMapper;
import com.emenu.service.vip.VipGradeService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * VipGradeServiceImpl
 *
 * @author Wang LM
 * @date 2015/12/14 17:21
 */
@Service(value = "vipGradeService")
public class VipGradeServiceImpl implements VipGradeService{

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private VipGradeMapper vipGradeMapper;

    @Override
    public List<VipGrade> listAll() throws SSException {
        List<VipGrade> list = Collections.emptyList();
        try {
            list = vipGradeMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipGradeFail, e);
        }
        return list;
    }

    @Override
    public VipGrade newVipGrade(VipGrade vipGrade) throws SSException {
        if (!checkBeforeSave(vipGrade)){
            return null;
        }
        try {
            return commonDao.insert(vipGrade);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertVipGradeFail, e);
        }
    }

    @Override
    public void updateById(VipGrade vipGrade) throws SSException {
        if (!checkBeforeSave(vipGrade)){
            return;
        }
        try {
            commonDao.update(vipGrade);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipGradeFail, e);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        Assert.lessOrEqualZero(id, EmenuException.VipGradeIdIllegal);
        try {
            commonDao.deleteById(VipGrade.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteVipGradeFail, e);
        }
    }

    @Override
    public VipGrade queryById(int id) throws SSException {
        Assert.lessOrEqualZero(id, EmenuException.VipGradeIdIllegal);
        VipGrade vipGrade = null;
        try {
            vipGrade = commonDao.queryById(VipGrade.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipGradeFail, e);
        }
        return vipGrade;
    }

    /**
     * 检查实体及其关键字段是否为空
     *
     * @param vipGrade
     * @return
     * @throws SSException
     */
    private boolean checkBeforeSave(VipGrade vipGrade) throws SSException {
        if (Assert.isNull(vipGrade)){
            return false;
        }

        Assert.isNotNull(vipGrade.getName(), EmenuException.VipGradeNameNotNull);
        if (Assert.isNull(vipGrade.getVipDishPricePlanId()) || Assert.lessOrEqualZero(vipGrade.getVipDishPricePlanId())){
            throw SSException.get(EmenuException.VipDishPricePlanIdIllegal);
        }
        if (Assert.isNull(vipGrade.getMinConsumption())){
            throw SSException.get(EmenuException.MinConsumptionIllegal);
        }
        if (Assert.isNull(vipGrade.getCreditAmount())){
            throw SSException.get(EmenuException.CreditAmountIllegal);
        }
        if (Assert.isNull(vipGrade.getSettlementCycle()) || Assert.lessOrEqualZero(vipGrade.getSettlementCycle())){
            throw SSException.get(EmenuException.SettlementCycleIllegal);
        }
        if (Assert.isNull(vipGrade.getPreReminderAmount())){
            throw SSException.get(EmenuException.PreReminderAmountIllegal);
        }

        return true;
    }
}
