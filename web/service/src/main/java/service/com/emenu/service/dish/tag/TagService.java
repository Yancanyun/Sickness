package com.emenu.service.dish.tag;

import com.emenu.common.entity.dish.tag.Tag;
import com.pandawork.core.common.exception.SSException;

import java.util.Collection;
import java.util.List;

/**
 * TagService
 * 菜品和原料类别Service层
 * @author dujuan
 * @date 2015/10/23
 */
public interface TagService {

    /**
     * 添加一个tag
     * @param tag
     * @return
     * @throws SSException
     */
    public Tag newTag(Tag tag) throws SSException;

    /**
     * 删除一个tag
     * @param tagId
     * @throws SSException
     */
    public void delById(int tagId) throws SSException;

    /**
     * 根据ID批量删除tag
     * @param ids
     * @throws SSException
     */
    public void delByIds(List<Integer> ids) throws SSException;

    /**
     * 根据ID修改Tag
     * @param tag
     * @throws SSException
     */
    public void updateTag(Tag tag) throws SSException;

    /**
     * 根据Id修改tag名字
     * @param tagId
     * @param name
     * @throws SSException
     */
    public void updateName(int tagId, String name) throws SSException;

    /**
     * 根据ID修改tag权重
     * @param tagId
     * @param weight
     * @throws SSException
     */
    public void updateWeight(int tagId, Integer weight) throws SSException;

    /**
     * 根据ID更新父亲节点ID
     * @param tagId
     * @param pId
     * @throws SSException
     */
    public void updatePid(int tagId, Integer pId) throws SSException;

    /**
     * 根据ID查询tag
     * @param tagId
     * @return
     * @throws SSException
     */
    public Tag queryById(int tagId) throws SSException;

    /**
     * 查询所有tag列表
     * TagDTO的初始化查询方法
     * @return
     * @throws SSException
     */
    public List<Tag> listAll() throws SSException;

    /**
     * 根据分页获取分类列表
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Tag> listByPage(int curPage, int pageSize) throws SSException;

    /**
     * 获取分类总数
     * @return
     * @throws SSException
     */
    public int countAll() throws SSException;

}
