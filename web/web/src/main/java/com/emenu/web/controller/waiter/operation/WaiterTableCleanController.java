package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * WaiterTableCleanController
 *
 * @author: yangch
 * @time: 2015/12/9 14:53
 */
@IgnoreLogin
@IgnoreAuthorization
@Controller
@Module(ModuleEnums.WaiterTableClean)
@RequestMapping(value = URLConstants.WAITER_TABLE_CLEAN_URL)
public class WaiterTableCleanController extends AbstractAppBarController {
    /**
     * Ajax 执行清台操作
     * @param tableId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cleanTable(@RequestParam("partyId") Integer partyId,
                                 @RequestParam("tableId") Integer tableId) {
        try {
            // TODO: 根据PartyId记录哪个服务员清的台

            tableService.cleanTable(tableId);
            callCacheService.delTableCallCache(tableId); // 清台操作还要把相应的呼叫服务缓存清除

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
