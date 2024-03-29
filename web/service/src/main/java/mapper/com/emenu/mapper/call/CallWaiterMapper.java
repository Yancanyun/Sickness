package com.emenu.mapper.call;

import com.emenu.common.entity.call.CallWaiter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CallServiceMapper
 * 服务管理Mapper
 * @author quanyibo
 * @date: 2016/5/23
 */
public interface CallWaiterMapper {

    /**
     * 添加服务
     * @param callWaiter
     * @throws Exception
     */
    public void newCallWaiter(@Param("callWaiter")CallWaiter callWaiter) throws Exception;

    /**
     * 删除服务
     * @param id
     * @throws Exception
     */
    public void delCallWaiter(@Param("id")Integer id) throws Exception;

    /**
     * 更新服务
     * @param callWaiter
     * @throws Exception
     */
    public void updateCallWaiter(@Param("callWaiter")CallWaiter callWaiter) throws Exception;

    /**
     * 查询出所有的服务
     * @param
     * @throws Exception
     */
    public List<CallWaiter> queryAllCallWaiter() throws Exception;

    /**
     * 修改服务的启用状态
     * @param
     * @throws Exception
     */
    public void updateCallWaiterStatus(@Param("status")int status,@Param("id") int id)throws Exception;

    /**
     * 查询同名服务,同名服务再次添加没有意义
     * @param
     * @throws Exception
     */
    public int countCallWaiterByName(@Param("name") String name) throws Exception;
}
