package com.emenu.web.controller.admin.stock;

import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by apple on 17/3/8.
 */
@Module(ModuleEnums.AdminStockSpecifications)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STOCK_SPECIFICATIONS_URL)
public class AdminStockSpecificationsController extends AbstractController {
}
