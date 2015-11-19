package com.emenu.service.storage.impl;

import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.storage.StorageItemService;
import com.emenu.service.storage.StorageTagService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 库存分类Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/10 19:28
 **/
@Service("storageTagService")
public class StorageTagServiceImpl implements StorageTagService {

    @Autowired
    private TagFacadeService tagFacadeService;

    @Autowired
    private StorageItemService storageItemService;

    @Override
    public List<TagDto> listAll() throws SSException {
        List<TagDto> list = Collections.emptyList();
        try {
            list = tagFacadeService.listByCurrentId(TagEnum.Storage.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageTagQueryFailed, e);
        }
        return list;
    }

    @Override
    public List<Tag> listAllSmallTag() throws SSException {
        List<Tag> smallTagList = Collections.emptyList();
        try {
            List<Tag> bigTagList = tagFacadeService.listByPId(TagEnum.Storage.getId());
            smallTagList = new ArrayList<Tag>();
            for (Tag tag : bigTagList) {
                smallTagList.addAll(tagFacadeService.listByPId(tag.getId()));
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return smallTagList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public Tag newStorageTag(int pId, String name) throws SSException {
        try {
            Assert.isNotNull(pId, EmenuException.TagPIdError);
            Assert.isNotNull(name, EmenuException.StorageTagNameNotNull);

            Tag tag = new Tag();
            tag.setpId(pId);
            tag.setName(name);
            tag.setWeight(20);

            tag = tagFacadeService.newTag(tag);
            return tag;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageTagInsertFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStorageTag(int id, int pId, String name) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.TagIdError);
            }
            Assert.isNotNull(name, EmenuException.StorageTagNameNotNull);

            Tag tag = new Tag();
            tag.setId(id);
            tag.setpId(pId);
            tag.setName(name);

            tagFacadeService.updateTag(tag);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageTagUpdateFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            if (checkUsingById(id)) {
                throw SSException.get(EmenuException.StorageTagHasItem);
            }

            // 删除分类
            tagFacadeService.delById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageTagDeleteFailed, e);
        }
    }

    /**
     * 检查是否有物品正在使用分类
     *
     * @param id
     * @return
     * @throws SSException
     */
    private boolean checkUsingById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return false;
        }
        Integer count = storageItemService.countByTagId(id);

        return count > 0;
    }
}
