package com.emenu.service.service.impl;

import com.emenu.common.entity.service.CallWaiter;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.service.CallWaiterMapper;
import com.emenu.service.service.CallWaiterService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * CallServiceService
 * 呼叫服务service
 * @author quanyibo
 * @date: 2016/5/23
 */
@Service("callWaiterService")
public class CallWaiterServiceImpl implements CallWaiterService {

    @Autowired
    CallWaiterMapper callWaiterMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public int newCallWaiter(CallWaiter callWaiter) throws SSException
    {
        try {
            if(Assert.isNotNull(callWaiter.getName())
                    &&!Assert.lessOrEqualZero(callWaiter.getWeight())
                    &&!Assert.lessZero(callWaiter.getStatus()))
            {
                callWaiterMapper.newCallWaiter(callWaiter);
                return 1;
            }
            else
                return 0;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertCallWaiterFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public int updateCallWaiter(CallWaiter callWaiter) throws SSException
    {
        try {
            if(Assert.isNotNull(callWaiter.getName())
                    &&!Assert.lessOrEqualZero(callWaiter.getWeight())
                    &&Assert.isNotNull(callWaiter.getStatus())
                    &&!Assert.lessOrEqualZero(callWaiter.getId()))
            {
                callWaiterMapper.updateCallWaiter(callWaiter);
                return 1;//更新成功
            }
            else
                return 0;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateCallWaiterFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public int delCallWaiter(Integer id) throws SSException
    {
        try {
            if(!Assert.lessOrEqualZero(id))
            {
                callWaiterMapper.delCallWaiter(id);
                return 1;//删除成功
            }
            else
                return 0;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteCallWaiterFail, e);
        }
    }

    @Override
    public List<CallWaiter> queryAllCallWaiter() throws SSException
    {
        List<CallWaiter> callWaiter = new ArrayList<CallWaiter>();
        try
        {
            callWaiter=callWaiterMapper.queryAllCallWaiter();
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryAllCallWaiterFail, e);
        }
        return callWaiter;
    }

    @Override
    public int updateCallWaiterStatus(int status, int id)throws SSException
    {
        try
        {
            if(!Assert.lessOrEqualZero(id))
            {
                callWaiterMapper.updateCallWaiterStatus(status,id);
                return  1;
            }
            else
                return 0;
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateCallWaiterStatusFail, e);
        }
    }
}
