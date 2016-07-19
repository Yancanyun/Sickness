package com.emenu.web.controller.mobile.call;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.cache.call.CallCache;
import com.emenu.common.entity.call.CallWaiter;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MobileCallController
 *
 * @author: quanyibo
 * @time: 2016/5/28
 */
@Controller
@IgnoreLogin
public class MobileCallController  extends AbstractController {

    /**
     * ajax获取呼叫服务列表
     *
     * @return
     */
    @RequestMapping(value = "/mobile/ajax/list/call",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListCall()
    {
        List<CallWaiter> callWaiter = new ArrayList<CallWaiter>();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try
        {
            callWaiter= callWaiterService.queryAllCallWaiter();
            for(CallWaiter dto : callWaiter)
            {
                JSONObject json = new JSONObject();
                if(dto.getStatus()==1)//服务启用
                {
                    json.put("name",dto.getName());
                    jsonArray.add(json);
                }

            }
            jsonObject.put("code",AJAX_SUCCESS_CODE);
            jsonObject.put("data",jsonArray);
            return jsonObject;
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax发出呼叫服务
     * 餐台状态：0、停用；1、可用；2、占用已结账；3、占用未结账；4、已并桌；5、已预订；6、已删除
     * @return
     */
    @RequestMapping(value = "/mobile/ajax/call",method = RequestMethod.GET)
    @Module(value = ModuleEnums.MobileCall)
    @ResponseBody
    public JSONObject ajaxCall(@RequestParam("tableId")Integer tableId
            ,CallCache callCache,HttpSession httpSession)
    {
        Table table = new Table();
        try
        {
           table = tableService.queryById(tableId);
            if(table.getStatus()!=0&&table.getStatus()!=6)//0为停用状态,6为删除状态,这两种状态不能发送请求
            {
                callCache.setCallTime(new Date());//发出时间
                callCache.setStatus(1);//服务员未响应为1 响应了为0
                callCache.setTableId(tableId);
                callCacheService.addCallCache(tableId,callCache);
                return sendJsonObject(AJAX_SUCCESS_CODE);
            }
           else
            {
                return sendErrMsgAndErrCode(SSException.get(EmenuException.TableNotAvailable));
            }
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
