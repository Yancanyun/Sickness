package com.emenu.service.table.impl;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.table.TableStateEnums;
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
            List<Table> tableList = tableMapper.listAll();

            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());
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
    public List<Table> listAll() throws SSException {
        try {
            return tableMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public List<TableDto> listTableDtoByAreaId(int areaId) throws SSException {
        try {
            List<TableDto> tableDtoList = new ArrayList<TableDto>();
            List<Table> tableList = tableMapper.listByAreaId(areaId);

            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());
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
    public List<Table> listByAreaId(int areaId) throws SSException {
        try {
            return tableMapper.listByAreaId(areaId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public List<TableDto> listTableDtoByState(int state) throws SSException {
        try {
            List<TableDto> tableDtoList = new ArrayList<TableDto>();
            List<Table> tableList = tableMapper.listByState(state);

            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());
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
    public List<Table> listByState(int state) throws SSException {
        try {
            return tableMapper.listByState(state);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public List<TableDto> listTableDtoByAreaIdAndState(int areaId, int state) throws SSException {
        try {
            List<TableDto> tableDtoList = new ArrayList<TableDto>();
            List<Table> tableList = tableMapper.listByAreaIdAndState(areaId, state);

            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());
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
    public List<Table> listByAreaIdAndState(int areaId, int state) throws SSException {
        try {
            return tableMapper.listByAreaIdAndState(areaId, state);
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
            tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());

            return tableDto;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public Table queryById(int id) throws SSException {
        try {
            return commonDao.queryById(Table.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public int queryStateById(int id) throws SSException {
        try {
            return tableMapper.queryStateById(id);
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
            if (areaService.queryById(table.getAreaId()) == null) {
                throw SSException.get(EmenuException.AreaNotExist);
            }
            //判断是否重名
            if (checkNameIsExist(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //判断是否为空
            if (table.getName() == null || "".equals(table.getName())) {
                throw SSException.get(EmenuException.TableNameIsNull);
            }

            //将状态设为"可用"

            table.setState(TableStateEnums.Enabled.getId());
            //TODO: 设置二维码地址
//            table.setQrCodePath(qrCodeService.createTDC(table.getId(), null, request));
            return commonDao.insert(table);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertTableFail, e);
        }
    }

    @Override
    public boolean checkNameIsExist(String name) throws SSException {
        try {
            if (tableMapper.countByName(name) > 0) {
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
            int state = queryStateById(table.getId());
            //仅当餐台状态为停用及可用时可以修改餐台
            if((state != TableStateEnums.Disabled.getId() && state != TableStateEnums.Enabled.getId())) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
            //判断AreaId是否存在
            //先判断是否传来了AreaId值，若未传来则表明未修改AreaId，直接跳过该段
            if (table.getAreaId() != null && areaService.queryById(table.getAreaId()) == null) {
                throw SSException.get(EmenuException.AreaNotExist);
            }
            //判断是否重名
            if (checkNameIsExist(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //判断是否为空
            if (table.getName() == null || "".equals(table.getName())) {
                throw SSException.get(EmenuException.TableNameIsNull);
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
            if (checkNameIsExist(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //判断是否为空
            if (table.getName() == null || "".equals(table.getName())) {
                throw SSException.get(EmenuException.TableNameIsNull);
            }

            commonDao.update(table);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delById(int id) throws SSException {
        try {
            int state = queryStateById(id);
            //仅当餐台状态为停用及可用时可以删除餐台
            if((state != TableStateEnums.Disabled.getId() && state != TableStateEnums.Enabled.getId())) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
            //将状态设为"删除"
            tableMapper.updateState(id, TableStateEnums.Deleted.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTableFail, e);
        }
    }

    @Override
    public int countByAreaId(int areaId) throws SSException {
        try {
            return tableMapper.countByAreaId(areaId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }
}