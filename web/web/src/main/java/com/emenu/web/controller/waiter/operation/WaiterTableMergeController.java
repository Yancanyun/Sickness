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

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * WaiterTableMergeController
 *
 * @author: yangch
 * @time: 2016/6/24 10:05
 */
@Controller
@Module(ModuleEnums.WaiterTableMerge)
@RequestMapping(value = URLConstants.WAITER_TABLE_MERGE_URL)
public class WaiterTableMergeController extends AbstractAppBarController {
    /**
     * TODO: 暂时不做服务员跨区域并台，这块代码暂时注释掉
     */
//    /**
//     * Ajax 把选中的餐桌加入缓存
//     * @param tableId
//     * @return
//     */
//    @RequestMapping(value = "cache/add", method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject addMergeTableToCache (@RequestParam("tableId") Integer tableId,
//                                            HttpSession httpSession) {
//        try {
//            Integer partyId = (Integer)httpSession.getAttribute("partyId");
//
//            tableMergeCacheService.newTable(partyId, tableId);
//
//            return sendJsonObject(AJAX_SUCCESS_CODE);
//        } catch (SSException e) {
//            LogClerk.errLog.error(e);
//            return sendErrMsgAndErrCode(e);
//        }
//    }
//
//    /**
//     * Ajax 从缓存中删除某餐桌
//     * @param tableId
//     * @return
//     */
//    @RequestMapping(value = "cache/del", method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject delMergeTableFromCache (@RequestParam("tableId") Integer tableId,
//                                              HttpSession httpSession) {
//        try {
//            Integer partyId = (Integer)httpSession.getAttribute("partyId");
//
//            tableMergeCacheService.delTable(partyId, tableId);
//
//            return sendJsonObject(AJAX_SUCCESS_CODE);
//        } catch (SSException e) {
//            LogClerk.errLog.error(e);
//            return sendErrMsgAndErrCode(e);
//        }
//    }

    /**
     * Ajax 执行并台操作
     * @param tableIdList
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject mergeTable (@RequestParam("tableIdList") List<Integer> tableIdList,
                                  HttpSession httpSession) {
        try {
            Integer partyId = (Integer)httpSession.getAttribute("partyId");

            // TODO: 记录哪个服务员并的台

            // 执行并台操作
            tableMergeService.mergeTable(tableIdList);

//            // 把缓存中该PartyId的缓存清空
//            tableMergeCacheService.cleanCacheByPartyId(partyId);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
