package com.emenu.web.controller.admin.vip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.vip.VipCardDto;
import com.emenu.common.entity.vip.VipCard;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.emenu.web.spring.AbstractController;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * AdminVipCardController
 *
 * @author: yangch
 * @time: 2016/1/19 17:19
 */
@Controller
@Module(ModuleEnums.AdminVipCard)
@RequestMapping(value = URLConstants.ADMIN_VIP_CARD_URL)
public class AdminVipCardController extends AbstractController {

    /**
     * 去会员卡管理页面
     *
     * @return
     */
    @Module(ModuleEnums.AdminVipCardList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toVipCardPage() {
        return "admin/vip/card/list_home";
    }

    /**
     * Ajax 获取分页数据
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Module(ModuleEnums.AdminVipCardList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxList(@PathVariable("pageNo") Integer pageNo,
                               @RequestParam("keyword") String keyword,
                               @RequestParam("startTime") Date startTime,
                               @RequestParam("endTime") Date endTime,
                               @RequestParam("pageSize") Integer pageSize) {
        List<VipCardDto> vipCardDtoList = Collections.emptyList();
        try {
            vipCardDtoList = vipCardService.listVipCardDtoByKeywordAndDate(keyword, startTime,
                                                                           endTime, pageNo, pageSize);
            JSONArray jsonArray = new JSONArray();
            for (VipCardDto vipCardDto : vipCardDtoList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", vipCardDto.getVipCard().getId());
                jsonObject.put("name", vipCardDto.getVipInfo().getName());
                jsonObject.put("phone", vipCardDto.getVipInfo().getPhone());
                jsonObject.put("cardNumber", vipCardDto.getVipCard().getCardNumber());
                jsonObject.put("createdTime", vipCardDto.getVipCard().getCreatedTimeStr());
                jsonObject.put("validityTime", vipCardDto.getVipCard().getValidityTimeStr());
                jsonObject.put("permanentlyEffective", vipCardDto.getVipCard().getPermanentlyEffectiveStr());
                jsonObject.put("operator", vipCardDto.getOperator());
                jsonObject.put("status", vipCardDto.getVipCard().getStatusStr());

                jsonArray.add(jsonObject);
            }

            Integer dataCount = vipCardService.countByKeywordAndDate(keyword, startTime, endTime);

            return sendJsonArray(jsonArray, dataCount);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 修改会员卡
     *
     * @param id
     * @param validityTime
     * @param permanentlyEffective
     * @return
     */
    @Module(ModuleEnums.AdminVipCardUpdate)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateVipCard(@PathVariable("id") Integer id,
                                        @RequestParam(required = false) Date validityTime,
                                        @RequestParam("permanentlyEffective") Integer permanentlyEffective) {
        try {
            VipCard vipCard = vipCardService.queryById(id);
            vipCard.setValidityTime(validityTime);
            vipCard.setPermanentlyEffective(permanentlyEffective);
            vipCard.setOperatorPartyId(getPartyId());
            vipCardService.updateVipCard(id, vipCard);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 修改会员卡状态
     *
     * @param id
     * @param status
     * @return
     */
    @Module(ModuleEnums.AdminVipCardUpdate)
    @RequestMapping(value = "ajax/status", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateStatus(@RequestParam("id") Integer id,
                                       @RequestParam("status") Integer status) {
        try {
            vipCardService.updateStatusById(id, status);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 删除会员卡
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminVipCardDel)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelVipCard(@PathVariable Integer id) {
        try {
            vipCardService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            return sendErrMsgAndErrCode(e);
        }
    }
}
