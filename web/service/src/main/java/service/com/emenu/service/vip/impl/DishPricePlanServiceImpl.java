package com.emenu.service.vip.impl;

import com.emenu.common.entity.vip.DishPricePlan;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.DishPricePlanMapper;
import com.emenu.service.vip.DishPricePlanService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 会员价方案管理service实现类
 *
 * @author chenyuting
 * @date 2015/11/11 9:32
 */
@Service("dishPricePlanService")
public class DishPricePlanServiceImpl implements DishPricePlanService{

    @Autowired
    private DishPricePlanMapper dishPricePlanMapper;

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<DishPricePlan> listAll() throws SSException{
        List<DishPricePlan> dishPricePlanList = Collections.emptyList();
        try{
            dishPricePlanList = dishPricePlanMapper.listAll();
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipDishPricePlanFail);
        }
        return dishPricePlanList;
    }

    @Override
    public DishPricePlan queryById(int id) throws SSException{
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return dishPricePlanMapper.queryById(id);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipDishPricePlanFail);
        }
    }

    @Override
    public DishPricePlan newPlan(DishPricePlan dishPricePlan) throws SSException{
        try{
            //判断会员方案名称是否为空
            if (!checkBeforeSave(dishPricePlan)){
                return null;
            }
            return commonDao.insert(dishPricePlan);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.InsertVipDishPricePlanFail);
        }
    }

    @Override
    public void updatePlan(DishPricePlan dishPricePlan) throws SSException{
        try{
            //判断id是否合法
            if (!Assert.isNull(dishPricePlan.getId()) && Assert.lessOrEqualZero(dishPricePlan.getId())){
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            //判断会员方案名称是否为空
            if (!this.checkBeforeSave(dishPricePlan)){
                throw SSException.get(EmenuException.UpdateVipDishPricePlanFail);
            }
            Assert.isNotNull(dishPricePlan);
            commonDao.update(dishPricePlan);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.UpdateVipDishPricePlanFail);
        }
    }

    @Override
    public void delPlanById(int id) throws SSException{
        try {
            //判断id是否合法
            if (!Assert.isNull(id) && Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.VipDishPricePlanIdError);
            }
            commonDao.deleteById(DishPricePlan.class, id);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.DeleteVipDishPricePlanFail);
        }
    }

    @Override
    public boolean checkBeforeSave(DishPricePlan dishPricePlan) throws SSException{
        if (Assert.isNull(dishPricePlan)){
            return false;
        }
        Assert.isNotNull(dishPricePlan.getName(),EmenuException.VipDishPricePlanNameNotNull);
        return true;
    }
}