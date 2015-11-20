package com.emenu.service.printer;

import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.printer.DishTagPrinter;
import com.emenu.common.entity.printer.Printer;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * DishTagPrinterService
 *
 * @author Wang Liming
 * @date 2015/11/20 14:56
 */
public interface DishTagPrinterService {

    /**
     * 根据菜品分类id查询关联打印机
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Printer queryByTagId(int id) throws SSException;

    /**
     * 根据打印机id查询关联菜品分类
     *
     * @param id
     * @return
     * @throws SSException
     */
    public List<Tag> listTagById(int id) throws SSException;

    /**
     * 根据打印机id查询关联菜品
     *
     * @param id
     * @return
     * @throws SSException
     */
    public List<String> listDishNameById(int id) throws SSException;

    /**
     * 查询没有关联打印机的菜品分类
     *
     * @return
     * @throws SSException
     */
    public List<Tag> listAvailableDishTag() throws SSException;

    /**
     * 新增菜品与打印机关联
     * @param dishTagPrinter
     * @return
     * @throws SSException
     */
    public void newPrinterDish(DishTagPrinter dishTagPrinter) throws SSException;

    /**
     * 修改菜品与打印机关联_根据菜品Id修改
     * @param dishTagPrinter
     * @throws SSException
     */
    public void updatePrinterDish(DishTagPrinter dishTagPrinter) throws SSException;

    /**
     * 删除菜品与打印机关联
     * @param tagId
     * @throws SSException
     */
    public void delPrinterDish(int tagId) throws SSException;

    /**
     * 打印机关联菜品分类
     *
     * @param printerId
     * @param dishTagId
     * @throws SSException
     */
    public void bindDishTag(int printerId, int dishTagId) throws SSException;

    /**
     * 打印机取消所有关联菜品分类
     *
     * @param printerId
     * @throws SSException
     */
    public void unBindAllDishTag(int printerId) throws SSException;
}
