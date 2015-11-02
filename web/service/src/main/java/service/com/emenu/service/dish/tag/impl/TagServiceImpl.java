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
        // 检查一下是否为空，如果为空，直接返回，不做没必要的数据库操作
        if (Assert.isNull(tag)) {
            return null;
        }
        try{
            return commonDao.insert(tag);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewTagFailed);
        }
    }

    @Override
    public void updateName(Integer tagId, String name) throws SSException {
        // 检查id是否<=0，如果是，直接返回
        if (!Assert.isNull(tagId) && Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        try{
            Tag tag = commonDao.queryById(Tag.class, tagId);
            if(tag != null){
                tag.setName(name);
                commonDao.update(tag);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTagFailed);
        }
    }

    @Override
    public void updateWeight(Integer tagId, Integer weight) throws SSException {
        if (!Assert.isNull(tagId) && Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        if (!Assert.isNull(weight) && Assert.lessOrEqualZero(weight)) {
            throw SSException.get(EmenuException.TagWeightError);
        }
        try {
            Tag tag = commonDao.queryById(Tag.class, tagId);
            if(tag != null){
                tag.setWeight(weight);
                commonDao.update(tag);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTagFailed);
        }
    }

    @Override
    public void updateTagPid(Integer tagId, Integer pId) throws SSException {
        if (!Assert.isNull(tagId) && Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        if (!Assert.isNull(pId) && Assert.lessOrEqualZero(pId)) {
            throw SSException.get(EmenuException.TagPIdError);
        }
        try {
            //检查pid是否存在
            Tag ptag = commonDao.queryById(Tag.class, pId);
            if(ptag == null) {
                throw SSException.get(EmenuException.TagPIdError);
            }
            Tag tag = commonDao.queryById(Tag.class, tagId);
            if(tag != null){
                tag.setpId(pId);
                commonDao.update(tag);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTagFailed);
        }
    }

    @Override
    public void delTagById(Integer tagId) throws SSException {
        if (!Assert.isNull(tagId) && Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        try {
            Tag tag = commonDao.queryById(Tag.class, tagId);
            if(tag != null){
                commonDao.delete(tag);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTagFailed);
        }
    }

    @Override
    public void delTags(List<Integer> ids) throws SSException {
        try{
            tagMapper.delTags(ids);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTagFailed);
        }
    }

    @Override
    public Tag queryTagById(Integer tagId) throws SSException {
        if (!Assert.isNull(tagId) && Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        try {
            return commonDao.queryById(Tag.class, tagId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTagFailed);
        }
    }

    @Override
    public void updateTag(Tag tag) throws SSException {
        if (!Assert.isNull(tag.getId()) && Assert.lessOrEqualZero(tag.getId())) {
            throw SSException.get(EmenuException.TagIdError);
        }
        try {
            commonDao.update(tag);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTagFailed);
        }
    }

    @Override
    public List<Tag> listTag() throws SSException {
        try {
            return tagMapper.listTag();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListTagFailed);
        }
    }

    @Override
    public List<Tag> listTagByPage(int curPage, int pageSize) throws SSException {
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return Collections.emptyList();
        }
        List<Tag> tagList = Collections.<Tag>emptyList();
        try {
            tagList = tagMapper.listTagByPage(curPage, pageSize);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListUnitFailed, e);
        }
        return tagList;
    }

    @Override
    public int countTag() throws SSException {
        try{
            return tagMapper.countTag();
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListUnitFailed, e);
        }
    }
}
