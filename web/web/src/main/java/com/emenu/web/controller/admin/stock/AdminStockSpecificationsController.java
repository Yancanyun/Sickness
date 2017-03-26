package com.emenu.web.controller.admin.stock;

import com.emenu.common.annotation.Module;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.stock.Specifications;
import com.emenu.common.enums.dish.UnitEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 17/3/8.
 */
@Module(ModuleEnums.AdminStock)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STOCK_SPECIFICATIONS_URL)
public class AdminStockSpecificationsController extends AbstractController {

    /**
     * 列举所有规格
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecificationsList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String toList(Model model){
        try{
            List<Specifications> specificationsList=specificationsService.listAll();
            model.addAttribute("specificationsList",specificationsList);
        }catch (SSException e)
        {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/stock/specifications/list_home";
    }

    /**
     * 添加规格
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecifications,extModule = ModuleEnums.AdminStockSpecificationsAdd)
    @RequestMapping(value = {"add"},method = RequestMethod.POST)
    public String AddSpecifications(Model model){
        try{
            List<Specifications> specificationsList = specificationsService.listAll();
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
            model.addAttribute("specificationsList",specificationsList);
            model.addAttribute("weightUnit", weightUnit);
            model.addAttribute("quantityUnit", quantityUnit);
        }catch (SSException e)
        {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/stock/specifications/add_home";
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
