package com.emenu.web.controller.mobile;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
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
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toWelcome(Model model) {
        try {
            IndexImg indexImg = indexImgService.queryByState(IndexImgEnum.Using);

            List<CallWaiter> callWaiter = new ArrayList<CallWaiter>();
            callWaiter= callWaiterService.queryAllCallWaiter();
            model.addAttribute("indexImg", indexImg.getImgPath());
            model.addAttribute("callWaiter",callWaiter);
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            return MOBILE_SYS_ERR_PAGE;
        }
        return "mobile/index";
    }
}
