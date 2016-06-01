package com.emenu.web.controller.mobile.call;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.cache.call.CallCache;
import com.emenu.common.entity.call.CallWaiter;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class MobileCallController  extends AbstractController{


    /**
     * ajax发出呼叫服务向缓存中添加呼叫服务
     *
     * @return
     */
    @RequestMapping(value = "/ajax/call",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxCall(@RequestParam("tableId")Integer tableId,CallCache callCache)
    {
        try
        {
           callCache.setCallTime(new Date());//发出时间
           callCache.setStatus(1);//服务员未响应为1 响应了为0
           callCacheService.addCallCache(tableId,callCache);
            return sendJsonObject(0);
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
