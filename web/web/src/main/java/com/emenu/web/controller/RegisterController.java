package com.emenu.web.controller;

import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * RegisterController
 *
 * @author: yangch
 * @time: 2016/8/3 11:14
 */
@IgnoreAuthorization
@IgnoreLogin
@Controller
@RequestMapping(value = "register")
public class RegisterController extends AbstractController {
    /**
     * 去注册页
     * @param model
     * @return
     */
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String toIndex(Model model) {
        try {
            model.addAttribute("sysId", registerService.getSysId());

            if (registerService.isRegistered()) {
                model.addAttribute("emsg", "已注册");
            } else {
                model.addAttribute("emsg", "未注册或已过期");
            }

            return "register";
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    /**
     * 验证注册文件
     * @param pandaworkMultipartFile
     * @param password
     * @param model
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String reg(@RequestParam PandaworkMultipartFile pandaworkMultipartFile,
                      @RequestParam String password,
                      Model model){
        try {
            registerService.uploadLicence(pandaworkMultipartFile.getInputStream(), password);
        } catch (Exception e) {
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return toIndex(model);
    }
}
