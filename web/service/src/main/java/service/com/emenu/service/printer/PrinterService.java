package com.emenu.service.printer;

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
     *
     * @param name
     * @param brand
     * @param printerModel
     * @param type
     * @param deviceNumber
     * @param ipAddress
     * @param state
     * @param isCashierPrinter
     * @return
     * @throws SSException
     */
    public Printer newPrinter(String name, int brand, int printerModel, int type, String deviceNumber,
                              String ipAddress, int state, int isCashierPrinter) throws SSException;

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
}
