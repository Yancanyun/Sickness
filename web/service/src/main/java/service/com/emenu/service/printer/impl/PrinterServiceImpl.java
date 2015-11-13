package com.emenu.service.printer.impl;

import com.emenu.common.entity.dish.tag.Tag;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.printer.PrinterMapper;
import com.emenu.service.printer.PrinterService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * PrinterServiceImpl
 *
 * @author Wang Liming
 * @date 2015/11/13 9:19
 */

@Service(value = "printerService")
public class PrinterServiceImpl implements PrinterService {

    @Autowired
    CommonDao commonDao;

    @Autowired
    PrinterMapper printerMapper;

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
        List<Printer> list = Collections.emptyList();
        try {
            list = printerMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryPrinterFail, e);
        }
        return list;
    }

    @Override
    public Printer queryById(int id) throws SSException {
        return null;
    }

    @Override
    public Printer queryByTagId(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.PrinterInfoIllegal);
            }
            return printerMapper.queryByTagId(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListTagFailed, e);
        }
    }

    @Override
    public List<Tag> queryTagById(int id) throws SSException {
        return null;
    }
}
