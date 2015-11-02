package com.emenu.service.table.impl;

import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.table.AreaMapper;
import com.emenu.service.table.AreaService;
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
 * AreaServiceImpl
 *
 * @author: yangch
 * @time: 2015/10/23 10:00
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService{
    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private TableService tableService;

    @Override
    public List<Area> listAllArea() throws SSException {
        try {
            return areaMapper.listAllArea();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryAreaFail, e);
        }
    }

    @Override
    public Area queryAreaById(int id) throws SSException {
        try {
            return commonDao.queryById(Area.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryAreaFail, e);
        }
    }

    @Override
    public int queryAreaStateById(int id) throws SSException {
        try {
            return areaMapper.queryAreaStateById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryAreaFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public Area newArea(Area area) throws SSException {
        try {
            //判断是否重名
            if (checkAreaName(area.getName())) {
                throw SSException.get(EmenuException.AreaNameExist);
            }
            //判断是否为空
            if (area.getName() == null || "".equals(area.getName())) {
                throw SSException.get(EmenuException.AreaNameExist);
            }
            //将状态设为可用
            area.setState(1);
            return commonDao.insert(area);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertAreaFail, e);
        }
    }

    @Override
    public boolean checkAreaName(String name) throws SSException {
        try {
            if (areaMapper.checkAreaName(name) > 0) {
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
    public void updateArea(Area area) throws SSException {
        try {
            //判断是否重名
            if (checkAreaName(area.getName())) {
                throw SSException.get(EmenuException.AreaNameExist);
            }

            commonDao.update(area);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateAreaFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delAreaById(int id) throws SSException {
        try {
            //判断该区域内是否有餐台，若有则不能删除
            if (tableService.countTableNumByAreaId(id) != 0) {
                throw SSException.get(EmenuException.AreaHasTableExist);
            }

            areaMapper.delTableById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteAreaFail, e);
        }
    }

    @Override
    public List<AreaDto> listAreaAndTable() throws SSException {
        List<AreaDto> areaDtoList = new ArrayList<AreaDto>();
        try {
            List<Area> areaList = listAllArea();
            for(Area area : areaList) {
                List<Table> tableList = tableService.listTableItselfByAreaId(area.getId());
                if(tableList != null && tableList.size() != 0){
                    AreaDto areaDto = new AreaDto();
                    areaDto.setArea(area);
                    areaDto.setTableList(tableList);
                    areaDtoList.add(areaDto);
                }
            }
            return areaDtoList;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }
}