package com.emenu.web.controller.admin.table;

import com.emenu.common.annotation.Module;
import com.emenu.common.entity.table.Area;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AreaController
 *
 * @author: yangch
 * @time: 2015/10/24 11:20
 */
@Controller
@Module(ModuleEnums.AdminRestaurantArea)
@RequestMapping(value = URLConstants.AREA_URL)
public class AdminAreaController extends AbstractController {
    /**
     * 去餐台区域管理页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantAreaList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toAreaPage(Model model) {
        try {
            List<Area> areaList = areaService.listAllArea();
            model.addAttribute("areaList", areaList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/restaurant/area/list_home";
    }

    /**
     * Ajax 添加区域
     * @param area
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantAreaNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewArea(Area area) {
        try {
            areaService.newArea(area);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 修改区域
     * @param id
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantAreaUpdate)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateArea(@PathVariable("id") Integer id,
                                             @RequestParam String name) {
        try {
            Area area = new Area();
            area.setId(id);
            area.setName(name);
            areaService.updateArea(area);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("areaId", area.getId());
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 删除区域
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantAreaDel)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelArea(@PathVariable Integer id) {
        try {
            areaService.delAreaById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            return sendErrMsgAndErrCode(e);
        }
    }
}
