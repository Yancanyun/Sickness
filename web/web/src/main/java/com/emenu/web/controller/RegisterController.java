package com.emenu.web.controller;

import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.IgnoreRegister;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * RegisterController
 *
 * @author: yangch
 * @time: 2016/8/3 11:14
 */
@IgnoreRegister
@IgnoreAuthorization
@IgnoreLogin
@Controller
@RequestMapping(value = URLConstants.REGISTER_URL)
public class RegisterController extends AbstractController {
    /**
     * 去注册页
     * @param model
     * @return
     */
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String toRegister(Model model) {
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
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String register(@RequestParam PandaworkMultipartFile pandaworkMultipartFile,
                           @RequestParam String password,
                           RedirectAttributes redirectAttributes){
        try {
            registerService.uploadLicence(pandaworkMultipartFile.getInputStream(), password);

            String successUrl = "/" + URLConstants.REGISTER_URL;
            // 返回上传成功信息
            redirectAttributes.addFlashAttribute("successMsg", "上传成功");
            return "redirect:" + successUrl;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.REGISTER_URL;
            // 返回上传失败信息
            redirectAttributes.addFlashAttribute("failedMsg", e.getMessage());
            return "redirect:" + failedUrl;
        }
    }
}
