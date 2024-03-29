package com.emenu.web.controller.mobile.prefer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishTagDto;
import com.emenu.common.entity.dish.DishTag;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.apache.ibatis.annotations.Param;
import org.bouncycastle.ocsp.Req;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
public class MobilePrefersController extends AbstractController {

    /**
     * 去今日特价页
     * @param model
     * @param session
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.MobileDishPreferCheapList)
    @RequestMapping(value = "cheap/list",method = RequestMethod.GET)
    public String toTodayCheap(Model model, HttpSession session) throws SSException{
        // 检查Session中是否存在TableId
        if (Assert.isNull(session.getAttribute("tableId"))) {
            return MOBILE_SESSION_OVERDUE_PAGE;
        }

        // 从Session中获取TableID
        Integer tableId = (Integer)session.getAttribute("tableId");
        model.addAttribute("tableId", tableId);

        // 检查餐台是否已开台
        if (tableService.queryStatusById(tableId) == TableStatusEnums.Disabled.getId()
                || tableService.queryStatusById(tableId) == TableStatusEnums.Enabled.getId()) {
            return MOBILE_NOT_OPEN_PAGE;
        }

        return "mobile/prefer/cheap/list_home";
    }

    /**
     * Ajax 获取今日特价数据
     * @param page
     * @param pageSize
     * @param keyword
     * @param session
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.MobileDishPreferCheapList)
    @RequestMapping(value = "ajax/cheap/list",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxGetCheapList(@Param("page") int page,
                                 @Param("pageSize") int pageSize,
                                 @RequestParam(required = false, value = "keyword")String keyword,
                                 HttpSession session) throws SSException{
        List<DishTagDto> dishTagDtos = Collections.emptyList();
        int dataCount= 0;
        try {
            dishTagDtos = dishTagService.listByTagIdAndPage(TagEnum.TodayCheap.getId(), page, pageSize, keyword);
            dataCount = dishTagService.countByTagId(TagEnum.TodayCheap.getId(), keyword);

            // 从缓存中取出该餐台已点但未下单的菜品
            Integer tableId = (Integer)session.getAttribute("tableId");
            TableOrderCache tableOrderCache = orderDishCacheService.listByTableId(tableId);
            List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
            if (tableOrderCache != null) {
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            }

            // 为空
            if(dishTagDtos==null
                    ||dishTagDtos.isEmpty()){
                return sendJsonObject(AJAX_FAILURE_CODE);
            }
            JSONArray jsonArray = new JSONArray();
            for(DishTagDto dishTagDto : dishTagDtos){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", dishTagDto.getId());
                jsonObject.put("dishId", dishTagDto.getDishId());
                jsonObject.put("name", dishTagDto.getDishName());
                if(dishTagDto.getSmallImg()!=null) {
                    jsonObject.put("src", dishTagDto.getSmallImg().getImgPath());
                }
                jsonObject.put("price", dishTagDto.getDishPrice());
                jsonObject.put("sale", dishTagDto.getDishSalePrice());
                // 从OrderDishCacheList中找dishId相同的菜品，把数量加起来发给前台
                Float number = new Float(0);
                for (OrderDishCache orderDishCache : orderDishCacheList) {
                    if (orderDishCache.getDishId() != null && orderDishCache.getDishId().equals(dishTagDto.getDishId())) {
                        number = number + orderDishCache.getQuantity();
                    }
                }
                if (number != 0) {
                    jsonObject.put("number", number);
                }
//            jsonObject.put("rankNumber", 0); // 销量
                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray,dataCount);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去销量排行页
     * @param model
     * @param session
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.MobileDishPreferRankList)
    @RequestMapping(value = "rank/list",method = RequestMethod.GET)
    public String toRankList(Model model, HttpSession session) throws SSException{
        // 检查Session中是否存在TableId
        if (Assert.isNull(session.getAttribute("tableId"))) {
            return MOBILE_SESSION_OVERDUE_PAGE;
        }

        // 从Session中获取TableID
        Integer tableId = (Integer)session.getAttribute("tableId");
        model.addAttribute("tableId", tableId);

        // 检查餐台是否已开台
        if (tableService.queryStatusById(tableId) == TableStatusEnums.Disabled.getId()
                || tableService.queryStatusById(tableId) == TableStatusEnums.Enabled.getId()) {
            return MOBILE_NOT_OPEN_PAGE;
        }

        return "mobile/prefer/rank/list_home";
    }

    /**
     * Ajax 获取销量排行数据
     * @param page
     * @param pageSize
     * @param keyword
     * @param session
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.MobileDishPreferRankList)
    @RequestMapping(value = "ajax/rank/list",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxGetRankList(@Param("page") int page,
                                @Param("pageSize") int pageSize,
                                @RequestParam(required = false, value = "keyword") String keyword,
                                HttpSession session) throws SSException{
        List<DishTagDto> dishTagDtos = Collections.emptyList();
        int dataCount= 0;
        try{
            dishTagDtos = dishTagService.listByTagIdAndPage(TagEnum.SaleRanking.getId(),page,pageSize,keyword);
            dataCount = dishTagService.countByTagId(TagEnum.SaleRanking.getId(),keyword);

            // 从缓存中取出该餐台已点但未下单的菜品
            Integer tableId = (Integer)session.getAttribute("tableId");
            TableOrderCache tableOrderCache = orderDishCacheService.listByTableId(tableId);
            List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
            if (tableOrderCache != null) {
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            }

            // 为空
            if(dishTagDtos==null
                    ||dishTagDtos.isEmpty()){
                return sendJsonObject(AJAX_FAILURE_CODE);
            }

            JSONArray jsonArray = new JSONArray();
            for(DishTagDto dishTagDto:dishTagDtos){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",dishTagDto.getId());
                jsonObject.put("dishId",dishTagDto.getDishId());
                jsonObject.put("name",dishTagDto.getDishName());
                if(dishTagDto.getSmallImg()!=null) {
                    jsonObject.put("src", dishTagDto.getSmallImg().getImgPath());
                }
                jsonObject.put("sale",dishTagDto.getDishSalePrice());
                // 从OrderDishCacheList中找dishId相同的菜品，把数量加起来发给前台
                Float number = new Float(0);
                for (OrderDishCache orderDishCache : orderDishCacheList) {
                    if (orderDishCache.getDishId() != null && orderDishCache.getDishId().equals(dishTagDto.getDishId())) {
                        number = number + orderDishCache.getQuantity();
                    }
                }
                if (number != 0) {
                    jsonObject.put("number", number);
                }
//            jsonObject.put("rankNumber", 0); // 销量
                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray,dataCount);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去本店特色页
     * @param model
     * @param session
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.MobileDishPreferFeatureList)
    @RequestMapping(value = "feature/list",method = RequestMethod.GET)
    public String toFeatureList(Model model, HttpSession session) throws SSException{
        // 检查Session中是否存在TableId
        if (Assert.isNull(session.getAttribute("tableId"))) {
            return MOBILE_SESSION_OVERDUE_PAGE;
        }

        // 从Session中获取TableID
        Integer tableId = (Integer)session.getAttribute("tableId");
        model.addAttribute("tableId", tableId);

        // 检查餐台是否已开台
        if (tableService.queryStatusById(tableId) == TableStatusEnums.Disabled.getId()
                || tableService.queryStatusById(tableId) == TableStatusEnums.Enabled.getId()) {
            return MOBILE_NOT_OPEN_PAGE;
        }

        return "mobile/prefer/feature/list_home";
    }

    /**
     * Ajax 获取销量排行数据
     * @param page
     * @param pageSize
     * @param keyword
     * @param session
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.MobileDishPreferFeatureList)
    @ResponseBody
    @RequestMapping(value = "ajax/feature/list",method = RequestMethod.GET)
    public JSON ajaxGetFeatureList(@Param("page") int page,
                                   @Param("pageSize") int pageSize,
                                    @RequestParam(required = false, value = "keyword") String keyword,
                                   HttpSession session) throws SSException{
        List<DishTagDto> dishTagDtos = Collections.emptyList();
        int dataCount= 0;
        try{
            dishTagDtos = dishTagService.listByTagIdAndPage(TagEnum.Feature.getId(),page,pageSize,keyword);
            dataCount = dishTagService.countByTagId(TagEnum.Feature.getId(),keyword);

            // 从缓存中取出该餐台已点但未下单的菜品
            Integer tableId = (Integer)session.getAttribute("tableId");
            TableOrderCache tableOrderCache = orderDishCacheService.listByTableId(tableId);
            List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
            if (tableOrderCache != null) {
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            }

            // 为空
            if(dishTagDtos==null
                    ||dishTagDtos.isEmpty()){
                return sendJsonObject(AJAX_FAILURE_CODE);
            }

            JSONArray jsonArray = new JSONArray();
            for(DishTagDto dishTagDto:dishTagDtos){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",dishTagDto.getId());
                jsonObject.put("dishId",dishTagDto.getDishId());
                jsonObject.put("name",dishTagDto.getDishName());
                if(dishTagDto.getSmallImg()!=null) {
                    jsonObject.put("src", dishTagDto.getSmallImg().getImgPath());
                }
                jsonObject.put("price",dishTagDto.getDishPrice());
                jsonObject.put("sale",dishTagDto.getDishSalePrice());
                // 从OrderDishCacheList中找dishId相同的菜品，把数量加起来发给前台
                Float number = new Float(0);
                for (OrderDishCache orderDishCache : orderDishCacheList) {
                    if (orderDishCache.getDishId() != null && orderDishCache.getDishId().equals(dishTagDto.getDishId())) {
                        number = number + orderDishCache.getQuantity();
                    }
                }
                if (number != 0) {
                    jsonObject.put("number", number);
                }
//            jsonObject.put("rankNumber", 0); // 销量
                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray,dataCount);
         }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
