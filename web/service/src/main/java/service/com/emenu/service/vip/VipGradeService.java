package com.emenu.service.vip;

import com.emenu.common.dto.vip.VipGradeDto;
import com.emenu.common.entity.vip.VipGrade;
import com.emenu.common.enums.vip.grade.IntegralEnableStatusEnums;
import com.pandawork.core.common.exception.SSException;

import java.math.BigDecimal;
import java.util.List;

/**
 * VipGradeService
 * 会员等级方案Service
 *
 * @author Wang LM
 * @date 2015/12/14 16:59
 */
public interface VipGradeService {

    /**
     * 查询所有会员等级方案
     *
     * @return
     * @throws SSException
     */
    public List<VipGrade> listAll() throws SSException;

    /**
     * 查询所有会员等级方案(包含会员等级信息)
     * @return
     * @throws SSException
     */
    public List<VipGradeDto> listAllVipGradeDto() throws SSException;

    /**
     * 新增会员等级方案
     *
     * @param vipGrade
     * @return
     * @throws SSException
     */
    public VipGrade newVipGrade(VipGrade vipGrade) throws SSException;

    /**
     * 根据id修改会员等级方案
     *
     * @param vipGrade
     * @throws SSException
     */
    public void updateById(VipGrade vipGrade) throws SSException;

    /**
     * 根据Id删除会员等级方案
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据Id查询会员等级方案
     *
     * @param id
     * @throws SSException
     */
    public VipGrade queryById(int id) throws SSException;

    /**
     * 查询某会员价方案中存在几个会员等级方案
     *
     * @return
     * @throws SSException
     */
    public int countByVipPricePlanId(int vipDishPricePlanId) throws SSException;

    /**
     * 根据消费金额查询对应会员等级
     *
     * @param consumption
     * @return
     * @throws SSException
     */
    public VipGrade queryByConsumption(BigDecimal consumption) throws SSException;

    /**
     * 根据id修改积分启用状态
     *
     * @param id
     * @param status
     * @throws SSException
     */
    public void updateIntegralStatus(int id, IntegralEnableStatusEnums status) throws SSException;

    /**
     * 计算当前使用最低消费金额的等级的数量
     * @param minConsumption
     * @return
     * @throws SSException
     */
    public VipGrade countMinConsumptionUsingNumber(BigDecimal minConsumption) throws SSException;

    /**
     * 根据id和最低金额判断该最低金额是否可以使用
     * @param id
     * @param minConsumption
     * @return
     * @throws SSException
     */
    public Boolean minConsumptionCanUse(Integer id, BigDecimal minConsumption) throws SSException;

}
