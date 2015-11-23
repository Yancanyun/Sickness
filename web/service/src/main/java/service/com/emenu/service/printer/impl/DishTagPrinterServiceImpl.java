package com.emenu.service.printer.impl;

import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.printer.DishTagPrinter;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.printer.PrinterDishEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.printer.DishTagPrinterMapper;
import com.emenu.service.printer.DishTagPrinterService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * DishTagPrinterServiceImpl
 *
 * @author Wang Liming
 * @date 2015/11/20 14:59
 */

@Service(value = "dishTagPrinterService")
public class DishTagPrinterServiceImpl implements DishTagPrinterService{

    @Autowired
    CommonDao commonDao;

    @Autowired
    DishTagPrinterMapper dishTagPrinterMapper;

    @Override
    public Printer queryByTagId(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.PrinterInfoIllegal);
            }
            return dishTagPrinterMapper.queryByTagId(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryPrinterFail, e);
        }
    }

    @Override
    public List<Tag> listTagById(int id) throws SSException {
        List<Tag> list = Collections.emptyList();
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.PrinterInfoIllegal);
            }
            list =  dishTagPrinterMapper.listTagById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListTagFailed, e);
        }
        return list;
    }

    @Override
    public List<String> listDishNameById(int id) throws SSException {
        List<String> list = Collections.emptyList();
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.PrinterInfoIllegal);
            }
            list = dishTagPrinterMapper.listDishNameById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListTagFailed, e);
        }
        return list;
    }

    @Override
    public List<Tag> listAvailableDishTag() throws SSException {
        List<Tag> list = Collections.emptyList();
        try {
            list = dishTagPrinterMapper.listAvailableDishTag();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListTagFailed, e);
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newPrinterDish(DishTagPrinter dishTagPrinter) throws SSException {
        try {
            if (!checkBeforeSave(dishTagPrinter)){
                return;
            }
            //查询关联是否已存在
            DishTagPrinter dishTagPrinter1 = dishTagPrinterMapper.queryPrinterDish(dishTagPrinter);
            if (!Assert.isNull(dishTagPrinter1)){
                throw SSException.get(EmenuException.PrinterDishExist);
            }

            dishTagPrinterMapper.newPrinterDish(dishTagPrinter);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewPrinterDishError, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updatePrinterDish(DishTagPrinter dishTagPrinter) throws SSException {
        try {
            if (!checkBeforeSave(dishTagPrinter)){
                return;
            }

            dishTagPrinterMapper.updatePrinterDish(dishTagPrinter);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdatePrinterDishError, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delPrinterDish(int tagId, int type) throws SSException {
        try {
            if (Assert.lessOrEqualZero(tagId)){
                throw SSException.get(EmenuException.TagIdError);
            }
            dishTagPrinterMapper.delPrinterDish(tagId, type);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelPrinterDishError, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void bindDishTag(int printerId, int dishTagId) throws SSException {
        try {
            if (Assert.lessOrEqualZero(printerId) || Assert.lessOrEqualZero(dishTagId)){
                throw SSException.get(EmenuException.PrinterInfoIllegal);
            }
            dishTagPrinterMapper.updatePrinterIdByDishTagId(printerId, dishTagId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewPrinterDishError, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void unBindAllDishTag(int printerId) throws SSException {
        try {
            if (Assert.lessOrEqualZero(printerId)){
                throw SSException.get(EmenuException.PrinterInfoIllegal);
            }
            dishTagPrinterMapper.updatePrinterIdByPrinterId(printerId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelPrinterDishError, e);
        }
    }

    /**
     * 检查实体及其关键字段是否为空
     *
     * @param dishTagPrinter
     * @return
     * @throws SSException
     */
    private boolean checkBeforeSave(DishTagPrinter dishTagPrinter) throws SSException{
        if (Assert.isNull(dishTagPrinter)){
            return false;
        }

        if (Assert.isNull(dishTagPrinter.getDishId()) || Assert.lessOrEqualZero(dishTagPrinter.getDishId())){
            throw SSException.get(EmenuException.TagIdError);
        }
        if (Assert.isNull(dishTagPrinter.getPrinterId()) || Assert.lessZero(dishTagPrinter.getPrinterId())){
            throw SSException.get(EmenuException.PrinterIdError);
        }
        if (Assert.isNull(PrinterDishEnum.valueOf(dishTagPrinter.getType()))){
            throw SSException.get(EmenuException.PrinterDishTypeError);
        }

        return true;
    }
}
