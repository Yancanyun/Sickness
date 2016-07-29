package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.VipGrade;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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

    /**
     * 查询表中存在几个与传入最低消费金额相同的记录
     *
     * @param minConsumption
     * @return
     * @throws Exception
     */
    public VipGrade countMinConsumptionUsingNumber(BigDecimal minConsumption) throws Exception;

    /**
     * 根据id修改积分启用状态
     *
     * @param id
     * @throws Exception
     */
    public void updateIntegralStatus(@Param("id") int id, @Param("status") int status) throws Exception;

    /**
     * 统计会员等级中使用会员价方案的个数
     * @param vipDishPricePlanId
     * @return
     * @throws Exception
     */
    public int countVipDishPricePlan(int vipDishPricePlanId) throws Exception;
}
