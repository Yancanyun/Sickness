package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.tag.Tag;
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
 * StorageTagController
 *
 * @author: zhangteng
 * @time: 2015/11/11 9:19
 **/
@Module(ModuleEnums.AdminStorage)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STORAGE_TAG_URL)
public class StorageTagController extends AbstractController {

    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminStorageTagList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        try {
            List<TagDto> tagDtoList = storageTagService.listAll();
            model.addAttribute("tagDtoList", tagDtoList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "admin/storage/tag/list_home";
    }

    /**
     * ajax添加
     *
     * @param pId
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminStorageTagNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxNew(@RequestParam("pId") Integer pId,
                        @RequestParam("name") String name) {
        try {
            Tag tag = storageTagService.newStorageTag(pId, name);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tag.getId());

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax更新
     *
     * @param id
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminStorageTagUpdate)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSON ajaxUpdate(@PathVariable("id") Integer id,
                           @RequestParam("pId") Integer pId,
                           @RequestParam("name") String name) {
        try {
            storageTagService.updateStorageTag(id, pId, name);
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
    @Module(ModuleEnums.AdminStorageTagDelete)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDelete(@PathVariable("id") Integer id) {
        try {
            storageTagService.delById(id);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
