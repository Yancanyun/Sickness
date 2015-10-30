package com.emenu.web.controller.admin.party.group.employee;

import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xiaozl
 * @date 2015/10/29
 * @time 18:01
 */
@Controller
@Module(ModuleEnums.AdminSAdmin)
@RequestMapping(value = URLConstants.EMPLOYEE_MANAGEMENT)
public class EmployeeController  extends AbstractController {

    @RequestMapping(value = "/delete/employee",method = RequestMethod.GET)
    public String delEmployee(){
        return null;
    }

}
