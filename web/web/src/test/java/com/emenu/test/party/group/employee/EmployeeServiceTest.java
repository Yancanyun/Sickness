package com.emenu.test.party.group.employee;

import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.CommonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xiaozl
 * @date 2015/10/26
 * @time 13:57
 */
public class EmployeeServiceTest extends AbstractTestCase {

    @Autowired

    protected  EmployeeService employeeService;

    /**
     * 测试员工信息列表获取
     * @throws SSException
     */
    @Test
    public void listEmployee() throws SSException{

        Integer partyId = 0;//获取当前登录用户id，admin
        List<EmployeeDto> employeeDtoList = employeeService.listEmployee(partyId);//向前端返回用户列表数据

        for (EmployeeDto employeeDto : employeeDtoList){
            Employee employee = employeeDto.getEmployee();

            System.out.println(employee.getName()+" "+employee.getPhone()+" "+employee.getStatus());

            List<String> roleNameList = employeeDto.getRoleName();

            for (String roleName : roleNameList){
                System.out.println(roleName);
            }

            System.out.println(employeeDto.getStatus());

            System.out.println("test");

        }
    }

    /**
     * 测试添加员工
     * @throws SSException
     */
    @Test
    public void newEmploee() throws SSException{

        Employee employee = new Employee();
        employee.setName("陈玉婷");
        employee.setPhone("55555555");


        EmployeeDto employeeDto = new EmployeeDto();
        // 对密码进行md5加密
        String password = CommonUtil.md5("123456");
        String username = "chenyt";


        List<Integer> roleList = new ArrayList<Integer>();

        roleList.add(2);
        roleList.add(4);


        List<Integer> tableList = new ArrayList<Integer>();

        tableList.add(1);
        tableList.add(2);
        employeeDto.setEmployee(employee);
        employeeDto.setRole(roleList);
        employeeDto.setTables(tableList);

        employeeService.newEmployee(employeeDto,username,password);
    }

    @Test
    public void updatePwd() throws SSException{

        Integer securityUserId = 3;
        String oldPwd ="1111111" ;
        String newPwd ="1111111";

        employeeService.updatePwd(securityUserId,oldPwd,newPwd);
    }

    /**
     *修改员工信息
     * @throws SSException
     */
    @Test
    public void updateEmployee() throws SSException{


        Employee employee = new Employee();
        employee.setId(5);
        employee.setName("杜鹃");
        employee.setPhone("22222222");


        EmployeeDto employeeDto = new EmployeeDto();
        // 对密码进行md5加密
        String password = CommonUtil.md5("123456");
        String newloginName = "dujuan";


        List<Integer> roleList = new ArrayList<Integer>();

        roleList.add(1);
        roleList.add(2);
        roleList.add(4);


        List<Integer> tableList = new ArrayList<Integer>();

        tableList.add(1);
        tableList.add(2);
        tableList.add(3);

        employeeDto.setEmployee(employee);
        employeeDto.setRole(roleList);
        employeeDto.setTables(tableList);

        employeeService.updateEmployee(employeeDto,3,newloginName,password);
    }

    @Test
    public void delEmployee() throws SSException{
        employeeService.delEmployeeByPartyId(3);
    }

    @Test
    public void listByCondition() throws SSException{

        List<Integer> roleList = new ArrayList<Integer>();
        roleList.add(2);
//        roleList.add(4);

        List<EmployeeDto> employeeDtoList =  employeeService.listEmployeeByContition(roleList,1 );

        for (EmployeeDto employeeDto : employeeDtoList){
            Employee employee = employeeDto.getEmployee();

            System.out.println(employee.getName()+" "+employee.getPhone()+" "+employee.getStatus());

            List<String> roleNameList = employeeDto.getRoleName();

            for (String roleName : roleNameList){
                System.out.println(roleName);
            }

            System.out.println(employeeDto.getStatus());

            System.out.println("test");

        }

    }

}
