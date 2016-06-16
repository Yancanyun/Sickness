package com.emenu.web.controller.admin.party.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.party.security.SecurityPermission;
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
 * 权限Controller
 *
 * @author: zhangteng
 * @time: 2015/10/23 10:14
 **/
@Controller
@Module(ModuleEnums.AdminSAdmin)
@RequestMapping(value = URLConstants.ADMIN_PARTY_SECURITY_PERMISSION_URL)
public class SecurityPermissionController extends AbstractController {

    /**
     * 去列表
     *
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityPermissionList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList() {
        return "admin/party/security/permission/list_home";
    }

    /**
     * ajax获取分页数据
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityPermissionList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo") Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize) {
        pageNo = pageNo > 0 ?  pageNo - 1 : pageNo;
        int dataCount = 0;
        try {
            dataCount = securityPermissionService.count();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        List<SecurityPermission> list = Collections.emptyList();
        try {
            list = securityPermissionService.listByPage(pageNo, pageSize);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (SecurityPermission securityPermission : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", securityPermission.getId());
            jsonObject.put("expression", securityPermission.getExpression());
            jsonObject.put("description", securityPermission.getDescription());

            jsonArray.add(jsonObject);
        }

        return sendJsonArray(jsonArray, dataCount, pageSize);
    }

    /**
     * ajax添加
     *
     * @param securityPermission
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityPermissionNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxNew(SecurityPermission securityPermission) {
        try {
            securityPermissionService.newPermission(securityPermission);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax更新
     *
     * @param securityPermission
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityPermissionUpdate)
    @RequestMapping(value = "ajax/update", method = RequestMethod.PUT)
    @ResponseBody
    public JSON ajaxUpdate(SecurityPermission securityPermission) {
        try {
            securityPermissionService.update(securityPermission);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax删除
     *
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminPartySecurity, extModule = ModuleEnums.AdminPartySecurityPermissionDelete)
    @RequestMapping(value = "ajax/del/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDelete(@PathVariable("id") Integer id) {
        try {
            securityPermissionService.delById(id);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
