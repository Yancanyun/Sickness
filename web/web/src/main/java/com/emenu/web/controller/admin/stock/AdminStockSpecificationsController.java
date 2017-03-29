package com.emenu.web.controller.admin.stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.stock.Specifications;
import com.emenu.common.enums.dish.UnitEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by apple on 17/3/8.
 */
@Module(ModuleEnums.AdminStock)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STOCK_SPECIFICATIONS_URL)
public class AdminStockSpecificationsController extends AbstractController {

    /**
     * 去列表页
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecificationsList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String toList(){
        return "admin/stock/specifications/list_home";
    }

    /**
     * ajax刷分页
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecifications, extModule = ModuleEnums.AdminStockSpecificationsList)
    @RequestMapping(value = "ajax/list/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo")Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize){
        pageSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
        }
        List<Specifications> list = Collections.emptyList();
        try{
            list = specificationsService.listByPage(offset,pageSize);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (Specifications  specifications: list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", specifications.getId());
            jsonObject.put("orderUnitName", specifications.getOrderUnitName());
            jsonObject.put("orderToStorage", specifications.getOrderToStorage());
            jsonObject.put("storageUnitName", specifications.getStorageUnitName());
            jsonObject.put("storageToCost", specifications.getStorageToCost());
            jsonObject.put("costCardUnitName", specifications.getCostCardUnitName());
            jsonArray.add(jsonObject);
        }
        int dataCount = 0;
        try {
            dataCount = specificationsService.count();
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
    @Module(value = ModuleEnums.AdminStockSpecifications, extModule = ModuleEnums.AdminStockSpecificationsAdd)
    @RequestMapping(value = "tonew", method = RequestMethod.GET)
    public String toNew(Model model) {
            try {
                List<Unit> unitList = unitService.listAll();
                List<Unit> weightUnit = new ArrayList<Unit>();
                List<Unit> quantityUnit = new ArrayList<Unit>();
                for (Unit unit : unitList) {
                    if (UnitEnum.HundredWeight.getId().equals(unit.getType())) {
                        weightUnit.add(unit);
                    } else {
                        quantityUnit.add(unit);
                    }
                }
                model.addAttribute("weightUnit", weightUnit);
                model.addAttribute("quantityUnit", quantityUnit);
            } catch (SSException e) {
                LogClerk.errLog.error(e);
                sendErrMsg(e.getMessage());
                return ADMIN_SYS_ERR_PAGE;
            }
        return "admin/stock/specifications/add_home";
    }

    /**
     * 添加规格
     * @param specifications
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecifications,extModule = ModuleEnums.AdminStockSpecificationsAdd)
    @RequestMapping(value = "ajax/new",method = RequestMethod.POST)
    @ResponseBody
    public String AddSpecifications(Specifications specifications){
         try{
             Specifications specifications1 = specifications;
             specificationsService.add(specifications);
             return "admin/stock/specifications/list_home";
         } catch (SSException e) {
             LogClerk.errLog.error(e);
             sendErrMsg(e.getMessage());
             return ADMIN_SYS_ERR_PAGE;
         }
    }

    /**
     * 根据id查询规格
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecifications, extModule = ModuleEnums.AdminStockSpecificationsList)
    @RequestMapping(value = "toQuery/{id}",method = RequestMethod.GET)
    public String toList(@PathVariable("id")Integer id, Model model){
        try {
            Specifications specifications = specificationsService.queryById(id);
            model.addAttribute("specifications",specifications);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/stock/specifications/list_home";
    }
}
