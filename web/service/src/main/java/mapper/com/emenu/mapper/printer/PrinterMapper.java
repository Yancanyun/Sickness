package com.emenu.mapper.printer;

import com.emenu.common.entity.printer.Printer;

import java.util.List;

/**
 * PrinterMapper
 *
 * @author Wang Liming
 * @date 2015/11/13 14:01
 */
public interface PrinterMapper {

    /**
     * 查询所有打印机
     *
     * @return
     * @throws Exception
     */
    public List<Printer> listAll() throws Exception;


}
