package com.emenu.service.dish.tag.impl;

import com.emenu.common.entity.dish.tag.Tag;
import com.emenu.service.dish.tag.TagCacheService;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.dish.tag.TagService;
import com.pandawork.core.common.exception.SSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * TagFacadeServiceImpl
 *
 * @author dujuan
 * @date 2015/11/2
 */
@Service("tagFacadeService")
public class TagFacadeServiceImpl implements TagFacadeService {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagCacheService tagCacheService;

    @Override
    public Tag newTag(Tag tag) throws Exception {
        tag = tagService.newTag(tag);
        return tagCacheService.newTag(tag);
    }

    @Override
    public void updateTag(Tag tag) throws SSException {

    }

    @Override
    public List<Tag> listByPId(int pId) throws SSException {
        return null;
    }

    @Override
    public void delById(int tagId) throws Exception {
        tagService.delById(tagId);
        tagCacheService.delById(tagId);
    }
}
