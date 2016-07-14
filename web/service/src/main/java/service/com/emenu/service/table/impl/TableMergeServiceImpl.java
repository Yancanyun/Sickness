package com.emenu.service.table.impl;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Table;
import com.emenu.common.entity.table.TableMerge;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.table.TableMergeMapper;
import com.emenu.service.table.TableMergeService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * TableMergeServiceImpl
 *
 * @author: yangch
 * @time: 2016/6/23 9:51
 */
@Service("tableMergeService")
public class TableMergeServiceImpl implements TableMergeService {
    @Autowired
    private TableMergeMapper tableMergeMapper;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private TableService tableService;

    @Override
    public List<TableMerge> listByMergeId(int mergeId) throws SSException {
        try {
            if (Assert.lessOrEqualZero(mergeId)) {
                throw SSException.get(EmenuException.MergeIdError);
            }

            return tableMergeMapper.listByMergeId(mergeId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMergeTableFail, e);
        }
    }

    @Override
    public TableMerge queryByTableId(int tableId) throws SSException {
        try {
            if (Assert.lessOrEqualZero(tableId)) {
                throw SSException.get(EmenuException.TableIdError);
            }

            return tableMergeMapper.queryByTableId(tableId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMergeTableFail, e);
        }
    }

    @Override
    public int queryLastMergeId() throws SSException {
        try {
            if (Assert.lessOrEqualZero(tableMergeMapper.countTableMerge())) {
                return 0;
            }

            return tableMergeMapper.queryLastMergeId();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMergeTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public List<TableMerge> mergeTable(List<Integer> tableIdList) throws SSException {
        try {
            if (Assert.isNull(tableIdList)) {
                throw SSException.get(EmenuException.TableIdError);
            }
            if (tableIdList.size() < 2) {
                throw SSException.get(EmenuException.MergeTableNumLessThanTwo);
            }

            int mergeId = 0;

            // 先找是否存在已经有被并台的餐台
            for (int tableId : tableIdList) {
                if (Assert.lessOrEqualZero(tableId)) {
                    throw SSException.get(EmenuException.TableIdError);
                }
                if (Assert.isNotNull(queryByTableId(tableId))) {
                    mergeId = queryByTableId(tableId).getMergeId();
                    break;
                }
            }

            // 若没找到已经被并台的餐台，则新建MergeId
            if (mergeId == 0) {
                mergeId = queryLastMergeId() + 1;
            }

            // 新建并台信息
            for (int tableId : tableIdList) {
                // 若已经存在该餐台的并台信息，则无需新增
                if (Assert.isNull(queryByTableId(tableId))) {
                    TableDto tableDto = tableService.queryTableDtoById(tableId);

                    TableMerge tableMerge = new TableMerge();
                    tableMerge.setMergeId(mergeId);
                    tableMerge.setTableId(tableId);
                    tableMerge.setLastTableStatus(tableDto.getTable().getStatus());

                    commonDao.insert(tableMerge);

                    // 把餐台状态改为"已并桌"
                    tableDto.setStatus(TableStatusEnums.Merged.getType());
                    Table table = tableDto.getTable();
                    table.setStatus(TableStatusEnums.Merged.getId());
                    tableDto.setTable(table);
                    tableService.forceUpdateTable(tableId, tableDto);
                }
            }

            List<TableMerge> tableMergeList = listByMergeId(mergeId);
            return tableMergeList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.MergeTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delTableMerge(int tableId) throws SSException {
        try {
            if (Assert.lessOrEqualZero(tableId)) {
                throw SSException.get(EmenuException.TableIdError);
            }

            // 把餐台状态改回原来的状态
            TableMerge tableMerge = queryByTableId(tableId);
            TableDto tableDto = tableService.queryTableDtoById(tableId);
            tableDto.setStatus(TableStatusEnums.valueOf(tableMerge.getLastTableStatus()).getType());
            Table table = tableDto.getTable();
            table.setStatus(TableStatusEnums.valueOf(tableMerge.getLastTableStatus()).getId());
            tableDto.setTable(table);
            tableService.forceUpdateTable(tableId, tableDto);


            tableMergeMapper.delTableMergeByTableId(tableId);

            // 若删除并台信息后，此并台剩余少于2个餐台，则把另一个并台信息也删除掉
            List<TableMerge> tableMergeList = new ArrayList<TableMerge>();
            int mergeId = tableMerge.getMergeId();
            tableMergeList = listByMergeId(mergeId);
            if (tableMergeList.size() < 2) {
                for (TableMerge tableMerge1 : tableMergeList) {
                    delTableMerge(tableMerge1.getTableId());
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.MergeTableFail, e);
        }
    }

    @Override
    public List<Table> listOtherTableByTableId(int tableId) throws SSException {
        List<Table> tableList = new ArrayList<Table>();

        try {
            TableMerge tableMerge = queryByTableId(tableId);
            if (tableMerge != null) {
                List<TableMerge> tableMergeList = listByMergeId(tableMerge.getMergeId());
                for (TableMerge tm : tableMergeList) {
                    if (tm.getTableId() != tableId) {
                        tableList.add(tableService.queryById(tm.getTableId()));
                    }
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryMergeTableFail, e);
        }

        return tableList;
    }
}