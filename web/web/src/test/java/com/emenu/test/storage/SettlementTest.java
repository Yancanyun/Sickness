package com.emenu.test.storage;

import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.service.other.SerialNumService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SettlementTest
 *
 * @author dujuan
 * @date 2015/11/15
 */
public class SettlementTest extends AbstractTestCase{

    @Autowired
    private SerialNumService serialNumService;

    @Test
    public void generateSerialNum() throws SSException {
        System.out.print(serialNumService.generateSerialNum(SerialNumTemplateEnums.SettlementSerialNum));
    }
}
