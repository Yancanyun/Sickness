package com.emenu.web.controller.admin.party.group.vip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.vip.VipInfoDto;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.party.SexEnums;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 会员基本信息管理controller
 * @author chenyuting
 * @date 2015/10/27 10:55
 */
@Controller
@Module(ModuleEnums.AdminVipInfo)
@RequestMapping(value = URLConstants.VIP_VIPINFO_URL)
public class VipInfoController extends AbstractController {

    /**
     * 去会员基本信息列表页面
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(){
        return "admin/party/group/vip/vip_info_list";
    }

    /**
     * 分页获取关键字搜索列表
     * @param curPage
     * @param pageSize
     * @param keyword
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoList)
    @RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListVipInfo(@PathVariable("curPage") Integer curPage,
                                      @RequestParam Integer pageSize,
                                      @RequestParam("keyword") String keyword){
        List<VipInfo> vipInfoList = Collections.emptyList();
        try{
            if (keyword =="" || keyword == null || keyword.equals("")){
                vipInfoList = vipInfoService.listByPage(curPage, pageSize);
            }else {
                vipInfoList = vipInfoService.listByKeyword(keyword, curPage, pageSize);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (VipInfo vipInfo: vipInfoList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vipInfo.getId());
            jsonObject.put("name", vipInfo.getName());
            jsonObject.put("phone", vipInfo.getPhone());
            jsonObject.put("state", vipInfo.getState());
            jsonArray.add(jsonObject);
        }
        Integer dataCount = 0;
        try {
            dataCount = vipInfoService.countAll();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * 去新增会员页面
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNewVipInfo(){
        return "admin/party/group/vip/vip_info_new";
    }

    /**
     * 新增会员
     * @param name
     * @param sex
     * @param birthday
     * @param phone
     * @param qq
     * @param email
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoNew)
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newVipInfo(VipInfo vipInfo){
        try{
            int userPartyId = getPartyId();
            vipInfoService.newVipInfo(userPartyId,vipInfo);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "redirect:list";
    }

    /**
     * 跳修改会员信息页
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoUpdate)
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdateVipInfo(@PathVariable("id") int id,
                                  Model model){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            VipInfo vipInfo = vipInfoService.queryById(id);
            String birthday = sdf.format(vipInfo.getBirthday());
            model.addAttribute("vipInfo",vipInfo);
            model.addAttribute("birthday",birthday == null ? "" : birthday);
        }catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/party/group/vip/vip_info_update";
    }

    /**
     * 修改会员信息
     * @param id
     * @param name
     * @param sex
     * @param birthday
     * @param phone
     * @param qq
     * @param email
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoUpdate)
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public String updateVipInfo(VipInfo vipInfo){
        try{
            vipInfoService.updateVipInfo(vipInfo);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "redirect:list";
    }

    /**
     * ajax删除（更改状态为3，隐藏不显示）
     * @param id
     * @return
     */
    @RequestMapping(value = "/ajax/del", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxDel(@RequestParam("id") Integer id){
        try{
            vipInfoService.updateStateById(id, UserStatusEnums.Deleted);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax返回电话号码存在的错误信息
     * @param phone
     * @return
     */
    @RequestMapping(value = "phone/ajax/exist", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject phoneIsExist(@RequestParam("phone") String phone,
                                   @RequestParam("id") Integer id){
        try{
            if (vipInfoService.checkPhoneIsExist(id, phone)){
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
     * @param id
     * @param state
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoUpdate)
    @RequestMapping(value = "ajax/state", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject updateState(@RequestParam("id") Integer id,
                                  @RequestParam("state") Integer state){
        try{
            UserStatusEnums vipInfostate = UserStatusEnums.valueOf(state);
            vipInfoService.updateStateById(id, vipInfostate);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 查看会员信息详情
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminVipInfoDetail)
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detailVipInfo(@PathVariable("id") Integer id,
                                Model model){
        try{
            VipInfo vipInfo = vipInfoService.queryById(id);
            Date birthday = vipInfo.getBirthday();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            VipInfoDto vipInfoDto = new VipInfoDto();
            vipInfoDto.setName(vipInfo.getName());
            vipInfoDto.setSex(SexEnums.valueOf(vipInfo.getSex()).getSex());
            vipInfoDto.setBirthday(sdf.format(birthday));
            vipInfoDto.setPhone(vipInfo.getPhone());
            vipInfoDto.setQq(vipInfo.getQq());
            vipInfoDto.setEmail(vipInfo.getEmail());
            model.addAttribute("vipInfoDto",vipInfoDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/party/group/vip/vip_info_detail";
    }


}