package com.emenu.web.controller.admin.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.storage.Ingredient;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.StringUtils;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 后台管理一些通用方法的controller
 *
 * @author: zhangteng
 * @time: 2015/11/11 9:21
 **/
@IgnoreLogin
@Controller
@RequestMapping(value = URLConstants.ADMIN_COMMON_URL)
public class AdminCommonController extends AbstractController {

    /**
     * 根据汉字获取拼音
     *
     * @param str
     * @param type
     * @return
     */
    @IgnoreAuthorization
    @RequestMapping(value = "tool/str2py/ajax", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxStr2Py(@RequestParam("str") String str,
                           @RequestParam(value = "type", defaultValue = "head") String type) {
        try {
            String res = "";
            if ("head".equals(type)) {
                res = StringUtils.str2Pinyin(str, StringUtils.PINYIN_TYPE_HEAD_CHAR);
            } else {
                res = StringUtils.str2Pinyin(str, StringUtils.PINYIN_TYPE_FULL);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pinyin", res);

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (Exception e) {
        	LogClerk.errLog.error(e);
        	return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }

    /**
     * 根据关键字获取库存物品名字列表
     *
     * @param keyword
     * @return
     */
    @IgnoreAuthorization
    @RequestMapping(value = "tool/storage/item/search/ajax", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchStorageItem(@RequestParam("keyword") String keyword) {
        List<StorageItem> storageItemList = Collections.emptyList();
        try {
            storageItemList = storageItemService.listByKeyword(keyword);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (StorageItem storageItem : storageItemList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", storageItem.getId());
            jsonObject.put("name", storageItem.getName());
            jsonObject.put("assistantCode", storageItem.getAssistantCode());

            BigDecimal price = new BigDecimal("0.00");
            if (storageItem.getTotalStockInQuantity().compareTo(BigDecimal.ZERO) != 0) {
                price = storageItem.getTotalStockInMoney().divide(storageItem.getTotalStockInQuantity(), 10, BigDecimal.ROUND_HALF_DOWN);
                price = price.setScale(2, BigDecimal.ROUND_HALF_DOWN);
            }
            jsonObject.put("price", price);

            jsonArray.add(jsonObject);
        }

        return sendJsonArray(jsonArray);
    }

    @Module(ModuleEnums.AdminStorageSettlementCheckList)
    @RequestMapping(value = "tool/storage/ingredient/search/ajax",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listIngredientByKeyword(@RequestParam("keyword")String keyword){
        try {
            List<Ingredient> ingredientList = ingredientService.listByKeyword(keyword);
            JSONArray jsonArray = new JSONArray();
            if (Assert.isNotNull(ingredientList)
                    && ingredientList.size() > 0){
                for (Ingredient ingredient : ingredientList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",ingredient.getId());
                    jsonObject.put("name",ingredient.getName());
                    jsonObject.put("assistantCode",ingredient.getAssistantCode());
                    jsonArray.add(jsonObject);
                }
            }
            if (Assert.isNull(keyword)){
                ingredientList = ingredientService.listAll();
                for (Ingredient ingredient : ingredientList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", ingredient.getId());
                    jsonObject.put("name", ingredient.getName());
                    jsonObject.put("assistantCode", ingredient.getAssistantCode());
                    jsonArray.add(jsonObject);
                }
            } else {
                keyword = keyword.replaceAll(" ","");
                if ("".equals(keyword)){
                ingredientList = ingredientService.listAll();
                for (Ingredient ingredient : ingredientList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", ingredient.getId());
                    jsonObject.put("name", ingredient.getName());
                    jsonObject.put("assistantCode", ingredient.getAssistantCode());
                    jsonArray.add(jsonObject);
                    }
                }
            }
            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }
}
