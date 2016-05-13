package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AdminDishPackageController
 *
 * @author: yangch
 * @time: 2016/3/25 9:48
 */
@Module(value = ModuleEnums.AdminDishManagement)
@Controller
@RequestMapping(value = URLConstants.ADMIN_DISH_PACKAGE_URL)
public class AdminDishPackageController extends AbstractController {
    // 暂存添加的菜品
    private List<DishPackage> dishPackageList = new ArrayList<DishPackage>();

    /**
     * 去列表页
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        List<Tag> tagList = new ArrayList<Tag>();
        try {
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Package.getId()));
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("tagList", tagList);
        return "admin/dish/package/list_home";
    }

    /**
     * Ajax 获取分页数据
     * @param pageNo
     * @param pageSize
     * @param searchDto
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo") Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize,
                         DishSearchDto searchDto) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        searchDto.setPageNo(pageNo);
        searchDto.setPageSize(pageSize);
        List<Dish> dishList = Collections.emptyList();
        try {
            dishList = dishPackageService.listBySearchDto(searchDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (Dish dish : dishList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", dish.getId());
            jsonObject.put("dishNumber", dish.getDishNumber());
            jsonObject.put("assistantCode", dish.getAssistantCode());
            jsonObject.put("name", dish.getName());
            try {
                jsonObject.put("categoryId", tagFacadeService.queryById(dish.getTagId()).getName());
            } catch (SSException e) {
                LogClerk.errLog.error(e);
                return sendErrMsgAndErrCode(e);
            }
            jsonObject.put("price", dish.getPrice());
            jsonObject.put("salePrice", dish.getSalePrice());
            jsonObject.put("status", dish.getStatus());
            jsonObject.put("likeNums", dish.getLikeNums());

            jsonArray.add(jsonObject);
        }

        int dataCount = 0;
        try {
            dataCount = dishPackageService.countBySearchDto(searchDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * Ajax 删除
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageDel)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDelete(@PathVariable("id") Integer id) {
        try {
            // 因为套餐也是一个"菜品"，因而直接使用dishService中的方法
            dishService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * Ajax 修改状态
     * @param id
     * @param status
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageUpdate)
    @RequestMapping(value = "ajax/status/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSON ajaxUpdateStatus(@PathVariable("id") Integer id,
                                 @RequestParam("status") Integer status) {
        try {
            // 因为套餐也是一个"菜品"，因而直接使用dishService中的方法
            dishService.updateStatusById(id, DishStatusEnums.valueOf(status));
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 去新增页
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNewDishPackage(Model model) {
        try {
            // TODO: 根据分类层数改变选择列表数量
            String categoryLayerStr = constantService.queryValueByKey(ConstantEnum.DishCategoryLayers.getKey());
            int categoryLayer = 2;
            if (Assert.isNotNull(categoryLayerStr)) {
                categoryLayer = Integer.parseInt(categoryLayerStr);
            }
            model.addAttribute("categoryLayer", categoryLayer);

            // 获取餐段
            List<MealPeriod> mealPeriodList = mealPeriodService.listAll();
            model.addAttribute("mealPeriodList", mealPeriodList);

            // 获取打印机
            List<Printer> printerList = printerService.listDishTagPrinter();
            model.addAttribute("printerList", printerList);

            // 获取"套餐"大类下的子类
            List<Tag> childTagList = tagFacadeService.listChildrenByTagId(TagEnum.Package.getId());
            model.addAttribute("childTagList", childTagList);

            // 获取菜品列表
            List<Dish> dishList = dishService.listAll();
            for (Dish dish: dishList) {
                // 获取每个菜品的单位名称
                Integer unitId = dish.getUnitId();
                if (Assert.isNotNull(unitId) && !Assert.lessOrEqualZero(unitId)) {
                    dish.setUnitName(unitService.queryById(unitId).getName());
                }
            }
            model.addAttribute("dishList", dishList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/dish/package/new_home";
    }

    /**
     * 新增套餐提交
     * @param dishDto
     * @param redirectAttributes
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageNew)
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newDish(DishDto dishDto,
                          RedirectAttributes redirectAttributes) {
        try {
            // 设置创建者
            dishDto.setCreatedPartyId(getPartyId());
            // 设置单位ID为“份”
            dishDto.setUnitId(11);

            // 新增套餐
            dishPackageService.newDishPackage(dishDto, dishPackageList);
            // 将套餐中包含的菜品清空
            dishPackageList.clear();

            String successUrl = "/" + URLConstants.ADMIN_DISH_PACKAGE_URL;
            //返回添加成功信息
            redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
            //返回列表页
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            redirectAttributes.addAttribute("msg", e.getMessage());

            String failedUrl = "/" + URLConstants.ADMIN_DISH_PACKAGE_URL + "/new";
            //返回添加失败信息
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            //返回添加页
            return "redirect:" + failedUrl;
        }
    }

    /**
     * Ajax 保存套餐中的菜品
     * @param dishId
     * @param dishQuantity
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageNew)
    @RequestMapping(value = "ajax/save/dish", method = RequestMethod.PUT)
    @ResponseBody
    public JSON ajaxSaveDish(@RequestParam("dishId") Integer dishId,
                             @RequestParam("dishQuantity") Integer dishQuantity) {
        // 先清空原有的dishPackageList
        dishPackageList.clear();

        DishPackage dishPackage = new DishPackage();
        dishPackage.setDishId(dishId);
        dishPackage.setDishQuantity(dishQuantity);
        dishPackageList.add(dishPackage);

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    public List<DishPackage> getDishPackageList() {
        return dishPackageList;
    }

    public void setDishPackageList(List<DishPackage> dishPackageList) {
        this.dishPackageList = dishPackageList;
    }

    /**
     * 去编辑页
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageUpdate)
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdateDishPackage(@PathVariable("id") Integer id, Model model) {
        try {
            // TODO: 根据分类层数改变选择列表数量
            String categoryLayerStr = constantService.queryValueByKey(ConstantEnum.DishCategoryLayers.getKey());
            int categoryLayer = 2;
            if (Assert.isNotNull(categoryLayerStr)) {
                categoryLayer = Integer.parseInt(categoryLayerStr);
            }
            model.addAttribute("categoryLayer", categoryLayer);

            // 获取餐段
            List<MealPeriod> mealPeriodList = mealPeriodService.listAll();
            model.addAttribute("mealPeriodList", mealPeriodList);

            // 获取打印机
            List<Printer> printerList = printerService.listDishTagPrinter();
            model.addAttribute("printerList", printerList);

            // 获取"套餐"大类下的子类
            List<Tag> childTagList = tagFacadeService.listChildrenByTagId(TagEnum.Package.getId());
            model.addAttribute("childTagList", childTagList);

            // 获取所有菜品列表
            List<Dish> dishList = dishService.listAll();
            for (Dish dish: dishList) {
                // 获取每个菜品的单位名称
                Integer unitId = dish.getUnitId();
                if (Assert.isNotNull(unitId) && !Assert.lessOrEqualZero(unitId)) {
                    dish.setUnitName(unitService.queryById(unitId).getName());
                }
            }
            model.addAttribute("dishList", dishList);

            // 获取当前套餐的信息及当前套餐内已存在的菜品
            DishPackageDto dishPackageDto = dishPackageService.queryDishPackageById(id);
            model.addAttribute("dishPackageDto", dishPackageDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/dish/package/update_home";
    }
}
