package com.emenu.mapper.printer;

import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.printer.DishTagPrinter;
import com.emenu.common.entity.printer.Printer;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DishTagPrinterMapper
 *
 * @author Wang Liming
 * @date 2015/11/20 15:02
 */
public interface DishTagPrinterMapper {

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
     * 根据打印机id查询关联菜品
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<String> listDishNameById(@Param("id") int id) throws Exception;

    /**
     * 查询没有关联打印机的菜品分类
     *
     * @return
     * @throws Exception
     */
    public List<Tag> listAvailableDishTag() throws Exception;

    /**
     * 新增菜品与打印机关联
     * @param dishTagPrinter
     * @return
     * @throws SSException
     */
    public void newPrinterDish(@Param("dishTagPrinter") DishTagPrinter dishTagPrinter) throws Exception;

    /**
     * 修改菜品与打印机关联
     * @param dishTagPrinter
     * @throws SSException
     */
    public void updatePrinterDish(@Param("dishTagPrinter") DishTagPrinter dishTagPrinter) throws Exception;

    /**
     * 删除菜品与打印机关联
     * @param tagId
     * @param type
     * @throws SSException
     */
    public void delPrinterDish(@Param("tagId") int tagId, int type) throws Exception;

    /**
     * 打印机关联菜品分类
     *
     * @param printerId
     * @param dishTagId
     * @throws SSException
     */
    public void updatePrinterIdByDishTagId(@Param("printerId") int printerId, @Param("dishTagId") int dishTagId) throws Exception;

    /**
     * 打印机取消所有关联菜品分类
     *
     * @param printerId
     * @throws SSException
     */
    public void updatePrinterIdByPrinterId(@Param("printerId") int printerId) throws Exception;

    /**
     * 查询关联是否已存在
     *
     * @param dishTagPrinter
     * @throws Exception
     */
    public int countPrinterDish(@Param("dishTagPrinter") DishTagPrinter dishTagPrinter) throws Exception;
}
