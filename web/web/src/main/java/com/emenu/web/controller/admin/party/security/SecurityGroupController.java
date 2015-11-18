package com.emenu.web.controller.admin.party.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.party.security.SecurityGroup;
import com.emenu.common.entity.party.security.SecurityGroupPermission;
import com.emenu.common.entity.party.security.SecurityPermission;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@RequestMapping(value = URLConstants.ADMIN_PARTY_SECURITY_GROUP_URL)
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
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
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
    @RequestMapping(value = "ajax",method = RequestMethod.PUT)
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
    @RequestMapping(value = "ajax/{id}",method = RequestMethod.DELETE)
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

    /**
     * 去安全组-权限列表页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityGroupPermissionList)
    @RequestMapping(value = "permission/{id}", method = RequestMethod.GET)
    public String toPermissionList(@PathVariable("id") Integer id,
                                   Model model) {
        try {
            SecurityGroup securityGroup = securityGroupService.queryById(id);
            List<SecurityPermission> noSelectedPermissionList = securityGroupPermissionService.listNotSelectedPermission(id);

            model.addAttribute("securityGroup", securityGroup);
            model.addAttribute("noSelectedPermissionList", noSelectedPermissionList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "admin/party/security/group/permission_list_home";
    }

    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityGroupPermissionList)
    @RequestMapping(value = "permission/{id}/ajax/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxPermissionList(@PathVariable("id") Integer id,
                                   @PathVariable("pageNo") Integer pageNo,
                                   @RequestParam("pageSize") Integer pageSize) {

        int dataCount = 0;
        try {
            dataCount = securityGroupPermissionService.countByGroupId(id);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        List<SecurityGroupPermission> list = Collections.emptyList();
        try {
            list = securityGroupPermissionService.listByGroupIdAndPage(id, pageNo, pageSize);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (SecurityGroupPermission securityGroupPermission : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", securityGroupPermission.getId());
            jsonObject.put("permissionExpression", securityGroupPermission.getPermissionExpression());
            jsonObject.put("permissionDescription", securityGroupPermission.getPermissionDescription());

            jsonArray.add(jsonObject);
        }

        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * 添加权限
     *
     * @param groupId
     * @param permissionId
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityGroupPermissionNew)
    @RequestMapping(value = "permission", method = RequestMethod.POST)
    public String newPermission(@RequestParam("groupId") Integer groupId,
                                @RequestParam("permissionId") Integer permissionId,
                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("code", 0);
        redirectAttributes.addFlashAttribute("msg", "添加成功!");
        SecurityGroupPermission securityGroupPermission = new SecurityGroupPermission();
        securityGroupPermission.setGroupId(groupId);
        securityGroupPermission.setPermissionId(permissionId);
        try {
            securityGroupPermissionService.newSecurityGroupPermission(securityGroupPermission);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
            redirectAttributes.addFlashAttribute("code", 1);
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }

        String redirectUrl = URLConstants.ADMIN_PARTY_SECURITY_GROUP_URL + "/permission/" + groupId;
        return "redirect:/" + redirectUrl;
    }

    /**
     * ajax删除权限
     *
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityGroupPermissionDelete)
    @RequestMapping(value = "permission/ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDeletePermission(@PathVariable("id") Integer id) {
        try {
            securityGroupPermissionService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
