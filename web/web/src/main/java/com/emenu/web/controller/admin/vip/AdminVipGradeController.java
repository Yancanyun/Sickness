package com.emenu.web.controller.admin.vip;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.vip.VipGrade;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * AdminVipGradeController
 *
 * @author Wang LM
 * @date 2015/12/16 10:56
 */

@Controller
@RequestMapping(value = URLConstants.ADMIN_VIP_GRADE_URL)
public class AdminVipGradeController extends AbstractController {

    /**
     * 去会员等级管理页面
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminVipGradeList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toVipGradePage(Model model) {
        try {
            List<VipGrade> vipGrades = vipGradeService.listAll();
            model.addAttribute("vipGrades", vipGrades);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return null;
    }

    /**
     * 去添加页
     *
     * @return
     */
    @Module(ModuleEnums.AdminVipGradeNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNewVipGradePage() {
        return null;
    }

    /**
     * 添加会员等级方案
     *
     * @param vipGrade
     * @param httpServletRequest
     * @param redirectAttributes
     * @return
     */
    @Module(ModuleEnums.AdminVipGradeNew)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String newVipGrade(VipGrade vipGrade, HttpServletRequest httpServletRequest,
                              RedirectAttributes redirectAttributes) {
        try {
            vipGradeService.newVipGrade(vipGrade);
            String successUrl = "/" + URLConstants.ADMIN_VIP_GRADE_URL;
            redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_VIP_GRADE_URL + "/new";
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:" + failedUrl;
        }
    }

    /**
     * 去编辑页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminVipGradeUpdate)
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdateVipGradePage(@PathVariable("id") int id, Model model) {
        try {
            VipGrade vipGrade = vipGradeService.queryById(id);
            model.addAttribute("vipGrade", vipGrade);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return  null;
    }

    /**
     * 编辑会员等级方案
     *
     * @param vipGrade
     * @param httpServletRequest
     * @param redirectAttributes
     * @return
     */
    @Module(ModuleEnums.AdminVipGradeUpdate)
    @RequestMapping(value = "" , method = RequestMethod.PUT)
    public String updateById(VipGrade vipGrade, HttpServletRequest httpServletRequest,
                             RedirectAttributes redirectAttributes) {
        try {
            vipGradeService.updateById(vipGrade);
            String successUrl = "/" + URLConstants.ADMIN_VIP_GRADE_URL;
            redirectAttributes.addFlashAttribute("msg", UPDATE_SUCCESS_MSG);
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_VIP_GRADE_URL + "/update/" + vipGrade.getId();
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:" + failedUrl;
        }
    }

    /**
     * ajax删除会员等级方案
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminVipGradeDelete)
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public JSONObject delById(int id) {
        try {
            vipGradeService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
