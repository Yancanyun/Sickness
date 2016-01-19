package com.emenu.service.table.impl;

import com.emenu.common.entity.table.TableMealPeriod;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.table.TableMealPeriodMapper;
import com.emenu.service.table.TableMealPeriodService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TableMealPeriodServiceImpl
 *
 * @author: yangch
 * @time: 2015/11/9 14:07
 */
@Service("tableMealPeriodService")
public class TableMealPeriodServiceImpl implements TableMealPeriodService{
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private TableMealPeriodMapper tableMealPeriodMapper;

    @Override
    public List<Integer> listMealPeriodIdByTableId(int tableId) throws SSException {
        //检查餐台ID是否合法
        if (Assert.lessOrEqualZero(tableId)) {
            return null;
        }
        try {
            return tableMealPeriodMapper.listByTableId(tableId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableMealPeriodFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void newTableMealPeriod (List<TableMealPeriod> tableMealPeriodList) throws SSException {
        try {
            if(tableMealPeriodList != null){
                commonDao.insertAll(tableMealPeriodList);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertTableMealPeriodFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateTableMealPeriod (List<TableMealPeriod> tableMealPeriodList) throws SSException {
        try {
            if(tableMealPeriodList != null){
                //先从删除该餐台ID下的所有餐段
                delByTableId(tableMealPeriodList.get(0).getTableId());
                //再插入选择的项
                newTableMealPeriod(tableMealPeriodList);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableMealPeriodFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delByTableId (int tableId) throws SSException {
        //检查TableID是否合法
        if (Assert.lessOrEqualZero(tableId)) {
            return ;
        }
        try {
            tableMealPeriodMapper.delByTableId(tableId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTableMealPeriodFail, e);
        }
    }
}
