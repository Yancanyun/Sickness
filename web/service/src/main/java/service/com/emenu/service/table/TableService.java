package com.emenu.service.table;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.table.TableStateEnums;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletRequest;
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
    public List<Table> listAll() throws SSException;

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
    public List<Table> listByAreaId(int areaId) throws SSException;

    /**
     * 根据状态查询餐台（包含区域表中的信息）
     * @param state
     * @return List<TableDto>
     * @throws SSException
     */
    public List<TableDto> listTableDtoByState(TableStateEnums state) throws SSException;

    /**
     * 根据状态查询餐台（仅餐台表本身的信息）
     * @param state
     * @return List<Table>
     * @throws SSException
     */
    public List<Table> listByState(TableStateEnums state) throws SSException;

    /**
     * 根据区域及状态查询餐台（包含区域表中的信息）
     * @param areaId
     * @param state
     * @return List<TableDto>
     * @throws SSException
     */
    public List<TableDto> listTableDtoByAreaIdAndState(int areaId, TableStateEnums state) throws SSException;

    /**
     * 根据区域及状态查询餐台（仅餐台表本身的信息）
     * @param areaId
     * @param state
     * @return List<Table>
     * @throws SSException
     */
    public List<Table> listByAreaIdAndState(int areaId, TableStateEnums state) throws SSException;

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
    public Table queryById(int id) throws SSException;

    /**
     * 根据ID查询餐台状态
     * @param id
     * @return int : 0、停用；1、可用；2、占用已结账；3、占用未结账4、已并桌；5、已预订；6、已删除
     * @throws SSException
     */
    public int queryStateById(int id) throws SSException;

    /**
     * 添加餐台
     * @param table
     * @return Table
     * @throws SSException
     */
    public Table newTable(Table table, HttpServletRequest request) throws SSException;

    /**
     * 检查是否有重复的餐台名称存在
     * @param name
     * @return boolean : true: 存在；false：不存在
     * @throws SSException
     */
    public boolean checkNameIsExist(String name) throws SSException;

    /**
     * 修改餐台
     * @param table
     * @throws SSException
     */
    public void updateTable(Table table) throws SSException;

    /**
     * 修改餐台二维码信息
     *
     * @param id
     * @param qrCodePath
     * @throws SSException
     */
    public void updateQrCode(int id, String qrCodePath) throws SSException;

    /**
     * 删除餐台
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据区域ID查询区域内餐台的数量
     * @param areaId
     * @return int
     * @throws SSException
     */
    public int countByAreaId(int areaId) throws SSException;
}
