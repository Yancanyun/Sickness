package com.emenu.web.controller.admin.table;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

/**
 * TableController
 *
 * @author: yangch
 * @time: 2015/10/24 15:14
 */
@Controller
@Module(ModuleEnums.AdminRestaurantTable)
@RequestMapping(value = URLConstants.ADMIN_TABLE_URL)
public class AdminTableController extends AbstractController {
    /**
     * 去餐台管理页
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toTablePage(Model model) {
        try {
            List<Area> areaList = areaService.listAll();
            model.addAttribute("areaList", areaList);
            List<TableDto> tableDtoList = tableService.listAllTableDto();
            model.addAttribute("tableDtoList", tableDtoList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
        return "admin/restaurant/table/list_home";
    }


    @Module(ModuleEnums.AdminRestaurantTableList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxList(@RequestParam(value = "areaId") Integer[] areaId,
                               @RequestParam(value = "state") Integer state) {
        try {
            List<TableDto> tableDtoList = new ArrayList<TableDto>();
            //选择区域，未选择状态
            if (areaId.length > 0 && state == -1) {
                //全选区域
                if (areaId[0] == -1){
                    tableDtoList = tableService.listAllTableDto();
                } else {
                    for (int i = 0; i < areaId.length; i++) {
                        if (i >= 0) {
                            tableDtoList.addAll(tableService.listTableDtoByAreaId(areaId[i]));
                        }
                    }
                }
            }
            //选择区域并选择状态
            else if (areaId.length > 0 && state != -1) {
                //全选区域
                if (areaId[0] == -1){
                    tableDtoList.addAll(tableService.listTableDtoByState(TableStateEnums.valueOf(state)));
                } else {
                    for (int i = 0; i < areaId.length; i++) {
                        if (i >= 0) {
                            tableDtoList.addAll(tableService.listTableDtoByAreaIdAndState(areaId[i], TableStateEnums.valueOf(state)));
                        }
                    }
                }
            }

            JSONArray jsonArray = new JSONArray();
            if(tableDtoList != null){
                for(TableDto tableDto: tableDtoList){
                    //保留小数点两位
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("areaId", tableDto.getTable().getAreaId());
                    jsonObject.put("areaName", tableDto.getAreaName());
                    jsonObject.put("id", tableDto.getTable().getId());
                    jsonObject.put("name", tableDto.getTable().getName());
                    jsonObject.put("seatNum", tableDto.getTable().getSeatNum());
                    jsonObject.put("state", decimalFormat.format(tableDto.getTable().getState()));
                    jsonObject.put("seatFee", decimalFormat.format(tableDto.getTable().getSeatFee()));
                    jsonObject.put("tableFee", decimalFormat.format(tableDto.getTable().getTableFee()));
                    jsonObject.put("minCost", decimalFormat.format(tableDto.getTable().getMinCost()));

                    jsonArray.add(jsonObject);
                }
            }
            //不分页，故dataCount填0
            return sendJsonArray(jsonArray, 0);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去餐台添加页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toTableNewPage(Model model) {
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
     * 添加餐台提交
     * @param table
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableNew)
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String tableNew(Table table, HttpServletRequest request) {
        try {
            tableService.newTable(table, request);
            return "redirect:/admin/restaurant/table";
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
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
            TableDto tableDto = tableService.queryTableDtoById(id);
            model.addAttribute("tableDto", tableDto);
            //显示区域选择框
            List<Area> areaList = areaService.listAll();
            model.addAttribute("areaList", areaList);

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
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    public String tableUpdate(@PathVariable int id, Table table) {
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
            tableService.updateState(id, state);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 删除单个区域
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableDel)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject delTable(@PathVariable Integer id) {
        try {
            tableService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 删除多个区域
     * @param idList
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableDel)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
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
