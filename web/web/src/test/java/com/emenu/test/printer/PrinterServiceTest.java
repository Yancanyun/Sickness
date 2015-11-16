package com.emenu.test.printer;

import com.emenu.common.dto.printer.PrinterDishDto;
import com.emenu.common.entity.dish.Tag;
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

    @Test
    public void newPrinterDish() throws SSException {
        PrinterDishDto printerDishDto = new PrinterDishDto();
        printerDishDto.setDishId(24);
        printerDishDto.setPrinterId(1);
        printerDishDto.setType(1);
        printerService.newPrinterDish(printerDishDto);
    }

    @Test
    public void updatePrinterIdish() throws SSException {
        PrinterDishDto printerDishDto  = new PrinterDishDto();
        printerDishDto.setDishId(24);
        printerDishDto.setPrinterId(2);
        printerDishDto.setType(0);
        printerService.updatePrinterDish(printerDishDto);
    }

    @Test
    public void delPrinterdish() throws SSException {
        printerService.delPrinterDish(24);
    }

    @Test
    public void newPrinter() throws SSException{
        Printer printer = new Printer();
        printer.setName("分类打印机1");
        printer.setState(1);
        printer.setBrand(1);
        printer.setDeviceNumber("1");
        printer.setIpAddress("192.168.1.4");
        printer.setPrinterModel(1);
        printer.setType(1);
        printer.setIsCashierPrinter(0);
        printerService.newPrinter(printer);
    }

    @Test
    public void updatePrinter() throws SSException{
        Printer printer = new Printer();
        printer.setId(1);
        printerService.updatePrinter(printer);
    }

    @Test
    public void delById() throws SSException{
        printerService.delById(4);
    }

    @Test
    public void queryById() throws SSException{
        System.out.println(printerService.queryById(1));
    }

    @Test
    public void listTagById() throws SSException{
        List<Tag> list = printerService.listTagById(3);
        for (Tag tag : list){
            System.out.println(tag);
        }
    }
}
