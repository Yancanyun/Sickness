package com.emenu.test.party.group.vip;

import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.service.party.group.vip.VipInfoService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 会员基本信息管理测试用例
 * @author chenyuting
 * @date 2015/10/26 13:52
 */
public class VipInfoServiceTest extends AbstractTestCase {

    @Autowired
    private VipInfoService vipInfoService;

    @Test
    public void newVipInfo() throws SSException{
        VipInfo vipInfo = new VipInfo();
        Integer userPartyId = 0;
        vipInfo.setName("陈玉婷");
        vipInfo.setPhone("18243003887");

        vipInfoService.newVipInfo(userPartyId,vipInfo);
    }

    @Test
    public void countVipInfoByKeyword() throws SSException{
        int number = vipInfoService.countByKeyword("18543132151");
        System.out.println(number);
    }

    /*@Test
    public void checkPhoneIsExist() throws SSException{
        String phone = "18543131024";
        boolean isExist = vipInfoService.checkPhoneIsExist(phone);
        System.out.println(isExist);
    }*/

    @Test
    public void updateVipInfoStateById() throws SSException{
        int id = 2;
        vipInfoService.updateStatusById(id, UserStatusEnums.Disabled);
        System.out.println("id为" + id + "的状态已经修改为" + UserStatusEnums.Disabled.getStatus());
    }

    @Test
    public void queryVipInfoById() throws SSException{
        int id = 2;
        VipInfo vipInfo = vipInfoService.queryById(id);
        String name = vipInfo.getName();
        String phone = vipInfo.getPhone();
        Date birthday = vipInfo.getBirthday();
        int state = vipInfo.getStatus();
        //String status =UserStatusEnums.getDescriptionById(state);
        //System.out.println("id为" + id + "的详细信息为" + "姓名：" + name + "；电话： " + phone + "；生日：" + birthday + "；用户状态：" + status );
    }

    /*@Test
    public void updateVipInfo() throws SSException{
        VipInfo vipInfo = new VipInfo();
        vipInfo.setPhone();
    }*/

    @Test
    public void querySecurityUserIdById() throws SSException{
        int id = 1;
        int securityUserId = vipInfoService.querySecurityUserIdById(id);
        System.out.println(securityUserId);
    }

    @Test
    public void listByKeyword() throws SSException{
        List<VipInfo> vipInfos = vipInfoService.listByKeyword("姜雪", 1, 10);
        for (VipInfo vipInfo: vipInfos){
            System.out.println(vipInfo.getPartyId());
            System.out.println(vipInfo.getName());
            System.out.println(vipInfo.getPhone());
        }
    }

    @Test
    public void countByGradeId() throws SSException{
        Integer count  = 0;
        Integer gradeId = 0;
        count = vipInfoService.countByGradeId(gradeId);
        System.out.println(count);
    }

    @Test
    public void searchByNameOrPhone() throws SSException{
        String keyword = "";
        List<VipInfo> vipInfoList = vipInfoService.searchByNameOrPhone(keyword);
        for (VipInfo vipInfo: vipInfoList){
            System.out.print(vipInfo.getName() + " ");
            System.out.println(vipInfo.getPhone());
            System.out.println("****************");
        }
    }

    @Test
    public void bondWechat() throws SSException {
        String phone = "18643063480";
        String password = "123456";
        String openId = "123";

        vipInfoService.bondWeChat(openId, phone);
    }

    @Test
    public void unbondWechat() throws SSException {
        String openId = "123";

        vipInfoService.unbondWeChat(openId);
    }
}