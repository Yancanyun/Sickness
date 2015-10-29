package com.emenu.service.table;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Table;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * TableService
 *
 * @author: yangch
 * @time: 2015/10/23 10:05
 */
public interface TableService {
    /**
     * 查询全部餐台（包含区域表中的信息）
     * @return List<TableDto>
     * @throws SSException
     */
    public List<TableDto> listAllTableDto() throws SSException;

    /**
     * 查询全部餐台（仅餐台表本身的信息）
     * @return List<Table>
     * @throws SSException
     */
    public List<Table> listAllTableItself() throws SSException;

    /**
     * 根据区域ID查询餐台（包含区域表中的信息）
     * @param areaId
     * @return List<TableDto>
     * @throws SSException
     */
    public List<TableDto> listTableDtoByAreaId(int areaId) throws SSException;

    /**
     * 根据区域ID查询餐台（仅餐台表本身的信息）
     * @param areaId
     * @return List<Table>
     * @throws SSException
     */
    public List<Table> listTableItselfByAreaId(int areaId) throws SSException;

    /**
     * 根据状态查询餐台（包含区域表中的信息）
     * @param state
     * @return List<TableDto>
     * @throws SSException
     */
    public List<TableDto> listTableDtoByState(int state) throws SSException;

    /**
     * 根据状态查询餐台（仅餐台表本身的信息）
     * @param state
     * @return List<Table>
     * @throws SSException
     */
    public List<Table> listTableItselfByState(int state) throws SSException;

    /**
     * 根据区域及状态查询餐台（包含区域表中的信息）
     * @param areaId
     * @param state
     * @return List<TableDto>
     * @throws SSException
     */
    public List<TableDto> listTableDtoByAreaIdAndState(int areaId, int state) throws SSException;

    /**
     * 根据区域及状态查询餐台（仅餐台表本身的信息）
     * @param areaId
     * @param state
     * @return List<Table>
     * @throws SSException
     */
    public List<Table> listTableItselfByAreaIdAndState(int areaId, int state) throws SSException;

    /**
     * 根据ID查询餐台（包含区域表中的信息）
     * @param id
     * @return TableDto
     * @throws SSException
     */
    public TableDto queryTableDtoById(int id) throws SSException;

    /**
     * 根据ID查询餐台（仅餐台表本身的信息）
     * @param id
     * @return Table
     * @throws SSException
     */
    public Table queryTableItselfById(int id) throws SSException;

    /**
     * 根据ID查询餐台状态
     * @param id
     * @return int : 0、停用；1、可用；2、占用已结账；3、占用未结账4、已并桌；5、已预订；6、已删除
     * @throws SSException
     */
    public int queryTableStateById(int id) throws SSException;

    /**
     * 添加餐台
     * @param table
     * @return Table
     * @throws SSException
     */
    public Table newTable(Table table) throws SSException;

    /**
     * 检查是否有重复的餐台名称存在
     * @param name
     * @return boolean : true: 存在；false：不存在
     * @throws SSException
     */
    public boolean checkTableName(String name) throws SSException;

    /**
     * 修改餐台
     * @param table
     * @throws SSException
     */
    public void updateTable(Table table) throws SSException;

    /**
     * 强制修改餐台(忽略餐台当前状态，仅生成二维码时使用)
     * @param table
     * @throws SSException
     */
    public void updateTableForce(Table table) throws SSException;

    /**
     * 删除餐台
     * @param id
     * @throws SSException
     */
    public void delTableById(int id) throws SSException;

    /**
     * 根据区域ID查询区域内餐台的数量
     * @param areaId
     * @return int
     * @throws SSException
     */
    public int countTableNumByAreaId(int areaId) throws SSException;
}
