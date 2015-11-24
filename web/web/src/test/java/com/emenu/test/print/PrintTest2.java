package com.emenu.test.print;

import com.emenu.common.entity.printer.Printer;
import com.emenu.common.utils.PrintUtils;
import com.emenu.service.printer.PrintService;
import com.emenu.service.printer.PrinterService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

/**
 * PrintTest2
 *
 * @author: yangch
 * @time: 2015/11/24 10:55
 */
public class PrintTest2 extends AbstractTestCase {

    @Autowired
    PrintService printService;

    @Test
    public void test1() throws SSException {
        printService.printDishNameAndQuantityAndPrice(1);
    }
}