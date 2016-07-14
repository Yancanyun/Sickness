package com.emenu.test.printer;

import com.emenu.common.entity.printer.DishTagPrinter;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.printer.PrinterBrandEnums;
import com.emenu.service.printer.DishTagPrinterService;
import com.emenu.service.printer.PrinterService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
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

    @Autowired
    DishTagPrinterService dishTagPrinterService;

    @Test
    public void listAll() throws SSException{
        List<Printer> list = printerService.listAll();
        for (Printer printer : list){
            System.out.println(printer);
        }
    }

    @Test
    public void listDishTagPrinter() throws SSException{
        List<Printer> list = printerService.listDishTagPrinter();
        for (Printer printer : list){
            System.out.println(printer);
        }
    }

    @Test
    public void listAvailableDishTag() throws SSException{
        List<Tag> list = dishTagPrinterService.listAvailableDishTag();
        for (Tag tag : list){
            System.out.println(tag);
        }
    }

    @Test
    public void queryByTagId() throws SSException{
        System.out.println(dishTagPrinterService.queryByTagId(17));
    }

    @Test
    public void newPrinterDish() throws SSException {
        DishTagPrinter dishTagPrinter = new DishTagPrinter();
        dishTagPrinter.setDishId(24);
        dishTagPrinter.setPrinterId(1);
        dishTagPrinter.setType(1);
        dishTagPrinterService.newPrinterDish(dishTagPrinter);
    }

    @Test
    public void updatePrinterDish() throws SSException {
        DishTagPrinter dishTagPrinter = new DishTagPrinter();
        dishTagPrinter.setDishId(24);
        dishTagPrinter.setPrinterId(2);
        dishTagPrinter.setType(0);
        dishTagPrinterService.updatePrinterDish(dishTagPrinter);
    }

    @Test
    public void delPrinterDish() throws SSException {
        dishTagPrinterService.delPrinterDish(9, 1);
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
        printerService.updatePrinter(printer, null);
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
        List<Tag> list = dishTagPrinterService.listTagById(3);
        for (Tag tag : list){
            System.out.println(tag);
        }
    }

    @Test
    public void bindDishTag() throws SSException{
        dishTagPrinterService.bindDishTag(3, 9);
    }

    @Test
    public void unBindAllDishTag() throws SSException{
        dishTagPrinterService.unBindAllDishTag(2);
    }

    @Test
    public void listBrand() throws SSException{
        List<PrinterBrandEnums> brandEnumsList = PrinterBrandEnums.getBrandList();
        for (PrinterBrandEnums enums : brandEnumsList){
            System.out.println(enums.getId() + ":    " + enums.getDescription());
        }
    }

    @Test
    public void newDishTagPrinter() throws SSException{
        DishTagPrinter dishTagPrinter = new DishTagPrinter();
        dishTagPrinter.setDishId(9);
        dishTagPrinter.setPrinterId(3);
        dishTagPrinter.setType(1);
        dishTagPrinterService.newPrinterDish(dishTagPrinter);
    }

    @Test
    public void delDishTagPrinter() throws SSException{
        dishTagPrinterService.delPrinterDish(1,1);
    }

    @Test
    public void testQueryByIdAndType() throws SSException
    {
        dishTagPrinterService.queryByTagIdAndType(1,1);
    }
}
