package com.emenu.service.table;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.table.TableStatusEnums;
import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
     * @param status
     * @return List<TableDto>
     * @throws SSException
     */
    public List<TableDto> listTableDtoByStatus(TableStatusEnums status) throws SSException;

    /**
     * 根据状态查询餐台（仅餐台表本身的信息）
     * @param status
     * @return List<Table>
     * @throws SSException
     */
    public List<Table> listByStatus(TableStatusEnums status) throws SSException;

    /**
     * 根据区域及状态查询餐台（包含区域表中的信息）
     * @param areaId
     * @param status
     * @return List<TableDto>
     * @throws SSException
     */
    public List<TableDto> listTableDtoByAreaIdAndStatus(int areaId, TableStatusEnums status) throws SSException;

    /**
     * 根据区域及状态查询餐台（仅餐台表本身的信息）
     * @param areaId
     * @param status
     * @return List<Table>
     * @throws SSException
     */
    public List<Table> listByAreaIdAndStatus(int areaId, TableStatusEnums status) throws SSException;

    /**
     * 根据ID查询餐台（包含区域表、餐段表中的信息）
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
     * @return int : 0、停用；1、可用；2、占用已结账；3、占用未结账；4、已并桌；5、已预订；6、已删除
     * @throws SSException
     */
    public int queryStatusById(int id) throws SSException;

    /**
     * 根据ID检查餐台是否可修改
     * @param id
     * @throws SSException
     */
    public void checkStatusById(int id) throws SSException;

    /**
     * 添加餐台
     * @param tableDto
     * @return Table
     * @throws SSException
     */
    public Table newTable(TableDto tableDto, HttpServletRequest request) throws SSException;

    /**
     * 检查是否有重复的餐台名称存在
     * @param name
     * @return boolean : true: 存在；false：不存在
     * @throws SSException
     */
    public boolean checkNameIsExist(String name) throws SSException;

    /**
     * 修改餐台
     * @param id
     * @param tableDto
     * @throws SSException
     */
    public void updateTable(Integer id, TableDto tableDto) throws SSException;

    /**
     * 强制修改餐台
     * @param id
     * @param tableDto
     * @throws SSException
     */
    public void forceUpdateTable(Integer id, TableDto tableDto) throws SSException;

    /**
     * 修改餐台二维码
     * @param id
     * @param qrCodePath
     * @throws SSException
     */
    public void updateQrCode(int id, String qrCodePath) throws SSException;

    /**
     * 修改餐台状态
     * @param id
     * @param status
     * @throws SSException
     */
    public void updateStatus(int id, int status) throws SSException;

    /**
     * 删除单个餐台
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 删除餐台
     * @param idList
     * @throws SSException
     */
    public void delByIds(List<Integer> idList) throws SSException;

    /**
     * 根据区域ID查询区域内餐台的数量
     * @param areaId
     * @return int
     * @throws SSException
     */
    public int countByAreaId(int areaId) throws SSException;

    /**
     * 开台
     * @param id
     * @param personNum
     * @throws SSException
     */
    public void openTable(int id, int personNum) throws SSException;

    /**
     * 换台
     * @param oldTableId
     * @param newTableId
     * @throws SSException
     */
    public void changeTable(int oldTableId, int newTableId) throws SSException;

    /**
     * 清台
     * @param id
     * @throws SSException
     */
    public void cleanTable(int id) throws SSException;

    /**
     * 根据搜索关键字餐台（仅餐台表本身的信息）
     * @param keywords
     * @return Table
     * @throws SSException
     */
    public Table queryByKeywords(String keywords) throws SSException;

    /**
     * 根据搜索关键字餐台（包含区域表、餐段表中的信息）
     * @param keywords
     * @return TableDto
     * @throws SSException
     */
    public TableDto queryTableDtoByKeywords(String keywords) throws SSException;
}
