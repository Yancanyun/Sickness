package com.emenu.service.printer.impl;

import com.emenu.common.dto.printer.PrinterDishDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.printer.*;
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
    public Printer newPrinter(Printer printer) throws SSException {
        try {
            if(!checkBeforeSave(printer)){
                return null;
            }
            return commonDao.insert(printer);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertPrinterFail, e);
        }
    }

    @Override
    public void updatePrinter(Printer printer) throws SSException {
        try {
            if (!checkBeforeUpdate(printer)){
                return;
            }
            commonDao.update(printer);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdatePrinterFail, e);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.PrinterIdError);
            }
            commonDao.deleteById(Printer.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeletePrinterFail, e);
        }
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
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.PrinterIdError);
            }
            return commonDao.queryById(Printer.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryPrinterFail, e);
        }
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
            throw SSException.get(EmenuException.QueryPrinterFail, e);
        }
    }

    @Override
    public List<Tag> listTagById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.PrinterInfoIllegal);
            }
            return printerMapper.listTagById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListTagFailed, e);
        }
    }

    @Override
    public void newPrinterDish(PrinterDishDto printerDishDto) throws SSException {
        try {
            if (Assert.lessOrEqualZero(printerDishDto.getDishId())){
                throw SSException.get(EmenuException.TagIdError);
            }
            if (Assert.lessOrEqualZero(printerDishDto.getPrinterId())){
                throw SSException.get(EmenuException.PrinterIdError);
            }
            if (Assert.lessZero(printerDishDto.getType())){
                throw SSException.get(EmenuException.PrinterDishTypeError);
            }
            printerMapper.newPrinterDish(printerDishDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewPrinterDishError, e);
        }
    }

    @Override
    public void updatePrinterDish(PrinterDishDto printerDishDto) throws SSException {
        try {
            if (Assert.lessOrEqualZero(printerDishDto.getDishId())){
                throw SSException.get(EmenuException.TagIdError);
            }
            if (Assert.lessOrEqualZero(printerDishDto.getPrinterId())){
                throw SSException.get(EmenuException.PrinterIdError);
            }
            printerMapper.updatePrinterDish(printerDishDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdatePrinterDishError, e);
        }
    }

    @Override
    public void delPrinterDish(int tagId) throws SSException {
        try {
            if (Assert.lessOrEqualZero(tagId)){
                throw SSException.get(EmenuException.TagIdError);
            }
            printerMapper.delPrinterDish(tagId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelPrinterDishError, e);
        }

    }

    private boolean checkValueExist(String name, String value) throws SSException{
        int num = 0;
        try {
            num = printerMapper.countByFieldName(name, value);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return num > 0;
    }

    /**
     * 检查实体及其关键字段
     *
     * @param printer
     * @return
     * @throws SSException
     */
    private boolean checkBeforeSave(Printer printer) throws SSException{
        if (Assert.isNull(printer)){
            return false;
        }
        Assert.isNotNull(printer.getName(), EmenuException.PrinterNameNotNull);
        Assert.isNotNull(PrinterBrandEnums.valueOf(printer.getBrand()), EmenuException.PrinterBrandIllegal);
        Assert.isNotNull(PrinterModelEnums.valueOf(printer.getPrinterModel()), EmenuException.PrinterModelIllegal);
        Assert.isNotNull(printer.getIpAddress(), EmenuException.PrinterIpAddressNotNull);
        Assert.isNotNull(PrinterTypeEnums.valueOf(printer.getType()), EmenuException.PrinterTypeIllegal);
        Assert.isNotNull(PrinterStateEnums.valueOf(printer.getState()), EmenuException.PrinterStateIllegal);
        Assert.isNotNull(IsCashierPrinterEnums.valueOf(printer.getIsCashierPrinter()), EmenuException.PrinterInfoIllegal);

        if (checkValueExist("name", printer.getName())){
            throw SSException.get(EmenuException.PrinterNameExist);
        }

        if (checkValueExist("ip_address", printer.getIpAddress())){
            throw SSException.get(EmenuException.PrinterIpAddressExist);
        }

        return true;
    }

    private boolean checkBeforeUpdate(Printer printer) throws SSException{
        if (Assert.isNull(printer)){
            return false;
        }
        if (!Assert.isNull(printer.getId())){
            if (Assert.lessOrEqualZero(printer.getId())){
                throw SSException.get(EmenuException.PrinterIdError);
            }
        }
        if (!Assert.isNull(printer.getBrand())){
            if (Assert.isNull(PrinterBrandEnums.valueOf(printer.getBrand()))){
                throw SSException.get(EmenuException.PrinterBrandIllegal);
            }
        }
        if (!Assert.isNull(printer.getPrinterModel())){
            if (Assert.isNull(PrinterModelEnums.valueOf(printer.getPrinterModel()))){
                throw SSException.get(EmenuException.PrinterModelIllegal);
            }
        }
        if (!Assert.isNull(printer.getType())){
            if (Assert.isNull(PrinterTypeEnums.valueOf(printer.getType()))){
                throw SSException.get(EmenuException.PrinterTypeIllegal);
            }
        }
        if (!Assert.isNull(printer.getState())){
            if (Assert.isNull(PrinterStateEnums.valueOf(printer.getState()))){
                throw SSException.get(EmenuException.PrinterStateIllegal);
            }
        }
        if (!Assert.isNull(printer.getIsCashierPrinter())){
            if (Assert.isNull(IsCashierPrinterEnums.valueOf(printer.getIsCashierPrinter()))){
                throw SSException.get(EmenuException.PrinterInfoIllegal);
            }
        }

        return true;
    }
}
