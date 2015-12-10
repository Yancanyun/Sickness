package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * BarTableCleanController
 *
 * @author: yangch
 * @time: 2015/12/10 17:03
 */
@Controller
@Module(ModuleEnums.BarTableClean)
@RequestMapping(value = URLConstants.BAR_TABLE_CLEAN_URL)
public class BarTableCleanController extends AbstractAppBarController {
    /**
     * Ajax 执行清台操作
     * @param tableId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JSONObject cleanTable(@RequestParam("tableId") Integer tableId) {
        try {
            tableService.cleanTable(tableId);

            return sendJsonArray(null);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
