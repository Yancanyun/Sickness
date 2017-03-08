package com.emenu.web.controller.admin.stock;

import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AdminStockItemController
 * 新版库存-物品管理Controller
 *
 * @author pengpeng
 * @time 2017/3/6 9:37
 */
@Controller
@Module(ModuleEnums.AdminStock)
@RequestMapping(value = URLConstants.ADMIN_STOCK_ITEM_URL)
public class AdminStockItemController extends AbstractController{

}
