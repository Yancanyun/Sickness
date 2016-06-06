package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.entity.dish.DishRemarkTag;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * AdminTagController
 * 菜品分类管理Controller
 * @author dujuan
 * @date 2015/10/28
 */
@Controller
@Module(ModuleEnums.AdminDishTag)
@RequestMapping(value = URLConstants.ADMIN_DISH_TAG)
public class AdminTagController extends AbstractController{

    /**
     * 去列表页
     * @return
     */
    @RequestMapping(value = {"","list"}, method = RequestMethod.GET)
    @Module(ModuleEnums.AdminDishTagList)
    public String toListTag(Model model) throws Exception {
        List<TagDto> tagDtoList = tagFacadeService.listDishByCurrentId(0);
        Map<TagDto, Integer> tagDtoMap = new LinkedHashMap<TagDto, Integer>();
        Map<TagDto, Integer> childrenTagDtoMap = new LinkedHashMap<TagDto, Integer>();
        List<RemarkTag> remarkTags = Collections.emptyList();
        remarkTags = remarkTagService.listByParentId(1);
        for(TagDto tagDto : tagDtoList){
            //获取打印机实体判断是否存在
            Printer printer = dishTagPrinterService.queryByTagId(tagDto.getTag().getId());
            Integer printerId = 0;
            if(Assert.isNotNull(printer)){
                printerId = printer.getId();
            }
            tagDtoMap.put(tagDto, printerId);
            for(TagDto childTagDto :tagDto.getChildTagList()){
                //获取打印机实体判断是否存在
                Printer childPrinter = dishTagPrinterService.queryByTagId(tagDto.getTag().getId());
                Integer childPrinterId = 0;
                if(Assert.isNotNull(childPrinter)){
                    childPrinterId = printer.getId();
                }
                childrenTagDtoMap.put(childTagDto, childPrinterId);
            }
        }
        //获取打印机列表
        List<Printer> printerList = printerService.listDishTagPrinter();
        model.addAttribute("tagDtoMap", tagDtoMap);
        model.addAttribute("childrenTagDtoMap", childrenTagDtoMap);
        model.addAttribute("printerList", printerList);
        model.addAttribute("remarkTags",remarkTags);
        return "admin/dish/tag/list_home";
    }

    @RequestMapping(value = "ajax/search", method = RequestMethod.GET)
    @Module(ModuleEnums.AdminDishTagList)
    @ResponseBody
    public JSONObject ajaxListTag(@RequestParam("rootId") Integer rootId){
        try{
            List<TagDto> tagDtoList = tagFacadeService.listDishByCurrentId(rootId);
            JSONArray jsonArray = new JSONArray();
            for(TagDto tagDto : tagDtoList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", tagDto.getTag().getId());
                jsonObject.put("name", tagDto.getTag().getName());
                jsonObject.put("weight", tagDto.getTag().getWeight());
                jsonObject.put("pId", tagDto.getTag().getpId());
                jsonObject.put("pName", tagFacadeService.queryById(tagDto.getTag().getpId()).getName());
                jsonObject.put("printAfterConfirmOrder", tagDto.getTag().getPrintAfterConfirmOrder());
                jsonObject.put("maxPrintNum", tagDto.getTag().getMaxPrintNum());
                jsonObject.put("timeLimit", tagDto.getTag().getTimeLimit());
                //获取打印机实体判断是否存在
                Printer printer = dishTagPrinterService.queryByTagId(tagDto.getTag().getId());
                Integer printerId = 0;
                if(Assert.isNotNull(printer)){
                    printerId = printer.getId();
                }
                jsonObject.put("printerId", printerId);
                JSONArray childJsonArray = new JSONArray();
                for(TagDto childTagDto :tagDto.getChildTagList()){
                    JSONObject childJsonObject = new JSONObject();
                    childJsonObject.put("id", childTagDto.getTag().getId());
                    childJsonObject.put("name", childTagDto.getTag().getName());
                    childJsonObject.put("weight", childTagDto.getTag().getWeight());
                    childJsonObject.put("pId", childTagDto.getTag().getpId());
                    childJsonObject.put("printAfterConfirmOrder", childTagDto.getTag().getPrintAfterConfirmOrder());
                    childJsonObject.put("maxPrintNum", childTagDto.getTag().getMaxPrintNum());
                    childJsonObject.put("timeLimit", childTagDto.getTag().getTimeLimit());
                    //获取打印机实体判断是否存在
                    Printer childPrinter = dishTagPrinterService.queryByTagId(tagDto.getTag().getId());
                    Integer childPrinterId = 0;
                    if(Assert.isNotNull(childPrinter)){
                        childPrinterId = printer.getId();
                    }
                    childJsonObject.put("printerId", childPrinterId);
                    childJsonArray.add(childJsonObject);
                }
                jsonObject.put("smallTagList", childJsonArray);
                jsonArray.add(jsonObject);
            }
            return sendJsonArray(jsonArray, 0);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }


    /**
     * Ajax添加
     * @param tag
     * @return
     */
    @Module(ModuleEnums.AdminDishTagNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewTag(Tag tag, Integer printerId,@RequestParam(required = false,value = "remarkId")String remarkId){
        try{
            Tag newTag = tagFacadeService.newTagPrinter(tag, printerId);
            if(remarkId!=null) {
                String[] remarkIds = remarkId.split(",");
                int[] remarks = new int[remarkIds.length];
                for (int i = 0; i < remarkIds.length; i++) {
                    remarks[i] = Integer.valueOf(remarkIds[i]).intValue();
                }
                List<DishRemarkTag> dishRemarkTagList = new ArrayList<DishRemarkTag>();
                for (int i = 0; i < remarks.length; i++) {
                    DishRemarkTag dishRemarkTag = new DishRemarkTag();
                    dishRemarkTag.setRemarkTagId(remarks[i]);
                    dishRemarkTag.setTagId(newTag.getId());
                    dishRemarkTagList.add(dishRemarkTag);
                }
                dishRemarkTagService.newDishRemarkTags(dishRemarkTagList);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", newTag.getId());
            return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }

    /**
     * Ajax修改
     * @param tag
     * @return
     */
    @Module(ModuleEnums.AdminDishTagUpdate)
    @RequestMapping(value = "ajax",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateTag(Tag tag, Integer printerId,@RequestParam(required = false,value = "remarkId") String remarkId){
        try{
            tagFacadeService.updateTagPrinter(tag, printerId);
            if(remarkId!=null) {
                String[] remarkIds = remarkId.split(",");
                int[] remarks = new int[remarkIds.length];
                for (int i = 0; i < remarkIds.length; i++) {
                    remarks[i] = Integer.valueOf(remarkIds[i]).intValue();
                }
                List<DishRemarkTag> dishRemarkTagList = new ArrayList<DishRemarkTag>();
                for (int i = 0; i < remarks.length; i++) {
                    DishRemarkTag dishRemarkTag = new DishRemarkTag();
                    dishRemarkTag.setRemarkTagId(remarks[i]);
                    dishRemarkTag.setTagId(tag.getId());
                    dishRemarkTagList.add(dishRemarkTag);
                }
                dishRemarkTagService.delBytTagId(tag.getId());
                dishRemarkTagService.newDishRemarkTags(dishRemarkTagList);
            }
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }

    /**
     * Ajax删除
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminDishTagDel)
    @RequestMapping(value = "ajax/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelTag(@PathVariable("id") Integer id){
        try{
            tagFacadeService.delTagPrinter(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }
}
