package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.cache.order.OrderDishCache;
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

/**
 * 结账单controller
 *
 * @author quanyibo
 * @date 2016/7/20
 */
@Controller
@Module(ModuleEnums.BarCheckout)
@RequestMapping(value = URLConstants.BAR_CHECKOUT_URL)
public class BarCheckoutController extends AbstractController {

    /**
     * 打印消费清单
     * @param tableId
     * @return
     */
    @Module(ModuleEnums.BarCheckoutPrint)
    @RequestMapping(value = "/print",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkoutPrint(@RequestParam("tableId")Integer tableId) {
        JSONObject jsonObject = new JSONObject();
        try {
            return checkoutService.printCheckOutByTableId(tableId);
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            jsonObject.put("code",3);
            return jsonObject;
        }
    }
}
