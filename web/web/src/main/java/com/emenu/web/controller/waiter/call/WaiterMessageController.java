package com.emenu.web.controller.waiter.call;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.entity.call.CallWaiter;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务员消息Controller
 *
 * @author: quanyibo
 * @time: 2016/6/21
 */
@Controller
@Module(ModuleEnums. WaiterCallList)
@RequestMapping(value = URLConstants.WAITER_MESSAGE_LIST_URL)
public class WaiterMessageController extends AbstractController {


    /**
     * 消息——存在多少条未应答的消息
     * @param
     * @return
     */
    @Module(ModuleEnums.WaiterCallTotal)
    @RequestMapping(value = "/total",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject notResponseTotalMessage(HttpSession httpSession) {
        JSONObject jsonObject = new JSONObject();
        Integer partyId;
        String temp;
        try{
            // 首先获取服务员的partyId
            temp = httpSession.getAttribute("partyId").toString();
            partyId = Integer.parseInt(temp);
            jsonObject.put("totalMessage",callCacheService.countNotResponseTotalMessage(partyId));
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 消息——返回所有的呼叫服务的种类
     * @param
     * @return
     */
    @Module(ModuleEnums.WaiterCallList)
    @RequestMapping(value = "/list/call",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listAllCallWaiter() {
        JSONArray jsonArray = new JSONArray();
        List<CallWaiter> callWaiters = new ArrayList<CallWaiter>();
        try{
            callWaiters = callWaiterService.queryAllCallWaiter();

            for(CallWaiter dto :callWaiters){
                JSONObject temp = new JSONObject();
                temp.put("id",dto.getId());
                temp.put("name",dto.getName());
                jsonArray.add(temp);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * 消息——根据服务的Id查询消息
     * @param
     * @return
     */
    @Module(ModuleEnums.WaiterCallList)
    @RequestMapping(value = "/list/by/id",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listCallById(@RequestParam("id")Integer id,
                                   HttpSession httpSession) {
        JSONObject jsonObject = new JSONObject();
        Integer partyId;
        String temp;
        try{
            // 首先获取服务员的partyId
            temp = httpSession.getAttribute("partyId").toString();
            partyId = Integer.parseInt(temp);
            jsonObject=callCacheService.queryCallById(id,partyId);

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
    }

    /**
     * 消息——应答服务请求
     * @param
     * @return
     */
    @Module(ModuleEnums.WaiterCallResponse)
    @RequestMapping(value = "/response",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject callResponse(@RequestParam("cacheId")Integer cacheId,
                                   @RequestParam("tableId") Integer tableId){
        JSONObject jsonObject = new JSONObject();
        try{
            callCacheService.responseCall(tableId,cacheId);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 清除所有已经应答了的服务
     * @param
     * @return
     */
    @Module(ModuleEnums. WaiterCallDelete)
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject callDelete(HttpSession httpSession){
        Integer partyId;
        String temp;
        try{
            // 首先获取服务员的partyId
            temp = httpSession.getAttribute("partyId").toString();
            partyId = Integer.parseInt(temp);
            callCacheService.delCallCachesByPartyId(partyId);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
