package com.emenu.web.controller.admin.table;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
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


    /**
     * Ajax 获取数据
     * @param areaId
     * @param status
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxList(@RequestParam(value = "areaId") Integer[] areaId,
                               @RequestParam(value = "status") Integer status) {
        try {
            List<TableDto> tableDtoList = new ArrayList<TableDto>();
            //选择区域，未选择状态
            if (areaId.length > 0 && status == -1) {
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
            else if (areaId.length > 0 && status != -1) {
                //全选区域
                if (areaId[0] == -1){
                    tableDtoList.addAll(tableService.listTableDtoByStatus(TableStatusEnums.valueOf(status)));
                } else {
                    for (int i = 0; i < areaId.length; i++) {
                        if (i >= 0) {
                            tableDtoList.addAll(tableService.listTableDtoByAreaIdAndStatus(areaId[i], TableStatusEnums.valueOf(status)));
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
                    jsonObject.put("status", decimalFormat.format(tableDto.getTable().getStatus()));
                    jsonObject.put("seatFee", decimalFormat.format(tableDto.getTable().getSeatFee()));
                    jsonObject.put("tableFee", decimalFormat.format(tableDto.getTable().getTableFee()));
                    jsonObject.put("minCost", decimalFormat.format(tableDto.getTable().getMinCost()));

                    //将餐段名拼成一个String
                    List<MealPeriod> mealPeriodList = new ArrayList<MealPeriod>();
                    String mealPeriodName = null;
                    mealPeriodList = tableDto.getMealPeriodList();
                    for (int i = 0; i < mealPeriodList.size(); i++) {
                        String name = mealPeriodList.get(i).getName();
                        //第一个餐段名
                        if (mealPeriodName == null){
                            mealPeriodName = name;
                        } else {
                            mealPeriodName = mealPeriodName + " " + name;
                        }
                    }
                    //若餐段不存在则显示"无"
                    if (mealPeriodName == null){
                        mealPeriodName = "无";
                    }
                    jsonObject.put("mealPeriodName", mealPeriodName);

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
            //显示区域选择下拉列表
            List<Area> areaList = areaService.listAll();
            model.addAttribute("areaList", areaList);
            //显示餐段选择框
            List<MealPeriod> mealPeriodList = mealPeriodService.listAll();
            model.addAttribute("mealPeriodList", mealPeriodList);
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
    public String tableNew(Table table, @RequestParam(required = false) List<Integer> mealPeriodIdList,
                           HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            TableDto tableDto = new TableDto();
            //往TableDto中插入Table
            tableDto.setTable(table);
            //往TableDto中插入MealPeriodList
            List<MealPeriod> mealPeriodList = new ArrayList<MealPeriod>();
            if (mealPeriodIdList != null) {
                for (int i = 0; i < mealPeriodIdList.size(); i++) {
                    MealPeriod mealPeriod = new MealPeriod();
                    mealPeriod.setId(mealPeriodIdList.get(i));
                    mealPeriodList.add(mealPeriod);
                }
            }
            tableDto.setMealPeriodList(mealPeriodList);
            tableService.newTable(tableDto, request);

            String successUrl = "/" + URLConstants.ADMIN_TABLE_URL;
            //返回添加成功信息
            redirectAttributes.addFlashAttribute("msg", "添加成功");
            //返回列表页
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_TABLE_URL + "/new";
            //返回添加失败信息
            redirectAttributes.addFlashAttribute("msg", "添加失败");
            //返回添加页
            return "redirect:" + failedUrl;
        }
    }

    /**
     * Ajax 返回餐台已存在错误提示
     * @param name
     * @return
     */
    @RequestMapping(value = "ajax/exist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject tableExist(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        try {
            //若未传来ID，则为增加页，直接判断该名称是否在数据库中已存在
            if(id == null && tableService.checkNameIsExist(name)) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //若传来ID，则为编辑页
            //判断传来的Name与相应ID在数据库中对应的名称是否一致，若不一致，再判断该名称是否在数据库中已存在
            else if (id != null && !name.equals(tableService.queryById(id).getName()) && tableService.checkNameIsExist(name)){
                throw SSException.get(EmenuException.TableNameExist);
            }
            else {
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
            //显示区域选择下拉列表
            List<Area> areaList = areaService.listAll();
            model.addAttribute("areaList", areaList);
            //显示餐段选择框
            List<MealPeriod> mealPeriodList = mealPeriodService.listAll();
            model.addAttribute("mealPeriodList", mealPeriodList);
            //显示被选中的餐段ID
            List<Integer> checkedMealPeriodList = tableMealPeriodService.listMealPeriodIdByTableId(id);
            model.addAttribute("checkedMealPeriodList", checkedMealPeriodList);

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
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String tableUpdate(@PathVariable Integer id, Table table,
                              @RequestParam(required = false) List<Integer> mealPeriodIdList,
                              RedirectAttributes redirectAttributes) {
        try {
            TableDto tableDto = new TableDto();
            //往TableDto中插入Table
            tableDto.setTable(table);
            table.setId(id);
            //往TableDto中插入MealPeriodList
            List<MealPeriod> mealPeriodList = new ArrayList<MealPeriod>();
            if (mealPeriodIdList != null) {
                for (int i = 0; i < mealPeriodIdList.size(); i++) {
                    MealPeriod mealPeriod = new MealPeriod();
                    mealPeriod.setId(mealPeriodIdList.get(i));
                    mealPeriodList.add(mealPeriod);
                }
            }
            tableDto.setMealPeriodList(mealPeriodList);
            tableService.updateTable(id, tableDto);

            String successUrl = "/" + URLConstants.ADMIN_TABLE_URL;
            //返回编辑成功信息
            redirectAttributes.addFlashAttribute("msg", "编辑成功");
            //返回列表页
            return "redirect:" + successUrl;

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());

            String failedUrl = "/" + URLConstants.ADMIN_TABLE_URL + "/update/" + id;
            //返回编辑失败信息
            redirectAttributes.addFlashAttribute("msg", "编辑失败");
            //返回编辑页
            return "redirect:" + failedUrl;
        }
    }

    /**
     * Ajax 查询餐台状态。若为可编辑状态，返回AJAX_SUCCESS_CODE；否则返回AJAX_FAILURE_CODE及错误信息
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableList)
    @RequestMapping(value = "ajax/status", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkStatus(@RequestParam("id") Integer id) {
        try {
            tableService.checkStatusById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * Ajax 修改餐台状态
     * @param id
     * @param status
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantTableUpdate)
    @RequestMapping(value = "ajax/status", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateStatus(@RequestParam("id") Integer id,
                                  @RequestParam("status") Integer status) {
        try {
            tableService.updateStatus(id, status);
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
            tableService.delByIds(idList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
