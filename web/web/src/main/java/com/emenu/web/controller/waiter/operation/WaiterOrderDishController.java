package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.dish.Taste;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务员点餐
 *
 * @author chenyuting
 * @date 2016/6/24 10:16
 */
@Controller
@Module(ModuleEnums.WaiterOrderDish)
@RequestMapping(value = URLConstants.WAITER_ORDER_DISH_URL)
public class WaiterOrderDishController extends AbstractController {

    /**
     * 去菜品列表页面
     * @param tableId
     * @param tagId
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toOrderDish(@RequestParam("tableId") Integer tableId,
                                  @RequestParam("tagId") Integer tagId,
                                  HttpSession httpSession){
        try{
            // 判断服务员是否可以服务该餐台
            Integer partyId = (Integer)httpSession.getAttribute("partyId");
            EmployeeDto employeeDto = employeeService.queryEmployeeDtoByPartyId(partyId);
            List<Integer> tableIdList = employeeDto.getTables();
            if (Assert.isNull(tableIdList)) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }
            Boolean canService = false;
            for (Integer integer : tableIdList) {
                if (tableId == integer) {
                    canService = true;
                    break;
                }
            }
            if (canService == false) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }

            // 获取二级分类列表
            JSONArray tagList = new JSONArray();
            List<Tag> tags = new ArrayList<Tag>();
            // 将“全部”分类加入list中，方便前台获取
            Tag tagAll = new Tag();
            tagAll.setId(0);
            tagAll.setName("全部");
            tags.add(tagAll);
            tags.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Dishes.getId()));
            tags.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Goods.getId()));
            tags.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Drinks.getId()));
            tags.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Package.getId()));
            // 放入Json数组中
            for(Tag tag: tags){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tagId",tag.getId());
                jsonObject.put("tagName",tag.getName());
                tagList.add(jsonObject);
            }

            // 获取菜品列表
            DishSearchDto dishSearchDto = new DishSearchDto();
            List<Integer> tagIdList = new ArrayList<Integer>();
            if(tagId != 0){
                tagIdList.add(tagId);
            }
            dishSearchDto.setTagIdList(tagIdList);
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
                jsonObject.put("dishName",dishDto.getName());
                jsonObject.put("salePrice",dishDto.getSalePrice());

                // 从OrderDishCacheList中找dishId相同的菜品，把数量加起来发给前台
                Float number = 0f;
                for (OrderDishCache orderDishCache : orderDishCacheList) {
                    if (orderDishCache.getDishId() != null && orderDishCache.getDishId().equals(dishDto.getId())) {
                        number = number + orderDishCache.getQuantity();
                    }
                }
                // 有数量则传值，没有数量则为空
                jsonObject.put("dishNumber", number);
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

    /**
     * 点菜-搜索
     * @param keyword
     * @return
     */
    @RequestMapping(value = "search",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject searchDish(@RequestParam("key") String keyword){
        try{
            DishSearchDto dishSearchDto = new DishSearchDto();
            dishSearchDto.setPageNo(0);
            dishSearchDto.setPageSize(10);
            dishSearchDto.setKeyword(keyword);
            List<DishDto> dishDtoList = dishService.listBySearchDtoInMobile(dishSearchDto);

            JSONArray dishList = new JSONArray();
            for (DishDto dishDto : dishDtoList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dishId", dishDto.getId());
                jsonObject.put("dishName", dishDto.getName());
                dishList.add(jsonObject);
            }
            return sendJsonArray(dishList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 进入菜品详情页
     * @param dishId
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toDishDetail(@RequestParam("dishId") Integer dishId){
        try{

            JSONObject jsonObject = new JSONObject();
            DishDto dishDto = dishService.queryById(dishId);
            jsonObject.put("dishId",dishDto.getId());
            jsonObject.put("dishName",dishDto.getName());
            jsonObject.put("salePrice",dishDto.getSalePrice());

            // 口味信息放入json中
            JSONArray tasteJsonArray = new JSONArray();
            for(Taste taste: dishDto.getTasteList()){
                JSONObject tasteJsonObject = new JSONObject();
                tasteJsonObject.put("tasteId",taste.getId());
                tasteJsonObject.put("tasteName",taste.getName());
                tasteJsonArray.add(tasteJsonObject);
            }
            jsonObject.put("tasteList", tasteJsonArray);


            // TODO 服务员端暂不显示快捷备注
            // 菜品备注信息放入json中
            /*JSONArray remarkJsonArray = new JSONArray();
            List<Remark> remarkList = dishService.queryDishRemarkByDishId(dishId);
            for (Remark remark: remarkList){
                JSONObject remarkJsonObject = new JSONObject();
                remarkJsonObject.put("remarkId",remark.getId());
                remarkJsonObject.put("remarkName",remark.getName());
                remarkJsonArray.add(remarkJsonObject);
            }
            jsonObject.put("remarkList",remarkJsonArray);*/
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 详情页点餐
     * @param httpSession
     * @param tableId
     * @param dishId
     * @param serveType
     * @param number
     * @param taste
     * @param remarks
     * @return
     */
    @RequestMapping(value = "new", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject orderDish(HttpSession httpSession,
                                @RequestParam("tableId") Integer tableId,
                                @RequestParam("dishId") Integer dishId,
                                @RequestParam(value = "serveType", required = false) Integer serveType,
                                @RequestParam("number") Float number,
                                @RequestParam(value = "taste", required = false) Integer taste,
                                @RequestParam("remarks") String remarks){
        try{
            // 判断服务员是否可以服务该餐台
            Integer partyId = (Integer)httpSession.getAttribute("partyId");
            EmployeeDto employeeDto = employeeService.queryEmployeeDtoByPartyId(partyId);
            List<Integer> tableIdList = employeeDto.getTables();
            if (Assert.isNull(tableIdList)) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }
            Boolean canService = false;
            for (Integer integer : tableIdList) {
                if (tableId == integer) {
                    canService = true;
                    break;
                }
            }
            if (canService == false) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }

            //检查这个桌是否已经开台
            if (tableService.queryStatusById(tableId) == TableStatusEnums.Disabled.getId()
                    || tableService.queryStatusById(tableId) == TableStatusEnums.Enabled.getId()) {
                throw SSException.get(EmenuException.TableNotOpen);
            }

            // 添加菜品至缓存
            OrderDishCache orderDishCache = new OrderDishCache();
            orderDishCache.setDishId(dishId);
            orderDishCache.setServeType(serveType);
            orderDishCache.setQuantity(number);
            orderDishCache.setTasteId(taste);
            orderDishCache.setRemark(remarks);
            orderDishCacheService.newDish(tableId, orderDishCache);
            // 无数据的返回
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 快捷点餐
     * @param tableId
     * @param dishId
     * @return
     */
    @RequestMapping(value = "new/quickly", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject orderDishQuickly(@RequestParam("tableId") Integer tableId,
                                       @RequestParam("dishId") Integer dishId,
                                       HttpSession httpSession){
        try{
            // 判断服务员是否可以服务该餐台
            Integer partyId = (Integer)httpSession.getAttribute("partyId");
            EmployeeDto employeeDto = employeeService.queryEmployeeDtoByPartyId(partyId);
            List<Integer> tableIdList = employeeDto.getTables();
            if (Assert.isNull(tableIdList)) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }
            Boolean canService = false;
            for (Integer integer : tableIdList) {
                if (tableId == integer) {
                    canService = true;
                    break;
                }
            }
            if (canService == false) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }

            OrderDishCache orderDishCache = new OrderDishCache();
            orderDishCache.setDishId(dishId);
            orderDishCacheService.newDish(tableId, orderDishCache);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}