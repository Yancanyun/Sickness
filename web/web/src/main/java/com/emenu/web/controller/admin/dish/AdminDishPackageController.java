package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.DishImg;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.dish.DishImgTypeEnums;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.emenu.common.enums.dish.TagEnum;
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

import java.math.BigDecimal;
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
    /**
     * 去列表页
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toDishPackageList(Model model) {
        List<Tag> tagList = new ArrayList<Tag>();
        try {
            tagList.addAll(tagFacadeService.listChildrenByTagId((TagEnum.Package.getId())));
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
    public JSON ajaxDishPackageList(@PathVariable("pageNo") Integer pageNo,
                                    @RequestParam("pageSize") Integer pageSize,
                                    DishSearchDto searchDto) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        searchDto.setPageNo(pageNo);
        searchDto.setPageSize(pageSize);
        searchDto.setIsPackage(1);
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
            dataCount = dishService.countBySearchDto(searchDto);
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
    public JSON ajaxDelDishPackage(@PathVariable("id") Integer id) {
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
    public String toNewDishPackagePage(Model model) {
        try {
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
    public String newDishPackage(DishDto dishDto,
                                 @RequestParam("dishId") String dishId,
                                 @RequestParam("dishQuantity") String dishQuantity,
                                 RedirectAttributes redirectAttributes) {
        try {
            // 把用String按逗号分割成List<Integer>
            List<Integer> dishIdList = new ArrayList<Integer>();
            String[] dishIdStringList = dishId.split(",");
            for (String dishIdString : dishIdStringList) {
                dishIdList.add(Integer.valueOf(dishIdString));
            }
            List<Integer> dishQuantityList = new ArrayList<Integer>();
            String[] dishQuantityStringList = dishQuantity.split(",");
            for (String dishQuantityString : dishQuantityStringList) {
                dishQuantityList.add(Integer.valueOf(dishQuantityString));
            }

            // 若dishIdList和dishQuantityList大小不一致，报系统内部异常
            if (dishIdList.size() != dishQuantityList.size()) {
                throw SSException.get(EmenuException.SystemException);
            }

            // 根据dishIdList和dishQuantityList新增套餐
            List<DishPackage> dishPackageList = new ArrayList<DishPackage>();
            for (int i = 0; i < dishIdList.size(); i++) {
                DishPackage dishPackage = new DishPackage();
                dishPackage.setDishId(dishIdList.get(i));
                dishPackage.setDishQuantity(dishQuantityList.get(i));
                dishPackageList.add(dishPackage);
            }

            // 设置创建者
            dishDto.setCreatedPartyId(getPartyId());
            // 设置单位ID为“份”
            dishDto.setUnitId(11);

            dishPackageService.newDishPackage(dishDto, dishPackageList);

            // 前往添加套餐图片页
            String successUrl = "/" + URLConstants.ADMIN_DISH_PACKAGE_URL + "/img/upload/" + dishDto.getId();
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            redirectAttributes.addAttribute("msg", e.getMessage());

            String failedUrl = "/" + URLConstants.ADMIN_DISH_PACKAGE_URL + "/new";
            // 返回添加失败信息
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            // 返回添加页
            return "redirect:" + failedUrl;
        }
    }

    /**
     * Ajax 统计套餐中的菜品的总价格及数量
     * @param dishId
     * @param dishQuantity
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageNew)
    @RequestMapping(value = "ajax/count", method = RequestMethod.PUT)
    @ResponseBody
    public JSON ajaxCountDish(@RequestParam("dishId") String dishId,
                              @RequestParam("dishPrice") String dishPrice,
                              @RequestParam("dishQuantity") String dishQuantity) throws SSException {
        // 把用String按逗号分割成List<Integer>
        List<Integer> dishIdList = new ArrayList<Integer>();
        String[] dishIdStringList = dishId.split(",");
        for (String dishIdString : dishIdStringList) {
            dishIdList.add(Integer.valueOf(dishIdString));
        }
        List<Integer> dishQuantityList = new ArrayList<Integer>();
        String[] dishQuantityStringList = dishQuantity.split(",");
        for (String dishQuantityString : dishQuantityStringList) {
            dishQuantityList.add(Integer.valueOf(dishQuantityString));
        }

        // 若dishIdList和dishQuantityList大小不一致，报系统内部异常
        if (dishIdList.size() != dishQuantityList.size()) {
            throw SSException.get(EmenuException.SystemException);
        }

        BigDecimal totalPrice = new BigDecimal(0.0);
        Integer totalQuantity = 0;
        for (int i = 0; i < dishIdList.size(); i++) {
            DishDto dishDto = dishService.queryById(dishIdList.get(i));
            totalPrice = totalPrice.add(dishDto.getPrice().multiply(new BigDecimal(dishQuantityList.get(i))));
            totalQuantity += dishQuantityList.get(i);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalPrice", totalPrice.toString()); // 把BigDecimal转成String交给前端，解决保留小数问题
        jsonObject.put("totalQuantity", totalQuantity);

        return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
    }

    /**
     * 去编辑页
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageUpdate)
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdateDishPackagePage(@PathVariable("id") Integer id, Model model) {

        try {
            // 判断是否是套餐
            if (dishPackageService.queryDishPackageById(id).getDishDto().getCategoryId() != TagEnum.Package.getId()) {
                throw SSException.get(EmenuException.DishIdIsNotPackage);
            }

            // 获取分级层数
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

            // 获取分类
            int smallTagId = dishPackageDto.getDishDto().getTagId();
            int bigTagId = tagFacadeService.queryById(smallTagId).getpId();
            List<Tag> bigTagList = tagFacadeService.listChildrenByTagId(dishPackageService.queryDishPackageById(id).getDishDto().getCategoryId());
            List<Tag> smallTagList = tagFacadeService.listChildrenByTagId(bigTagId);
            model.addAttribute("smallTagId", smallTagId);
            model.addAttribute("bigTagId", bigTagId);
            model.addAttribute("bigTagList", bigTagList);
            model.addAttribute("smallTagList", smallTagList);

            // 计算当前套餐内的已存在菜品的总价格及数量
            BigDecimal totalPrice = new BigDecimal(0.0);
            Integer totalQuantity = 0;
            for (DishDto dishDto : dishPackageDto.getChildDishDtoList()) {
                totalPrice = totalPrice.add(dishDto.getPrice().multiply(new BigDecimal(dishDto.getDishPackage().getDishQuantity())));
                totalQuantity += dishDto.getDishPackage().getDishQuantity();
            }
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalQuantity", totalQuantity);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/dish/package/update_home";
    }

    /**
     * 编辑套餐提交
     * @param dishDto
     * @param redirectAttributes
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageUpdate)
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String updateDishPackagePage(DishDto dishDto,
                                        @RequestParam("dishId") String dishId,
                                        @RequestParam("dishQuantity") String dishQuantity,
                                        RedirectAttributes redirectAttributes) {
        try {
            // 把用String按逗号分割成List<Integer>
            List<Integer> dishIdList = new ArrayList<Integer>();
            String[] dishIdStringList = dishId.split(",");
            for (String dishIdString : dishIdStringList) {
                dishIdList.add(Integer.valueOf(dishIdString));
            }
            List<Integer> dishQuantityList = new ArrayList<Integer>();
            String[] dishQuantityStringList = dishQuantity.split(",");
            for (String dishQuantityString : dishQuantityStringList) {
                dishQuantityList.add(Integer.valueOf(dishQuantityString));
            }

            // 若dishIdList和dishQuantityList大小不一致，报系统内部异常
            if (dishIdList.size() != dishQuantityList.size()) {
                throw SSException.get(EmenuException.SystemException);
            }

            // 根据dishIdList和dishQuantityList新增套餐
            List<DishPackage> dishPackageList = new ArrayList<DishPackage>();
            for (int i = 0; i < dishIdList.size(); i++) {
                DishPackage dishPackage = new DishPackage();
                dishPackage.setDishId(dishIdList.get(i));
                dishPackage.setDishQuantity(dishQuantityList.get(i));
                dishPackageList.add(dishPackage);
            }

            // 设置编辑者
            dishDto.setCreatedPartyId(getPartyId());
            // 设置单位ID为“份”
            dishDto.setUnitId(11);

            // 编辑套餐
            dishPackageService.updateDishPackage(dishDto, dishPackageList);

            String successUrl = "/" + URLConstants.ADMIN_DISH_PACKAGE_URL;
            // 返回编辑成功信息
            redirectAttributes.addFlashAttribute("msg", UPDATE_SUCCESS_MSG);
            // 返回列表页
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            redirectAttributes.addAttribute("msg", e.getMessage());

            String failedUrl = "/" + URLConstants.ADMIN_DISH_PACKAGE_URL + "/update";
            // 返回编辑失败信息
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            // 返回编辑页
            return "redirect:" + failedUrl;
        }
    }

    /**
     * 去添加图片页
     * @param dishId
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageNew)
    @RequestMapping(value = "img/upload/{dishId}", method = RequestMethod.GET)
    public String toUploadDishPackageImg(@PathVariable("dishId") Integer dishId,
                                         Model model) {
        model.addAttribute("dishId", dishId);
        return "admin/dish/package/img_upload_home";
    }

    /**
     * 菜品图片上传
     * @param dishId
     * @param imgType
     * @param image
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageNew)
    @RequestMapping(value = "img/upload/ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxUploadDishPackageImg(@RequestParam("dishId") Integer dishId,
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
     * Ajax 删除图片
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageDel)
    @RequestMapping(value = "img/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDelDishPackageImg(@PathVariable("id") Integer id) {
        try {
            dishImgService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
