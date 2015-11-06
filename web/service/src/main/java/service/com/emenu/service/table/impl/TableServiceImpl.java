package com.emenu.service.table.impl;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.table.TableStateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.table.TableMapper;
import com.emenu.service.table.AreaService;
import com.emenu.service.table.QrCodeService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
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
    private QrCodeService qrCodeService;

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<TableDto> listAllTableDto() throws SSException {
        List<TableDto> tableDtoList = new ArrayList<TableDto>();
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listAll();

            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());
                    tableDtoList.add(tableDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableDtoList;
    }

    @Override
    public List<Table> listAll() throws SSException {
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableList;
    }

    @Override
    public List<TableDto> listTableDtoByAreaId(int areaId) throws SSException {
        //检查AreaID是否合法
        if (Assert.lessOrEqualZero(areaId)) {
            return null;
        }
        List<TableDto> tableDtoList = new ArrayList<TableDto>();
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByAreaId(areaId);
            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());
                    tableDtoList.add(tableDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableDtoList;
    }

    @Override
    public List<Table> listByAreaId(int areaId) throws SSException {
        //检查AreaID是否合法
        if (Assert.lessOrEqualZero(areaId)) {
            return null;
        }
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByAreaId(areaId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableList;
    }

    @Override
    public List<TableDto> listTableDtoByState(TableStateEnums state) throws SSException {
        //检查State是否合法(不小于0且不大于Deleted)
        if (Assert.lessZero(state.getId()) || state.getId() > TableStateEnums.Deleted.getId()) {
            return null;
        }
        List<TableDto> tableDtoList = new ArrayList<TableDto>();
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByState(state.getId());
            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());
                    tableDtoList.add(tableDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableDtoList;
    }

    @Override
    public List<Table> listByState(TableStateEnums state) throws SSException {
        //检查State是否合法(不小于0且不大于Deleted)
        if (Assert.lessZero(state.getId()) || state.getId() > TableStateEnums.Deleted.getId()) {
            return null;
        }
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByState(state.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableList;
    }

    @Override
    public List<TableDto> listTableDtoByAreaIdAndState(int areaId, TableStateEnums state) throws SSException {
        //检查State是否合法(不小于0且不大于Deleted)
        if (Assert.lessZero(state.getId()) || state.getId() > TableStateEnums.Deleted.getId()) {
            return null;
        }
        //检查AreaID是否合法
        if (Assert.lessOrEqualZero(areaId)) {
            return null;
        }
        List<TableDto> tableDtoList = new ArrayList<TableDto>();
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByAreaIdAndState(areaId, state.getId());
            if(!tableList.isEmpty()) {
                for(Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());
                    tableDtoList.add(tableDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableDtoList;
    }

    @Override
    public List<Table> listByAreaIdAndState(int areaId, TableStateEnums state) throws SSException {
        //检查State是否合法(不小于0且不大于Deleted)
        if (Assert.lessZero(state.getId()) || state.getId() > TableStateEnums.Deleted.getId()) {
            return null;
        }
        //检查AreaID是否合法
        if (Assert.lessOrEqualZero(areaId)) {
            return null;
        }
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByAreaIdAndState(areaId, state.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableList;
    }

    @Override
    public TableDto queryTableDtoById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
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
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return commonDao.queryById(Table.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public int queryStateById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return 0;
        }
        try {
            return tableMapper.queryStateById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public void checkStateById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            int state = queryStateById(id);
            if (state != TableStateEnums.Enabled.getId() && state != TableStateEnums.Disabled.getId()) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public Table newTable(Table table, HttpServletRequest request) throws SSException {
        try {
            //判断AreaId是否存在
            if (Assert.isNull(areaService.queryById(table.getAreaId()))) {
                throw SSException.get(EmenuException.AreaNotExist);
            }
            //判断是否重名
            if (checkNameIsExist(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //判断名称是否为空
            if (Assert.isNull(table.getName())) {
                throw SSException.get(EmenuException.TableNameIsNull);
            }
            //将状态设为"可用"
            table.setState(TableStateEnums.Enabled.getId());
            commonDao.insert(table);
            //先插入以得到ID，再设置二维码地址
            updateQrCode(table.getId(), qrCodeService.newQrCode(table.getId(), null, request));
            return table;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertTableFail, e);
        }
    }

    @Override
    public boolean checkNameIsExist(String name) throws SSException {
        //检查Name是否为空
        if (Assert.isNull(name)) {
            return false;
        }
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
    public void updateTable(Integer id, Table table) throws SSException {
        try {
            int state = queryStateById(table.getId());
            //仅当餐台状态为停用及可用时可以修改餐台
            if((state != TableStateEnums.Disabled.getId() && state != TableStateEnums.Enabled.getId())) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
            //判断AreaId是否合法
            if (Assert.lessOrEqualZero(table.getAreaId())){
                throw SSException.get(EmenuException.AreaNotExist);
            }
            //若未传来ID，则为增加页，直接判断该名称是否在数据库中已存在
            if(id == null && checkNameIsExist(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //若传来ID，则为编辑页
            //判断传来的Name与相应ID在数据库中对应的名称是否一致，若不一致，再判断该名称是否在数据库中已存在
            if (id != null && !table.getName().equals(queryById(id).getName())){
                throw SSException.get(EmenuException.TableNameExist);
            }
            //判断是否为空
            if (Assert.isNull(table.getName())) {
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
    public void updateQrCode(int id, String qrCodePath) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        //检查QrCodePath是否合法
        if (Assert.isNull(qrCodePath)) {
            return ;
        }
        try {
            tableMapper.updateQrCode(id, qrCodePath);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateState(int id, int state) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        //检查State是否合法
        if (Assert.lessZero(state)) {
            return ;
        }
        try {
            //获取修改前的State值
            int nowState = tableMapper.queryStateById(id);
            //仅当修改前餐台状态为停用及可用时可以修改餐台
            if((nowState != TableStateEnums.Disabled.getId() && nowState != TableStateEnums.Enabled.getId())) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
            tableMapper.updateState(id, state);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
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
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delByIds(List<Integer> idList) throws SSException {
        //检查IDList是否合法
        if (Assert.isNull(idList)) {
            return ;
        }
        try {
            if (idList != null) {
                //先判断是否全部餐台均可以删除，若不能，报错
                for (int i : idList) {
                    if ((queryStateById(i) != TableStateEnums.Enabled.getId()) &&
                        (queryStateById(i) != TableStateEnums.Disabled.getId())) {
                        throw SSException.get(EmenuException.TableHasUsed);
                    }
                }
                //第二遍循环，逐个删除
                for (int i : idList) {
                    delById(i);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTableFail, e);
        }
    }

    @Override
    public int countByAreaId(int areaId) throws SSException {
        //检查AreaID是否合法
        if (Assert.lessOrEqualZero(areaId)) {
            return 0;
        }
        try {
            return tableMapper.countByAreaId(areaId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }
}