package com.emenu.service.vip;

import com.emenu.common.entity.vip.VipRechargePlan;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * VipRechargePlanService
 *
 * @author: yangch
 * @time: 2015/12/14 14:55
 */
public interface VipRechargePlanService {
    /**
     * 查询全部充值方案
     * @return
     * @throws SSException
     */
    public List<VipRechargePlan> listAll() throws SSException;

    /**
     * 根据ID查询充值方案
     * @param id
     * @return
     * @throws SSException
     */
    public VipRechargePlan queryById(int id) throws SSException;

    /**
     * 添加充值方案
     * @param vipRechargePlan
     * @return
     * @throws SSException
     */
    public VipRechargePlan newVipRechargePlan(VipRechargePlan vipRechargePlan) throws SSException;

    /**
     * 检查是否有重复的充值方案名称存在
     * @param name
     * @return boolean : true: 存在；false：不存在
     * @throws SSException
     */
    public boolean checkNameIsExist(String name) throws SSException;

    /**
     * 修改充值方案
     * @param id
     * @param vipRechargePlan
     * @throws SSException
     */
    public void updateVipRechargePlan(int id, VipRechargePlan vipRechargePlan) throws SSException;

    /**
     * 删除充值方案
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据ID修改充值方案状态
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatus(int id, int status) throws SSException;
}
