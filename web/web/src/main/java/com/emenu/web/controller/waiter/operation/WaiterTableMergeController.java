package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.table.Table;
import com.emenu.common.entity.table.TableMerge;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WaiterTableMergeController
 *
 * @author: yangch
 * @time: 2016/6/24 10:05
 */
@Controller
@Module(ModuleEnums.WaiterTableMerge)
@RequestMapping(value = URLConstants.WAITER_TABLE_MERGE_URL)
public class WaiterTableMergeController extends AbstractController {
    /**
     * Ajax 返回并台信息
     * @param tableIdList
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toMergeTable (@RequestParam("tableIdList") List<Integer> tableIdList,
                                    HttpSession httpSession) {
        try {
            if (Assert.isNull(tableIdList) || tableIdList.size() < 2) {
                throw SSException.get(EmenuException.MergeTableNumLessThanTwo);
            }

            Integer partyId = (Integer)httpSession.getAttribute("partyId");

            List<Integer> newTableIdList = tableIdList;
            // 利用Map的不可重复功能，实现不重复的记录全部已并台的餐台
            Map<Integer, Table> newTableIdMap = new HashMap<Integer, Table>();

            for (Integer tableId : tableIdList) {
                newTableIdMap.put(tableId, tableService.queryById(tableId));

                // 寻找传来的餐台ID列表中存在已并台的餐台
                if (tableService.queryStatusById(tableId) == TableStatusEnums.Merged.getId()) {
                    // 如果存在，则把与之并台的餐台中未加入newTableIdList的加入newTableIdList
                    List<Table> tableList = tableMergeService.listOtherTableByTableId(tableId);
                    if (Assert.isNotNull(tableList)) {
                        for (Table table : tableList) {
                            newTableIdMap.put(table.getId(), tableService.queryById(table.getId()));
                        }
                    }
                }
            }

            JSONArray jsonArray = new JSONArray();

            // 从Map中取出全部餐台
            for (Map.Entry<Integer, Table> entry : newTableIdMap.entrySet()) {
                Table table = entry.getValue();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tableId", table.getId());
                jsonObject.put("tableName", table.getName());
                jsonObject.put("status", table.getStatus());
                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 执行并台操作
     * @param tableIdList
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject mergeTable (@RequestParam("tableIdList") List<Integer> tableIdList) {
        try {
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

    /**
     * TODO: 暂时不做服务员跨区域并台，跟缓存相关的代码暂时注释掉
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
}
