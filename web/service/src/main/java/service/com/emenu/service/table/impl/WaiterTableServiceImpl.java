package com.emenu.service.table.impl;

import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;
import com.emenu.common.entity.table.WaiterTable;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.table.WaiterTableMapper;
import com.emenu.service.table.AreaService;
import com.emenu.service.table.TableService;
import com.emenu.service.table.WaiterTableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private WaiterTableMapper waiterTableMapper;

    @Autowired
    private TableService tableService;


    @Autowired
    private AreaService areaService;

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



    @Override
    public List<Integer> queryByPartyId(int partyId) throws SSException {
        try {
            List<Integer> tableIdList = Collections.emptyList();
            if(Assert.lessOrEqualZero(partyId)){
                throw SSException.get(EmenuException.PartyIdError);
            }

            tableIdList = waiterTableMapper.queryByPartyId(partyId);

            return tableIdList;

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertWaiterTableFail, e);
        }
    }

    @Override
    public List<AreaDto> queryAreaDtoByPartyId(int partyId) throws SSException {
        try {
            // 查询服务员负责的餐台的id，查t_wariter_table
            List<Integer> tableIdList = queryByPartyId(partyId);
            List<Table> tableList = new ArrayList<Table>();

            //查询服务员负责餐台的详细信息
            for(int tid : tableIdList) {
                tableList.add(tableService.queryById(tid));
            }
            // 把服务员负责的餐台封装成AreaDto
            List<AreaDto> areaDtoList = new ArrayList<AreaDto>();
            //返回所有区域
            List<Area> areaList = areaService.listAll();
            //获取所有区域和每一区域下的所有餐台
            for(Area area : areaList) {
                //areaDto包括已tablelist和一个area
                AreaDto areaDto = new AreaDto();
                List<Table> tables = new ArrayList<Table>();
                for(Table table : tableList) {
                    if(table.getAreaId().equals(area.getId())) {
                        tables.add(table);
                    }
                }
                if(tables!=null && tables.size()!=0) {
                    //把区域和对应的餐台加入到areaDto里面
                    areaDto.setArea(area);
                    areaDto.setTableList(tables);
                    areaDtoList.add(areaDto);
                }
            }

            return areaDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryWaiterTableFail, e);
        }
      /*  */
    }

    @Override
    public List<AreaDto> queryAreaDto() throws SSException {
        try {
            //获取所有餐桌
            List<Table> tableList = new ArrayList<Table>();
            tableList = tableService.listAll();

            // 将餐桌按区域分类
            List<AreaDto> areaDtoList = new ArrayList<AreaDto>();
            //返回所有区域
            List<Area> areaList = areaService.listAll();
            //获取所有区域和每一区域下的所有餐台
            for(Area area : areaList) {
                //areaDto包括已tablelist和一个area
                AreaDto areaDto = new AreaDto();
                List<Table> tables = new ArrayList<Table>();
                for(Table table : tableList) {
                    if(table.getAreaId().equals(area.getId())) {
                        tables.add(table);
                    }
                }
                if(tables!=null && tables.size()!=0) {
                    //把区域和对应的餐台加入到areaDto里面
                    areaDto.setArea(area);
                    areaDto.setTableList(tables);
                    areaDtoList.add(areaDto);
                }
            }

            return areaDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryWaiterTableFail, e);
        }
    }
}
