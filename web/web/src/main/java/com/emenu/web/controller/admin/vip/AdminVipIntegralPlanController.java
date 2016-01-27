package com.emenu.web.controller.admin.vip;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.vip.VipIntegralDto;
import com.emenu.common.dto.vip.VipIntegralTypeDto;
import com.emenu.common.entity.vip.VipGrade;
import com.emenu.common.entity.vip.VipIntegralPlan;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.common.enums.vip.grade.IntegralEnableStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author chenyuting
 * @date 2016/1/21 15:11
 */
@Controller
@RequestMapping(value = URLConstants.ADMIN_VIP_INTEGRAL_URL)
public class AdminVipIntegralPlanController extends AbstractController {

    /**
     * 列表
     * @param gradeId
     * @param model
     * @return
     */
    @RequestMapping(value = "list/{gradeId}", method = RequestMethod.GET)
    public String listAll(@PathVariable("gradeId") Integer gradeId,
                          Model model){
        List<VipIntegralDto> vipIntegralDtoList = Collections.emptyList();
        String gradeName = "";
        try{
            vipIntegralDtoList = vipIntegralPlanService.listDtosGradeId(gradeId);
            VipGrade vipGrade = vipGradeService.queryById(gradeId);
            model.addAttribute("vipIntegralDtoList",vipIntegralDtoList);
            model.addAttribute("vipGrade",vipGrade);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/integral/list_home";
    }

    /**
     * 插入/修改
     * @param vipIntegralTypeDtoList
     * @return
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String newPlans(@RequestParam("vipIntegralTypeDtoList") List<VipIntegralTypeDto> vipIntegralTypeDtoList,
                           @RequestParam("gradeId") int gradeId){
        /*try{
            for (VipIntegralTypeDto vipIntegralTypeDto: vipIntegralTypeDtoList){
                List<String> typeNames = vipIntegralTypeDto.getTypeName(vipIntegralTypeDto);
                for (String typeName: typeNames){
                    if ()
                }
            }
        }*/
        return "";
    }

    /**
     * ajax修改
     * @param gradeId
     * @param status
     * @return
     */
    @RequestMapping(value = "ajax/status", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject updateStatus(@RequestParam("gradeId") int gradeId,
                                   @RequestParam("status") int status){
        try{
            vipIntegralPlanService.updateStatus(gradeId, status);
            vipGradeService.updateIntegralStatus(gradeId, IntegralEnableStatusEnums.valueOf(status));
        }  catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax删除
     * @param id
     * @return
     */
    @RequestMapping(value = "ajax/del", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject deletePlan(@RequestParam("id") int id){
        try{
            vipIntegralPlanService.deletePlanById(id);
        }  catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

}