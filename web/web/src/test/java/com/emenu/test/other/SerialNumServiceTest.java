package com.emenu.test.other;

import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.service.other.SerialNumService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SerialNumServiceImpl
 *
 * @author: zhangteng
 * @time: 2015/11/11 15:49
 **/
public class SerialNumServiceTest extends AbstractTestCase {

    @Autowired
    private SerialNumService serialNumService;

    @Test
    public void querySerialNum() throws Exception {
        System.out.println("\n\n\n\n\n\n");

        System.out.println(serialNumService.generateSerialNum(SerialNumTemplateEnums.StockInSerialNum));
    }
}
