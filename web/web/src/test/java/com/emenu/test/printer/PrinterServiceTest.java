package com.emenu.test.printer;

import com.emenu.common.entity.printer.Printer;
import com.emenu.service.printer.PrinterService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * PrinterServiceTest
 *
 * @author Wang Liming
 * @date 2015/11/13 14:37
 */
public class PrinterServiceTest extends AbstractTestCase{

    @Autowired
    PrinterService printerService;

    @Test
    public void listAll() throws SSException{
        List<Printer> list = printerService.listAll();
        for (Printer printer : list){
            System.out.println(printer);
        }
    }

    @Test
    public void queryByTagId() throws SSException{
        System.out.println(printerService.queryByTagId(17));
    }
}
