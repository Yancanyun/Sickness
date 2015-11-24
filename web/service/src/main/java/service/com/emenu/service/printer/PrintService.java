package com.emenu.service.printer;

import com.emenu.common.entity.printer.Printer;
import com.pandawork.core.common.exception.SSException;

/**
 * PrintService
 *
 * @author: yangch
 * @time: 2015/11/24 10:49
 */
public interface PrintService {
    /**
     * 打印菜品名称、数量、单价
     * @param printerId
     * @return
     * @throws SSException
     */
    public boolean printDishNameAndQuantityAndPrice(int printerId) throws SSException;
}
