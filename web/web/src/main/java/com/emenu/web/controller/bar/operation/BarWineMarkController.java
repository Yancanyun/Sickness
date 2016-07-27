package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * BarWineMarkController
 *
 * @author xiaozl
 * @date: 2016/7/26
 */
@Controller
@Module(ModuleEnums.BarWineMark)
@RequestMapping(value = URLConstants.BAR_WINE_MARK)
public class BarWineMarkController extends AbstractController{

    /**
     * 去标缺酒水
     * @return
     */
    @Module(ModuleEnums.BarWineMarKList)
    @RequestMapping(value = "tomark",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toMarkWine() {
        try {
            Tag tag = tagService.queryByName("酒水");
            Assert.isNotNull(tag, EmenuException.TagNotExist);
            List<Tag> tagList = tagService.listByParentId(tag.getId());
            if (Assert.isNull(tagList)
                    || tagList.size()==0){
                throw SSException.get(EmenuException.TagNotExist);
            }
            List<Integer> tagIdList = new ArrayList<Integer>();
            for (Tag tag1 : tagList){
                tagIdList.add(tag1.getId());
            }
            List<Dish> dishList = dishService.listByTagIdList(tagIdList);
            JSONArray jsonArray = new JSONArray();

            if (Assert.isNull(dishList)
                    || dishList.size()==0){
                return sendJsonArray(jsonArray);
            } else {
                for (Dish dish : dishList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",dish.getId());
                    jsonObject.put("name",dish.getName());
                    jsonObject.put("status",dish.getStatus());
                    jsonArray.add(jsonObject);
                }
            }
            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去标缺酒水
     * @return
     */
    @Module(ModuleEnums.BarWineMarkUpdate)
    @RequestMapping(value = "markit",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject markit(@RequestParam("lackedWineIds")Integer [] lackedWineIds,HttpServletRequest request) {
        try {
            System.out.println(request.getParameter("lackedWineIds"));
            System.out.println("xiao");
            if (Assert.isNotNull(lackedWineIds)
                    && lackedWineIds.length>0){
                List<Integer> lackedWineIdList = new ArrayList<Integer>();
                for (int i = 0; i < lackedWineIds.length; i++) {
                    lackedWineIdList.add(lackedWineIds[i]);
                }
                for (Integer id : lackedWineIds){
                    dishService.updateStatusById(id,DishStatusEnums.Lack);
                }
            }
            Tag tag = tagService.queryByName("酒水");
            Assert.isNotNull(tag, EmenuException.TagNotExist);
            List<Tag> tagList = tagService.listByParentId(tag.getId());
            if (Assert.isNull(tagList)
                    || tagList.size()==0){
                throw SSException.get(EmenuException.TagNotExist);
            }
            List<Integer> tagIdList = new ArrayList<Integer>();
            for (Tag tag1 : tagList){
                tagIdList.add(tag1.getId());
            }
            List<Dish> dishList = dishService.listByTagIdList(tagIdList);
            JSONArray jsonArray = new JSONArray();

            if (Assert.isNull(dishList)
                    || dishList.size()==0){
                return sendJsonArray(jsonArray);
            } else {
                for (Dish dish : dishList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",dish.getId());
                    jsonObject.put("name",dish.getName());
                    jsonObject.put("status",dish.getStatus());
                    jsonArray.add(jsonObject);
                }
            }
            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 还原酒水标缺
     * @return
     */
    @Module(ModuleEnums.BarWineMarkUpdate)
    @RequestMapping(value = "returnwine",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject returnWine(@RequestParam("notLackedWineIds")List<Integer> notLackedWineIds) {
        try {
            if (Assert.isNotNull(notLackedWineIds)
                    && notLackedWineIds.size()>0){
                for (Integer id : notLackedWineIds){
                    dishService.updateStatusById(id,DishStatusEnums.OnSale);
                }
            }
            Tag tag = tagService.queryByName("酒水");
            Assert.isNotNull(tag, EmenuException.TagNotExist);
            List<Tag> tagList = tagService.listByParentId(tag.getId());
            if (Assert.isNull(tagList)
                    || tagList.size()==0){
                throw SSException.get(EmenuException.TagNotExist);
            }
            List<Integer> tagIdList = new ArrayList<Integer>();
            for (Tag tag1 : tagList){
                tagIdList.add(tag1.getId());
            }
            List<Dish> dishList = dishService.listByTagIdList(tagIdList);
            JSONArray jsonArray = new JSONArray();

            if (Assert.isNull(dishList)
                    || dishList.size()==0){
                return sendJsonArray(jsonArray);
            } else {
                for (Dish dish : dishList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",dish.getId());
                    jsonObject.put("name",dish.getName());
                    jsonObject.put("status",dish.getStatus());
                    jsonArray.add(jsonObject);
                }
            }
            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 酒水标缺搜索
     * @return
     */
    @Module(ModuleEnums.BarWineMarkUpdate)
    @RequestMapping(value = "search",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject searchWine(@RequestParam("keyword")String keyword,
                                       @RequestParam("status")Integer status) {
        try {
            if (Assert.isNull(status)
                    || Assert.lessOrEqualZero(status)){
                throw SSException.get(EmenuException.DishStatusIllegal);
            }
            List<Dish> dishList = dishService.listByKeywordAndStatus(keyword,status);

            JSONArray jsonArray = new JSONArray();
            if (Assert.isNull(dishList)
                    || dishList.size()==0){
                return sendJsonArray(jsonArray);
            } else {
                for (Dish dish : dishList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",dish.getId());
                    jsonObject.put("name",dish.getName());
                    jsonObject.put("status",dish.getStatus());
                    jsonArray.add(jsonObject);
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
