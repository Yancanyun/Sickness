package com.emenu.web.controller.mobile.dish;

import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/5/30 15:57
 */
@IgnoreLogin
@Controller
@RequestMapping(value = URLConstants.MOBILE_DISH_DETAIL_URL)
public class MobileDishDetailController extends AbstractController{

    @RequestMapping(value = "{dishId}", method = RequestMethod.GET)
    public String toDishDetail(@PathVariable("dishId") Integer dishId,
                               Model model){
        List<RemarkDto> remarkDtoList = new ArrayList<RemarkDto>();
        try{
            DishDto dishDto = dishService.queryById(dishId);
            // 获取备注信息（普通备注类型为1）
            List<RemarkTag> childTagList = remarkTagService.listByParentId(1);
            for (RemarkTag remarkTag: childTagList){
                // 获取该子分类下的所有备注
                List<RemarkDto> childRemarkDtoList = remarkService.listRemarkDtoByRemarkTagId(remarkTag.getId());
                for (RemarkDto remarkDto: childRemarkDtoList){
                    remarkDtoList.add(remarkDto);
                }
            }
            model.addAttribute("dishDto",dishDto);
            model.addAttribute("remarkDtoList",remarkDtoList);
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            return MOBILE_SYS_ERR_PAGE;
        }
        return "mobile/dish/detail/detail_home";
    }

}