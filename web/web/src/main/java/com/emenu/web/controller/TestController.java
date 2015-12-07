package com.emenu.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.dto.storage.TestDto;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.IpUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * TestController
 *
 * @author: zhangteng
 * @time: 15/10/14 上午8:56
 */
@Controller
@IgnoreLogin
@RequestMapping(value = "test")
public class TestController extends AbstractController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String test() {
        //LogClerk.bizLog.info("basePath: " + CommonUtil.getBasepath(getRequest()));
        // LogClerk.bizLog.info("client ip: " + IpUtil.getClientIP(getRequest()));
        getResponse().addHeader("Access-Control-Allow-Origin","*");
        return "admin/storage/report/list_home";
    }

    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxTest(@RequestBody TestDto testDto,
                         Model model) {
        try {
            System.out.println(BeanUtils.describe(testDto));
        } catch (Exception e) {
            LogClerk.errLog.error(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    @RequestMapping(value = "ajax/bill", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxBill() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("money", "100");
        return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
    }
}
