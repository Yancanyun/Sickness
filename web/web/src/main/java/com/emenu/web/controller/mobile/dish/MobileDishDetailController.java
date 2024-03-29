package com.emenu.web.controller.mobile.dish;

import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.Assert;
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
        List<Remark> remarkList = new ArrayList<Remark>();
        List<Tag> tagList = new ArrayList<Tag>();
        try{
            // 检查Session中是否存在TableId
            if (Assert.isNull(session.getAttribute("tableId"))) {
                return MOBILE_SESSION_OVERDUE_PAGE;
            }

            // 从Session中获取TableID
            Integer tableId = (Integer)session.getAttribute("tableId");
            model.addAttribute("tableId", tableId);

            // 检查餐台是否已开台
            if (tableService.queryStatusById(tableId) == TableStatusEnums.Disabled.getId()
                    || tableService.queryStatusById(tableId) == TableStatusEnums.Enabled.getId()) {
                return MOBILE_NOT_OPEN_PAGE;
            }

            // 获取菜品所有分类
            tagList.addAll(tagFacadeService.listChildrenByTagId((TagEnum.Dishes.getId())));
            tagList.addAll(tagFacadeService.listChildrenByTagId((TagEnum.Drinks.getId())));
            tagList.addAll(tagFacadeService.listChildrenByTagId((TagEnum.Goods.getId())));
            model.addAttribute("tagList", tagList);

            DishDto dishDto = dishService.queryById(dishId);
            // 获取到菜品的小类信息
            remarkList = dishService.queryDishRemarkByDishId(dishId);
            model.addAttribute("dishDto",dishDto);
            model.addAttribute("remarkList",remarkList);

            // 从缓存中取出该餐台已点但未下单的菜品
            TableOrderCache tableOrderCache = orderDishCacheService.listByTableId(tableId);
            List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
            if (tableOrderCache != null) {
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            }
            Float dishTotalNumber = new Float(0);
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