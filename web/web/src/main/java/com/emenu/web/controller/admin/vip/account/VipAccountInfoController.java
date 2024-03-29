package com.emenu.web.controller.admin.vip.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.vip.VipAccountInfoDto;
import com.emenu.common.entity.vip.VipGrade;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

/**
 * VipCountinfoController
 *
 * @author xubr
 * @date 2016/1/19.
 */
@Controller
@Module(ModuleEnums.AdminVipAccount)
@RequestMapping(value = URLConstants.ADMIN_VIP_ACCOUNT_URL)
public class VipAccountInfoController extends AbstractController{

    /**
     * 去列表页面
     *
     * @return
     */
    @Module(ModuleEnums.AdminVipAccountList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toListVipAccount(Model model) {
        try{
            List<VipGrade> list = vipGradeService.listAll();
            model.addAttribute("list",list);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/account/list_home";
    }

    /**
     * Ajax获取分页数据
     *
     * @param curPage
     * @param pageSize
     * @return
     */
    @Module(ModuleEnums.AdminVipAccountList)
    @RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListVipAccount(@PathVariable("curPage") Integer curPage,
                                         @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam(value = "orderType",required = false) Integer orderType,
                                         @RequestParam(value = "orderBy",required = false)String orderBy,
                                         @RequestParam(value= "keyword", required = false) String keyWord,
                                         @RequestParam(value= "gradeIdList", required = false) List<Integer> gradeIdList) {
        int  dataCount = 0;
        if(orderType==null||Assert.isNull(orderBy)) {
            orderBy= "minConsumption";
            orderType = 0;
        }
        List<VipAccountInfoDto> accountList = Collections.emptyList();
        try {
            accountList = vipAccountInfoService.listByPageAndMin(curPage, pageSize,orderType,orderBy,keyWord,gradeIdList);
            dataCount = vipAccountInfoService.CountByKeywordAndGrade(keyWord,gradeIdList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (VipAccountInfoDto vipAccountInfoDto :accountList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vipAccountInfoDto.getId());
            String vipGrade = (vipAccountInfoDto.getVipGrade() == null)? "" : vipAccountInfoDto.getVipGrade();
            jsonObject.put("vipGrade", vipGrade);
            String name = (vipAccountInfoDto.getName() == null)? "" : vipAccountInfoDto.getName();
            jsonObject.put("name", name);
            String phone = (vipAccountInfoDto.getPhone() == null)? "" : vipAccountInfoDto.getPhone();
            jsonObject.put("phone", phone);
            String cardNumber = (vipAccountInfoDto.getCardNumber() == null)? "" : vipAccountInfoDto.getCardNumber();
            jsonObject.put("cardNumber", cardNumber);
            jsonObject.put("balance", vipAccountInfoDto.getBalance());
            jsonObject.put("integral", vipAccountInfoDto.getIntegral());
            jsonObject.put("totalConsumption", vipAccountInfoDto.getTotalConsumption());
            jsonObject.put("usedCreditAmount", vipAccountInfoDto.getTotalConsumption());
            jsonObject.put("status", vipAccountInfoDto.getStatus());
            jsonArray.add(jsonObject);
        }


        return sendJsonArray(jsonArray, dataCount);
    }


    /**
     * ajax更改会员账户状态
     *
     * @param id
     * @param status
     * @return
     */
    @Module(ModuleEnums.AdminVipAccountUpdateStatus)
    @RequestMapping(value = "ajax/status", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject updateStatus(@RequestParam("id") Integer id,
                                   @RequestParam("status") Integer status) {
        try {
            // vipInfo表里该id被停用，则不能在vipAccount处启用
            if(status == StatusEnums.Enabled.getId() && vipInfoService.queryById(id).getStatus() == UserStatusEnums.Disabled.getId()){
                throw SSException.get(EmenuException.VipAccountInfoStatusError);
            }
            StatusEnums vipCountStatus = StatusEnums.valueOf(status);
            vipAccountInfoService.updateStatusById(id, vipCountStatus);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
