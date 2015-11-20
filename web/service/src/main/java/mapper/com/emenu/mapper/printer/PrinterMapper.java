package com.emenu.mapper.printer;

import com.emenu.common.entity.printer.DishTagPrinter;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.printer.Printer;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查询所有分类打印机
     *
     * @return
     * @throws Exception
     */
    public List<Printer> listDishTagPrinter() throws Exception;

    /**
     * 判断某字段中是否存在相同值
     * 仅支持String类型字段
     *
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public Integer countByFieldName(@Param("name")String name, @Param("value")String value) throws Exception;
}
