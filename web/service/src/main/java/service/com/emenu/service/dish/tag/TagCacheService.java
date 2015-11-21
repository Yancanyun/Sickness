package com.emenu.service.dish.tag;

import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.Tag;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * TagCacheService
 * 菜品原料类别缓存管理
 * @author dujuan
 * @date 2015/10/22
 */
public interface TagCacheService {

    /**
     * 初始化类别缓存,在容器启动时自动执行
     * @throws SSException
     */
    public void initCache() throws SSException;

    /**
     * 刷新类别缓存
     * @throws Exception
     */
    public void refreshCache() throws SSException;

    /**
     * 根据TagId获取上一级（父亲）Tag
     * @param tagId
     * @return
     * @throws SSException
     */
    public Tag queryParentById(int tagId) throws SSException;

    /**
     * 根据tagId得到儿子节点列表——按权重排序
     * @param tagId
     * @return
     * @throws SSException
     */
    public List<Tag> listChildrenById(int tagId) throws SSException;

    /**
     * 根据TagId得到所有孙子节点——按权重排序
     * @param tagId
     * @return
     * @throws Exception
     */
    public List<Tag> listGrandsonById(int tagId) throws SSException;

    /**
     * 根据tagId查询根节点
     * @param tagId
     * @return Tag
     * @throws SSException
     */
    public Tag queryRootById(int tagId) throws SSException;

    /**
     * 获得所有根Tag——按权重排序
     * type为0时候表示获得所有根节点
     * @return List<Tag>
     * @throws Exception
     */
    public List<Tag> listAllRoot() throws SSException;

    /**
     * 获得所有Tag
     * @return
     * @throws Exception
     */
    public List<Tag> listAll() throws SSException;

    /**
     * 由当前节点获取展开tag
     * @param tagId
     * @return List<Tag>
     * @throws SSException
     */
    public List<Tag> listByCurrentId(int tagId) throws SSException;

    /**
     * 由当前节点获取展开tag组成嵌套List
     * @param tagId
     * @return List<TagDto>
     * @throws Exception
     */
    public List<TagDto> listDtoByCurrentId(int tagId) throws SSException;

    /**
     * 根据id查找tag,返回tag的clone对象，防止直接修改内存数据
     * @param tagId
     * @return Tag
     * @throws Exception
     */
    public Tag queryCloneById(int tagId) throws SSException;

    /**
     * 添加一个Tag——添加到缓存中
     * 使用时需要先添加到数据库获得返回的id
     * @param tag
     * @return Tag
     * @throws Exception
     */
    public Tag newTag(Tag tag) throws SSException;

    /**
     * 删除一个tag
     * @param tagId
     * @throws SSException
     */
    public void delById(int tagId) throws SSException;

    /**
     * 根据Id修改Tag
     * @param tag
     * @throws Exception
     */
    public void updateTag(Tag tag) throws SSException;

    /**
     * 根据ID联级删除Tag
     * @param tagId
     * @throws SSException
     */
    public void delCascadeById(int tagId) throws SSException;

    /**
     * 查询tag的祖先路径
     * @param tagId
     * @return
     * @throws Exception
     */
    public List<Tag> listPathById(int tagId) throws SSException;

}
