package com.emenu.web.controller.admin.vip.price;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.vip.VipDishPriceDto;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.entity.vip.VipDishPricePlan;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @author chenyuting
 * @date 2015/11/23 17:56
 */
@Controller
@RequestMapping(value = URLConstants.ADMIN_VIP_VIP_DISH_PRICE_URL)
public class VipDishPriceController extends AbstractController{

    protected final static String NEW_SUCCESS_MSG = "会员价生成成功！";
    protected final static String UPDATE_SUCCESS_MSG = "会员价修改成功！";

    /**
     * 会员价列表
     * @param vipDishPricePlanId
     * @param model
     * @return
     */
    @RequestMapping(value = "list/{vipDishPricePlanId}", method = RequestMethod.GET)
    public String listVipDishPrice(@PathVariable("vipDishPricePlanId") int vipDishPricePlanId,
                                   Model model){
        List<VipDishPriceDto> vipDishPriceDtoList = Collections.emptyList();
        try{
            vipDishPriceDtoList = vipDishPriceService.listVipDishPriceDtos(vipDishPricePlanId);
            model.addAttribute("vipDishPriceDtoList",vipDishPriceDtoList);
        }catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/price/vip_dish_price_list";
    }

    /**
     * 根据关键字搜索会员价列表
     * @param keyword
     * @param vipDishPricePlanId
     * @return
     */
    @RequestMapping(value = "ajax/list/{keyword}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxSearch(@PathVariable("keyword") String keyword,
                                 @RequestParam("vipDishPricePlanId") int vipDishPricePlanId){
        List<VipDishPriceDto> vipDishPriceDtoList = Collections.emptyList();
        try {
            vipDishPriceDtoList = vipDishPriceService.listVipDishPriceDtosByKeyword(vipDishPricePlanId, keyword);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }

        JSONArray jsonArray = new JSONArray();
        for (VipDishPriceDto vipDishPriceDto: vipDishPriceDtoList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vipDishPriceDto.getId());
            jsonObject.put("dishName", vipDishPriceDto.getDishName());
            jsonObject.put("dishNumber", vipDishPriceDto.getDishNumber());
            jsonObject.put("price", vipDishPriceDto.getPrice());
            jsonObject.put("salePrice", vipDishPriceDto.getSalePrice());
            jsonObject.put("vipDishPrice", vipDishPriceDto.getVipDishPrice());
            jsonObject.put("difference", vipDishPriceDto.getDifference());
            jsonArray.add(jsonObject);
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * 根据dishId和VipDishPricePlanID更新会员价
     * @param dishId
     * @param vipDishPricePlanId
     * @param vipDishPrice
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "ajax/{dishId}", method = RequestMethod.PUT)
    public String updateVipDishPrice(@PathVariable("dishId") int dishId,
                                     @RequestParam("vipDishPricePlanId") int vipDishPricePlanId,
                                     @RequestParam("vipDishPrice") BigDecimal vipDishPrice,
                                     RedirectAttributes redirectAttributes){
        try{
            vipDishPriceService.updateVipDishPrice(dishId, vipDishPricePlanId, vipDishPrice);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("msg",UPDATE_SUCCESS_MSG);
        String redirectUrl = "/" + URLConstants.ADMIN_VIP_VIP_DISH_PRICE_URL + "/list";
        return "redirect:" + redirectUrl;
    }
}