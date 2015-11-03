package com.emenu.service.dish.unit.impl;
import com.emenu.common.entity.dish.unit.Unit;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.unit.UnitMapper;
import com.emenu.service.dish.unit.UnitService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * UnitServiceImpl
 *
 * @author xubaorong
 * @date 2015/10/23.
 */

@Service("unitService")
public class UnitServiceImpl implements UnitService {

    @Autowired
    @Qualifier("unitMapper")
    private UnitMapper unitMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    /**
     * 获取所有单位
     *
     * @throws SSException
     */
    @Override
    public List<Unit> listAll() throws SSException {
        List<Unit> list = Collections.emptyList();
        try {
            list = unitMapper.listAll();
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListUnitFailed, e);
        }
        return list;
    }

    @Override
    public List<Unit> listByPage(int curPage, int pageSize) throws SSException {
        List<Unit> unitList = Collections.emptyList();
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return unitList;
        }
        try {
            unitList = unitMapper.listByPage(offset, pageSize);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListUnitFailed, e);
        }
        return unitList;
    }

    @Override
    public int countAll() throws SSException {
        try{
            return unitMapper.countAll();
        } catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListUnitFailed, e);
        }
    }

    /**
     * 根据id查询一条单位信息
     *
     * @param
     * @param id
     * @throws SSException
     */
    @Override
    public Unit queryById(int id) throws SSException {
        // 检查id是否<=0，如果是，直接返回
        if (!Assert.isNull(id) && Assert.lessOrEqualZero(id)) {
            throw SSException.get(EmenuException.UnitIdError);
        }
        try {
            // 检查id是否<=0，如果是，直接返回
            if (Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.UnitIdError);
            }
            return commonDao.queryById(Unit.class, id);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryUnitFailed, e);
        }
    }

    /**
     * 增加一个单元
     *
     * @param unit
     * @throws SSException
     */
    @Override
    public Unit newUnit(Unit unit) throws SSException {
        try {
            if(Assert.isNull(unit.getName())){
                throw SSException.get(EmenuException.UnitNameError);
            }
            //检查用户名是否存在
            if(checkNameIsExist(unit.getName())){
                throw SSException.get(EmenuException.UnitNameIsExist);
            }
            if(!Assert.isNull(unit.getType()) && Assert.lessOrEqualZero(unit.getType())){
                throw SSException.get(EmenuException.UnitTypeError);
            }
            return commonDao.insert(unit);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewUnitFailed,e);
        }
    }

    /**
     * 删除一个单元
     *
     * @param id
     * @throws SSException
     */
    @Override
    public void delById(int id) throws SSException {
        // 检查id是否<=0，如果是，直接返回
        if (!Assert.isNull(id) && Assert.lessOrEqualZero(id)) {
            throw SSException.get(EmenuException.UnitIdError);
        }
        try {
            // 检查id是否<=0，如果是，直接返回
            if (Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.UnitIdError);
            }
            commonDao.deleteById(Unit.class, id);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteUnitFailed, e);
        }
    }

    /**
     * 修改一个单元
     *
     * @param unit
     * @throws SSException
     */
    @Override
    public void updateUnit(Unit unit) throws SSException {
        try {
            // 检查id是否<=0，如果是，直接返回
            if (!Assert.isNull(unit.getId()) && Assert.lessOrEqualZero(unit.getId())) {
                throw SSException.get(EmenuException.UnitIdError);
            }
            if(Assert.isNull(unit.getName())){
                throw SSException.get(EmenuException.UnitNameError);
            }
            //检查用户名是否存在
            if(checkNameIsExist(unit.getName())){
                throw SSException.get(EmenuException.UnitNameIsExist);
            }
            if(!Assert.isNull(unit.getType()) && Assert.lessOrEqualZero(unit.getType())){
                throw SSException.get(EmenuException.UnitTypeError);
            }
            commonDao.update(unit);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateUnitFailed, e);
        }
    }

    /**
     * 检查名称是否重复
     * @param name
     * @return
     * @throws SSException
     */
    private boolean checkNameIsExist(String name) throws SSException {
        int count = 0;
        try {
            count = unitMapper.checkNameIsExist(name);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryUnitFailed, e);
        }
        return count == 0 ? false : true;
    }

}
