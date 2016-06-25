package com.emenu.web.controller.mobile;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.call.CallWaiter;
import com.emenu.common.entity.page.IndexImg;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.page.IndexImgEnum;
import com.emenu.common.utils.URLConstants;
import com.emenu.service.call.CallWaiterService;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * MobileIndexController
 *
 * @author: yangch
 * @time: 2016/5/27 16:42
 */
@Controller
@IgnoreAuthorization
@IgnoreLogin
@RequestMapping(value = {URLConstants.MOBILE_URL, URLConstants.MOBILE_INDEX_URL})
public class MobileIndexController extends AbstractController {
    @Module(ModuleEnums.MobileIndex)
    @RequestMapping(value = "{tableId}", method = RequestMethod.GET)
    public String toIndex(@PathVariable("tableId")Integer tableId, HttpSession session, Model model) {
        try {
            IndexImg indexImg = indexImgService.queryByState(IndexImgEnum.Using);

            List<CallWaiter> callWaiter = new ArrayList<CallWaiter>();
            callWaiter= callWaiterService.queryAllCallWaiter();
            model.addAttribute("indexImg", indexImg.getImgPath());
            model.addAttribute("callWaiter",callWaiter);

            // 把TableID塞到Session里
            session.setAttribute("tableId", tableId);
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            return MOBILE_SYS_ERR_PAGE;
        }
        return "mobile/index";
    }

    /**
     * 去404
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String to404() {
        return MOBILE_NOT_FOUND_PAGE;
    }

    /**
     * 去500
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "500", method = RequestMethod.GET)
    public String to500() {
        int statusCode = getResponse().getStatus();
        if (statusCode == HttpServletResponse.SC_BAD_REQUEST) {
            String url = (String) getRequest().getAttribute("eUrl");

            // ajax请求
            if (url.contains("ajax")) {
                JSONObject json = new JSONObject();
                json.put("code", AJAX_FAILURE_CODE);
                json.put("errMsg", "少传了参数!");

                try {
                    getResponse().setContentType("application/json;charset=UTF-8");
                    PrintWriter writer = getResponse().getWriter();
                    writer.write(json.toJSONString());
                    writer.close();
                    return "";
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                }
            } else {
                getRequest().setAttribute("eMsg", "少传了参数!");
                getRequest().setAttribute("javax.servlet.error.request_uri", url);
            }
        }
        return MOBILE_SYS_ERR_PAGE;
    }
}
