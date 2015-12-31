package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.VipGrade;

import java.util.List;

/**
 * VipGradeMapper
 *
 * @author Wang LM
 * @date 2015/12/15 10:28
 */
public interface VipGradeMapper {

    /**
     * 查询所有会员等级方案
     *
     * @return
     * @throws Exception
     */
    public List<VipGrade> listAll() throws Exception;

    /**
     * 查询某会员价方案中存在几个会员等级
     *
     * @return
     * @throws Exception
     */
    public int countByVipPricePlanId(int vipDishPricePlanId) throws Exception;
}
