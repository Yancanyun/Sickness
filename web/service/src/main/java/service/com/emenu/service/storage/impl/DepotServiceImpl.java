package com.emenu.service.storage.impl;

import com.emenu.common.entity.storage.Depot;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.storage.DepotMapper;
import com.emenu.service.storage.DepotService;
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
 * DopotServiceImpl
 * 存放点service层实现
 * @author xubr
 * @date 2015/11/10.
 */
@Service("depotService")
public class DepotServiceImpl implements DepotService {

    @Autowired
    @Qualifier("depotMapper")
    private DepotMapper depotMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    /**
     * 分页查询存放点
     *
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    @Override
    public List<Depot> listByPage(int curPage, int pageSize) throws SSException {

        curPage = curPage <=0 ? 0:curPage - 1;
        int offset = curPage*pageSize;
        if(Assert.lessZero(offset)) {
            return Collections.emptyList();
        }

        List<Depot> depotList = Collections.<Depot>emptyList();
        try {
            depotList = depotMapper.listByPage(offset, pageSize);
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListDepotPageFailed,e);
        }
        return depotList;
    }

    /**
     * 查询全部存放点
     *
     * @return
     * @throws SSException
     */
    @Override
    public List<Depot> listAll() throws SSException {

        List<Depot> list = Collections.<Depot>emptyList();

        try {
            list = depotMapper.listAll();
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListDepotFailed);
        }
        return list;
    }

    /**
     * 计算存放点总数
     *
     * @return
     * @throws SSException
     */
    @Override
    public int countAll() throws SSException {

        try {
            return depotMapper.countAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountDepotFailed,e);
        }
    }

    /**
     * 根据存放点名称查询存放点
     *
     * @param name
     * @return
     * @throws SSException
     */
    @Override
    public Depot queryByName(String name) throws SSException {

        //检查name是否为空
        if(Assert.isNull(name)) {
            throw SSException.get(EmenuException.DepotNameError);
        }

        try {
            return depotMapper.queryByName(name);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDepotByNameFailed);
        }
    }

    /**
     * 添加一个存放点
     *
     * @param depot
     * @return
     * @throws SSException
     */
    @Override
    public Depot newDepot(Depot depot) throws SSException {

        if(Assert.isNull(depot)) {
            return null;
        }

        try {
            return commonDao.insert(depot);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewDepotFailed,e);
        }
    }

    /**
     * 根据id删除存放点
     *
     * @param id
     * @throws SSException
     */
    @Override
    public void delById(int id) throws SSException {

        //检查id是否为空或者小于等于0，如果是，直接返回
        if(!Assert.isNull(id)&&Assert.lessOrEqualZero(id)) {
            throw SSException.get(EmenuException.DepotIdError);
        }

        try {
            commonDao.deleteById(Depot.class,id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DepotIdError,e);
        }
    }

    /**
     * 修改存放点
     *
     * @param depot
     * @throws SSException
     */
    @Override
    public void updateDepot(Depot depot) throws SSException {

        if(Assert.isNull(depot)) {
            return;
        }

        try {
            commonDao.update(depot);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateDepotFailed);
        }
    }
}
