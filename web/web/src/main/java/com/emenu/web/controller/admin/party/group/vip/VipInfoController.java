package com.emenu.web.controller.admin.party.group.vip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.vip.VipInfoDto;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.entity.vip.ConsumptionActivity;
import com.emenu.common.entity.vip.VipAccountInfo;
import com.emenu.common.entity.vip.VipCard;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.party.SexEnums;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.common.enums.vip.ConsumptionActivityTypeEnums;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.common.enums.vip.VipCardStatusEnums;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 会员基本信息管理controller
 *
 * @author chenyuting
 * @date 2015/10/27 10:55
 */
@Controller
@Module(ModuleEnums.AdminVipInfo)
@RequestMapping(value = URLConstants.ADMIN_PARTY_VIP_VIPINFO_URL)
public class VipInfoController extends AbstractController {

    protected final static String NEW_SUCCESS_MSG = "添加成功";
    protected final static String UPDATE_SUCCESS_MSG = "修改成功";
    protected final static String DELETE_SUCCESS_MSG = "删除成功";

    /**
     * 去会员基本信息列表页面
     *
     * @return
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList() {
        return "admin/party/group/vip/vip_info_list";
    }

    /**
     * 分页获取关键字搜索列表
     *
     * @param curPage
     * @param pageSize
     * @param keyword
     * @return
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoList)
    @RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListVipInfo(@PathVariable("curPage") Integer curPage,
                                      @RequestParam Integer pageSize,
                                      @RequestParam("keyword") String keyword) {
        List<VipInfo> vipInfoList = Collections.emptyList();
        try {
            if (keyword == "" || keyword == null || keyword.equals("")) {
                vipInfoList = vipInfoService.listByPage(curPage, pageSize);
            } else {
                vipInfoList = vipInfoService.listByKeyword(keyword, curPage, pageSize);
            }

            JSONArray jsonArray = new JSONArray();
            for (VipInfo vipInfo : vipInfoList) {
                VipAccountInfo vipAccountInfo = vipAccountInfoService.queryByPartyId(vipInfo.getPartyId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", vipInfo.getId());
                jsonObject.put("name", vipInfo.getName());
                jsonObject.put("phone", vipInfo.getPhone());
                jsonObject.put("status", vipInfo.getStatus());
                jsonObject.put("partyId", vipInfo.getPartyId());
                jsonObject.put("money",vipAccountInfo.getBalance());

                //获取会员卡号
                Integer partyId = vipInfo.getPartyId();
                VipCard vipCard = vipCardService.queryByPartyId(partyId);
                if (vipCard != null) {
                    jsonObject.put("cardNum", vipCard.getCardNumber());
                }else {
                    jsonObject.put("cardNum", "暂无实体卡");
                }

                jsonArray.add(jsonObject);
            }
            Integer dataCount = 0;
            if (keyword == "" || keyword == null || keyword.equals("")) {
                dataCount = vipInfoService.countAll();
            } else {
                dataCount = vipInfoService.countByKeyword(keyword);
            }

            return sendJsonArray(jsonArray, dataCount);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去新增会员页面
     *
     * @return
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNewVipInfo() {
        return "admin/party/group/vip/vip_info_new";
    }

    /**
     * 新增会员
     *
     * @param vipInfo
     * @param redirectAttributes
     * @return
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoNew)
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newVipInfo(VipInfo vipInfo,
                             RedirectAttributes redirectAttributes) {
        try {
            int userPartyId = getPartyId();
            vipInfoService.newVipInfo(userPartyId, vipInfo);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            redirectAttributes.addFlashAttribute("msg", "保存失败！");
            return "admin/party/group/vip/vip_info_new";
        }
        redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
        String redirectUrl = "/" + URLConstants.ADMIN_PARTY_VIP_VIPINFO_URL + "/list";
        return "redirect:" + redirectUrl;
    }

    /**
     * 跳修改会员信息页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoUpdate)
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdateVipInfo(@PathVariable("id") int id,
                                  Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String birthday = null;
        try {
            VipInfo vipInfo = vipInfoService.queryById(id);
            if (vipInfo.getBirthday() == null) {
                birthday = "";
            } else {
                birthday = sdf.format(vipInfo.getBirthday());
            }
            model.addAttribute("vipInfo", vipInfo);
            model.addAttribute("birthday", birthday);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/party/group/vip/vip_info_update";
    }

    /**
     * 修改会员信息
     *
     * @param vipInfo
     * @param redirectAttributes
     * @return
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoUpdate)
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public String updateVipInfo(VipInfo vipInfo,
                                RedirectAttributes redirectAttributes) {
        try {
            vipInfoService.updateVipInfo(vipInfo);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        redirectAttributes.addFlashAttribute("msg", UPDATE_SUCCESS_MSG);
        String redirectUrl = "/" + URLConstants.ADMIN_PARTY_VIP_VIPINFO_URL + "/list";
        return "redirect:" + redirectUrl;
    }

    /**
     * ajax删除（更改状态为3，隐藏不显示）
     *
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoDel)
    @RequestMapping(value = "/ajax/del", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxDelById(@RequestParam("id") Integer id) {
        try {
            vipInfoService.updateStatusById(id, UserStatusEnums.Deleted);
            // vipAccountInfo状态同步
            vipAccountInfoService.updateStatusById(id, StatusEnums.Deleted);
            //将对应的会员卡也删除
            VipInfo vipInfo = vipInfoService.queryById(id);
            vipCardService.updateStatusByPartyId(vipInfo.getPartyId(), VipCardStatusEnums.Deleted.getId());

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax返回电话号码存在的错误信息
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "phone/ajax/exist", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject phoneIsExist(@RequestParam("phone") String phone,
                                   @RequestParam("id") Integer id) {
        try {
            if (vipInfoService.checkPhoneIsExist(id, phone)) {
                return sendJsonObject(AJAX_FAILURE_CODE);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax更改会员状态
     *
     * @param id
     * @param status
     * @return
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoUpdate)
    @RequestMapping(value = "ajax/status", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject updateStatus(@RequestParam("id") Integer id,
                                   @RequestParam("status") Integer status) {
        try {
            UserStatusEnums vipInfostatus = UserStatusEnums.valueOf(status);
            vipInfoService.updateStatusById(id, vipInfostatus);
            if(status == 2){
                status = 0;
            }
            // vipAccountInfo状态同步
            vipAccountInfoService.updateStatusById(id, StatusEnums.valueOf(status));

            //将对应的会员卡也修改状态
            VipInfo vipInfo = vipInfoService.queryById(id);
            if (vipInfostatus == UserStatusEnums.Disabled) {
                vipCardService.updateStatusByPartyId(vipInfo.getPartyId(), VipCardStatusEnums.Disabled.getId());
            }
            if (vipInfostatus == UserStatusEnums.Enabled) {
                vipCardService.updateStatusByPartyId(vipInfo.getPartyId(), VipCardStatusEnums.Enabled.getId());
            }

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 查看会员信息详情
     *
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoDetail)
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detailVipInfo(@PathVariable("id") Integer id,
                                Model model) {
        String birthdayStr = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            VipInfo vipInfo = vipInfoService.queryById(id);
            Date birthday = vipInfo.getBirthday();
            if (birthday == null) {
                birthdayStr = "";
            } else {
                birthdayStr = sdf.format(birthday);
            }
            VipInfoDto vipInfoDto = new VipInfoDto();
            vipInfoDto.setName(vipInfo.getName());
            vipInfoDto.setSex(SexEnums.valueOf(vipInfo.getSex()).getSex());
            vipInfoDto.setBirthday(birthdayStr);
            vipInfoDto.setPhone(vipInfo.getPhone());
            vipInfoDto.setQq(vipInfo.getQq());
            vipInfoDto.setEmail(vipInfo.getEmail());
            // 卡号
            VipCard vipCard = vipCardService.queryByPartyId(vipInfo.getPartyId());
            if (vipCard == null){
                vipInfoDto.setCardNumber("暂无实体卡");
            }else {
                vipInfoDto.setCardNumber(vipCard.getCardNumber());
            }

            model.addAttribute("vipInfoDto", vipInfoDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/party/group/vip/vip_info_detail";
    }

    /**
     * 去会员消费详情列表页
     * @param partyId
     * @param model
     * @return
     *
     * @author yangch
     * @date 2016/7/26 09:50
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoConsumption)
    @RequestMapping(value = "consumption/{partyId}", method = RequestMethod.GET)
    public String toList(@PathVariable("partyId") Integer partyId, Model model) {
        model.addAttribute("partyId", partyId);

        return "admin/party/group/vip/vip_consumption_list";
    }

    /**
     * Ajax 获取消费详情列表
     * @param curPage
     * @param pageSize
     * @param partyId
     * @return
     */
    @Module(value = ModuleEnums.AdminVipInfo , extModule = ModuleEnums.AdminVipInfoConsumption)
    @RequestMapping(value = "ajax/consumption/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxConsumptionList(@PathVariable("curPage") Integer curPage,
                                          @RequestParam Integer pageSize,
                                          @RequestParam("partyId") Integer partyId,
                                          @RequestParam(required = false) Date startTime,
                                          @RequestParam(required = false) Date endTime) {
        List<ConsumptionActivity> consumptionActivityList = Collections.emptyList();
        try {
            // 将结束时间设置为当天的最后一秒，以查询当天的记录
            if (endTime != null) {
                endTime.setHours(23);
                endTime.setMinutes(59);
                endTime.setSeconds(59);
            }

            JSONArray jsonArray = new JSONArray();
            consumptionActivityList = consumptionActivityService.listByPartyIdAndPageAndDate(partyId, curPage, pageSize, startTime, endTime);

            for (ConsumptionActivity consumptionActivity : consumptionActivityList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("consumptionId", consumptionActivity.getId());
                if (Assert.isNotNull(consumptionActivity.getCreatedTime())) {
                    jsonObject.put("consumptionTime", DateUtils.formatDatetime(consumptionActivity.getCreatedTime()));
                } else {
                    jsonObject.put("consumptionTime", "");
                }
                String type = "";
                if (consumptionActivity.getType() == ConsumptionActivityTypeEnums.Consumption.getId()) {
                    type = "消费";
                }
                if (consumptionActivity.getType() == ConsumptionActivityTypeEnums.Recharge.getId()) {
                    type = "充值";
                }
                jsonObject.put("type", type);
                jsonObject.put("oldMoney", consumptionActivity.getOriginalAmount());
                if (consumptionActivity.getType() == ConsumptionActivityTypeEnums.Consumption.getId()) {
                    jsonObject.put("consumptionMoney", "-" + consumptionActivity.getConsumptionAmount());
                }
                if (consumptionActivity.getType() == ConsumptionActivityTypeEnums.Recharge.getId()) {
                    jsonObject.put("consumptionMoney", "+" + consumptionActivity.getConsumptionAmount());
                }
                jsonObject.put("realMoney", consumptionActivity.getActualPayment());
                jsonObject.put("newMoney", consumptionActivity.getResidualAmount());
                jsonObject.put("operName", consumptionActivity.getOperator());

                jsonArray.add(jsonObject);
            }

            Integer dataCount = 0;
            dataCount = consumptionActivityService.countByPartyIdAndDate(partyId, startTime, endTime);

            return sendJsonArray(jsonArray, dataCount);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}