package com.emenu.web.controller.bar.vip;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.party.group.Party;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
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

import java.lang.reflect.Method;
import java.util.Date;

/**
 * BarVipRegister
 *
 * @author xiaozl
 * @date: 2016/7/23
 */
@Controller
@Module(ModuleEnums.AdminVip)
@RequestMapping(value = URLConstants.BAR_VIP_URL)
public class BarVipRegisterController extends AbstractController{

    /**
     * 会员注册
     * @param name
     * @param sex
     * @param phone
     * @param birthday
     * @param validityTime
     * @param permanentlyEffective
     * @param uid
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @Module(ModuleEnums.BarVipNew)
    @ResponseBody
    public JSONObject registerVip(@RequestParam("name")String name,
                                  @RequestParam("sex")Integer sex,
                                  @RequestParam("phone")String phone,
                                  @RequestParam("birthday")Date birthday,
                                  @RequestParam("validityTime")Date validityTime,
                                  @RequestParam("permanentlyEffective")Integer permanentlyEffective,
                                  @RequestParam("uid")Integer uid){
        try {
            // 获取进行此操作的用户信息
            Assert.isNotNull(uid, PartyException.UserIdNotNull);
            SecurityUser securityUser = securityUserService.queryById(uid);
            Assert.isNotNull(securityUser, PartyException.UserNotExist);
            Integer operatorPartyId = securityUser.getPartyId();
            vipOperationService.registerVip(name,sex,phone,birthday,validityTime,permanentlyEffective,operatorPartyId);

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
        return null;
    }

    @RequestMapping(value = "checkphone",method = RequestMethod.GET)
    @Module(ModuleEnums.BarVipNew)
    @ResponseBody
    public JSONObject checkPhoneIsExist(@RequestParam("phone")String phone){
        try {
            if (vipInfoService.checkPhoneIsExist(null,phone)){
                return sendMsgAndCode(AJAX_FAILURE_CODE,"此电话已注册");
            }
            return sendMsgAndCode(AJAX_SUCCESS_CODE,"电话未重复");
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

}
