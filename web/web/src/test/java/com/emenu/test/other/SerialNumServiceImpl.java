package com.emenu.test.other;

import com.emenu.mapper.other.SerialNumMapper;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SerialNumServiceImpl
 *
 * @author: zhangteng
 * @time: 2015/11/11 15:49
 **/
public class SerialNumServiceImpl extends AbstractTestCase {

    @Autowired
    private SerialNumMapper serialNumMapper;

    @Test
    public void querySerialNum() throws Exception {
        System.out.println("\n\n\n\n\n\n");
        System.out.println(serialNumMapper.querySerialNum("RKD-20151111"));
    }
}
