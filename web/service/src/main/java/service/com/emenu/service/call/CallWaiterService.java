package com.emenu.service.call;

import com.emenu.common.entity.call.CallWaiter;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * CallWaiterService
 * 后台呼叫服务类型管理service
 * @author quanyibo
 * @date: 2016/5/23
 */

public interface CallWaiterService {

    /**
     * 添加服务
     * @param callWaiter
     * @throws Exception
     */
    public int newCallWaiter(CallWaiter callWaiter) throws SSException;

    /**
     * 删除服务
     * @param id
     * @throws Exception
     */
    public int delCallWaiter(Integer id) throws SSException;

    /**
     * 更新服务
     * @param callWaiter
     * @throws Exception
     */
    public int updateCallWaiter(CallWaiter callWaiter) throws SSException;

    /**
     * 查询出所有的服务
     * @param
     * @throws Exception
     */
    public List<CallWaiter> queryAllCallWaiter() throws SSException;

    /**
     * 修改服务的启用状态
     * @param
     * @throws Exception
     */
    public int updateCallWaiterStatus(int status, int id)throws SSException;
}
