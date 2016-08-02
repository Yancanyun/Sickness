package com.emenu.web.controller.mobile;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.call.CallWaiter;
import com.emenu.common.entity.page.IndexImg;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.page.IndexImgEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.service.call.CallWaiterService;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
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
public class MobileController extends AbstractController {
    @Module(ModuleEnums.MobileIndex)
    @RequestMapping(value = "{tableId}", method = RequestMethod.GET)
    public String toIndex(@PathVariable("tableId")Integer tableId,
                          HttpSession session,
                          Model model,
                          HttpServletRequest httpServletRequest) {
        try {
            /**
             * 检查是否访问者是否来自局域网内
             */
            // 访问者的IP地址(若未经过反向代理，则该地址为真实的访问者IP地址，否则为反向代理服务器的IP地址)
            String customerIp = httpServletRequest.getRemoteAddr();
            // 访问者的真实IP地址(若经过Nginx反向代理，则该地址为真实的访问者IP地址，否则为空)
            String xRealIp = httpServletRequest.getHeader("X-Real-IP");
            // 若存在X-Real-IP，则用X-Real-IP作为访问者的IP地址
            if (Assert.isNotNull(xRealIp)) {
                customerIp = xRealIp;
            }
            // 常量表中配置的内网IP地址
            String internalNetworkAddress = constantService.queryValueByKey(ConstantEnum.InternalNetworkAddress.getKey());
            String[] internalNetworkAddresses = internalNetworkAddress.split("\\.");
            String[] customerIps = customerIp.split("\\.");
            // 若未正确配置内网地址，则直接报错
            if (internalNetworkAddresses.length != 4) {
                throw SSException.get(EmenuException.InternalNetworkAddressError);
            }
            // 若不是本机进行访问，则对IP地址的前三组进行匹配。若匹配不成功，则证明是外网访问，拒绝访问
            if (!customerIp.equals("127.0.0.1") && !customerIp.equals("0:0:0:0:0:0:0:1")) {
                for (int i = 0; i < 3; i++) {
                    if (!customerIps[i].equals(internalNetworkAddresses[i])) {
                        throw SSException.get(EmenuException.CustomerIsNotInLAN);
                    }
                }
            }

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
     * 去404页
     * 用*来拦截所有请求，这样便可以不走web.xml的404
     *
     * @author: yangch
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "*", method = RequestMethod.GET)
    public String to404() {
        return MOBILE_NOT_FOUND_PAGE;
    }

    /**
     * 去500页
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
