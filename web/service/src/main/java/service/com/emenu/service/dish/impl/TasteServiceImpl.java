package com.emenu.service.dish.impl;

import com.emenu.common.entity.dish.Taste;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.TasteMapper;
import com.emenu.service.dish.TasteService;
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
 * TasteServiceImpl
 *
 * @author dujuan
 * @date 2015/11/23
 */
@Service("tasteService")
public class TasteServiceImpl implements TasteService{

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private TasteMapper tasteMapper;

    @Override
    public Taste newTaste(Taste taste) throws SSException {
        try {
            if(Assert.isNull(taste.getName())){
                throw SSException.get(EmenuException.TasteNameError);
            }
            if(checkNameIsExist(taste.getName(), null)){
                throw SSException.get(EmenuException.TasteNameIsExist);
            }
            return commonDao.insert(taste);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewTasteFailed,e);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        try {
            if(!Assert.isNotNull(id) && Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.TasteIdError);
            }
            commonDao.deleteById(Taste.class, id);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTasteFailed,e);
        }
    }

    @Override
    public void update(Taste taste) throws SSException {
        try {
            if(!Assert.isNotNull(taste.getId()) && Assert.lessOrEqualZero(taste.getId())){
                throw SSException.get(EmenuException.TasteIdError);
            }
            if(Assert.isNull(taste.getName())){
                throw SSException.get(EmenuException.TasteNameError);
            }
            if(checkNameIsExist(taste.getName(), queryById(taste.getId()).getName())){
                throw SSException.get(EmenuException.TasteNameIsExist);
            }
            commonDao.update(taste);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTasteFailed,e);
        }
    }

    @Override
    public Taste queryById(int id) throws SSException {
        try {
            if(!Assert.isNotNull(id) && Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.TasteIdError);
            }
            return commonDao.queryById(Taste.class, id);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTasteFailed,e);
        }
    }

    @Override
    public List<Taste> listAll(int curPage, int pageSize) throws SSException {
        List<Taste> tasteList = Collections.emptyList();
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        try {
            tasteList = tasteMapper.listAll(offset, pageSize);
            return tasteList;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTasteFailed,e);
        }
    }

    @Override
    public int countAll() throws SSException {
        return 0;
    }

    /**
     * 检查名称是否重复
     * @param name
     * @return
     * @throws SSException
     */
    private boolean checkNameIsExist(String name, String oldname) throws SSException {
        int count = 0;
        try {
            count = tasteMapper.checkNameIsExist(name,oldname);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryUnitFailed, e);
        }
        return count == 0 ? false : true;
    }
}
