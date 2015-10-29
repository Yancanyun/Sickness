package com.emenu.web.controller.admin.page;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.page.Keywords;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.page.KeywordsEnum;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * KeywordsController
 *
 * @author Wang LiMing
 * @date 2015/10/23 11:06
 */
@Controller
@Module(ModuleEnums.AdminBasicInfo)
@RequestMapping(value = URLConstants.KEYWORDS_URL)
public class AdminKeywordsController extends AbstractController {

    /**
     * 去搜索风向标页面
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoKeywordsList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toKeywordsPage(Model model) {
        try {
            List<Keywords> orderingKeywordsList = keywordsService.listByType(KeywordsEnum.Ordering.getId());
            List<Keywords> waiterSystemKeywordsList = keywordsService.listByType(KeywordsEnum.WaiterSystem.getId());
            model.addAttribute("orderingKeywordsList", orderingKeywordsList);
            model.addAttribute("waiterSystemKeywordsList", waiterSystemKeywordsList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return PUB_SYS_ERR_PAGE;
        }
        return "admin/keywords/list";
    }

    /**
     * ajax添加关键字
     *
     * @param keywords
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoKeywordsNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewKeywords(Keywords keywords) {
        try {
            Keywords newKeywords = keywordsService.newKeywords(keywords);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("keywordsId", newKeywords.getId());
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax删除关键字
     *
     * @param keywordsId
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoKeywordsDel)
    @RequestMapping(value = "ajax/del/{keywordsId}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDeleteKeywords(@PathVariable Integer keywordsId){
        try {
            keywordsService.delById(keywordsId);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
