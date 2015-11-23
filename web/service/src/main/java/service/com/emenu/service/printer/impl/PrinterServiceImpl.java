package com.emenu.service.printer.impl;

import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.TrueEnums;
import com.emenu.common.enums.printer.PrinterBrandEnums;
import com.emenu.common.enums.printer.PrinterModelEnums;
import com.emenu.common.enums.printer.PrinterStateEnums;
import com.emenu.common.enums.printer.PrinterTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.printer.PrinterMapper;
import com.emenu.service.printer.DishTagPrinterService;
import com.emenu.service.printer.PrinterService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    @Qualifier("dishTagPrinterService")
    DishTagPrinterService dishTagPrinterService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updatePrinter(Printer printer, List<Integer> dishTagList) throws SSException {
        try {
            if (!checkBeforeUpdate(printer)){
                return;
            }

            //修改菜品关联
            //解除所有关联菜品后将选择的菜品重新关联
            dishTagPrinterService.unBindAllDishTag(printer.getId());
            if (printer.getType().equals(PrinterTypeEnums.DishTagPrinter.getId())) {
                if (!Assert.isEmpty(dishTagList)){
                    for (int id : dishTagList){
                        dishTagPrinterService.bindDishTag(printer.getId(), id);
                    }
                }
            }
            commonDao.update(printer);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdatePrinterFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.PrinterIdError);
            }
            if (!Assert.isEmpty(dishTagPrinterService.listTagById(id)) ||
                    !Assert.isEmpty(dishTagPrinterService.listDishNameById(id))){
                throw SSException.get(EmenuException.PrinterIsUsing);
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
    public List<Printer> listDishTagPrinter() throws SSException {
        List<Printer> list = Collections.emptyList();
        try {
            list = printerMapper.listDishTagPrinter();
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
        Assert.isNotNull(TrueEnums.valueOf(printer.getIsCashierPrinter()), EmenuException.PrinterInfoIllegal);

        //判断是否重名
        //如果是编辑，忽略与其本身重名的情况
        Printer printer1 = new Printer();
        if (!Assert.isNull(printer.getId()) && !Assert.lessOrEqualZero(printer.getId())){
            printer1 = queryById(printer.getId());
        }

        if (!printer.getName().equals(printer1.getName())) {
            if (checkValueExist("name", printer.getName())){
                throw SSException.get(EmenuException.PrinterNameExist);
            }
        }

        //判断ip是否相同
        //如果是编辑，忽略与其本身相同的情况
        if (!printer.getIpAddress().equals(printer1.getIpAddress())) {
            if (checkValueExist("ip_address", printer.getIpAddress())){
                throw SSException.get(EmenuException.PrinterIpAddressExist);
            }
        }

        return true;
    }

    private boolean checkBeforeUpdate(Printer printer) throws SSException{
        if (!checkBeforeSave(printer)){
            return false;
        }

        if (!Assert.isNull(printer.getId())){
            if (Assert.lessOrEqualZero(printer.getId())){
                throw SSException.get(EmenuException.PrinterIdError);
            }
        } else {
            throw SSException.get(EmenuException.PrinterIdError);
        }

        return true;
    }
}
