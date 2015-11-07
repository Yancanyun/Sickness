package com.emenu.service.remark.impl;

import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.enums.remark.RemarkTagStateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.remark.RemarkTagMapper;
import com.emenu.service.remark.RemarkService;
import com.emenu.service.remark.RemarkTagService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * RemarkTagServiceImpl
 *
 * @author: yangch
 * @time: 2015/11/7 13:49
 */
@Service("remarkTagService")
public class RemarkTagServiceImpl implements RemarkTagService{
    @Autowired
    private RemarkTagMapper remarkTagMapper;
    
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private RemarkService remarkService;

    @Override
    public List<RemarkTag> listAll() throws SSException {
        List<RemarkTag> remarkTagList = Collections.emptyList();
        try {
            remarkTagList = remarkTagMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryRemarkTagFail, e);
        }
        return remarkTagList;
    }

    @Override
    public RemarkTag queryById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return commonDao.queryById(RemarkTag.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryRemarkTagFail, e);
        }
    }
    
    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public RemarkTag newRemarkTag(RemarkTag remarkTag) throws SSException {
        try {
            //判断是否重名
            if (checkNameIsExist(remarkTag.getName())) {
                throw SSException.get(EmenuException.RemarkTagNameExist);
            }
            //判断名称是否为空
            if (Assert.isNull(remarkTag.getName())) {
                throw SSException.get(EmenuException.RemarkTagNameIsNull);
            }
            //将状态设为"可用"
            remarkTag.setState(RemarkTagStateEnums.Enabled.getId());
            return commonDao.insert(remarkTag);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertRemarkTagFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateRemarkTag(int id, RemarkTag remarkTag) throws SSException {
        try {
            //若Name与相应ID在数据库中对应的名称不一致，再去判断该名称是否在数据库中已存在
            if (!remarkTag.getName().equals(queryById(id).getName()) && checkNameIsExist(remarkTag.getName())){
                throw SSException.get(EmenuException.RemarkTagNameExist);
            }
            //判断名称是否为空
            if (Assert.isNull(remarkTag.getName())) {
                throw SSException.get(EmenuException.RemarkTagNameIsNull);
            }
            commonDao.update(remarkTag);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateRemarkTagFail, e);
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
            //判断该备注分类内是否有备注，若有则不能删除
            if (remarkService.countByRemarkTagId(id) > 0) {
                throw SSException.get(EmenuException.RemarkTagHasRemarkExist);
            }
            //将状态设为"删除"
            remarkTagMapper.updateState(id, RemarkTagStateEnums.Deleted.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteRemarkTagFail, e);
        }
    }
    
    @Override
    public boolean checkNameIsExist(String name) throws SSException {
        //检查Name是否为空
        if (Assert.isNull(name)) {
            return false;
        }
        try {
            if (remarkTagMapper.countByName(name) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }
}
