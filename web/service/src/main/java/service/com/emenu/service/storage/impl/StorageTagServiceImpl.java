package com.emenu.service.storage.impl;

import com.emenu.common.entity.dish.tag.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.storage.StorageTagService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Tag> listAll() throws SSException {
        List<Tag> list = Collections.emptyList();
        try {
            list = tagFacadeService.listByPId(TagEnum.Storage.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageTagQueryFailed, e);
        }
        return list;
    }

    @Override
    public Tag newStorageTag(int pId, String name) throws SSException {
        try {
            Assert.isNotNull(pId, EmenuException.TagPIdError);
            Assert.isNotNull(name, EmenuException.StorageTagNameNotNull);

            Tag tag = new Tag();
            tag.setpId(pId);
            tag.setName(name);

            tag = tagFacadeService.newTag(tag);
            return tag;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageTagInsertFailed, e);
        }
    }

    @Override
    public void updateStorageTag(int id, String name) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.TagIdError);
            }
            Assert.isNotNull(name, EmenuException.StorageTagNameNotNull);

            Tag tag = new Tag();
            tag.setId(id);
            tag.setName(name);

            tagFacadeService.updateTag(tag);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageTagNameNotNull, e);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            // TODO: 2015/11/10  需要先判断分类下是否存在物品

            // 删除分类
            tagFacadeService.delById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageTagDeleteFailed, e);
        }
    }
}
