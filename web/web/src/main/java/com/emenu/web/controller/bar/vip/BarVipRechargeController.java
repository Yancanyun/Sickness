package com.emenu.web.controller.bar.vip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.vip.VipRechargePlan;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * 吧台充值
 *
 * @author chenyuting
 * @date 2016/7/23 14:58
 */
@Controller
@Module(ModuleEnums.BarVip)
@RequestMapping(value = URLConstants.BAR_VIP_URL)
public class BarVipRechargeController extends AbstractController {

    /**
     * 获取充值方案
     * @return
     */
    @Module(ModuleEnums.VarVipRecharge)
    @RequestMapping(value = "recharge", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject searchVipInfo(){
        try{
            JSONArray jsonArray = new JSONArray();
            List<VipRechargePlan> vipRechargePlanList = vipRechargePlanService.listAll();
            for (VipRechargePlan vipRechargePlan:vipRechargePlanList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rechargePlanId",vipRechargePlan.getId());
                jsonObject.put("rechargePlanName",vipRechargePlan.getName());
                jsonObject.put("rechargeAmount",vipRechargePlan.getRechargeAmount());
                jsonObject.put("payAmount",vipRechargePlan.getPayAmount());
                jsonArray.add(jsonObject);
            }
            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 充值
     * @param vipPartyId
     * @param rechargePlanId
     * @param rechargeAmount
     * @param payAmount
     * @param paymentType
     * @param uid
     * @return
     */
    @Module(ModuleEnums.VarVipRechargeConfirm)
    @RequestMapping(value = "recharge/confirm", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject rechargeConfirm(@RequestParam("vipPartyId") Integer vipPartyId,
                                      @RequestParam("rechargePlanId") Integer rechargePlanId,
                                      @RequestParam("rechargeAmount") BigDecimal rechargeAmount,
                                      @RequestParam("payAmount") BigDecimal payAmount,
                                      @RequestParam("paymentType") Integer paymentType,
                                      @RequestParam("uid") Integer uid){
        try{
            // 根据uid获取PartyID
            SecurityUser securityUser = securityUserService.queryById(uid);
            if (Assert.isNull(securityUser)) {
                throw SSException.get(EmenuException.QueryEmployeeInfoFail);
            }
            int partyId = securityUser.getPartyId();
            vipOperationService.rechargeByVipPartyId(vipPartyId,rechargePlanId,rechargeAmount,payAmount,partyId,paymentType);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }
}
