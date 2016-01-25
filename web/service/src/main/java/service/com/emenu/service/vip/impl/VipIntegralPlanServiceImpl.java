package com.emenu.service.vip.impl;

import com.emenu.common.dto.vip.VipIntegralDto;
import com.emenu.common.entity.vip.VipIntegralPlan;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.common.enums.vip.VipIntegralPlanTypeEnums;
import com.emenu.common.enums.vip.grade.IntegralEnableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.VipIntegralPlanMapper;
import com.emenu.service.vip.VipGradeService;
import com.emenu.service.vip.VipIntegralPlanService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/1/19 9:16
 */
@Service(value = "vipIntegralPlanService")
public class VipIntegralPlanServiceImpl implements VipIntegralPlanService{

    @Autowired
    private VipIntegralPlanMapper vipIntegralPlanMapper;

    @Autowired
    private VipGradeService vipGradeService;

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<VipIntegralPlan> listByGradeId(int gradeId) throws SSException{
        List<VipIntegralPlan> vipIntegralPlanList = Collections.emptyList();
        try{
            if (Assert.isNull(gradeId)
                    && Assert.lessOrEqualZero(gradeId)){
                throw SSException.get(EmenuException.VipGradeIdIllegal);
            }
            vipIntegralPlanList = vipIntegralPlanMapper.listByGradeId(gradeId);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.ListVipIntegralPlanFail);
        }
        return vipIntegralPlanList;
    }

    @Override
    public List<VipIntegralDto> listDtosGradeId(int gradeId) throws SSException{
        List<VipIntegralPlan> vipIntegralPlanList = Collections.emptyList();
        List<VipIntegralDto> vipIntegralDtoList = new ArrayList<VipIntegralDto>();
        try{
            if (Assert.isNull(gradeId)
                    && Assert.lessOrEqualZero(gradeId)){
                throw SSException.get(EmenuException.VipGradeIdIllegal);
            }
            vipIntegralPlanList = this.listByGradeId(gradeId);
            for (VipIntegralPlan vipIntegralPlan:vipIntegralPlanList){
                VipIntegralDto vipIntegralDto = new VipIntegralDto();
                vipIntegralDto.setId(vipIntegralPlan.getId());
                vipIntegralDto.setType(vipIntegralPlan.getType());
                vipIntegralDto.setIntegralType(VipIntegralPlanTypeEnums.valueOf(vipIntegralPlan.getType()).getType());
                vipIntegralDto.setValue(vipIntegralPlan.getValue());
                vipIntegralDtoList.add(vipIntegralDto);
            }
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.ListVipIntegralPlanFail);
        }
        return vipIntegralDtoList;
    }

    /*@Override
    public void newPlan(VipIntegralPlan vipIntegralPlan) throws SSException{
        try{
            if (Assert.isNull(vipIntegralPlan.getGradeId())
                    && Assert.lessOrEqualZero(vipIntegralPlan.getGradeId())){
                throw SSException.get(EmenuException.VipGradeIdIllegal);
            }
            if (!checkBeforeSave(vipIntegralPlan)){
                throw SSException.get(EmenuException.InsertVipIntegralPlanFail);
            }
            vipIntegralPlan.setStatus(StatusEnums.Enabled.getId());
            commonDao.insert(vipIntegralPlan);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.InsertVipIntegralPlanFail);
        }
    }*/

    public void newPlans(List<VipIntegralPlan> vipIntegralPlanList, Integer gradeId) throws SSException{
        try{
            for (VipIntegralPlan vipIntegralPlan: vipIntegralPlanList){
                vipIntegralPlan.setGradeId(gradeId);
            }
            vipIntegralPlanMapper.insetAll(vipIntegralPlanList);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.InsertVipIntegralPlanFail);
        }
    }

    @Override
    public void generateBeforeSave(List<VipIntegralDto> vipIntegralDtoList) throws SSException{

    }

    @Override
    public boolean checkBeforeSave(VipIntegralPlan vipIntegralPlan) throws SSException{
        if (Assert.isNull(vipIntegralPlan.getGradeId())){
            return false;
        }
        Assert.isNotNull(vipIntegralPlan.getType(), EmenuException.VipIntegralPlanTypeNotNull);
        Assert.isNotNull(vipIntegralPlan.getValue(),EmenuException.VipIntegralPlanValueNotNull);
        return true;
    }

    @Override
    public void updatePlan(VipIntegralPlan vipIntegralPlan, IntegralEnableStatusEnums integralStatus) throws SSException{
        try{
            if (Assert.isNull(integralStatus)){
                throw SSException.get(EmenuException.IntegralStatusIllegal);
            }
            if (!checkBeforeSave(vipIntegralPlan)){
                throw SSException.get(EmenuException.InsertVipIntegralPlanFail);
            }
            vipGradeService.updateIntegralStatus(vipIntegralPlan.getGradeId(), integralStatus);
            commonDao.update(vipIntegralPlan);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.UpdateVipIntegralPlanFail);
        }
    }

    @Override
    public void updatePlans(List<VipIntegralPlan> vipIntegralPlans,
                            Integer gradeId,
                            IntegralEnableStatusEnums integralStatus ) throws SSException{
        try{
            if (Assert.isNull(integralStatus)){
                throw SSException.get(EmenuException.IntegralStatusIllegal);
            }
            if (Assert.isNull(gradeId)
                    && Assert.lessOrEqualZero(gradeId)){
                throw SSException.get(EmenuException.VipGradeIdIllegal);
            }
            if(vipIntegralPlans.size() == 0
                    && vipIntegralPlans.isEmpty()){
                vipGradeService.updateIntegralStatus(gradeId, integralStatus);
            }else {
                for (VipIntegralPlan vipIntegralPlan: vipIntegralPlans){
                    vipIntegralPlan.setGradeId(gradeId);
                    this.updatePlan(vipIntegralPlan, integralStatus);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.UpdateVipIntegralPlanFail);
        }
    }

    @Override
    public void deletePlanById(Integer id) throws SSException{
        try{
            if (Assert.isNull(id)
                    && Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.VipIntegralPlanIdIllegal);
            }
            vipIntegralPlanMapper.deletePlanById(id);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.UpdateVipIntegralPlanFail);
        }
    }

    @Override
    public void updateStatus(int gradeId, int status) throws SSException{
        try{
            if (Assert.isNull(gradeId)
                && Assert.lessOrEqualZero(gradeId)){
            throw SSException.get(EmenuException.VipIntegralPlanIdIllegal);
        }
            vipIntegralPlanMapper.updateStatus(gradeId, status);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(EmenuException.UpdateVipIntegralPlanFail);
        }
    }

}