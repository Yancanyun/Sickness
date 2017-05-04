package com.emenu.service.stock.impl;

import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.stock.StockItemService;
import com.emenu.service.stock.StockTagService;
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
 * StockTagServiceImpl
 *
 * @author renhongshuai
 * @Time 2017/5/4 16:13.
 */
@Service("stockTagService")
public class StockTagServiceImpl implements StockTagService {
    @Autowired
    private TagFacadeService tagFacadeService;

    @Autowired
    private StockItemService stockItemService;

    @Override
    public List<TagDto> listAll() throws SSException {
        List<TagDto> list = Collections.emptyList();
        try {
            list = tagFacadeService.listByCurrentId(TagEnum.Storage.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StockTagQueryFailed, e);
        }
        return list;
    }

    @Override
    public List<Tag> listAllSmallTag() throws SSException {
        List<Tag> smallTagList = Collections.emptyList();
        try {
            List<Tag> bigTagList = tagFacadeService.listChildrenByTagId(TagEnum.Storage.getId());
            smallTagList = new ArrayList<Tag>();
            for (Tag tag : bigTagList) {
                smallTagList.addAll(tagFacadeService.listChildrenByTagId(tag.getId()));
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
            Assert.isNotNull(name, EmenuException.StockTagNameNotNull);

            Tag tag = new Tag();
            tag.setpId(pId);
            tag.setName(name);
            tag.setWeight(20);

            tag = tagFacadeService.newTag(tag);
            return tag;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StockTagInsertFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStorageTag(int id, int pId, String name) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.TagIdError);
            }
            Assert.isNotNull(name, EmenuException.StockTagNameNotNull);

            Tag tag = new Tag();
            tag.setId(id);
            tag.setpId(pId);
            tag.setName(name);
            tagFacadeService.updateTag(tag);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StockTagUpdateFailed, e);
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
                throw SSException.get(EmenuException.StockTagHasItem);
            }

            // 删除分类
            tagFacadeService.delById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StockTagDeleteFailed, e);
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
        Integer count = stockItemService.countByTagId(id);

        return count > 0;
    }
}
