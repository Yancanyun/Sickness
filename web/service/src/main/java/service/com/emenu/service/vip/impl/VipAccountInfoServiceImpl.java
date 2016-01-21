package com.emenu.service.vip.impl;

import com.emenu.common.dto.vip.VipAccountInfoDto;
import com.emenu.common.entity.vip.VipAccountInfo;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.VipAccountInfoMapper;
import com.emenu.service.vip.VipAccountInfoService;
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
 * VipAccountInfoServiceImpl
 * 会员账号信息Service层实现
 *
 * @author xubr
 * @date 2016/1/18.
 */
@Service("vipAccountInfoService")
public class VipAccountInfoServiceImpl implements VipAccountInfoService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private VipAccountInfoMapper vipAccountInfoMapper;

    @Override
    public List<VipAccountInfoDto> listByPageAndMin(int curPage, int pageSize,int orderType,String orderBy) throws SSException {
        List<VipAccountInfoDto> countList = Collections.emptyList();
        curPage = curPage <= 0 ? 0:curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return countList;
        }
        try {
            countList = vipAccountInfoMapper.listByPageAndMin(offset, pageSize,orderType,orderBy);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipAccountFailed, e);
        }
        return countList;
    }

    @Override
    public int countAll() throws SSException {
        int count = 0;
        try {
            count = vipAccountInfoMapper.countAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountAllVipAccountInfoFailed,e);
        }
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public VipAccountInfo newVipAccountInfo(VipAccountInfo vipAccountInfo) throws SSException {
        if (Assert.isNull(vipAccountInfo)) {
            throw SSException.get(EmenuException.VipAccountInfoIsNotNull);
        }
        if (Assert.isNull(vipAccountInfo.getPartyId()) || Assert.lessOrEqualZero(vipAccountInfo.getPartyId())) {
            throw SSException.get(EmenuException.VipAccountInfoPartyIdError);
        }
        vipAccountInfo.setStatus(1);
        try {
            return commonDao.insert(vipAccountInfo);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.NewVipAccountInfoFailed);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateVipAccountInfo(VipAccountInfo vipAccountInfo) throws SSException {
        if (Assert.isNull(vipAccountInfo)) {
            throw SSException.get(EmenuException.VipAccountInfoIsNotNull);
        }
        if (Assert.isNull(vipAccountInfo.getPartyId()) || Assert.lessOrEqualZero(vipAccountInfo.getPartyId())) {
            throw SSException.get(EmenuException.VipAccountInfoIdError);
        }
        try {
           commonDao.update(vipAccountInfo);
        } catch(Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.UpdateVipAccountInfoFailed);
        }
    }

    @Override
    public void deleteVipAccountInfo(Integer id) throws SSException {
        if(Assert.isNull(id)||Assert.lessOrEqualZero(id)) {
            throw SSException.get(EmenuException.VipAccountInfoIdError);
        }
        try {
            commonDao.deleteById(VipAccountInfo.class,id);
        } catch(Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.DeleteVipAccountInfoFailed);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStatusById(int id, StatusEnums status) throws SSException {
        Integer stateType = 0;
        try {
            if (!Assert.isNull(id) && Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.VipAccountInfoIdError);
            }
            Assert.isNotNull(status, EmenuException.VipAccountInfoStatesError);

            //获取当前更新的状态
            stateType = status.getId();
            //更新状态
            vipAccountInfoMapper.updateStatusById(id, stateType);
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipAccountInfoStatusFail);
        }
    }
}
