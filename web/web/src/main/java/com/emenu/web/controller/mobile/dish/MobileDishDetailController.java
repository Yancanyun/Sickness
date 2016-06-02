package com.emenu.web.controller.mobile.dish;

import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishCache;
import com.emenu.common.dto.order.TableOrderCache;
import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/5/30 15:57
 */
@Module(ModuleEnums.MobileDishDetail)
@IgnoreLogin
@Controller
@RequestMapping(value = URLConstants.MOBILE_DISH_DETAIL_URL)
public class MobileDishDetailController extends AbstractController{

    @Module(ModuleEnums.MobileDishDetailList)
    @RequestMapping(value = "{dishId}", method = RequestMethod.GET)
    public String toDishDetail(@PathVariable("dishId") Integer dishId,
                               HttpSession session,
                               Model model){
        List<RemarkDto> remarkDtoList = new ArrayList<RemarkDto>();
        try{
            DishDto dishDto = dishService.queryById(dishId);
            // 获取备注信息（普通备注类型为1）
            List<RemarkTag> childTagList = remarkTagService.listByParentId(1);
            for (RemarkTag remarkTag : childTagList){
                // 获取该子分类下的所有备注
                List<RemarkDto> childRemarkDtoList = remarkService.listRemarkDtoByRemarkTagId(remarkTag.getId());
                for (RemarkDto remarkDto: childRemarkDtoList){
                    remarkDtoList.add(remarkDto);
                }
            }
            model.addAttribute("dishDto",dishDto);
            model.addAttribute("remarkDtoList",remarkDtoList);

            // 从Session中获取TableID
            Integer tableId = (Integer)session.getAttribute("tableId");
            model.addAttribute("tableId", tableId);

            // 从缓存中取出该餐台已点但未下单的菜品
            TableOrderCache tableOrderCache = orderDishCacheService.listByTableId(tableId);
            List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
            if (tableOrderCache != null) {
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            }
            Integer dishTotalNumber = 0;
            for (OrderDishCache orderDishCache : orderDishCacheList) {
                dishTotalNumber = dishTotalNumber + orderDishCache.getQuantity();
            }
            if (dishTotalNumber != 0) {
                model.addAttribute("dishTotalNumber", dishTotalNumber);
            }
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            return MOBILE_SYS_ERR_PAGE;
        }
        return "mobile/dish/detail/detail_home";
    }
}