package com.emenu.web.controller.admin.storage;

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
 * 单据controller
 * @author xiaozl
 * @date 2015/11/21
 * @time 10:49
 */

@Module(ModuleEnums.AdminStorage)
@Controller
@RequestMapping(URLConstants.ADMIN_STORAGE_REPORT_URL)
public class StorageReportAndReportItemController extends AbstractController {

    @RequestMapping(value = "ajax/list/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("curPage") Integer curPage,
                         @RequestParam("pageSize") Integer pageSize) {

  /*      int dataCount = 0;
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

        return sendJsonArray(jsonArray, dataCount, pageSize);*/
        return null;
    }

}
