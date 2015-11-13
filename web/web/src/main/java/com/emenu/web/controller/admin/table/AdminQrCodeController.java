package com.emenu.web.controller.admin.table;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * QrCodeController
 *
 * @author: yangch
 * @time: 2015/10/26 15:18
 */
@Controller
@Module(ModuleEnums.AdminRestaurantQrCode)
@RequestMapping(value = URLConstants.ADMIN_QRCODE_URL)
public class AdminQrCodeController extends AbstractController {
    /**
     * 去餐台二维码管理页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantQrCodeList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toQrCodePage(Model model) {
        try {
            //获取域名信息
            String webDomain = constantService.queryValueByKey(ConstantEnum.WebDomain.getKey());
            List<AreaDto> areaDtoList = areaService.listDto();
            for(AreaDto areaDto : areaDtoList) {
                for(Table table : areaDto.getTableList()) {
                    System.out.println(table.getQrCodePath());
                }
            }
            model.addAttribute("webDomain", webDomain);
            model.addAttribute("areaDtoList", areaDtoList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/restaurant/qrcode/list_home";
    }

    /**
     * 生成全部二维码
     * @param webDomain
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantQrCodeNew)
    @RequestMapping(value = "newAllQrCode", method = RequestMethod.POST)
    public String newAllQrCode(@RequestParam String webDomain) {
        try {
            qrCodeService.newAllQrCode(webDomain, getRequest());
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "redirect:";
    }

    /**
     * 下载属于某区域ID的餐台的二维码
     * @param areaId
     * @param request
     * @param response
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantQrCodeDownload)
    @RequestMapping(value = "download/{areaId}", method = RequestMethod.GET)
    public String downloadQrCodeByAreaId(@PathVariable("areaId") Integer areaId,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        try {
            qrCodeService.downloadByAreaId(areaId, request, response);
            return null;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    /**
     * 下载全部二维码
     * @param request
     * @param response
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantQrCodeDownload)
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public String downloadAllQrCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            qrCodeService.downloadAllQrCode(request, response);
            return null;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }
}
