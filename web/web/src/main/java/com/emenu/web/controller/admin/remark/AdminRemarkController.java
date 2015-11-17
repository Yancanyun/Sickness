package com.emenu.web.controller.admin.remark;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.dto.remark.RemarkTagDto;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
            //先显示上级分类列表, 上级分类为pId=0的分类
            List<RemarkTag> bigTagList = remarkTagService.listByParentId(0);
            model.addAttribute("bigTagList", bigTagList);

            //将第一个上级分类下的所有子分类存为一个List
            List<RemarkTag> firstChildTagList = remarkTagService.listByParentId(1);

            //将第一个上级分类下的所有子分类及其所拥有的所有备注存为一个List
            List<RemarkTagDto> firstChildTagDtoList = new ArrayList<RemarkTagDto>();
            for (int i = 0; i < firstChildTagList.size(); i++) {
                RemarkTagDto remarkTagDto = new RemarkTagDto();
                //设置该子分类
                remarkTagDto.setRemarkTag(firstChildTagList.get(i));
                //获取并设置该子分类下的所有备注
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
     * @param pId
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantRemarkList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxList(@RequestParam(value = "pId") Integer pId) {
        try {
            //将该PID下的所有子分类存为一个List
            List<RemarkTag> childTagList = remarkTagService.listByParentId(pId);

            JSONArray jsonArray = new JSONArray();

            //将该PID下的所有子分类及其所拥有的所有备注放入JSONArray中
            for (RemarkTag remarkTag : childTagList) {
                //获取该子分类下的所有备注
                List<RemarkDto> remarkDtoList = remarkService.listRemarkDtoByRemarkTagId(remarkTag.getId());

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
                    childJsonObject.put("relatedCharges", remarkDto.getRemark().getRelatedCharges());
                    if (remarkDto.getRemark().getIsCommon() == 0) {
                        childJsonObject.put("isCommon", "否");
                    }
                    if (remarkDto.getRemark().getIsCommon() == 1) {
                        childJsonObject.put("isCommon", "是");
                    }
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
}