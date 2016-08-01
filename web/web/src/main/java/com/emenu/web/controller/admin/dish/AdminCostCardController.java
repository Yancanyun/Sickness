package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.*;
import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.entity.dish.CostCard;
import com.emenu.common.entity.dish.CostCardItem;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.storage.Ingredient;
import com.emenu.common.enums.dish.ItemTypeEnums;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.cache.Cache;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.Log;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.bouncycastle.ocsp.Req;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestScope;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * AdminCostCardController
 *
 * @author quanyibo && xubaorong
 * @date 2016/5/17.
 */
@Module(value = ModuleEnums.AdminDishManagement)
@Controller
@RequestMapping(value = URLConstants.ADMIN_COST_CARD_URL)
public class AdminCostCardController extends AbstractController {

    /**
     * 去成本卡页面
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishCostCard, extModule = ModuleEnums.AdminDishCostCardList)
    @RequestMapping(value = {"","/list"} ,method = RequestMethod.GET)
    public String  toList(Model model) {
        List<Tag> tagList = Collections.EMPTY_LIST;
        try {
            tagList = tagFacadeService.listAllByTagId(TagEnum.Dishes.getId());
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("tagList",tagList);
        return "admin/dish/card/list_home";
    }

    /**
     * ajax获取成本卡列表
     * @param searchDto
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Module(value = ModuleEnums.AdminDishCostCard, extModule = ModuleEnums.AdminDishCostCardList)
    @RequestMapping(value = "/ajax/list/cost/card/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListCostCard(@PathVariable("pageNo") Integer pageNo,
                                       @RequestParam("pageSize") Integer pageSize,
                                       DishSearchDto searchDto)throws SSException {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        searchDto.setPageNo(pageNo);
        searchDto.setPageSize(pageSize);
        List<CostCardDto> costCardDto= new ArrayList<CostCardDto>();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonMessage = new JSONObject();
        int dataCount = 0;
        try {
            dataCount=costCardService.countBySearchDto(searchDto);
            costCardDto = costCardService.queryCostCardDto(searchDto);
            if(Assert.isNotEmpty(costCardDto)) {
                jsonMessage.put("code",AJAX_SUCCESS_CODE);
                for(CostCardDto dto : costCardDto) {
                    JSONObject json = new JSONObject();
                    json.put("id",dto.getId());//成本卡的id
                    json.put("costCardNumber",dto.getCostCardNumber());
                    json.put("name",dto.getName());
                    json.put("assistantCode",dto.getAssistantCode());
                    json.put("mainCost",dto.getMainCost());
                    json.put("assistCost",dto.getAssistCost());
                    json.put("deliciousCost",dto.getDeliciousCost());
                    json.put("standardCost",dto.getStandardCost());
                    jsonArray.add(json);
                }
                jsonMessage.put("list",jsonArray);
                jsonMessage.put("dataCount",dataCount);
            }
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return jsonMessage;
    }

    /**
     * ajax删除成本卡
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminDishCostCard, extModule = ModuleEnums.AdminDishCostCardDel)
    @RequestMapping(value = "/ajax/del" ,method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxDelCostCard(@RequestParam("id")Integer id) throws SSException {
        JSONObject jsonObject = new JSONObject();
        try {
            if(!Assert.lessOrEqualZero(id)) {
                costCardService.delCostCardById(id);
                jsonObject.put("code",0);
                jsonObject.put("errMsg","成本卡删除成功");
            }
            else {
                jsonObject.put("code",1);
                jsonObject.put("errMsg","成本卡未删除成功");
            }
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return jsonObject;
    }

    @RequestMapping(value = "update/{id}",method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id")int id,Model model) throws SSException{
        CostCardDto costCardDto = null;
        List<Ingredient> ingredientList = Collections.emptyList();
        try{
            ingredientList = ingredientService.listAll();
            costCardDto = costCardService.queryById(id);
            List<CostCardItemDto> costCardItemDtoList = costCardItemService.listByCostCardId(id);

            for(CostCardItemDto costCardItemDto:costCardItemDtoList) {
                CostCardIngrediens ingrediens = new CostCardIngrediens();
                ingrediens.setIngredientId(costCardItemDto.getIngredientId().toString());
                ingrediens.setIngredientName(costCardItemDto.getIngredientName());
                ingrediens.setUnit(costCardItemDto.getCostCardUnit());
                ingrediens.setItemType(costCardItemDto.getItemType().toString());
                ingrediens.setOtherCount(costCardItemDto.getOtherCount().toString());
                ingrediens.setNetCount(costCardItemDto.getNetCount().toString());
                ingrediens.setIsAutoOut(costCardItemDto.getIsAutoOut().toString());
                String item = JSONObject.toJSONString(ingrediens);
               /* String item1 = item.replaceAll("\"","\\\"");*/

//                        System.out.print(item1);
              /*  if(i!=costCardItemDtoList.size()-1) {
                    ingredientInfo+=",";
                }*/
               costCardItemDto.setIngredientInfo(item);
            }
            costCardDto.setCostCardItemDtos(costCardItemDtoList);

        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("costCardDto",costCardDto);
        model.addAttribute("ingredientList",ingredientList);
        return "admin/dish/card/update_home";
    }
    /**
     * 去添加成本卡页
     * @param
     * @return
     */
    @Module(value = ModuleEnums.AdminDishCostCard, extModule = ModuleEnums.AdminDishCostCardNew)
    @RequestMapping(value = "/add/cost/card",method = RequestMethod.GET)
    public String toAddCostCard(Model model)throws SSException {
        List<Dish> dishList = Collections.emptyList();
        List<Ingredient> ingredientList = Collections.emptyList();
        try {
            dishList  = dishService.listAll();
            ingredientList = ingredientService.listAll();
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("dishList",dishList);
        model.addAttribute("ingredientList",ingredientList);
        return "admin/dish/card/new_home";
    }

   /* @Module(value = ModuleEnums.AdminDishCostCard, extModule = ModuleEnums.AdminDishCostCard)*/
    @RequestMapping(value = "ajax/dish/list",method = RequestMethod.GET)
    @ResponseBody
    public JSON getDishList(@RequestParam("keyword")String keyword) throws SSException{
        DishSearchDto searchDto = new DishSearchDto();
        searchDto.setKeyword(keyword);
        List<Dish> dishList = Collections.EMPTY_LIST;
        try {
            dishList = dishService.listBySearchDto(searchDto);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for(Dish dish:dishList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",dish.getId());
            jsonObject.put("name",dish.getName());
            jsonObject.put("assistantCode",dish.getAssistantCode());
            jsonArray.add(jsonObject);
        }
        return sendJsonArray(jsonArray);
    }

    @RequestMapping(value = "ajax/ingredient/list",method = RequestMethod.GET)
    @ResponseBody
    public JSON getIngredientList(@RequestParam("keyword")String keyword) throws SSException{
        ItemAndIngredientSearchDto searchDto = new ItemAndIngredientSearchDto();
        searchDto.setKeyword(keyword);
        List<Ingredient> ingredientList = Collections.emptyList();
        try {
            ingredientList = ingredientService.listBySearchDto(searchDto);
        } catch (SSException e){
            LogClerk.errLog.equals(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (Ingredient ingredient:ingredientList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",ingredient.getId());
            jsonObject.put("name",ingredient.getName());
            jsonObject.put("assistantCode",ingredient.getAssistantCode());
            jsonObject.put("unit",ingredient.getCostCardUnitName());
            jsonArray.add(jsonObject);
        }
        return sendJsonArray(jsonArray);
    }

    @RequestMapping(value = "get/unit",method = RequestMethod.GET)
    @ResponseBody
    public JSON getIngredientUnit(@RequestParam("id")int id)throws SSException{
        Ingredient ingredient = null;
        try{
            ingredient = ingredientService.queryById(id);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("unit",ingredient.getCostCardUnitName());
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    @RequestMapping(value = "afterAdd/getPrice",method = RequestMethod.GET)
    @ResponseBody
    public JSON getPriceAfterAdd(@RequestParam(required = false,value = "mainCost")BigDecimal mainCost,
                                 @RequestParam(required = false,value = "assistCost") BigDecimal assistCost,
                                 @RequestParam(required = false,value = "deliciousCost")BigDecimal deliciousCost,
                                 @Param("itemType") int itemType,
                                 @Param("otherCount") BigDecimal otherCount,
                                 @Param("netCount") BigDecimal netCount,
                                 @Param("ingredientId")int ingredientId,
                                 @Param("ingredientName")String ingredientName,
                                 @Param("isAutoOut")int isAutoOut,
                                 @Param("unit")String unit) throws SSException{
        Ingredient ingredient = null;
        BigDecimal standardCost = new BigDecimal(0.00);
        if(mainCost==null){
            mainCost = new BigDecimal(0.00);
        }
        if(assistCost==null){
            assistCost = new BigDecimal(0.00);
        }
        if(deliciousCost==null){
            deliciousCost= new BigDecimal(0.00);
        }
        try{
            ingredient = ingredientService.queryById(ingredientId);
            BigDecimal averagePrice = ingredient.getAveragePrice();
            if(itemType== ItemTypeEnums.MainIngredient.getId()){
                mainCost = mainCost.add(averagePrice.multiply(otherCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            if(itemType==ItemTypeEnums.AssistIngredient.getId()){
                assistCost = assistCost.add(averagePrice.multiply(otherCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            if(itemType==ItemTypeEnums.DeliciousIngredient.getId()){
                deliciousCost = deliciousCost.add(averagePrice.multiply(otherCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            standardCost = mainCost.add(assistCost.add(deliciousCost)).setScale(2, BigDecimal.ROUND_HALF_UP);

        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mainCost",mainCost);
        jsonObject.put("assistCost",assistCost);
        jsonObject.put("deliciousCost",deliciousCost);
        jsonObject.put("standardCost",standardCost);
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 添加和编辑提交后保存
     * @param id
     * @param dishId
     * @param mainCost
     * @param assistCost
     * @param deliciousCost
     * @param ingredientInfo
     * @param request
     * @return
     * @throws SSException
     */
    @RequestMapping(value = "save/all",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject saveAll(@RequestParam(required = false,value = "id")Integer id,
                              @Param("dishId")int dishId,
                              @Param("mainCost")BigDecimal mainCost,
                              @Param("assistCost")BigDecimal assistCost,
                              @Param("deliciousCost")BigDecimal deliciousCost,
                              @Param("ingredientInfo")String ingredientInfo,
                              ServletRequest request) throws SSException{
        try{
            // 接受的原配料数据是json数据，处理之后转成实体接受
            List<CostCardItem> ingredientInfos = new ArrayList<CostCardItem>();
            if (Assert.isNotNull(ingredientInfo)){
                String[] aa = ingredientInfo.split("},");
                for(int i=0;i<aa.length;i++){
                    if(i!=aa.length-1) {
                        aa[i] = aa[i] + "}";
                    }
                    CostCardItem item = (CostCardItem) JSON.parseObject(aa[i], CostCardItem.class);
                    ingredientInfos.add(item);
                }
                int listSize = ingredientInfos.size();
                Map<Integer,CostCardItem> costItems = new HashMap<Integer,CostCardItem>();
                for(CostCardItem costCardItem:ingredientInfos) {
                    costItems.put(costCardItem.getIngredientId(),costCardItem);
                }
                int setSize = costItems.size();
                if(listSize!=setSize){
                    return sendMsgAndCode(AJAX_FAILURE_CODE,"添加失败,不能添加相同原材料");
                }
            }
            //生成成卡编号
            String costCardNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.CostCardSerialNum);
            BigDecimal standardCost = mainCost.add(assistCost.add(deliciousCost)).setScale(2, BigDecimal.ROUND_HALF_UP);
            CostCard costCard = new CostCard();

            if(Assert.isNull(id)) {
                costCard.setDishId(dishId);
                costCard.setCostCardNumber(costCardNumber);
                costCard.setMainCost(mainCost);
                costCard.setAssistCost(assistCost);
                costCard.setDeliciousCost(deliciousCost);
                costCard.setStandardCost(standardCost);
                CostCard dbCostCard = costCardService.newCostCard(costCard);
                Assert.isNotNull(dbCostCard, EmenuException.CostCardInsertFailed);
                if (ingredientInfos.size()>0) {
                    int cardId = costCard.getId();
                    List<CostCardItem> costCardItemList = new ArrayList<CostCardItem>();
                    for (CostCardItem costCardItem : ingredientInfos) {
                        CostCardItem item = new CostCardItem();
                        item.setCostCardId(cardId);
                        item.setIngredientId(costCardItem.getIngredientId());
                        item.setItemType(costCardItem.getItemType());
                        item.setIsAutoOut(costCardItem.getIsAutoOut());
                        item.setNetCount(costCardItem.getNetCount());
                        item.setOtherCount(costCardItem.getOtherCount());
                        costCardItemList.add(item);
                    }
                    costCardItemService.newCostCardItems(costCardItemList);
                }
            } else if(Assert.isNotNull(id)
                    && !Assert.lessOrEqualZero(id)){
                costCard.setId(id);
                costCard.setDishId(dishId);
                costCard.setCostCardNumber(costCardNumber);
                costCard.setMainCost(mainCost);
                costCard.setAssistCost(assistCost);
                costCard.setDeliciousCost(deliciousCost);
                costCard.setStandardCost(standardCost);
                costCardService.updateCostCard(costCard);

                Assert.isNotNull(id,EmenuException.CostCardIdError);
                Assert.lessOrEqualZero(id,EmenuException.CostCardIdError);

                for(CostCardItem costCardItem:ingredientInfos){
                    costCardItem.setCostCardId(id);
                }
                costCardItemService.updateCostCardItemList(ingredientInfos,id);
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            JSONObject jsonObject = new JSONObject();
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String path = httpRequest.getContextPath();
            int port = httpRequest.getServerPort();
            String href = httpRequest.getScheme() + "://" + httpRequest.getServerName()
                    + (port == 80 ? "" : (":" + port)) + "/admin/dish/cost/card";
            jsonObject.put("data",href);
            jsonObject.put("code",AJAX_FAILURE_CODE);
            jsonObject.put("errMsg", e.getMessage());
            return jsonObject;
        }
        JSONObject jsonObject = new JSONObject();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getContextPath();
        int port = httpRequest.getServerPort();
        String href = httpRequest.getScheme() + "://" + httpRequest.getServerName()
                + (port == 80 ? "" : (":" + port)) + "/admin/dish/cost/card";

        jsonObject.put("data",href);
        jsonObject.put("code",AJAX_SUCCESS_CODE);
        return jsonObject;
    }

   @RequestMapping(value = "afterEdit/getPrice",method = RequestMethod.GET)
   @ResponseBody
    public JSON getPriceAfterEdit(@Param("ingredientInfo")String ingredientInfo) throws SSException{
       String[] aa = ingredientInfo.split("},");
       List<CostCardItem> ingredientInfos = new ArrayList<CostCardItem>();
       for(int i=0;i<aa.length;i++){
           if(i!=aa.length-1) {
               aa[i] = aa[i] + "}";
           }
           CostCardItem item = (CostCardItem) JSON.parseObject(aa[i], CostCardItem.class);
           ingredientInfos.add(item);
       }
       BigDecimal mainCost = new BigDecimal(0.00);
       BigDecimal assistCost = new BigDecimal(0.00);
       BigDecimal deliciousCost = new BigDecimal(0.00);
       BigDecimal standardCost = new BigDecimal(0.00);
       try {
           for (CostCardItem costCardItem : ingredientInfos) {
               if (costCardItem != null) {
                   Ingredient ingredient = ingredientService.queryById(costCardItem.getIngredientId());
                   BigDecimal averagePrice = ingredient.getAveragePrice();
                   BigDecimal otherCount = costCardItem.getOtherCount();
                   if(costCardItem.getItemType()== ItemTypeEnums.MainIngredient.getId()){
                      mainCost = mainCost.add(averagePrice.multiply(otherCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
                   }
                   if(costCardItem.getItemType()==ItemTypeEnums.AssistIngredient.getId()){
                       assistCost = assistCost.add(averagePrice.multiply(otherCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
                   }
                   if(costCardItem.getItemType()==ItemTypeEnums.DeliciousIngredient.getId()){
                       deliciousCost = deliciousCost.add(averagePrice.multiply(otherCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
                   }
               }
           }
           standardCost = mainCost.add(assistCost.add(deliciousCost)).setScale(2, BigDecimal.ROUND_HALF_UP);
       }catch (SSException e){
           LogClerk.errLog.error(e);
           return sendErrMsgAndErrCode(e);
       }
       JSONObject jsonObject = new JSONObject();
       jsonObject.put("mainCost",mainCost);
       jsonObject.put("assistCost",assistCost);
       jsonObject.put("deliciousCost",deliciousCost);
       jsonObject.put("standardCost",standardCost);
       return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
   }

    @RequestMapping(value = "afterDel/getPrice",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getPriceAfterDel(@RequestParam(required = false,value = "mainCost")BigDecimal mainCost,@RequestParam(required = false,value = "assistCost") BigDecimal assistCost,
                                       @RequestParam(required = false,value = "deliciousCost")BigDecimal deliciousCost, @Param("itemType") int itemType,
                                       @Param("otherCount") BigDecimal otherCount, @Param("ingredientId")int ingredientId) throws SSException{
        BigDecimal standardCost = new BigDecimal(0.00);
        if(mainCost==null){
            mainCost = new BigDecimal(0.00);
        }
        if(assistCost==null){
            assistCost = new BigDecimal(0.00);
        }
        if(deliciousCost==null){
            deliciousCost= new BigDecimal(0.00);
        }
        try {
            Ingredient ingredient = ingredientService.queryById(ingredientId);
            if (ingredient != null) {
                BigDecimal averagePrice = ingredient.getAveragePrice();
                if (itemType == ItemTypeEnums.MainIngredient.getId()) {
                    mainCost = mainCost.subtract(averagePrice.multiply(otherCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                if (itemType == ItemTypeEnums.AssistIngredient.getId()) {
                    assistCost = assistCost.subtract(averagePrice.multiply(otherCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                if (itemType == ItemTypeEnums.DeliciousIngredient.getId()) {
                    deliciousCost = deliciousCost.subtract(averagePrice.multiply(otherCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                standardCost = mainCost.add(assistCost.add(deliciousCost)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mainCost",mainCost);
        jsonObject.put("assistCost",assistCost);
        jsonObject.put("deliciousCost",deliciousCost);
        jsonObject.put("standardCost",standardCost);
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    @RequestMapping(value = "afterHand/getPrice",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getPriceAfterHand(@Param("mainCost")BigDecimal mainCost, @Param("assistCost") BigDecimal assistCost,
                                        @Param("deliciousCost")BigDecimal deliciousCost) throws SSException{
        BigDecimal standardCost = new BigDecimal(0.00);
        if(mainCost==null){
            mainCost = new BigDecimal(0.00);
        }
        if(assistCost==null){
            assistCost = new BigDecimal(0.00);
        }
        if(deliciousCost==null){
            deliciousCost= new BigDecimal(0.00);
        }
        standardCost = mainCost.add(assistCost.add(deliciousCost)).setScale(2, BigDecimal.ROUND_HALF_UP);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mainCost",mainCost);
        jsonObject.put("assistCost",assistCost);
        jsonObject.put("deliciousCost",deliciousCost);
        jsonObject.put("standardCost",standardCost);
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

}
