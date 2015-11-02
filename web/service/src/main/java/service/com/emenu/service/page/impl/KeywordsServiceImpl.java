package com.emenu.service.page.impl;

import com.emenu.common.entity.page.Keywords;
import com.emenu.common.enums.page.KeywordsEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.page.KeywordsMapper;
import com.emenu.service.page.KeywordsService;
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
 * 搜索风向标-关键字Service实现
 *
 * @author Wang LiMing
 * @date 2015/10/23 8:50
 */

@Service("keywordsService")
public class KeywordsServiceImpl implements KeywordsService{

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private KeywordsMapper keywordsMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public Keywords newKeywords(Keywords keywords) throws SSException {
        if (!checkBeforeSave(keywords)){
            return null;
        }
        try {
            return commonDao.insert(keywords);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)){
            return;
        }

        try {
            commonDao.deleteById(Keywords.class,id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteKeywordsFail, e);
        }
    }

    @Override
    public List<Keywords> listByType(KeywordsEnum type) throws SSException {
        List<Keywords> list = Collections.emptyList();
        try {
            list =  keywordsMapper.listByType(type.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryKeywordsFail, e);
        }
        return list;
    }

    /**
     * 检查关键字是否已存在
     *
     * @param key
     * @param type
     * @return
     * @throws SSException
     */
    private boolean checkKeyExist(String key, Integer type) throws SSException{
        int num = 0;
        try {
            num = keywordsMapper.countByKeyAndType(key, type);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return num > 0;
    }

    /**
     * 检查实体及其关键字段是否为空
     *
     * @param keywords
     * @return
     * @throws SSException
     */
    private boolean checkBeforeSave(Keywords keywords) throws SSException{
        if (Assert.isNull(keywords)){
            return false;
        }

        Assert.isNotNull(keywords.getKey(), EmenuException.KeyNotNull);
        Assert.isNotNull(keywords.getType(), EmenuException.KeyTypeNotNull);
        Assert.isNotNull(KeywordsEnum.valueOf(keywords.getType()), EmenuException.KeyTypeWrong);

        if (checkKeyExist(keywords.getKey(), keywords.getType())){
            throw SSException.get(EmenuException.KeywordsExist);
        }

        return true;
    }
}
