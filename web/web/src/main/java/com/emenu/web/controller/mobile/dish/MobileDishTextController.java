package com.emenu.web.controller.mobile.dish;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.call.CallWaiter;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/5/28 8:45
 */
@Controller
@RequestMapping(value = URLConstants.MOBILE_DISH_TEXT_URL)
public class MobileDishTextController extends AbstractController {

    @IgnoreLogin
    @RequestMapping(value = {"","list"}, method = RequestMethod.GET)
    public String toList(Model model)
    {
        try
        {
            List<CallWaiter> callWaiter = new ArrayList<CallWaiter>();
            callWaiter=callWaiterService.queryAllCallWaiter();
            model.addAttribute("callWaiter",callWaiter);//呼叫服务列表
        }
        catch (SSException e) {
            sendErrMsg(e.getMessage());
        }
        return "mobile/dish/text/list_home";
    }

    @RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject AjaxMobileDishText(@PathVariable("curPage") Integer curPage,
                                         @RequestParam Integer pageSize,
                                         @RequestParam ("keyword") String keyword) {
        List<DishDto> dishDtoList = new ArrayList<DishDto>();
        DishSearchDto dishSearchDto = new DishSearchDto();
        Integer dataCount = 0;
        try{
            dishSearchDto.setPageNo(curPage);
            dishSearchDto.setPageSize(pageSize);
            dishSearchDto.setKeyword(keyword);
            dishDtoList = dishService.listBySearchDtoInMobile(dishSearchDto);
            dataCount = dishService.countBySearchDto(dishSearchDto);
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();

        for (DishDto dishDto:dishDtoList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dishId",dishDto.getId());
            jsonObject.put("name",dishDto.getName());
            jsonObject.put("price",dishDto.getPrice());
            jsonObject.put("sale",dishDto.getSalePrice());

            jsonArray.add(jsonObject);
        }

        return sendJsonArray(jsonArray,dataCount);
    }


}