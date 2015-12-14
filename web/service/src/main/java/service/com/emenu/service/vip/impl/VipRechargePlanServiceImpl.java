package com.emenu.service.vip.impl;

import com.emenu.common.entity.vip.VipRechargePlan;
import com.emenu.common.enums.vip.VipRechargePlanStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.VipRechargePlanMapper;
import com.emenu.service.vip.VipRechargePlanService;
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
 * VipRechargePlanServiceImpl
 *
 * @author: yangch
 * @time: 2015/12/14 15:00
 */
@Service("vipRechargePlanService")
public class VipRechargePlanServiceImpl implements VipRechargePlanService {
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private VipRechargePlanMapper vipRechargePlanMapper;

    @Override
    public List<VipRechargePlan> listAll() throws SSException {
        List<VipRechargePlan> vipRechargePlanList = Collections.emptyList();

        try {
            vipRechargePlanList = vipRechargePlanMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipRechargePlanFail, e);
        }
        return vipRechargePlanList;
    }

    @Override
    public VipRechargePlan queryById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return commonDao.queryById(VipRechargePlan.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipRechargePlanFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public VipRechargePlan newVipRechargePlan(VipRechargePlan vipRechargePlan) throws SSException {
        try {
            //判断是否重名
            if (checkNameIsExist(vipRechargePlan.getName())) {
                throw SSException.get(EmenuException.VipRechargePlanNameExist);
            }
            //判断名称是否为空
            if (Assert.isNull(vipRechargePlan.getName())) {
                throw SSException.get(EmenuException.VipRechargePlanNameIsNull);
            }
            //将状态设为"可用"
            vipRechargePlan.setStatus(VipRechargePlanStatusEnums.Enabled.getId());

            return commonDao.insert(vipRechargePlan);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertVipRechargePlanFail, e);
        }
    }
    @Override
    public boolean checkNameIsExist(String name) throws SSException {
        //检查Name是否为空
        if (Assert.isNull(name)) {
            return false;
        }
        try {
            if (vipRechargePlanMapper.countByName(name) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }
    
    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateVipRechargePlan(int id, VipRechargePlan vipRechargePlan) throws SSException {
        try {
            //若Name与相应ID在数据库中对应的名称不一致，再去判断该名称是否在数据库中已存在
            if (!vipRechargePlan.getName().equals(queryById(id).getName()) && checkNameIsExist(vipRechargePlan.getName())){
                throw SSException.get(EmenuException.VipRechargePlanNameExist);
            }
            //判断名称是否为空
            if (Assert.isNull(vipRechargePlan.getName())) {
                throw SSException.get(EmenuException.VipRechargePlanNameIsNull);
            }
            commonDao.update(vipRechargePlan);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipRechargePlanFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            //将状态设为"删除"
            vipRechargePlanMapper.updateStatus(id, VipRechargePlanStatusEnums.Deleted.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteVipRechargePlanFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateStatus(int id, int status) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return;
        }
        //检查Status是否合法
        if (Assert.lessZero(status)) {
            return;
        }
        try {
            vipRechargePlanMapper.updateStatus(id, status);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipRechargePlanFail, e);
        }
    }
}
