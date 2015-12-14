package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.VipRechargePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * VipRechargePlanMapper
 *
 * @author: yangch
 * @time: 2015/12/14 15:03
 */
public interface VipRechargePlanMapper {
    /**
     * 查询全部充值方案
     * @return
     * @throws Exception
     */
    public List<VipRechargePlan> listAll() throws Exception;

    /**
     * 查询某充值方案名称的数量
     * @param name
     * @return int
     * @throws Exception
     */
    public int countByName(@Param("name") String name) throws Exception;

    /**
     * 根据ID修改充值方案状态
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatus(@Param("id") int id, @Param("status") int status) throws Exception;
}
