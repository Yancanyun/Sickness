package com.emenu.mapper.printer;

import com.emenu.common.entity.dish.tag.Tag;
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

    /**
     * 根据菜品分类id查询关联打印机
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Printer queryByTagId(int id) throws Exception;

    /**
     * 根据打印机id查询关联菜品分类
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<Tag> queryTagById(int id) throws Exception;
}
