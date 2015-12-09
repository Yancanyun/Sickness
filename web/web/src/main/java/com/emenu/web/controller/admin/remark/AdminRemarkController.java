package com.emenu.web.controller.admin.remark;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.dto.remark.RemarkTagDto;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.remark.RemarkStatusEnums;
import com.emenu.common.enums.remark.RemarkTagStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * AdminRemarkController
 *
 * @author: yangch
 * @time: 2015/11/13 14:05
 */
@Controller
@Module(ModuleEnums.AdminRestaurantRemark)
@RequestMapping(value = URLConstants.ADMIN_REMARK_URL)
public class AdminRemarkController extends AbstractController {
    /**
     * 去备注管理页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantRemarkList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toAreaPage(Model model) {
        try {
            //显示上级分类列表（上级分类为pId=0的分类）
            List<RemarkTag> bigTagList = remarkTagService.listByParentId(0);
            model.addAttribute("bigTagList", bigTagList);

            //显示第一个上级分类的名字
            RemarkTag firstBigTag = remarkTagService.queryById(1);
            model.addAttribute("firstBigTag", firstBigTag);

            //将第一个上级分类下的所有子分类存为一个List
            List<RemarkTag> firstChildTagList = remarkTagService.listByParentId(1);

            //将第一个上级分类下的所有子分类及其所拥有的所有备注存为一个List
            List<RemarkTagDto> firstChildTagDtoList = new ArrayList<RemarkTagDto>();
            for (int i = 0; i < firstChildTagList.size(); i++) {
                RemarkTagDto remarkTagDto = new RemarkTagDto();
                //向Dto插入该子分类
                remarkTagDto.setRemarkTag(firstChildTagList.get(i));
                //获取并向Dto插入该子分类下的所有备注
                List<RemarkDto> remarkDtoList = remarkService.listRemarkDtoByRemarkTagId(firstChildTagList.get(i).getId());
                remarkTagDto.setRemarkDtoList(remarkDtoList);

                firstChildTagDtoList.add(remarkTagDto);
            }

            //显示第一个上级分类下的所有子分类及其所拥有的所有备注
            model.addAttribute("firstChildTagDtoList", firstChildTagDtoList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/restaurant/remark/list_home";
    }

    /**
     * Ajax 获取上级分类下所有子分类及其所拥有的所有备注
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantRemarkList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxList(@RequestParam("id") Integer id) {
        try {
            //将该分类下的所有子分类存为一个List
            List<RemarkTag> childTagList = remarkTagService.listByParentId(id);

            JSONArray jsonArray = new JSONArray();

            //将该分类下的所有子分类及其所拥有的所有备注放入JSONArray中
            for (RemarkTag remarkTag : childTagList) {
                //获取该子分类下的所有备注
                List<RemarkDto> remarkDtoList = remarkService.listRemarkDtoByRemarkTagId(remarkTag.getId());

                //保留小数点两位
                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bigTagId", remarkTag.getpId());
                jsonObject.put("bigTagName", remarkTagService.queryById(remarkTag.getpId()).getName());
                jsonObject.put("smallTagId", remarkTag.getId());
                jsonObject.put("smallTagName", remarkTag.getName());

                JSONArray contentList = new JSONArray();
                for (RemarkDto remarkDto : remarkDtoList) {
                    JSONObject childJsonObject = new JSONObject();
                    childJsonObject.put("contentId", remarkDto.getRemark().getId());
                    childJsonObject.put("weight", remarkDto.getRemark().getWeight());
                    childJsonObject.put("content", remarkDto.getRemark().getName());
                    childJsonObject.put("relatedCharge", decimalFormat.format(remarkDto.getRemark().getRelatedCharge()));
                    childJsonObject.put("isCommon", remarkDto.getRemark().getIsCommon());
                    contentList.add(childJsonObject);
                }

                jsonObject.put("contentList", contentList);
                jsonArray.add(jsonObject);
            }

            //不分页，故dataCount填0
            return sendJsonArray(jsonArray, 0);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 添加备注分类
     * @param pId
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantRemarkNew)
    @RequestMapping(value = "ajax/remark/tag", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewChildRemarkTag(@RequestParam("pId") Integer pId,
                                            @RequestParam("name") String name) {
        try {
            RemarkTag remarkTag = new RemarkTag();
            remarkTag.setpId(pId);
            remarkTag.setName(name);
            //设置状态为可用
            remarkTag.setStatus(RemarkTagStatusEnums.Enabled.getId());
            remarkTagService.newRemarkTag(remarkTag);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", remarkTag.getId());
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 添加备注
     * @param smallTagId
     * @param name
     * @param weight
     * @param isCommon
     * @param relatedCharge
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantRemarkNew)
    @RequestMapping(value = "ajax/remark", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewRemark(@RequestParam("smallTagId") Integer smallTagId,
                                    @RequestParam("name") String name,
                                    @RequestParam("weight") Integer weight,
                                    @RequestParam("isCommon") Integer isCommon,
                                    @RequestParam(required = false) BigDecimal relatedCharge) {
        try {
            Remark remark = new Remark();
            remark.setRemarkTagId(smallTagId);
            remark.setName(name);
            remark.setWeight(weight);
            remark.setIsCommon(isCommon);
            if (relatedCharge != null) {
                remark.setRelatedCharge(relatedCharge);
            }
            //设置状态为可用
            remark.setStatus(RemarkStatusEnums.Enabled.getId());
            remarkService.newRemark(remark);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", remark.getId());
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 修改备注分类
     * @param id
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantRemarkUpdate)
    @RequestMapping(value = "ajax/remark/tag/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateChildRemarkTag(@PathVariable("id") Integer id,
                                               @RequestParam("name") String name) {
        try {
            RemarkTag remarkTag = new RemarkTag();
            remarkTag.setId(id);
            remarkTag.setName(name);
            remarkTagService.updateRemarkTag(id, remarkTag);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 修改备注
     * @param id
     * @param name
     * @param weight
     * @param isCommon
     * @param relatedCharge
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantRemarkUpdate)
    @RequestMapping(value = "ajax/remark/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateRemark(@PathVariable("id") Integer id,
                                       @RequestParam("name") String name,
                                       @RequestParam("weight") Integer weight,
                                       @RequestParam("isCommon") Integer isCommon,
                                       @RequestParam(required = false) BigDecimal relatedCharge) {
        try {
            Remark remark = new Remark();
            remark.setId(id);
            remark.setName(name);
            remark.setWeight(weight);
            remark.setIsCommon(isCommon);
            if (relatedCharge != null) {
                remark.setRelatedCharge(relatedCharge);
            }
            remarkService.updateRemark(id, remark);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 删除备注分类
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantRemarkDel)
    @RequestMapping(value = "ajax/remark/tag/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelChildRemarkTag(@PathVariable("id") Integer id) {
        try {
            remarkTagService.delById(id);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 删除备注
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantRemarkDel)
    @RequestMapping(value = "ajax/remark/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelRemark(@PathVariable("id") Integer id) {
        try {
            remarkService.delById(id);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
