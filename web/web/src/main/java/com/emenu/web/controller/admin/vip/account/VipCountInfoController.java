package com.emenu.web.controller.admin.vip.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.vip.VipCountInfoDto;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.service.vip.VipCountInfoService;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
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
public class VipCountInfoController extends AbstractController{

    /**
     * 去列表页面
     *
     * @return
     */
    @Module(ModuleEnums.AdminVipAccountList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toListVipAccount() {
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
                                         @RequestParam(value = "orderBy",required = false)String orderBy) {
        if(orderType==null||Assert.isNull(orderBy)) {
            orderBy= "minConsumption";
            orderType = 0;
        }
        List<VipCountInfoDto> accountList = Collections.emptyList();
        try {
            accountList = vipCountInfoService.listByPageAndMin(curPage, pageSize,orderType,orderBy);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (VipCountInfoDto vipCountInfoDto :accountList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vipCountInfoDto.getId());
            jsonObject.put("vipGrade",vipCountInfoDto.getVipGrade());
            jsonObject.put("name",vipCountInfoDto.getName());
            jsonObject.put("phone",vipCountInfoDto.getPhone());
            jsonObject.put("cardNumber",vipCountInfoDto.getCardNumber());
            jsonObject.put("balance",vipCountInfoDto.getBalance());
            jsonObject.put("integral",vipCountInfoDto.getIntegral());
            jsonObject.put("totalConsumption",vipCountInfoDto.getTotalConsumption());
            jsonObject.put("usedCreditAmount",vipCountInfoDto.getTotalConsumption());
            jsonObject.put("status",vipCountInfoDto.getStatus());
            jsonArray.add(jsonObject);
        }
        int dataCount = 0;
        try {
            dataCount = vipCountInfoService.countAll();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * ajax更改会员状态
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
            StatusEnums vipCountStatus = StatusEnums.valueOf(status);
            vipCountInfoService.updateStatusById(id, vipCountStatus);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
