package com.emenu.test.party.group.vip;

import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.service.party.group.vip.VipInfoService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
        vipInfo.setName("陈玉婷");
        vipInfo.setPhone("18243003887");

        vipInfoService.newVipInfo(vipInfo);
    }

    @Test
    public void countVipInfoByKeyword() throws SSException{
        int number = vipInfoService.countVipInfoByKeyword("18543132151");
        System.out.println(number);
    }

    @Test
    public void checkPhoneIsExist() throws SSException{
        String phone = "18543131024";
        boolean isExist = vipInfoService.checkPhoneIsExist(phone);
        System.out.println(isExist);
    }

    @Test
    public void updateVipInfoStateById() throws SSException{
        int id = 2;
        vipInfoService.updateVipInfoStateById(id, UserStatusEnums.Disabled);
        System.out.println("id为" + id + "的状态已经修改为" + UserStatusEnums.Disabled.getState());
    }

    @Test
    public void queryVipInfoById() throws SSException{
        int id = 2;
        VipInfo vipInfo = vipInfoService.queryVipInfoById(id);
        String name = vipInfo.getName();
        String phone = vipInfo.getPhone();
        Date birthday = vipInfo.getBirthday();
        int state = vipInfo.getState();
        //String status =UserStatusEnums.getDescriptionById(state);
        //System.out.println("id为" + id + "的详细信息为" + "姓名：" + name + "；电话： " + phone + "；生日：" + birthday + "；用户状态：" + status );
    }
}