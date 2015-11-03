package com.emenu.service.dish.tag.impl;

import com.emenu.common.entity.dish.tag.Tag;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.tag.TagMapper;
import com.emenu.service.dish.tag.TagService;
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
 * TagServiceImpl
 * 菜品和原料类别Service实现
 * @author dujuan
 * @date 2015/10/23
 */
@Service("tagService")
public class TagServiceImpl implements TagService{

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    @Qualifier("tagMapper")
    private TagMapper tagMapper;

    @Override
    public Tag newTag(Tag tag) throws SSException {
        try{
            // 检查一下是否为空,名称
            if (Assert.isNull(tag.getName())) {
                throw SSException.get(EmenuException.TagNameIsNull);
            }
            if (Assert.isNull(tag.getpId())&& Assert.lessOrEqualZero(tag.getpId())) {
                throw SSException.get(EmenuException.TagPIdError);
            }
            if (Assert.isNull(tag.getType())&& Assert.lessOrEqualZero(tag.getpId())) {
                throw SSException.get(EmenuException.TagTypeError);
            }
            if (Assert.isNull(tag.getWeight())&& Assert.lessOrEqualZero(tag.getWeight())) {
                throw SSException.get(EmenuException.TagWeightError);
            }
            return commonDao.insert(tag);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewTagFailed, e);
        }
    }

    @Override
    public void delById(int tagId) throws SSException {
        try {
            if (Assert.lessOrEqualZero(tagId)) {
                throw SSException.get(EmenuException.TagIdError);
            }
            commonDao.deleteById(Tag.class, tagId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTagFailed, e);
        }
    }

    @Override
    public void delByIds(List<Integer> ids) throws SSException {
        try{
            if(Assert.isEmpty(ids)){
                throw SSException.get(EmenuException.TagIdError);
            }
            tagMapper.delByIds(ids);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTagFailed, e);
        }
    }

    @Override
    public void updateTag(Tag tag) throws SSException {
        try {
            if (!Assert.isNull(tag.getId()) && Assert.lessOrEqualZero(tag.getId())) {
                throw SSException.get(EmenuException.TagIdError);
            }
            if (Assert.isNull(tag.getName())) {
                throw SSException.get(EmenuException.TagNameIsNull);
            }
            if (Assert.isNull(tag.getpId())&& Assert.lessOrEqualZero(tag.getpId())) {
                throw SSException.get(EmenuException.TagPIdError);
            }
            if (Assert.isNull(tag.getType())&& Assert.lessOrEqualZero(tag.getpId())) {
                throw SSException.get(EmenuException.TagTypeError);
            }
            if (Assert.isNull(tag.getWeight())&& Assert.lessOrEqualZero(tag.getWeight())) {
                throw SSException.get(EmenuException.TagWeightError);
            }
            commonDao.update(tag);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTagFailed, e);
        }
    }

    @Override
    public void update(Tag tag) throws SSException {
        try{
            // 检查id是否<=0，如果是，直接返回
            if (Assert.lessOrEqualZero(tag.getId())) {
                throw SSException.get(EmenuException.TagIdError);
            }
            commonDao.updateFieldsById(tag);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTagFailed, e);
        }
    }

    @Override
    public Tag queryById(int tagId) throws SSException {
        try {
            if (Assert.lessOrEqualZero(tagId)) {
                throw SSException.get(EmenuException.TagIdError);
            }
            return commonDao.queryById(Tag.class, tagId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTagFailed, e);
        }
    }

    @Override
    public List<Tag> listAll() throws SSException {
        List<Tag> tagList = Collections.emptyList();
        try {
            tagList = tagMapper.listAll();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListTagFailed, e);
        }
        return tagList;
    }

    @Override
    public List<Tag> listByPage(int curPage, int pageSize) throws SSException {
        List<Tag> tagList = Collections.emptyList();
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return tagList;
        }
        try {
            tagList = tagMapper.listByPage(curPage, pageSize);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListTagFailed, e);
        }
        return tagList;
    }

    @Override
    public int countAll() throws SSException {
        Integer count = 0;
        try{
            count = tagMapper.countAll();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListTagFailed, e);
        }
        return count == null ? 0 : count;
    }
}
