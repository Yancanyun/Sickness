package com.emenu.web.controller.admin.vip.price;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.vip.VipDishPriceDto;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.entity.vip.VipDishPricePlan;
import com.emenu.common.enums.TrueEnums;
import com.emenu.common.enums.other.ModuleEnums;
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
@Module(ModuleEnums.AdminVip)
@RequestMapping(value = URLConstants.ADMIN_VIP_VIP_DISH_PRICE_URL)
public class VipDishPriceController extends AbstractController{


    protected final static String UPDATE_SUCCESS_MSG = "会员价修改成功！";
    protected final static String UPDATE_FAIL_MSG = "会员价修改成功！";
    protected final static String GENERATE_SUCCESS_MSG = "会员价自动生成成功！";

    /**
     * 会员价列表
     * @param vipDishPricePlanId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminVipVipDishPriceList)
    @RequestMapping(value = "list/{vipDishPricePlanId}", method = RequestMethod.GET)
    public String listVipDishPrice(@PathVariable("vipDishPricePlanId") int vipDishPricePlanId,
                                   Model model){
        List<VipDishPriceDto> vipDishPriceDtoList = Collections.emptyList();
        try{
            vipDishPriceDtoList = vipDishPriceService.listVipDishPriceDtos(vipDishPricePlanId);
            VipDishPricePlan vipDishPricePlan = vipDishPricePlanService.queryById(vipDishPricePlanId);
            model.addAttribute("vipDishPriceDtoList",vipDishPriceDtoList);
            model.addAttribute("vipDishPricePlan",vipDishPricePlan);
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
    @Module(ModuleEnums.AdminVipVipDishPriceList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxSearch(@RequestParam("keyword") String keyword,
                                 @RequestParam("vipDishPricePlanId") int vipDishPricePlanId){
        List<VipDishPriceDto> vipDishPriceDtoList = Collections.emptyList();
        BigDecimal zero = new BigDecimal("0.00");
        try {
            if (keyword == null || keyword == ""){
                vipDishPriceDtoList = vipDishPriceService.listVipDishPriceDtos(vipDishPricePlanId);
            }else {
                vipDishPriceDtoList = vipDishPriceService.listVipDishPriceDtosByKeyword(vipDishPricePlanId, keyword);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }

        JSONArray jsonArray = new JSONArray();
        for (VipDishPriceDto vipDishPriceDto: vipDishPriceDtoList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vipDishPriceDto.getId());
            jsonObject.put("dishId",vipDishPriceDto.getDishId());
            jsonObject.put("dishName", vipDishPriceDto.getDishName());
            jsonObject.put("dishNumber", vipDishPriceDto.getDishNumber());
            jsonObject.put("price", vipDishPriceDto.getPrice().toString());
            jsonObject.put("salePrice", vipDishPriceDto.getSalePrice().toString());
            jsonObject.put("vipDishPrice", vipDishPriceDto.getVipDishPrice().toString());
            jsonObject.put("difference", vipDishPriceDto.getPrice().subtract(vipDishPriceDto.getVipDishPrice()).toString());
            if (vipDishPriceDto.getPrice().subtract(vipDishPriceDto.getVipDishPrice()).compareTo(zero) > 0){
                jsonObject.put("order",0);
            }else {
                jsonObject.put("order",1);
            }

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
    @Module(ModuleEnums.AdminVipVipDishPriceUpdate)
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
            redirectAttributes.addFlashAttribute("msg", UPDATE_FAIL_MSG);
        }
        redirectAttributes.addFlashAttribute("msg",UPDATE_SUCCESS_MSG);
        String redirectUrl = "/" + URLConstants.ADMIN_VIP_VIP_DISH_PRICE_URL + "/list";
        return "redirect:" + redirectUrl;
    }

    /**
     * 自动生成会员价
     * @param dishIds
     * @param discount
     * @param difference
     * @param lowPrice
     * @param includeDrinks
     * @param cover
     * @param vipDishPricePlanId
     * @return
     */
    @Module(ModuleEnums.AdminVipVipDishPriceUpdate)
    @RequestMapping(value = "ajax/generate/", method = RequestMethod.GET)
    public String ajaxGenerate(@RequestParam("dishIds") List<Integer> dishIds,
                               @RequestParam("discount") BigDecimal discount,
                               @RequestParam("difference") BigDecimal difference,
                               @RequestParam("lowPrice") BigDecimal lowPrice,
                               @RequestParam("includeDrinks") Integer includeDrinks,
                               @RequestParam("cover") Integer cover,
                               @RequestParam("vipDishPricePlanId") int vipDishPricePlanId,
                               RedirectAttributes redirectAttributes){
        TrueEnums includeDrinksEnum = null;
        TrueEnums coverEnum = null;
        List<VipDishPriceDto> vipDishPriceDtoList = Collections.emptyList();
        try{
            if (includeDrinks != null){
                includeDrinksEnum = TrueEnums.valueOf(includeDrinks);
            }
            if (cover != null){
                coverEnum = TrueEnums.valueOf(cover);
            }
            vipDishPriceService.generateVipDishPrice(dishIds, discount, difference, lowPrice, includeDrinksEnum, coverEnum, vipDishPricePlanId);
            redirectAttributes.addFlashAttribute("msg",GENERATE_SUCCESS_MSG);
            String redirectUrl = "/" + URLConstants.ADMIN_VIP_VIP_DISH_PRICE_URL + "/list";
            return "redirect:" + redirectUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            redirectAttributes.addFlashAttribute("msg", UPDATE_FAIL_MSG);
            String redirectUrl = "/" + URLConstants.ADMIN_VIP_VIP_DISH_PRICE_URL + "/list";
            return "redirect:" + redirectUrl;
        }
    }

}