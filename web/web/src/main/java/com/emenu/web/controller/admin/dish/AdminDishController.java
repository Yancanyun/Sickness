package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.*;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.dish.DishImgTypeEnums;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.dish.UnitEnum;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * 菜品管理Controller
 *
 * @author: zhangteng
 * @time: 2015/11/20 16:52
 **/
@Module(value = ModuleEnums.AdminDishManagement)
@Controller
@RequestMapping(value = URLConstants.ADMIN_DISH_URL)
public class AdminDishController extends AbstractController {

    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        List<Tag> tagList = new ArrayList<Tag>();
        try {
            tagList.addAll(tagFacadeService.listChildrenByTagId((TagEnum.Dishes.getId())));
            tagList.addAll(tagFacadeService.listChildrenByTagId((TagEnum.Drinks.getId())));
            tagList.addAll(tagFacadeService.listChildrenByTagId((TagEnum.Goods.getId())));
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("tagList", tagList);
        return "admin/dish/dish/list_home";
    }

    /**
     * ajax获取分页数据
     *
     * @param pageNo
     * @param pageSize
     * @param searchDto
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo") Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize,
                         DishSearchDto searchDto) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        searchDto.setPageNo(pageNo);
        searchDto.setPageSize(pageSize);
        searchDto.setIsPackage(0);
        List<Dish> dishList = Collections.emptyList();
        try {
            dishList = dishService.listBySearchDto(searchDto);
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
            jsonObject.put("unitName", dish.getUnitName());
            jsonObject.put("price", dish.getPrice());
            jsonObject.put("salePrice", dish.getSalePrice());
            jsonObject.put("status", dish.getStatus());
            jsonObject.put("likeNums", dish.getLikeNums());

            jsonArray.add(jsonObject);
        }

        int dataCount = 0;
        try {
            dataCount = dishService.countBySearchDto(searchDto);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * 去添加页
     *
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNew(Model model) {
        try {
            addAttributesToModel(model);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "admin/dish/dish/new_home";
    }

    /**
     * 添加提交
     *
     * @param dishDto
     * @param redirectAttributes
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishNew)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String newDish(DishDto dishDto,
                          RedirectAttributes redirectAttributes) {
        try {
            // 设置创建者
            dishDto.setCreatedPartyId(getPartyId());
            dishDto = dishService.newDish(dishDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            redirectAttributes.addAttribute("msg", e.getMessage());
            return "admin/dish/dish/new_home";

        }

        String redirectUrl = "/" + URLConstants.ADMIN_DISH_URL + "/img/upload/" + dishDto.getId();
        return "redirect:" + redirectUrl;
    }

    /**
     * 去添加图片页
     *
     * @param dishId
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishNew)
    @RequestMapping(value = "img/upload/{dishId}", method = RequestMethod.GET)
    public String toUploadDishImg(@PathVariable("dishId") Integer dishId,
                                  Model model) {
        model.addAttribute("dishId", dishId);
        return "admin/dish/dish/img_upload_home";
    }

    /**
     * 菜品图片上传
     *
     * @param dishId
     * @param imgType
     * @param image
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishNew)
    @RequestMapping(value = "img/upload/ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxUploadImg(@RequestParam("dishId") Integer dishId,
                              @RequestParam("imgType") Integer imgType,
                              @RequestParam("image") PandaworkMultipartFile image) {
        try {
            DishImg dishImg = new DishImg();
            dishImg.setDishId(dishId);
            dishImg.setImgType(DishImgTypeEnums.valueOf(imgType).getId());

            dishImgService.newDishImg(dishImg, image);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax删除图片
     *
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishUpdate)
    @RequestMapping(value = "img/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDeleteDishImg(@PathVariable("id") Integer id) {
        try {
            dishImgService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 去修改页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishUpdate)
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id") Integer id,
                           Model model) {
        try {
            // 判断是否是套餐
            if (dishPackageService.queryDishPackageById(id).getDishDto().getCategoryId() == TagEnum.Package.getId()) {
                throw SSException.get(EmenuException.DishIdIsNotPackage);
            }

            DishDto dishDto = dishService.queryById(id);

            // 分类
            int smallTagId = dishDto.getTagId();
            int bigTagId = tagFacadeService.queryById(smallTagId).getpId();
            List<Tag> bigTagList = tagFacadeService.listChildrenByTagId(dishPackageService.queryDishPackageById(id).getDishDto().getCategoryId());
            List<Tag> smallTagList = tagFacadeService.listChildrenByTagId(bigTagId);

//            for (Tag tag : smallTagList) {
//                if (tag.getId().equals(dishDto.getTagId())) {
//                    smallTagId = tag.getpId();
//                }
//            }

            // 餐段
            Map<Integer, Integer> selectedMealPeriod = new HashMap<Integer, Integer>();
            if (Assert.isNotEmpty(dishDto.getMealPeriodList())) {
                for (DishMealPeriod mealPeriod : dishDto.getMealPeriodList()) {
                    selectedMealPeriod.put(mealPeriod.getMealPeriodId(), 1);
                }
            }

            // 口味
            List<DishTaste> dishTasteList = dishTasteService.listByDishId(dishDto.getId());
            List<Taste> tasteList = new ArrayList<Taste>();
            for (DishTaste dishTaste : dishTasteList) {
                Taste taste = tasteService.queryById(dishTaste.getTasteId());
                tasteList.add(taste);
            }

            model.addAttribute("selectedMealPeriod", selectedMealPeriod);
            model.addAttribute("tagPid", smallTagId);
            model.addAttribute("smallTagList", smallTagList);
            model.addAttribute("bigTagList", bigTagList);
            model.addAttribute("bigTagId", bigTagId);
            model.addAttribute("dishDto", dishDto);
            model.addAttribute("selectTasteList", tasteList);
            addAttributesToModel(model);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "admin/dish/dish/update_home";
    }

    /**
     * 更新提交
     *
     * @param dishDto
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishUpdate)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String updateDish(DishDto dishDto,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("code", 0);
        redirectAttributes.addFlashAttribute("msg", UPDATE_SUCCESS_MSG);
        try {
            dishService.updateDish(dishDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            redirectAttributes.addFlashAttribute("code", 0);
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }

        String redirectUrl = "/" + URLConstants.ADMIN_DISH_URL + "/update/" + dishDto.getId();
        return "redirect:" + redirectUrl;
    }

    /**
     * ajax删除
     *
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishDelete)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDelete(@PathVariable("id") Integer id) {
        try {
            dishService.delById(id);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax更改状态(停用、启用)
     *
     * @param id
     * @param status
     * @return
     */
    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishUpdate)
    @RequestMapping(value = "ajax/status/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSON ajaxUpdateStatus(@PathVariable("id") Integer id,
                                 @RequestParam("status") Integer status) {
        try {
            dishService.updateStatusById(id, DishStatusEnums.valueOf(status));
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 根据父分类id获取子分类
     *
     * @param pId
     * @return
     */
    @RequestMapping(value = "ajax/tag/children", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxGetChildrenTag(@RequestParam("pId") Integer pId) {
        List<Tag> childList = Collections.emptyList();
        try {
            childList = tagFacadeService.listChildrenByTagId(pId);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (Tag tag : childList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tag.getId());
            jsonObject.put("name", tag.getName());

            jsonArray.add(jsonObject);
        }

        return sendJsonArray(jsonArray);
    }


    /**
     * 进入添加页和编辑页之前，添加一些数据到model里
     *
     * @param model
     * @throws SSException
     */
    private void addAttributesToModel(Model model) throws SSException {
        // 获取分类
        String categoryLayerStr = constantService.queryValueByKey(ConstantEnum.DishCategoryLayers.getKey());
        int categoryLayer = 2;
        if (Assert.isNotNull(categoryLayerStr)) {
            categoryLayer = Integer.parseInt(categoryLayerStr);
        }

        // 获取单位
        List<Unit> unitList = unitService.listAll();
        List<Unit> weightUnitList = new ArrayList<Unit>();
        List<Unit> quantityUnitList = new ArrayList<Unit>();
        for (Unit unit : unitList) {
            if (UnitEnum.HundredWeight.getId().equals(unit.getType())) {
                weightUnitList.add(unit);
            } else {
                quantityUnitList.add(unit);
            }
        }

        // 获取餐段
        List<MealPeriod> mealPeriodList = mealPeriodService.listAll();

        // 获取打印机
        List<Printer> printerList = printerService.listDishTagPrinter();

        // 获取口味
        List<Taste> tasteList = tasteService.listAll();

        model.addAttribute("categoryLayer", categoryLayer);
        model.addAttribute("weightUnitList", weightUnitList);
        model.addAttribute("quantityUnitList", quantityUnitList);
        model.addAttribute("mealPeriodList", mealPeriodList);
        model.addAttribute("printerList", printerList);
        model.addAttribute("tasteList", tasteList);
    }
}
