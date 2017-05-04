package com.emenu.mapper.stock;

import com.emenu.common.dto.stock.StockItemSearchDto;
import com.emenu.common.entity.stock.StockItem;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StockItemMapper
 *
 * @author pengpeng
 * @time 2017/3/6 9:20
 */
public interface StockItemMapper {

    /**
     * 根据Name查询物品个数
     *
     * @param name
     * @return
     * @throws SSException
     */
    public int countByName(@Param("name") String name) throws SSException;

    /**
     * 根据分类Id查询物品个数
     * @param id
     * @return
     * @throws SSException
     */
    public int countByTagId(@Param("id") int id) throws SSException;


    /**
     * 修改
     * @param itemId
     * @param status
     * @throws SSException
     */
    public void updateStockItemStatusById(@Param("itemId") int itemId, @Param("status") int status) throws SSException;

    /**
     * 根据SearchDto列举Item
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<StockItem> listBySearchDto(@Param("searchDto") StockItemSearchDto searchDto) throws SSException;

    /**
     * 获取全部库存物品
     *
     * @return
     * @throws SSException
     */
    public List<StockItem> listAll() throws SSException;

    /**
     * 分页显示库存物品列表
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<StockItem> listByPage(@Param("offset") Integer offset,@Param("pageSize") Integer pageSize) throws SSException;

    /**
     * 统计物品数量
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;
}
