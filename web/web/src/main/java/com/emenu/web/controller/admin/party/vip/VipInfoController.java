package com.emenu.web.controller.admin.party.vip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.party.vip.VipInfo;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 会员基本信息管理controller
 * @author chenyuting
 * @date 2015/10/27 10:55
 */
@Controller
@Module(ModuleEnums.AdminVipInfo)
@RequestMapping(value = URLConstants.VIP_VIPINFO_URL)
public class VipInfoController extends AbstractController {

    /**
     * 去会员基本信息列表页面
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(){
        return "admin/party/vip/vip_info_list";
    }

    /**
     * 分页显示会员基本信息列表
     * @param curPage
     * @param pageSize
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoList)
    @RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListVipInfo(@PathVariable("curPage") Integer curPage,
                         @RequestParam Integer pageSize){
        List<VipInfo> vipInfoList = Collections.emptyList();
        try{
            vipInfoList = vipInfoService.listByPage(curPage, pageSize);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (VipInfo vipInfo:vipInfoList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vipInfo.getId());
            jsonObject.put("name", vipInfo.getName());
            jsonObject.put("phone", vipInfo.getPhone());
            jsonObject.put("state", vipInfo.getState());
            jsonArray.add(jsonObject);
        }
        int dataCount = 0;
        try {
            dataCount = vipInfoService.count();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * 去新增会员页面
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNewVipInfo(){
        return "admin/party/vip/vip_info_new";
    }

    /**
     * 新增会员
     * @param name
     * @param sex
     * @param birthday
     * @param phone
     * @param qq
     * @param email
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoNew)
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newVipInfo(@RequestParam("name") String name,
                             @RequestParam("sex") int sex,
                             @RequestParam("birthday") Date birthday,
                             @RequestParam("phone") String phone,
                             @RequestParam("qq") String qq,
                             @RequestParam("email") String email){
        try{
            VipInfo vipInfo = new VipInfo();
            vipInfo.setName(name);
            vipInfo.setSex(sex);
            vipInfo.setBirthday(birthday);
            vipInfo.setPhone(phone);
            vipInfo.setQq(qq);
            vipInfo.setEmail(email);
            vipInfoService.newVipInfo(vipInfo);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "redirect:/admin/vip/party/list";
    }

    /**
     * ajax返回电话号码存在的错误信息
     * @param phone
     * @return
     */
    @RequestMapping(value = "phone/ajax/exist", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject phoneIsExist(@RequestParam("phone") String phone){
        try{
            if (vipInfoService.checkPhoneIsExist(phone)){
                throw SSException.get(EmenuException.VipInfoPhoneExist);
            } else {
                return sendJsonObject(AJAX_SUCCESS_CODE);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax更改会员状态
     * @param id
     * @param state
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoUpdate)
    @RequestMapping(value = "ajax/state", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject lock(@RequestParam("id") int id,
                                         @RequestParam("state") int state){
        try{
            UserStatusEnums vipInfostate = UserStatusEnums.valueOf(state);
            vipInfoService.updateVipInfoStateById(id, vipInfostate);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 查看会员信息详情
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoDetail)
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detailVipInfo(@PathVariable("id") int id,
                                Model model){
        try{
            VipInfo vipInfo = vipInfoService.queryVipInfoById(id);
            model.addAttribute(vipInfo);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/party/vip/vip_info_detail";
    }
}