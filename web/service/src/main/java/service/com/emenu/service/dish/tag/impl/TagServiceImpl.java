package com.emenu.service.dish.tag.impl;

import com.emenu.common.entity.dish.Tag;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.TagMapper;
import com.emenu.service.dish.tag.TagService;
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
    private TagMapper tagMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public Tag newTag(Tag tag) throws SSException {
        try{
            // 检查一下是否为空,名称
            if (Assert.isNull(tag.getName())) {
                throw SSException.get(EmenuException.TagNameIsNull);
            }
            if (Assert.isNull(tag.getpId())&& Assert.lessOrEqualZero(tag.getpId())) {
                throw SSException.get(EmenuException.TagPIdError);
            }
            return commonDao.insert(tag);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewTagFailed, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void delById(int tagId) throws SSException {
        try {
            if (Assert.lessOrEqualZero(tagId)) {
                throw SSException.get(EmenuException.TagIdError);
            }
            if(tagMapper.countChildrenById(tagId)!=0) {
                throw SSException.get(EmenuException.TagChildrenIsError);
            }
            commonDao.deleteById(Tag.class, tagId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTagFailed, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
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
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
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
            commonDao.update(tag);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTagFailed, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updateFiled(Tag tag, String filedName) throws SSException {
        try{
            // 检查id是否<=0，如果是，直接返回
            if (Assert.lessOrEqualZero(tag.getId())) {
                throw SSException.get(EmenuException.TagIdError);
            }
            if (Assert.isNull(filedName)) {
                throw SSException.get(EmenuException.TagFiledIsNull);
            }
            commonDao.updateFieldsById(tag, filedName);
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListTagFailed, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public Tag queryLayer2TagByDishId(int dishId) throws SSException{
        Tag tag = new Tag();
        try{
            if (Assert.lessOrEqualZero(dishId)) {
                throw SSException.get(EmenuException.DishIdNotNull);
            }
            tag = tagMapper.queryDishTagByDishId(dishId);
            // 如果不是二级分类，则一直查找它的父分类
            while (this.isLayer2Tag(tag) == false){
                tag = this.queryById(tag.getpId());
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTagFailed, e);
        }
        return tag;
    }

    /**
     * 私有方法
     * 判断是否是二级分类
     *
     * @param tag
     * @return
     * @throws SSException
     */
    private Boolean isLayer2Tag(Tag tag) throws SSException{
        try{
            if (tag.getpId() < 3 || tag.getpId() > 6){
                return false;
            }else {
                return true;
            }
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTagFailed, e);
        }
    }
}
