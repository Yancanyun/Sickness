package com.emenu.test.vip;

import com.emenu.common.dto.vip.VipAccountInfoDto;
import com.emenu.common.entity.vip.VipAccountInfo;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.service.vip.VipAccountInfoService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * VipAccountInfoTest
 * 会员账号测试
 *
 * @author xubr
 * @date 2016/1/19.
 */
public class VipAccountInfoTest extends AbstractTestCase {

    @Autowired
    private VipAccountInfoService vipAccountInfoService;

    @Test
    public void listByPageAndMin() throws SSException {
        List<Integer> gradeIdList = new ArrayList<Integer>();
        gradeIdList.add(1);
        List<VipAccountInfoDto> countList = vipAccountInfoService.listByPageAndMin(1,50,0,"minConsumption","测试",gradeIdList);
        for(VipAccountInfoDto vipAccountInfoDto : countList) {
            System.out.print(vipAccountInfoDto.getMinConsumption());
            System.out.print(vipAccountInfoDto.getName());
            System.out.print(vipAccountInfoDto.getBalance());
            System.out.print(vipAccountInfoDto);
        }
        System.out.print(vipAccountInfoService.countAll());
    }

    @Test
    public void newVipAccountInfo() throws SSException {
        VipAccountInfo vipAccountInfo = new VipAccountInfo();
        vipAccountInfo.setPartyId(22);
        vipAccountInfo.setUsedCreditAmount(new BigDecimal(3000));
        vipAccountInfo.setBalance(new BigDecimal(3000));
        vipAccountInfo.setIntegral(500);
        vipAccountInfo.setTotalConsumption(new BigDecimal(3000));
        vipAccountInfo.setStatus(0);
        vipAccountInfoService.newVipAccountInfo(vipAccountInfo);
    }

    @Test
    public void updateVipAccountInfo() throws SSException {
        VipAccountInfo vipAccountInfo = new VipAccountInfo();
        vipAccountInfo.setId(1);
        vipAccountInfo.setPartyId(6);
        vipAccountInfo.setUsedCreditAmount(new BigDecimal(4000));
        vipAccountInfo.setBalance(new BigDecimal(4000));
        vipAccountInfo.setIntegral(4000);
        vipAccountInfo.setTotalConsumption(new BigDecimal(4000));
        vipAccountInfo.setStatus(1);
        vipAccountInfoService.updateVipAccountInfo(vipAccountInfo);
    }

    @Test
    public void deleteVipAccountInfo() throws SSException {
        vipAccountInfoService.deleteVipAccountInfo(4);
    }

    @Test
    public void updateVipAccountStatus() throws SSException {
        vipAccountInfoService.updateStatusById(5, StatusEnums.valueOf(0));
    }

    @Test
    public void queryByOpenId() throws SSException {
        VipAccountInfoDto vipAccountInfoDto = vipAccountInfoService.queryByOpenId("oFizls4L71GcsOzCjEMrOLkH77A0");
        System.out.println(vipAccountInfoDto.getIntegral());
    }
}
