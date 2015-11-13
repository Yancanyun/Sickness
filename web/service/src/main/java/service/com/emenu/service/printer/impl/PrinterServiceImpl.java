package com.emenu.service.printer.impl;

import com.emenu.common.entity.dish.tag.Tag;
import com.emenu.common.entity.printer.Printer;
import com.emenu.service.printer.PrinterService;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * PrinterServiceImpl
 *
 * @author Wang Liming
 * @date 2015/11/13 9:19
 */
public class PrinterServiceImpl implements PrinterService {

    @Override
    public Printer newPrinter(String name, int brand, int printerModel, int type, String deviceNumber, String ipAddress, int state, int isCashierPrinter) throws SSException {
        return null;
    }

    @Override
    public void updatePrinter(Printer printer) throws SSException {

    }

    @Override
    public void delById(int id) throws SSException {

    }

    @Override
    public List<Printer> listAll() throws SSException {
        return null;
    }

    @Override
    public Printer queryById(int id) throws SSException {
        return null;
    }

    @Override
    public Printer queryByDishTagId(int id) throws SSException {
        return null;
    }

    @Override
    public List<Tag> queryDishTagById(int id) throws SSException {
        return null;
    }
}
