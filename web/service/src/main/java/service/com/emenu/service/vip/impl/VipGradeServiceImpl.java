package com.emenu.service.vip.impl;

import com.emenu.common.dto.vip.VipGradeDto;
import com.emenu.common.entity.vip.VipDishPricePlan;
import com.emenu.common.entity.vip.VipGrade;
import com.emenu.common.enums.vip.grade.IntegralEnableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.VipGradeMapper;
import com.emenu.service.party.group.vip.VipInfoService;
import com.emenu.service.vip.VipDishPricePlanService;
import com.emenu.service.vip.VipGradeService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * VipGradeServiceImpl
 *
 * @author Wang LM
 * @date 2015/12/14 17:21
 */
@Service(value = "vipGradeService")
public class VipGradeServiceImpl implements VipGradeService{

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private VipGradeMapper vipGradeMapper;

    @Autowired
    private VipDishPricePlanService vipDishPricePlanService;

    @Autowired
    private VipInfoService vipInfoService;

    @Override
    public List<VipGrade> listAll() throws SSException {
        List<VipGrade> list = Collections.emptyList();
        try {
            list = vipGradeMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipGradeFail, e);
        }
        return list;
    }

    @Override
    public List<VipGradeDto> listAllVipGradeDto() throws SSException {
        List<VipGradeDto> vipGradeDtoList = new ArrayList<VipGradeDto>();
        List<VipGrade> vipGradeList = Collections.emptyList();
        try {
            vipGradeList = vipGradeMapper.listAll();

            if (!vipGradeList.isEmpty()) {
                for (VipGrade vipGrade : vipGradeList) {
                    VipGradeDto vipGradeDto = new VipGradeDto();
                    vipGradeDto.setVipGrade(vipGrade);

                    VipDishPricePlan vipDishPricePlan = vipDishPricePlanService.queryById(vipGrade.getVipDishPricePlanId());
                    if (vipDishPricePlan == null){
                        vipGradeDto.setVipDishPricePlanName("暂无会员价方案");
                    }else {
                        vipGradeDto.setVipDishPricePlanName(vipDishPricePlan.getName());
                    }
                    vipGradeDtoList.add(vipGradeDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipGradeFail, e);
        }
        return vipGradeDtoList;
    }

    @Override
    public VipGrade newVipGrade(VipGrade vipGrade) throws SSException {
        if (!checkBeforeSave(vipGrade)){
            return null;
        }
        try {
            //查看数据库中是否有相同最低消费金额的记录，去除与自己的重复
            VipGrade vipGrade1 = vipGradeMapper.countMinConsumptionUsingNumber(vipGrade.getMinConsumption());
            if (!Assert.isNull(vipGrade1)){
                if (vipGrade1.getId() != vipGrade.getId()){
                    throw SSException.get(EmenuException.MinConsumptionExist);
                }
            }
            return commonDao.insert(vipGrade);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertVipGradeFail, e);
        }
    }

    @Override
    public void updateById(VipGrade vipGrade) throws SSException {
        if (!checkBeforeSave(vipGrade)){
            return;
        }
        try {
            //查看数据库中是否有相同最低消费金额的记录，并且忽略其本身那条记录
            VipGrade vipGrade1 = vipGradeMapper.countMinConsumptionUsingNumber(vipGrade.getMinConsumption());
            if (!Assert.isNull(vipGrade1)){
                if (vipGrade1.getId() != vipGrade.getId()){
                    throw SSException.get(EmenuException.MinConsumptionExist);
                }
            }
            commonDao.update(vipGrade);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipGradeFail, e);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        try {
            Assert.lessOrEqualZero(id, EmenuException.VipGradeIdIllegal);
            //判断是否正在被使用
            if (vipInfoService.countByGradeId(id) > 0){
                throw SSException.get(EmenuException.VipGradeIdIsUsing);
            }else {
                commonDao.deleteById(VipGrade.class, id);
            }

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteVipGradeFail, e);
        }
    }

    @Override
    public VipGrade queryById(int id) throws SSException {
        Assert.lessOrEqualZero(id, EmenuException.VipGradeIdIllegal);
        VipGrade vipGrade = null;
        try {
            vipGrade = commonDao.queryById(VipGrade.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipGradeFail, e);
        }
        return vipGrade;
    }

    @Override
    public int countByVipPricePlanId(int vipDishPricePlanId) throws SSException {
        Assert.lessOrEqualZero(vipDishPricePlanId);
        int count = 0;
        try {
            count = vipGradeMapper.countByVipPricePlanId(vipDishPricePlanId);
            return count;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMultipleIntegralPlanFail, e);
        }
    }

    @Override
    public VipGrade queryByConsumption(BigDecimal consumption) throws SSException {
        if (Assert.isNull(consumption)){
            return null;
        }
        try {
            List<VipGrade> vipGrades = vipGradeMapper.listAll();
            Collections.sort(vipGrades, new Comparator<VipGrade>() {
                @Override
                public int compare(VipGrade o1, VipGrade o2) {
                    return o2.getMinConsumption().compareTo(o1.getMinConsumption());
                }
            });
            for (VipGrade vipGrade : vipGrades){
                if (consumption.subtract(vipGrade.getMinConsumption()).compareTo(new BigDecimal(0)) >= 0){
                    return vipGrade;
                }
            }

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipGradeFail, e);
        }
        //传入的数据为空或小于0或没有符合最低消费要求的记录会返回null
        return null;
    }

    @Override
    public void updateIntegralStatus(int id, IntegralEnableStatusEnums status) throws SSException {
        if (Assert.lessOrEqualZero(id)){
            throw SSException.get(EmenuException.VipGradeIdIllegal);
        }
        try {
            vipGradeMapper.updateIntegralStatus(id, status.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipGradeFail, e);
        }
    }

    @Override
    public VipGrade countMinConsumptionUsingNumber(BigDecimal minConsumption) throws SSException{
        try{
            VipGrade vipGrade = vipGradeMapper.countMinConsumptionUsingNumber(minConsumption);
            return vipGrade;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipGradeFail, e);
        }
    }

    /**
     * 检查实体及其关键字段是否为空
     *
     * @param vipGrade
     * @return
     * @throws SSException
     */
    private boolean checkBeforeSave(VipGrade vipGrade) throws SSException {
        if (Assert.isNull(vipGrade)){
            return false;
        }

        Assert.isNotNull(vipGrade.getName(), EmenuException.VipGradeNameNotNull);
        if (Assert.isNull(vipGrade.getVipDishPricePlanId()) || Assert.lessOrEqualZero(vipGrade.getVipDishPricePlanId())){
            throw SSException.get(EmenuException.VipDishPricePlanIdIllegal);
        }
        if (Assert.isNull(vipGrade.getMinConsumption())){
            throw SSException.get(EmenuException.MinConsumptionIllegal);
        }
        if (Assert.isNull(vipGrade.getCreditAmount())){
            throw SSException.get(EmenuException.CreditAmountIllegal);
        }
        if (Assert.isNull(vipGrade.getSettlementCycle()) || Assert.lessOrEqualZero(vipGrade.getSettlementCycle())){
            throw SSException.get(EmenuException.SettlementCycleIllegal);
        }
        if (Assert.isNull(vipGrade.getPreReminderAmount())){
            throw SSException.get(EmenuException.PreReminderAmountIllegal);
        }

        return true;
    }
}
