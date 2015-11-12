package com.emenu.service.dish.tag;

import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.tag.Tag;
import com.pandawork.core.common.exception.SSException;

import java.util.List;


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
     * 更新tag
     *
     * @param tag
     * @throws SSException
     */
    public void updateTag(Tag tag) throws SSException;

    /**
     * 根据pId获取所有的儿子节点
     *
     * @param pId
     * @return
     * @throws SSException
     */
    public List<Tag> listByPId(int pId) throws SSException;

    /**
     * 删除一个tag
     * @param tagId
     * @throws SSException
     */
    public void delById(int tagId) throws Exception;

    /**
     * 根据ID获取当前ID展开的Tag
     * @param tagId
     * @return
     * @throws Exception
     */
    public List<TagDto> listByCurrentId(int tagId) throws Exception;

}
