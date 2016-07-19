package com.emenu.web.controller.waiter.call;

import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * WaiterCallController
 *
 * @author: quanyibo
 * @time: 2016/6/21
 */
@Controller
@Module(ModuleEnums. WaiterCallList)
@RequestMapping(value = URLConstants.WAITER_CALL_LIST_URL)
public class WaiterCallController extends AbstractController {
    
}
