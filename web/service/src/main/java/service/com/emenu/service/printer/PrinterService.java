package com.emenu.service.printer;

import com.emenu.common.dto.printer.PrinterDishDto;
import com.emenu.common.entity.dish.tag.Tag;
import com.emenu.common.entity.printer.Printer;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * PrinterService
 *
 * @author Wang Liming
 * @date 2015/11/12 18:43
 */
public interface PrinterService {

    /**
     * 新增打印机
     * @param printer
     * @return
     * @throws SSException
     */
    public Printer newPrinter(Printer printer) throws SSException;

    /**
     * 修改打印机信息
     *
     * @param printer
     * @throws SSException
     */
    public void updatePrinter(Printer printer) throws SSException;

    /**
     * 根据id删除打印机
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 查询所有打印机
     *
     * @return
     * @throws SSException
     */
    public List<Printer> listAll() throws SSException;

    /**
     * 根据id查询打印机
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Printer queryById(int id) throws SSException;

    /**
     * 根据菜品分类id查询关联打印机
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Printer queryByTagId(int id) throws SSException;

    /**
     * 根据打印机id查询关联菜品分类
     *
     * @param id
     * @return
     * @throws SSException
     */
    public List<Tag> queryTagById(int id) throws SSException;

    /**
     * 新增菜品与打印机关联
     * @param printerDishDto
     * @return
     * @throws SSException
     */
    public void newPrinterDish(PrinterDishDto printerDishDto) throws SSException;

    /**
     * 修改菜品与打印机关联_根据菜品Id修改
     * @param printerDishDto
     * @throws SSException
     */
    public void updatePrinterDish(PrinterDishDto printerDishDto) throws SSException;

    /**
     * 删除菜品与打印机关联
     * @param tagId
     * @throws SSException
     */
    public void delPrinterDish(int tagId) throws SSException;
}
