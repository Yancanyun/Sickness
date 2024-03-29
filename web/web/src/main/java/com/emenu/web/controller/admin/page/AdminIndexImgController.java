package com.emenu.web.controller.admin.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.page.IndexImg;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.page.IndexImgEnum;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IndexImgController
 *
 * @author Wang LiMing
 * @date 2015/10/24 10:47
 */

@Controller
@Module(ModuleEnums.AdminBasicInfoIndexImg)
@RequestMapping(value = URLConstants.ADMIN_INDEX_IMG_URL)
public class AdminIndexImgController extends AbstractController{

    /**
     * 去点餐平台首页图片管理页面
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoIndexImgList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toIndexImgPage(Model model){
        try {
            IndexImg defaultImg = indexImgService.queryByState(IndexImgEnum.Using);
            List<IndexImg> indexImgList = indexImgService.listAll();
            model.addAttribute("defaultImg", defaultImg);
            model.addAttribute("indexImgList", indexImgList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/page/indexImg/list_home";
    }

    /**
     * ajax上传首页图片
     *
     * @param file
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoIndexImgNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSON uploadIndexImg(@RequestParam("file") PandaworkMultipartFile file) {
        try {
            indexImgService.newIndexImg(file, getRequest());
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax修改首页展示图片
     *
     * @param imgId
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoIndexImgUpdate)
    @RequestMapping(value = "ajax/{imgId}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxChangeIndexImg(@PathVariable("imgId") Integer imgId) {
        try {
            IndexImg defaultImg = indexImgService.queryByState(IndexImgEnum.Using);
            if (!Assert.isNull(defaultImg)){
                if (!Assert.lessOrEqualZero(defaultImg.getId()))
                indexImgService.updateStateById(defaultImg.getId(), IndexImgEnum.UnUsing);
            }
            indexImgService.updateStateById(imgId, IndexImgEnum.Using);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax删除首页图片
     *
     * @param imgId
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoIndexImgDel)
    @RequestMapping(value = "ajax/{imgId}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDeleteImg(@PathVariable("imgId") Integer imgId){
        try {
            indexImgService.delById(imgId);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
