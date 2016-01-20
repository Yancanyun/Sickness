package com.emenu.test.vip;

import com.emenu.common.dto.vip.VipCountInfoDto;
import com.emenu.common.entity.vip.VipCountInfo;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.service.vip.VipCountInfoService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import junit.framework.TestResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.SybaseAnywhereMaxValueIncrementer;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by xubr on 2016/1/19.
 */
public class VipCountInfoTest extends AbstractTestCase {

    @Autowired
    private VipCountInfoService vipCountInfoService;

    @Test
    public void listByPageAndMin() throws SSException {
        List<VipCountInfoDto> countList = vipCountInfoService.listByPageAndMin(2,3,0,"minConsumption");
        for(VipCountInfoDto vipCountInfoDto : countList) {
            System.out.print(vipCountInfoDto.getMinConsumption());
            System.out.print(vipCountInfoDto.getName());
            System.out.print(vipCountInfoDto.getBalance());
            System.out.print(vipCountInfoDto);
        }
        System.out.print(vipCountInfoService.countAll());
    }

    @Test
    public void newVipCountInfo() throws SSException {
        VipCountInfo vipCountInfo = new VipCountInfo();
        vipCountInfo.setPartyId(22);
        vipCountInfo.setUsedCreditAmount(new BigDecimal(3000));
        vipCountInfo.setBalance(new BigDecimal(3000));
        vipCountInfo.setIntegral(500);
        vipCountInfo.setTotalConsumption(new BigDecimal(3000));
        vipCountInfo.setStatus(0);
        vipCountInfoService.newVipCountInfo(vipCountInfo);
    }

    @Test
    public void updateVipCountInfo() throws SSException {
        VipCountInfo vipCountInfo = new VipCountInfo();
        vipCountInfo.setId(1);
        vipCountInfo.setPartyId(6);
        vipCountInfo.setUsedCreditAmount(new BigDecimal(4000));
        vipCountInfo.setBalance(new BigDecimal(4000));
        vipCountInfo.setIntegral(4000);
        vipCountInfo.setTotalConsumption(new BigDecimal(4000));
        vipCountInfo.setStatus(1);
        vipCountInfoService.updateVipCountInfo(vipCountInfo);
    }

    @Test
    public void deleteVipCountInfo() throws SSException {
        vipCountInfoService.deleteVipCountInfo(4);
    }

    @Test
    public void updateVipCountStatus() throws SSException {
        vipCountInfoService.updateStatusById(5, StatusEnums.valueOf(0));
    }
}
