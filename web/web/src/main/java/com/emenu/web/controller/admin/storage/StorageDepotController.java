package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.storage.StorageDepot;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 存放点Controller
 *
 * @author xubr
 * @date 2015/11/12.
 */
@Module(ModuleEnums.AdminStorageDepot)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STORAGE_DEPOT)
public class StorageDepotController extends AbstractController{

//    /**
//     * 去列表页
//     *
//     * @return
//     */
//    @Module(ModuleEnums.AdminStorageDepotList)
//    @RequestMapping(value = {"","list"},method = RequestMethod.GET )
//    public String toListStorageDepot(){ return "admin/storage/depot/list_home"; }
//
//    /**
//     * ajax获取分页数据
//     *
//     * @param curPage
//     * @param pageSize
//     * @return
//     */
//    @Module(ModuleEnums.AdminStorageDepotList)
//    @RequestMapping(value = "ajax/list/{curPage}",method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject ajaxListStorageDepot(@PathVariable("curPage")Integer curPage,
//                                           @RequestParam Integer pageSize) {
//        List<StorageDepot> depotList = Collections.<StorageDepot>emptyList();
//        try {
//            depotList = storageDepotService.listByPage(curPage, pageSize);
//        } catch (SSException e) {
//            LogClerk.errLog.error(e);
//            return sendErrMsgAndErrCode(e);
//        }
//
//        JSONArray jsonArray = new JSONArray();
//        for (StorageDepot storageDepot : depotList) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("id", storageDepot.getId());
//            jsonObject.put("name", storageDepot.getName());
//            jsonObject.put("instruction", storageDepot.getIntroduction());
//
//            jsonArray.add(jsonObject);
//        }
//        int dataCount = 0;
//        try {
//            dataCount = storageDepotService.listAll();
//        } catch (SSException e) {
//            LogClerk.errLog.error(e);
//            sendErrMsgAndErrCode(e);
//        }
//        return sendJsonArray(jsonArray, dataCount);
//    }
//
//    /**
//     * Ajax添加
//     *
//     * @param storageDepot
//     * @return
//     */
//    @Module(ModuleEnums.AdminStorageDepotNew)
//    @RequestMapping(value = "ajax",method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject ajaxNewStorageDepot(StorageDepot storageDepot) {
//        try {
//            storageDepotService.newStorageDepot(storageDepot);
//            return sendJsonObject(AJAX_SUCCESS_CODE);
//        } catch (SSException e) {
//            LogClerk.errLog.error(e);
//            return sendErrMsgAndErrCode(e);
//        }
//    }
//
//    /**
//     * ajax修改
//     *
//     * @param storageDepot
//     * @return
//     */
//    @Module(ModuleEnums.AdminBasicInfoIndexImgUpdate)
//    @RequestMapping(value= "ajax",method = RequestMethod.PUT)
//    @ResponseBody
//    public JSONObject ajaxUpdateStorageDepot(StorageDepot storageDepot) {
//        try {
//            storageDepotService.updateStorageDepot(storageDepot);
//            return sendJsonObject(AJAX_SUCCESS_CODE);
//        } catch(SSException e) {
//            LogClerk.errLog.error(e);
//            return sendErrMsgAndErrCode(e);
//        }
//    }
//
//    /**
//     * ajax删除
//     *
//     * @param id
//     * @return
//     */
//    @Module(ModuleEnums.AdminStorageDepotDelete)
//    @RequestMapping(value="ajax/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public JSONObject ajaxDelStorageDepot(@PathVariable("id")Integer id) {
//        try {
//            storageDepotService.delById(id);
//            return sendJsonObject(AJAX_SUCCESS_CODE);
//        } catch(SSException e) {
//            LogClerk.errLog.error(e);
//            return sendErrMsgAndErrCode(e);
//        }
//    }
}
