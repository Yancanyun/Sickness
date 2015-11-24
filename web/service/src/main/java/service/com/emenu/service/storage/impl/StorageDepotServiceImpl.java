package com.emenu.service.storage.impl;

import com.emenu.common.entity.storage.StorageDepot;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.storage.StorageDepotMapper;
import com.emenu.service.storage.StorageDepotService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 存放点Service实现
 *
 * @author xubr
 * @date 2015/11/10.
 */
@Service("storageDepotService")
public class StorageDepotServiceImpl implements StorageDepotService {

    @Autowired
    @Qualifier("storageDepotMapper")
    private StorageDepotMapper storageDepotMapper;

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
    public List<StorageDepot> listByPage(int curPage, int pageSize) throws SSException {

        curPage = curPage <=0 ? 0:curPage - 1;
        int offset = curPage*pageSize;
        if(Assert.lessZero(offset)) {
            return Collections.emptyList();
        }

        List<StorageDepot> depotList = Collections.<StorageDepot>emptyList();
        try {
            depotList = storageDepotMapper.listByPage(offset, pageSize);
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
    public List<StorageDepot> listAll() throws SSException {
        List<StorageDepot> list = Collections.<StorageDepot>emptyList();

        try {
            list = storageDepotMapper.listAll();
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
            return storageDepotMapper.countAll();
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
    public StorageDepot queryByName(String name) throws SSException {
        //检查name是否为空
        if(Assert.isNull(name)) {
            throw SSException.get(EmenuException.DepotNameError);
        }
        try {
            return storageDepotMapper.queryByName(name);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDepotByNameFailed);
        }
    }

    @Override
    public StorageDepot queryById(int id) throws SSException {
        //检查id是否为空或者小于等于0，如果是，直接返回
        if (!Assert.isNull(id) && Assert.lessOrEqualZero(id)) {
            throw SSException.get(EmenuException.DepotIdError);
        }
        try {
            return storageDepotMapper.queryById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDepotByIdFailed);
        }

    }

    /**
     * 添加一个存放点
     *
     * @param storageDepot
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED ,rollbackFor = {SSException.class,Exception.class,RuntimeException.class})
    public StorageDepot newStorageDepot(StorageDepot storageDepot) throws SSException {
        //检查存放点名称是否为空
        if(Assert.isNull(storageDepot.getName())) {
           throw SSException.get(EmenuException.DepotNameError);
        }
        //检查存放点名称是否已经存在
        if(checkNameIsExist(storageDepot.getName())){
            throw SSException.get(EmenuException.DepotNameIsExist);
        }
        try {
            return commonDao.insert(storageDepot);
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        //检查id是否为空或者小于等于0，如果是，直接返回
        if(!Assert.isNull(id)&&Assert.lessOrEqualZero(id)) {
            throw SSException.get(EmenuException.DepotIdError);
        }
        try {
            commonDao.deleteById(StorageDepot.class,id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DepotIdError,e);
        }
    }

    /**
     * 修改存放点
     *
     * @param storageDepot
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStorageDepot(StorageDepot storageDepot) throws SSException {
        //检查id是否为空或者小于等于0，如果是则直接返回
        if(!Assert.isNull(storageDepot.getId())&&Assert.lessOrEqualZero(storageDepot.getId())) {
            throw SSException.get(EmenuException.DepotIdError);
        }
        //检查存放点名称是否为空
        if(Assert.isNull(storageDepot.getName())) {
            throw SSException.get(EmenuException.DepotNameError);
        }
        //检查名称是否与其他存放点名冲突
        if (checkNameIsConflict(storageDepot.getName(), storageDepot.getId())){
            throw SSException.get(EmenuException.DepotNameIsConflict);
        }
        try {
            commonDao.update(storageDepot);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateDepotFailed);
        }
    }

    /**
     * 检查名字是否已经存在
     * @param name
     * @return
     * @throws SSException
     */
    public Boolean checkNameIsExist(String name) throws SSException {
        int count = 0;
        try {
            count = storageDepotMapper.checkNameIsExist(name);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckDepotNameFailed);
        }
        return count == 0 ? false:true;
    }

    /**
     * 查询名字是否与其他存放点名冲突
     * @param name
     * @param id
     * @return
     * @throws SSException
     */
    public Boolean checkNameIsConflict(String name,int id) throws SSException {
        int count = 0;
        try {
            count = storageDepotMapper.checkNameIsConflict(name,id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckDepotNameConflictFailed);
        }
        return count == 0 ? false : true;
    }
}
