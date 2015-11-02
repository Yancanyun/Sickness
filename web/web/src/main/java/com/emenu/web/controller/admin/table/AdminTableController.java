package com.emenu.web.controller.admin.table;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TableController
 *
 * @author: yangch
 * @time: 2015/10/24 15:14
 */
@Controller
@Module(ModuleEnums.AdminRestaurantTable)
@RequestMapping(value = URLConstants.TABLE_URL)
public class AdminTableController extends AbstractController {
    /**
     * 去餐台管理页
     * @param areaId : 根据区域筛选餐台
     * @param state : 根据状态筛选餐台
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toTablePage(List<Integer> areaId, Integer state, Model model) {
        try {
            //餐台列表
            List<TableDto> tableDtoList = new ArrayList<TableDto>();

            //若areaId不为空，则根据区域对餐台进行筛选
            if (areaId != null && state == null) {
                //可能存在多选区域的情况
                for (int i : areaId) {
                    if(i > 0) {
                        tableDtoList.addAll(tableService.listTableDtoByAreaId(i));
                    }
                }
            }
            //若state不为空，则根据状态对餐台进行筛选
            if (state != null && areaId == null) {
                //不存在多选状态的情况
                tableDtoList.addAll(tableService.listTableDtoByState(state));
            }
            //若areaId、state均不为空，则根据区域及状态对餐台进行筛选
            if (areaId != null && state != null) {
                //可能存在多选区域的情况
                for (int i : areaId) {
                    if (i > 0) {
                        tableDtoList.addAll(tableService.listTableDtoByAreaIdAndState(i, state));
                    }
                }
            }
            //若areaId、state全部为空，则显示全部餐台
            else {
                tableDtoList = tableService.listAllTableDto();
            }
            model.addAttribute("tableDtoList", tableDtoList);

            //显示区域选择框
            List<Area> areaList = areaService.listAll();
            boolean[] flags = new boolean[areaList.size()];
            if(areaId != null){
                for(int i = 0 ; i < areaList.size() ; i++){
                    //初始情况下选择框为未勾选
                    boolean flag = false;
                    for(int j : areaId){
                        if(j == areaList.get(i).getId()) {
                            //若传来areaId，则将选择框勾选上
                            flag = true;
                            break;
                        }
                    }
                    flags[i] = flag;
                }
            }
            //全选框
            boolean isAll = true;
            for(boolean flag : flags){
                if(!flag){
                    isAll = false;
                    break;
                }
            }
            model.addAttribute("areaList",areaList);
            model.addAttribute("flags",flags);
            model.addAttribute("isAll",isAll);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/restaurant/table/list_home";
    }

    /**
     * 去餐台添加页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toTableInsertPage(Model model) {
        try {
            //显示区域选择框
            List<Area> areaList = areaService.listAll();
            model.addAttribute("areaList", areaList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/restaurant/table/new_home";
    }

    /**
     * Ajax 返回餐台已存在错误提示
     * @param name
     * @return
     */
    @RequestMapping(value = "ajax/exist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject tableExist(@RequestParam("name") String name) {
        try {
            if(tableService.checkNameIsExist(name)){
                throw SSException.get(EmenuException.TableNameExist);
            } else {
                return sendJsonObject(AJAX_SUCCESS_CODE);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去餐台修改页
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableUpdate)
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toTableUpdate(@PathVariable("id") Integer id, Model model) {
        try {
            Table table = tableService.queryById(id);
            model.addAttribute("table", table);
            //显示区域选择框
            List<Area> areaList = areaService.listAll();
            model.addAttribute("areaList",areaList);

            return "admin/restaurant/table/update_home";
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    /**
     * 修改餐台提交
     * @param id
     * @param table
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableUpdate)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public String tableEdit(@PathVariable int id, Table table) {
        try {
            table.setId(id);
            tableService.updateTable(table);
            return "redirect:/admin/restaurant/table";
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    /**
     * Ajax 查询餐台状态
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableList)
    @RequestMapping(value = "ajax/state", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryState(@RequestParam("id") Integer id) {
        try {
            int state = tableService.queryStateById(id);
            JSONObject jsonObject = new JSONObject();
            //传递state数值给前端
            jsonObject.put("state", state);
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 修改餐台状态
     * @param id
     * @param state
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableUpdate)
    @RequestMapping(value = "ajax/state", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateState(@RequestParam("id") Integer id,
                                                @RequestParam("state") Integer state) {
        try {
            Table table=new Table();
            table.setId(id);
            table.setState(state);
            tableService.updateTable(table);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 删除区域(可删除多个区域)
     * @param idList
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableDel)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delTables(@RequestParam("idList") List<Integer> idList) {
        try {
            //删除多个区域
            if(idList != null) {
                for(int i : idList) {
                    tableService.delById(i);
                }
            }
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
