package com.emenu.service.dish.tag;

import com.emenu.common.entity.dish.tag.Tag;
import com.pandawork.core.common.exception.SSException;


/**
 * TagFacadeService
 *
 * @author dujuan
 * @date 2015/11/2
 */
public interface TagFacadeService {
    /**
     * 添加一个tag
     * @param tag
     * @return
     * @throws SSException
     */
    public Tag newTag(Tag tag) throws Exception;

    /**
     * 删除一个tag
     * @param tagId
     * @throws SSException
     */
    public void delById(int tagId) throws Exception;

}
