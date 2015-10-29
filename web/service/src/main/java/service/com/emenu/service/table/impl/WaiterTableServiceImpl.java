package com.emenu.service.table.impl;

import com.emenu.common.entity.table.WaiterTable;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.table.WaiterTableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozl
 * @date 2015/10/27
 * @time 16:32
 */
@Service("waiterTableService")
public class WaiterTableServiceImpl implements WaiterTableService {


    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;//core包

    @Override
    public void insertWaiterTable(List<WaiterTable> waiterTables) throws SSException {
        try {
            if(waiterTables!=null){
                commonDao.insertAll(waiterTables);
                // 删除缓存中该服务员对应的桌的信息
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertWaiterTableFail, e);
        }
    }
}
