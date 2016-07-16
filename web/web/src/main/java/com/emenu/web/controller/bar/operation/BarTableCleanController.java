package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * BarTableCleanController
 *
 * @author: yangch
 * @time: 2015/12/10 17:03
 */
@Controller
@Module(ModuleEnums.BarTable)
@RequestMapping(value = URLConstants.BAR_TABLE_CLEAN_URL)
public class BarTableCleanController extends AbstractController {
    /**
     * Ajax 执行清台操作
     * @param tableId
     * @return
     */
    @Module(ModuleEnums.BarTableClean)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cleanTable(@RequestParam("tableId") Integer tableId) {
        try {
            tableService.cleanTable(tableId);
            callCacheService.delTableCallCache(tableId); // 清台操作还要把相应的呼叫服务缓存清除

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
