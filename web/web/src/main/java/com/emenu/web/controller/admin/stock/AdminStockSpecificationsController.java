package com.emenu.web.controller.admin.stock;

import com.emenu.common.annotation.Module;
import com.emenu.common.entity.stock.Specifications;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by apple on 17/3/8.
 */
@Module(ModuleEnums.AdminStock)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STOCK_SPECIFICATIONS_URL)
public class AdminStockSpecificationsController extends AbstractController {

    @Module(value = ModuleEnums.AdminStockSpecifications,extModule = ModuleEnums.AdminStockSpecificationsList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String ListSpecifications(Model model){
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

}
