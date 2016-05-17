package com.emenu.web.controller.admin.dish;

import com.emenu.common.annotation.Module;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.entity.AbstractEntity;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AdminCostCardController
 *
 * @author xubaorong
 * @date 2016/5/17.
 */
@Module(value = ModuleEnums.Admin)
@Controller
@RequestMapping(value = URLConstants.ADMIN_COST_CARD_URL)
public class AdminCostCardController extends AbstractController {

    @RequestMapping(value = {"","list"} ,method = RequestMethod.GET)
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




}
