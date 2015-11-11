package com.emenu.web.controller.admin.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.utils.StringUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台管理一些通用方法的controller
 *
 * @author: zhangteng
 * @time: 2015/11/11 9:21
 **/
@Controller
@RequestMapping(value = URLConstants.ADMIN_COMMON_URL)
public class AdminCommonController extends AbstractController {

    @IgnoreAuthorization
    @RequestMapping(value = "tool/str2py/ajax", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxStr2Py(@RequestParam("str") String str,
                           @RequestParam(value = "type", defaultValue = "head") String type) {
        try {
            String res = "";
            if ("head".equals(type)) {
                res = StringUtils.str2Pinyin(str, StringUtils.PINYIN_TYPE_HEAD_CHAR);
            } else {
                res = StringUtils.str2Pinyin(str, StringUtils.PINYIN_TYPE_FULL);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pinyin", res);

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (Exception e) {
        	LogClerk.errLog.error(e);
        	return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }
}
