package com.emenu.web.controller.waiter.table;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * WaiterTableOpenController
 *
 * @author: yangch
 * @time: 2016/6/21 17:13
 */
@Controller
@Module(ModuleEnums.WaiterTable)
@RequestMapping(value = URLConstants.WAITER_TABLE_AREA_URL)
public class WaiterTableAreaController extends AbstractController {
    /**
     * Ajax 获取可管理餐台区域列表
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.WaiterTableAreaList)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject areaList(HttpSession httpSession) {
        try {
            Integer partyId = (Integer)httpSession.getAttribute("partyId");

            //根据PartyId获取所有的AreaDto
            List<AreaDto> areaDtoList = waiterTableService.queryAreaDtoByPartyId(partyId);

            JSONArray jsonArray = new JSONArray();

            for (AreaDto areaDto : areaDtoList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("areaId", areaDto.getArea().getId());
                jsonObject.put("areaName", areaDto.getArea().getName());

                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
