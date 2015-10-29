package com.emenu.web.controller.admin.page;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.page.IndexImg;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.page.IndexImgEnum;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * IndexImgController
 *
 * @author Wang LiMing
 * @date 2015/10/24 10:47
 */

@Controller
@Module(ModuleEnums.AdminBasicInfo)
@RequestMapping(value = URLConstants.INDEX_IMG_URL)
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
            IndexImg defaultImg = indexImgService.queryByState(IndexImgEnum.Using.getId());
            List<IndexImg> indexImgList = indexImgService.listAll();
            model.addAttribute("defaultImg", defaultImg);
            model.addAttribute("indexImgList", indexImgList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return PUB_SYS_ERR_PAGE;
        }
        return "admin/index/img/list";
    }

    /**
     * ajax修改首页展示图片
     *
     * @param imgId
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoIndexImgUpdate)
    @RequestMapping(value = "ajax/state/update/{imgId}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxChangeIndexImg(@PathVariable("imgId") Integer imgId) {
        try {
            IndexImg defaultImg = indexImgService.queryByState(IndexImgEnum.Using.getId());
            if (defaultImg != null) {
                if (!imgId.equals(defaultImg.getId())){
                    indexImgService.updateStateById(imgId, IndexImgEnum.Using.getId());
                }
                indexImgService.updateStateById(defaultImg.getId(), IndexImgEnum.UnUsing.getId());
            } else {
                indexImgService.updateStateById(imgId, IndexImgEnum.Using.getId());
            }
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
    @RequestMapping(value = "ajax/del/{imgId}", method = RequestMethod.DELETE)
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

    @Module(ModuleEnums.AdminBasicInfoIndexImgNew)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadImg(){

        return null;
    }
}
