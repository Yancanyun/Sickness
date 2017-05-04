package com.emenu.web.controller.admin.stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StockTagController
 *
 * @author renhongshuai
 * @Time 2017/5/4 15:50.
 */
@Module(ModuleEnums.AdminStock)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STOCK_TAG_URL)
public class StockTagController extends AbstractController {

    /**
     * 去列表页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminStockTagList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String toList(Model model){
        try{
            List<TagDto> tagList = stockTagService.listAll();
            model.addAttribute("tagDtoList",tagList);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/stock/tag/list_home";
    }

    @Module(ModuleEnums.AdminStockTagNew)
    @RequestMapping(value = "new",method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxNew(@RequestParam("pId") Integer pId,
                        @RequestParam("name") String name){
        try{
            Tag tag = stockTagService.newStorageTag(pId,name);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tag.getId());
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax修改
     *
     * @param id
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminStockTagUpdate)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSON ajaxUpdate(@PathVariable("id") Integer id,
                           @RequestParam("pId") Integer pId,
                           @RequestParam("name") String name) {
        try {
            stockTagService.updateStorageTag(id, pId, name);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax删除
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminStockTagDelete)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDelete(@PathVariable("id") Integer id) {
        try {
            stockTagService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
