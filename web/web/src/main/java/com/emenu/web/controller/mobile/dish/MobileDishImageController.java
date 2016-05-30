package com.emenu.web.controller.mobile.dish;

import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishTagDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * MobileDishImageController
 * 菜品分类图片版
 * @author: yangch
 * @time: 2016/5/28 10:11
 */

@Module(ModuleEnums.MobileDishImage)
@Controller
@IgnoreLogin
@RequestMapping(value = URLConstants.MOBILE_DISH_IMAGE_URL)
public class MobileDishImageController extends AbstractController {
    /**
     * 去列表页
     * @param model
     * @return
     */
    @Module(ModuleEnums.MobileDishImageList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        try {
            // 从今日特价中获取前两个
            List<DishTagDto> todayCheapList = dishTagService.listDtoByTagId(TagEnum.TodayCheap.getId());
            model.addAttribute("todayCheapActive", todayCheapList.get(0));
            model.addAttribute("todayCheapSecond", todayCheapList.get(1));

            // 从本店特色中获取前两个
            List<DishTagDto> featureList = dishTagService.listDtoByTagId(TagEnum.Feature.getId());
            if (featureList.size() > 2) {
                featureList.remove(2);
            }
            model.addAttribute("featureList", featureList);

            // 从菜品中获取八个
            DishSearchDto dishSearchDto = new DishSearchDto();
            dishSearchDto.setPageNo(1);
            dishSearchDto.setPageSize(8);
            List<DishDto> dishDtoList = dishService.listBySearchDtoInMobile(dishSearchDto);
            model.addAttribute("dishDtoList", dishDtoList);

            // 获取二级分类
            List<Tag> tagList = new ArrayList<Tag>();
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Dishes.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Goods.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Drinks.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Package.getId()));
            model.addAttribute("tagList", tagList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "mobile/dish/image/list_home";
    }
}
