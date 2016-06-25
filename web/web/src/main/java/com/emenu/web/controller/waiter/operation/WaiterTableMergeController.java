package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * WaiterTableMergeController
 *
 * @author: yangch
 * @time: 2016/6/24 10:05
 */
@IgnoreLogin
@IgnoreAuthorization
@Controller
@Module(ModuleEnums.WaiterTableMerge)
@RequestMapping(value = URLConstants.WAITER_TABLE_MERGE_URL)
public class WaiterTableMergeController extends AbstractAppBarController {
    /**
     * Ajax 把选中的餐桌加入缓存
     * @param partyId
     * @param tableId
     * @return
     */
    @RequestMapping(value = "cache/add", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addMergeTableToCache (@RequestParam("partyId") Integer partyId,
                                            @RequestParam("tableId") Integer tableId) {
        try {
            tableMergeCacheService.newTable(partyId, tableId);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 从缓存中删除某餐桌
     * @param partyId
     * @param tableId
     * @return
     */
    @RequestMapping(value = "cache/del", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delMergeTableFromCache (@RequestParam("partyId") Integer partyId,
                                              @RequestParam("tableId") Integer tableId) {
        try {
            tableMergeCacheService.delTable(partyId, tableId);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 执行并台操作
     * @param partyId
     * @param tableIdList
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject mergeTable (@RequestParam("partyId") Integer partyId,
                                  @RequestParam("tableIdList") List<Integer> tableIdList) {
        try {
            // TODO: 根据PartyId记录哪个服务员并的台

            // 执行并台操作
            tableMergeService.mergeTable(tableIdList);

            // 把缓存中该PartyId的缓存清空
            tableMergeCacheService.cleanCacheByPartyId(partyId);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
