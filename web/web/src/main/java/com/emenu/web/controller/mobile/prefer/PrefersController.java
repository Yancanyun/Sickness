package com.emenu.web.controller.mobile.prefer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishTagDto;
import com.emenu.common.entity.dish.DishTag;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.apache.ibatis.annotations.Param;
import org.bouncycastle.ocsp.Req;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PrefersController
 *
 * @author xubaorong
 * @date 2016/5/30.
 */
@Module(ModuleEnums.MobileDishPrefer)
@Controller
@IgnoreLogin
@RequestMapping(value = URLConstants.MOBILE_DISH_PREFER_URL)
public class PrefersController extends AbstractController {

    //去今日特价页
    @RequestMapping(value = "cheap/list",method = RequestMethod.GET)
    public String toTodayCheap(Model model) throws SSException{
        return "mobile/prefer/cheap/list_home";
    }

    @RequestMapping(value = "ajax/cheap/list",method = RequestMethod.GET)
    public JSON ajaxGetCheapList(@Param("page")int page,@Param("pageSize")int pageSize,@Param("userId")int userId,
                                  @RequestParam(required = false,value = "keyword")String keyword) throws SSException{
        List<DishTagDto> dishTagDtos = Collections.emptyList();
        int dataCount= 0;
        try{
            dishTagDtos = dishTagService.listByTagIdAndPage(TagEnum.TodayCheap.getId(),page,pageSize,keyword);
            dataCount = dishTagService.countByTagId(TagEnum.TodayCheap.getId(),keyword);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for(DishTagDto dishTagDto:dishTagDtos){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",dishTagDto.getId());
            jsonObject.put("dishId",dishTagDto.getDishId());
            jsonObject.put("name",dishTagDto.getDishName());
            jsonObject.put("src",dishTagDto.getSmallImg().getImgPath());
            jsonObject.put("price",dishTagDto.getDishPrice());
            jsonObject.put("sale",dishTagDto.getDishSalePrice());
            jsonObject.put("number",5);//测试用，以后有订单再修改
            jsonArray.add(jsonObject);
        }

        return sendJsonArray(jsonArray,dataCount);
    }

    //去销量排行页
    @RequestMapping(value = "rank/list",method = RequestMethod.GET)
    public String toRankList(Model model) throws SSException{
        return "mobile/prefer/rank/list_home";
    }

    @RequestMapping(value = "ajax/rank/list",method = RequestMethod.GET)
    public JSON ajaxGetRankList(@Param("page")int page,@Param("pageSize")int pageSize,@Param("userId")int userId,
                                 @RequestParam(required = false,value = "keyword")String keyword) throws SSException{
        List<DishTagDto> dishTagDtos = Collections.emptyList();
        int dataCount= 0;
        try{
            dishTagDtos = dishTagService.listByTagIdAndPage(TagEnum.SaleRanking.getId(),page,pageSize,keyword);
            dataCount = dishTagService.countByTagId(TagEnum.SaleRanking.getId(),keyword);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for(DishTagDto dishTagDto:dishTagDtos){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",dishTagDto.getId());
            jsonObject.put("dishId",dishTagDto.getDishId());
            jsonObject.put("name",dishTagDto.getDishName());
            jsonObject.put("src",dishTagDto.getSmallImg().getImgPath());
            jsonObject.put("price",dishTagDto.getDishPrice());
            jsonObject.put("sale",dishTagDto.getDishSalePrice());
            jsonObject.put("number",5);//测试用，以后有订单再修改
            jsonArray.add(jsonObject);
        }

        return sendJsonArray(jsonArray,dataCount);
    }

    //去本店特色页
    @RequestMapping(value = "feature/list",method = RequestMethod.GET)
    public String toFeatureList(Model model) throws SSException{
        return "mobile/prefer/feature/list_home";
    }

    @RequestMapping(value = "ajax/feature/list",method = RequestMethod.GET)
    public JSON ajaxGetFeatureList(@Param("page")int page,@Param("pageSize")int pageSize,@Param("userId")int userId,
                                 @RequestParam(required = false,value = "keyword")String keyword) throws SSException{
        List<DishTagDto> dishTagDtos = Collections.emptyList();
        int dataCount= 0;
        try{
            dishTagDtos = dishTagService.listByTagIdAndPage(TagEnum.Feature.getId(),page,pageSize,keyword);
            dataCount = dishTagService.countByTagId(TagEnum.Feature.getId(),keyword);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for(DishTagDto dishTagDto:dishTagDtos){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",dishTagDto.getId());
            jsonObject.put("dishId",dishTagDto.getDishId());
            jsonObject.put("name",dishTagDto.getDishName());
            jsonObject.put("src",dishTagDto.getSmallImg().getImgPath());
            jsonObject.put("price",dishTagDto.getDishPrice());
            jsonObject.put("sale",dishTagDto.getDishSalePrice());
            jsonObject.put("number",5);//测试用，以后有订单再修改
            jsonArray.add(jsonObject);
        }

        return sendJsonArray(jsonArray,dataCount);
    }

}
