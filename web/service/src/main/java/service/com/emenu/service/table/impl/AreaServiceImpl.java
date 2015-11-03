package com.emenu.service.table.impl;

import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.party.security.SecurityGroup;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.table.AreaStateEnums;
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
import java.util.Collections;
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
    public List<Area> listAll() throws SSException {
        List<Area> areaList = Collections.emptyList();
        try {
            areaList = areaMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryAreaFail, e);
        }
        return areaList;
    }

    @Override
    public Area queryById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return commonDao.queryById(Area.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryAreaFail, e);
        }
    }

    @Override
    public int queryStateById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return 0;
        }
        try {
            return areaMapper.queryStateById(id);
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
            if (checkNameIsExist(area.getName())) {
                throw SSException.get(EmenuException.AreaNameExist);
            }
            //判断名称是否为空
            if (Assert.isNull(area.getName())) {
                throw SSException.get(EmenuException.AreaNameIsNull);
            }
            //将状态设为"可用"
            area.setState(AreaStateEnums.Enabled.getId());
            return commonDao.insert(area);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertAreaFail, e);
        }
    }

    @Override
    public boolean checkNameIsExist(String name) throws SSException {
        //检查Name是否为空
        if (Assert.isNull(name)) {
            return false;
        }
        try {
            if (areaMapper.countByName(name) > 0) {
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
            if (checkNameIsExist(area.getName())) {
                throw SSException.get(EmenuException.AreaNameExist);
            }
            //判断名称是否为空
            if (Assert.isNull(area.getName())) {
                throw SSException.get(EmenuException.AreaNameIsNull);
            }
            commonDao.update(area);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateAreaFail, e);
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
            //判断该区域内是否有餐台，若有则不能删除
            if (tableService.countByAreaId(id) > 0) {
                throw SSException.get(EmenuException.AreaHasTableExist);
            }
            //将状态设为"删除"
            areaMapper.updateState(id, AreaStateEnums.Deleted.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteAreaFail, e);
        }
    }

    @Override
    public List<AreaDto> listDto() throws SSException {
        List<AreaDto> areaDtoList = new ArrayList<AreaDto>();
        try {
            List<Area> areaList = listAll();
            for(Area area : areaList) {
                List<Table> tableList = tableService.listByAreaId(area.getId());
                if(tableList != null && tableList.size() != 0){
                    AreaDto areaDto = new AreaDto();
                    areaDto.setArea(area);
                    areaDto.setTableList(tableList);
                    areaDtoList.add(areaDto);
                }
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return areaDtoList;
    }
}