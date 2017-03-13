package com.emenu.service.stock.impl;

import com.emenu.common.entity.stock.Specifications;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.stock.SpecificationsMapper;
import com.emenu.service.stock.SpecificationsService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by apple on 17/2/27.
 */
@Service("specificationsService")
public class SpecificationsServiceImpl implements SpecificationsService {

    @Autowired
    private SpecificationsMapper specificationsMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void add(Specifications specifications) throws SSException {
        try {
            if (Assert.isNull(specifications)) {
                throw SSException.get(EmenuException.SpecificationsIsNull);
            }
            commonDao.insert(specifications);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.AddSpecificationsFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void deleteById(int id) throws SSException {
        if (id <= 0) return;

        try {
            commonDao.deleteById(Specifications.class, id);

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteSpecificationsFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void update(Integer id, Specifications specifications) throws SSException {
        if (id <= 0) return;

        try {
            if (Assert.isNull(specifications)) {
                throw SSException.get(EmenuException.SpecificationsIsNull);
            }
            specificationsMapper.update(id, specifications);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateSpecificationsFail, e);
        }
    }

    @Override
    public Specifications queryById(int id) throws SSException {
        if (id <= 0) return null;

        try {
            return commonDao.queryById(Specifications.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QuerySpecificationsByIdFail, e);
        }
    }

    @Override
    public List<Specifications> listAll() throws SSException {
        try {
            return specificationsMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListAllSpecificationsFail, e);
        }
    }
}
