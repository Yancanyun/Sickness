package com.emenu.service.vip.impl;

import com.emenu.common.dto.vip.VipCountInfoDto;
import com.emenu.common.entity.vip.VipCountInfo;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.VipCountInfoMapper;
import com.emenu.service.vip.VipCountInfoService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * VipCountInfoServiceImpl
 * 会员账号信息Service层实现
 *
 * @author xubr
 * @date 2016/1/18.
 */
@Service("vipCountInfoService")
public class VipCountInfoServiceImpl implements VipCountInfoService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private VipCountInfoMapper vipCountInfoMapper;

    @Override
    public List<VipCountInfoDto> listByPageAndMin(int curPage, int pageSize,int orderType,String orderBy) throws SSException {
        List<VipCountInfoDto> countList = Collections.emptyList();
        curPage = curPage <= 0 ? 0:curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return countList;
        }
        try {
            countList = vipCountInfoMapper.listByPageAndMin(offset, pageSize,orderType,orderBy);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipCountFailed, e);
        }
        return countList;
    }

    @Override
    public int countAll() throws SSException {
        int count = 0;
        try {
            count = vipCountInfoMapper.countAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountAllVipInfoFailed,e);
        }
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public VipCountInfo newVipCountInfo(VipCountInfo vipCountInfo) throws SSException {
        if (Assert.isNull(vipCountInfo)) {
            throw SSException.get(EmenuException.VipCountInfoIsNotNull);
        }
        if (Assert.isNull(vipCountInfo.getPartyId()) || Assert.lessOrEqualZero(vipCountInfo.getPartyId())) {
            throw SSException.get(EmenuException.VipCountInfoPartyIdError);
        }
        vipCountInfo.setStatus(1);
        try {
            return commonDao.insert(vipCountInfo);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.NewVipCountInfoFailed);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateVipCountInfo(VipCountInfo vipCountInfo) throws SSException {
        if (Assert.isNull(vipCountInfo)) {
            throw SSException.get(EmenuException.VipCountInfoIsNotNull);
        }
        if (Assert.isNull(vipCountInfo.getPartyId()) || Assert.lessOrEqualZero(vipCountInfo.getPartyId())) {
            throw SSException.get(EmenuException.VipCountInfoIdError);
        }
        try {
           commonDao.update(vipCountInfo);
        } catch(Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.UpdateVipCountInfoFailed);
        }
    }

    @Override
    public void deleteVipCountInfo(Integer id) throws SSException {
        if(Assert.isNull(id)||Assert.lessOrEqualZero(id)) {
            throw SSException.get(EmenuException.VipCountInfoIdError);
        }
        try {
            commonDao.deleteById(VipCountInfo.class,id);
        } catch(Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.DeleteVipCountInfoFailed);
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
            Assert.isNotNull(status, EmenuException.VipCountInfoStatesError);

            //获取当前更新的状态
            stateType = status.getId();
            //更新状态
            vipCountInfoMapper.updateStatusById(id, stateType);
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipCountInfoStatusFail);
        }
    }
}
