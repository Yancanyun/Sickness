package com.emenu.web.controller.admin.party.security;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.party.security.SecurityGroup;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * SecurityGroupController
 * 权限组管理Controller
 * @author: zhangteng
 * @time: 2015/10/28 17:07
 **/
@Controller
@Module(ModuleEnums.AdminSAdmin)
@RequestMapping(value = URLConstants.ADMIN_PARTY_SECURITY_GROUP)
public class SecurityGroupController extends AbstractController {

    /**
     * 去列表页面
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityGroupList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList() {
        return "admin/party/security/group/list_home";
    }

    /**
     * Ajax获取分页
     * @param curPage
     * @param pageSize
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityGroupList)
    @RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listSecurityGroup(@PathVariable("curPage") Integer curPage,
                                        @RequestParam Integer pageSize){
        List<SecurityGroup> securityGroupList = Collections.emptyList();
        try {
            securityGroupList = securityGroupService.listByPage(curPage, pageSize);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (SecurityGroup  securityGroup: securityGroupList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", securityGroup.getId());
            jsonObject.put("name", securityGroup.getName());
            jsonObject.put("description", securityGroup.getDescription());
            jsonArray.add(jsonObject);
        }

        int dataCount = 0;
        try {
            dataCount = securityGroupService.count();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);

    }

    /**
     * Ajax添加
     * @param securityGroup
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityGroupNew)
    @RequestMapping(value = "ajax/securityGroup", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewSecurityGroup(SecurityGroup securityGroup){
        try{
            securityGroupService.newSecurityGroup(securityGroup);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax修改
     * @param securityGroup
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityGroupUpdate)
    @RequestMapping(value = "ajax/securityGroup",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateSecurityGroup(SecurityGroup securityGroup){
        try{
            securityGroupService.update(securityGroup);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax删除
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityGroupDel)
    @RequestMapping(value = "ajax/securityGroup/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelSecurityGroup(@PathVariable("id") Integer id){
        try{
            securityGroupService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

}
