package com.emenu.test.vip;

import com.emenu.common.dto.vip.VipRegisterDto;
import com.emenu.service.vip.VipOperationService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * VipOperationTest
 *
 * @author xiaozl
 * @date: 2016/7/23
 */
public class VipOperationTest extends AbstractTestCase {

    @Autowired
    VipOperationService vipOperationService;

    @Test
    public void registerVip() throws SSException{

        VipRegisterDto rejisterDto = vipOperationService.registerVip("yang",1,"18982436412",new Date(),new Date(),1,1);
        System.out.println("xiaoz");
    }

    @Test
    public void queryByKeyword() throws SSException{
        VipRegisterDto registerDto = vipOperationService.queryByKeyword("20160723006");
        System.out.println("xiao");
    }
}
