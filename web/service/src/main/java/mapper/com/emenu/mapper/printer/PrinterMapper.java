package com.emenu.mapper.printer;

import com.emenu.common.dto.printer.PrinterDishDto;
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
     * 根据菜品分类id查询关联打印机
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Printer queryByTagId(@Param("id") int id) throws Exception;

    /**
     * 根据打印机id查询关联菜品分类
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<Tag> listTagById(@Param("id") int id) throws Exception;

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

    /**
     * 新增菜品与打印机关联
     * @param printerDishDto
     * @return
     * @throws SSException
     */
    public void newPrinterDish(@Param("printerDishDto") PrinterDishDto printerDishDto) throws SSException;

    /**
     * 修改菜品与打印机关联
     * @param printerDishDto
     * @throws SSException
     */
    public void updatePrinterDish(@Param("printerDishDto") PrinterDishDto printerDishDto) throws SSException;

    /**
     * 删除菜品与打印机关联
     * @param tagId
     * @throws SSException
     */
    public void delPrinterDish(@Param("tagId") int tagId) throws SSException;
}
