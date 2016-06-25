package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/6/24 10:16
 */
@IgnoreLogin
@IgnoreAuthorization
@Controller
@Module(ModuleEnums.WaiterOrderDish)
@RequestMapping(value = URLConstants.WAITER_ORDER_DISH_URL)
public class WaiterOrderDishController extends AbstractAppBarController {

    /**
     * 去菜品展示页面
     * @param partyId
     * @param tableId
     * @param tagId
     * @param keyword
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toOrderDish(@RequestParam("partyId") Integer partyId,
                                  @RequestParam("tableId") Integer tableId,
                                  @RequestParam(required = false) Integer tagId,
                                  @RequestParam(required = false) String keyword){
        try{

            // TODO: 根据PartyId检查服务员是否可点菜

            // 根据ID检查餐台是否可以点菜
            Integer status = tableService.queryStatusById(tableId);
            if (status != TableStatusEnums.Enabled.getId()
                    && status != TableStatusEnums.Merged.getId()
                    && status != TableStatusEnums.Checkouted.getId()) {
                throw SSException.get(EmenuException.TableIsNotEnabled);
            }

            // 获取二级分类列表
            JSONArray tagList = new JSONArray();
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Dishes.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Goods.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Drinks.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Package.getId()));

            // 获取菜品列表
            DishSearchDto dishSearchDto = new DishSearchDto();
            List<Integer> tagIdList = new ArrayList<Integer>();
            tagIdList.add(tagId);
            dishSearchDto.setTagIdList(tagIdList);
            dishSearchDto.setKeyword(keyword);
            List<DishDto> DishDtoList = dishService.listBySearchDtoInMobile(dishSearchDto);

            // 从缓存中取出该餐台已点但未下单的菜品
            TableOrderCache tableOrderCache = orderDishCacheService.listByTableId(tableId);
            List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
            if (tableOrderCache != null) {
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            }

            // 将菜品信息放入json数组中
            JSONArray dishDtoList = new JSONArray();
            for (DishDto dishDto: DishDtoList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dishId",dishDto.getId());
                jsonObject.put("name",dishDto.getName());
                jsonObject.put("price",dishDto.getPrice());
                jsonObject.put("sale",dishDto.getSalePrice());

                // 从OrderDishCacheList中找dishId相同的菜品，把数量加起来发给前台
                Float number = new Float(0);
                for (OrderDishCache orderDishCache : orderDishCacheList) {
                    if (orderDishCache.getDishId() != null && orderDishCache.getDishId().equals(dishDto.getId())) {
                        number = number + orderDishCache.getQuantity();
                    }
                }
                // 有数量则传值，没有数量则为空
                if (number != 0) {
                    jsonObject.put("number", number);
                }
                dishDtoList.add(jsonObject);
            }

            // 将分类list和菜品list放入一个jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagList",tagList);
            jsonObject.put("dishDtoList",dishDtoList);
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /*@RequestMapping(value = "order",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addDish(){

    }*/

}