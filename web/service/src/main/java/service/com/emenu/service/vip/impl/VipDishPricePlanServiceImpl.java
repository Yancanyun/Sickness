package com.emenu.service.vip.impl;

import com.emenu.common.entity.vip.VipDishPricePlan;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.VipDishPricePlanMapper;
import com.emenu.service.vip.VipDishPricePlanService;
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
 * 会员价方案管理service实现类
 *
 * @author chenyuting
 * @date 2015/11/11 9:32
 */
@Service("vipDishPricePlanService")
public class VipDishPricePlanServiceImpl implements VipDishPricePlanService {

    @Autowired
    private VipDishPricePlanMapper vipDishPricePlanMapper;

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<VipDishPricePlan> listAll() throws SSException{
        List<VipDishPricePlan> vipDishPricePlanList = Collections.emptyList();
        try{
            vipDishPricePlanList = vipDishPricePlanMapper.listAll();
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipDishPricePlanFail);
        }
        return vipDishPricePlanList;
    }

    @Override
    public VipDishPricePlan queryById(int id) throws SSException{
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return vipDishPricePlanMapper.queryById(id);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipDishPricePlanFail);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public VipDishPricePlan newPlan(VipDishPricePlan vipDishPricePlan) throws SSException{
        try{
            //判断会员方案名称是否为空
            if (!checkBeforeSave(vipDishPricePlan)){
                return null;
            }
            return commonDao.insert(vipDishPricePlan);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.InsertVipDishPricePlanFail);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updatePlan(VipDishPricePlan vipDishPricePlan) throws SSException{
        try{
            //判断id是否合法
            if (!Assert.isNull(vipDishPricePlan.getId()) && Assert.lessOrEqualZero(vipDishPricePlan.getId())){
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            //判断会员方案名称是否为空
            if (!this.checkBeforeSave(vipDishPricePlan)){
                throw SSException.get(EmenuException.UpdateVipDishPricePlanFail);
            }
            Assert.isNotNull(vipDishPricePlan);
            commonDao.update(vipDishPricePlan);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.UpdateVipDishPricePlanFail);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delPlanById(int id) throws SSException{
        try {
            //判断id是否合法
            if (!Assert.isNull(id) && Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            commonDao.deleteById(VipDishPricePlan.class, id);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.DeleteVipDishPricePlanFail);
        }
    }

    @Override
    public boolean checkBeforeSave(VipDishPricePlan vipDishPricePlan) throws SSException{
        if (Assert.isNull(vipDishPricePlan)){
            return false;
        }
        Assert.isNotNull(vipDishPricePlan.getName(),EmenuException.VipDishPricePlanNameNotNull);
        return true;
    }
}