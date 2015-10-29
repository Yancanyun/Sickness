package com.emenu.service.table.impl;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.table.TableEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.table.TableMapper;
import com.emenu.service.table.AreaService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * TableServiceImpl
 *
 * @author: yangch
 * @time: 2015/10/23 10:47
 */
@Service("tableService")
public class TableServiceImpl implements TableService{
    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private AreaService areaService;

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<TableDto> listAllTableDto() throws SSException {
        try {
            List<TableDto> tableDtoList = new ArrayList<TableDto>();
            List<Table> tableList = tableMapper.listAllTableItself();

            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryAreaById(table.getAreaId()).getName());
                    tableDtoList.add(tableDto);
                }
            }
            return tableDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public List<Table> listAllTableItself() throws SSException {
        try {
            return tableMapper.listAllTableItself();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public List<TableDto> listTableDtoByAreaId(int areaId) throws SSException {
        try {
            List<TableDto> tableDtoList = new ArrayList<TableDto>();
            List<Table> tableList = tableMapper.listTableItselfByAreaId(areaId);

            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryAreaById(table.getAreaId()).getName());
                    tableDtoList.add(tableDto);
                }
            }
            return tableDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public List<Table> listTableItselfByAreaId(int areaId) throws SSException {
        try {
            return tableMapper.listTableItselfByAreaId(areaId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public List<TableDto> listTableDtoByState(int state) throws SSException {
        try {
            List<TableDto> tableDtoList = new ArrayList<TableDto>();
            List<Table> tableList = tableMapper.listTableItselfByState(state);

            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryAreaById(table.getAreaId()).getName());
                    tableDtoList.add(tableDto);
                }
            }
            return tableDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public List<Table> listTableItselfByState(int state) throws SSException {
        try {
            return tableMapper.listTableItselfByState(state);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public List<TableDto> listTableDtoByAreaIdAndState(int areaId, int state) throws SSException {
        try {
            List<TableDto> tableDtoList = new ArrayList<TableDto>();
            List<Table> tableList = tableMapper.listTableItselfByAreaIdAndState(areaId, state);

            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryAreaById(table.getAreaId()).getName());
                    tableDtoList.add(tableDto);
                }
            }
            return tableDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public List<Table> listTableItselfByAreaIdAndState(int areaId, int state) throws SSException {
        try {
            return tableMapper.listTableItselfByAreaIdAndState(areaId, state);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public TableDto queryTableDtoById(int id) throws SSException {
        try {
            TableDto tableDto = new TableDto();
            Table table = commonDao.queryById(Table.class, id);

            tableDto.setTable(table);
            tableDto.setAreaName(areaService.queryAreaById(table.getAreaId()).getName());

            return tableDto;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public Table queryTableItselfById(int id) throws SSException {
        try {
            return commonDao.queryById(Table.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public int queryTableStateById(int id) throws SSException {
        try {
            return tableMapper.queryTableStateById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public Table newTable(Table table) throws SSException {
        try {
            //判断AreaId是否存在
            if (areaService.queryAreaById(table.getAreaId()) == null) {
                throw SSException.get(EmenuException.AreaNotExist);
            }
            //判断是否重名
            if (checkTableName(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }

            //将状态设为可用
            table.setState(TableEnums.Enabled.getId());
            //TODO: 设置二维码地址
            //table.setQrcodePath(qrCodeService.createTDC(table.getId(), null, request));
            return commonDao.insert(table);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertTableFail, e);
        }
    }

    @Override
    public boolean checkTableName(String name) throws SSException {
        try {
            if (tableMapper.checkTableName(name) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateTable(Table table) throws SSException {
        try {
            int state = queryTableStateById(table.getId());
            //仅当餐台状态为停用及可用时可以修改餐台
            if((state != TableEnums.Disabled.getId() && state != TableEnums.Enabled.getId())) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
            //判断AreaId是否存在
            //先判断是否传来了AreaId值，若未传来则表明未修改AreaId，直接跳过该段
            if (table.getAreaId() != null && areaService.queryAreaById(table.getAreaId()) == null) {
                throw SSException.get(EmenuException.AreaNotExist);
            }
            //判断是否重名
            if (checkTableName(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }

            commonDao.update(table);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateTableForce(Table table) throws SSException {
        try {
            //判断是否重名
            if (checkTableName(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }

            commonDao.update(table);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delTableById(int id) throws SSException {
        try {
            int state = queryTableStateById(id);
            //仅当餐台状态为停用及可用时可以删除餐台
            if((state != TableEnums.Disabled.getId() && state != TableEnums.Enabled.getId())) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
            tableMapper.delTableById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTableFail, e);
        }
    }

    @Override
    public int countTableNumByAreaId(int areaId) throws SSException {
        try {
            return tableMapper.countTableNumByAreaId(areaId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }
}