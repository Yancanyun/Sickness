package com.emenu.service.remark.impl;

import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.enums.remark.RemarkStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.remark.RemarkMapper;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RemarkServiceImpl
 *
 * @author: yangch
 * @time: 2015/11/7 15:46
 */
@Service("remarkService")
public class RemarkServiceImpl implements RemarkService {
    @Autowired
    private RemarkMapper remarkMapper;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private RemarkTagService remarkTagService;

    @Override
    public List<Remark> listAll() throws SSException {
        List<Remark> remarkList = Collections.emptyList();
        try {
            remarkList = remarkMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryRemarkFail, e);
        }
        return remarkList;
    }

    @Override
    public List<RemarkDto> listAllRemarkDto() throws SSException {
        List<RemarkDto> remarkDtoList = new ArrayList<RemarkDto>();
        List<Remark> remarkList = Collections.emptyList();
        try {
            remarkList = remarkMapper.listAll();

            if(!remarkList.isEmpty()) {
                for(Remark remark : remarkList) {
                    RemarkDto remarkDto = new RemarkDto();
                    remarkDto.setRemark(remark);
                    remarkDto.setRemarkTagName(remarkTagService.queryById(remark.getRemarkTagId()).getName());
                    remarkDtoList.add(remarkDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryRemarkFail, e);
        }
        return remarkDtoList;
    }

    @Override
    public List<Remark> listByRemarkTagId(int remarkTagId) throws SSException {
        //检查RemarkTagID是否合法
        if (Assert.lessOrEqualZero(remarkTagId)) {
            return null;
        }
        List<Remark> remarkList = Collections.emptyList();
        try {
            remarkList = remarkMapper.listByRemarkTagId(remarkTagId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryRemarkFail, e);
        }
        return remarkList;
    }
    
    @Override
    public List<RemarkDto> listRemarkDtoByRemarkTagId(int remarkTagId) throws SSException {
        //检查RemarkTagID是否合法
        if (Assert.lessOrEqualZero(remarkTagId)) {
            return null;
        }
        List<RemarkDto> remarkDtoList = new ArrayList<RemarkDto>();
        List<Remark> remarkList = Collections.emptyList();
        try {
            remarkList = remarkMapper.listByRemarkTagId(remarkTagId);
            if(!remarkList.isEmpty()) {
                for(Remark remark : remarkList) {
                    RemarkDto remarkDto = new RemarkDto();
                    remarkDto.setRemark(remark);
                    remarkDto.setRemarkTagName(remarkTagService.queryById(remark.getRemarkTagId()).getName());
                    remarkDtoList.add(remarkDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryRemarkFail, e);
        }
        return remarkDtoList;
    }

    @Override
    public Remark queryById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return commonDao.queryById(Remark.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryRemarkFail, e);
        }
    }
    
    @Override
    public RemarkDto queryRemarkDtoById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            RemarkDto remarkDto = new RemarkDto();
            Remark remark = commonDao.queryById(Remark.class, id);

            remarkDto.setRemark(remark);
            remarkDto.setRemarkTagName(remarkTagService.queryById(remark.getRemarkTagId()).getName());

            return remarkDto;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryRemarkFail, e);
        }
    }
    
    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public Remark newRemark(Remark remark) throws SSException {
        try {
            //判断RemarkTagId是否存在
            if (Assert.isNull(remarkTagService.queryById(remark.getRemarkTagId()))) {
                throw SSException.get(EmenuException.RemarkTagNotExist);
            }
            //判断是否重名
            if (checkNameIsExist(remark.getName())) {
                throw SSException.get(EmenuException.RemarkNameExist);
            }
            //判断名称是否为空
            if (Assert.isNull(remark.getName())) {
                throw SSException.get(EmenuException.RemarkNameIsNull);
            }
            //将状态设为"可用"
            remark.setStatus(RemarkStatusEnums.Enabled.getId());
            return commonDao.insert(remark);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertRemarkFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateRemark(Integer id, Remark remark) throws SSException {
        try {
            //若未传来ID，则为增加页，直接判断该名称是否在数据库中已存在
            if(id == null && checkNameIsExist(remark.getName())) {
                throw SSException.get(EmenuException.RemarkNameExist);
            }
            //若传来ID，则为编辑页
            //判断传来的Name与相应ID在数据库中对应的名称是否一致，若不一致，再判断该名称是否在数据库中已存在
            if (id != null && !remark.getName().equals(queryById(id).getName()) && checkNameIsExist(remark.getName())){
                throw SSException.get(EmenuException.RemarkNameExist);
            }
            //判断是否为空
            if (Assert.isNull(remark.getName())) {
                throw SSException.get(EmenuException.RemarkNameIsNull);
            }
            commonDao.update(remark);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateRemarkFail, e);
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
            //将状态设为"删除"
            remarkMapper.updateStatus(id, RemarkStatusEnums.Deleted.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteRemarkFail, e);
        }
    }
    
    @Override
    public boolean checkNameIsExist(String name) throws SSException {
        //检查Name是否为空
        if (Assert.isNull(name)) {
            return false;
        }
        try {
            if (remarkMapper.countByName(name) > 0) {
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
    public int countByRemarkTagId(int remarkTagId) throws SSException {
        //检查RemarkTagID是否合法
        if (Assert.lessOrEqualZero(remarkTagId)) {
            return 0;
        }
        try {
            return remarkMapper.countByRemarkTagId(remarkTagId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }
}
