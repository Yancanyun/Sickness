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
    public void initCache() throws Exception;

    /**
     * 刷新类别缓存
     * @throws Exception
     */
    public void refreshCache() throws Exception;

    /**
     * 根据TagId获取上一级（父亲）Tag
     * @param tagId
     * @return
     * @throws SSException
     */
    public Tag queryParentById(int tagId) throws Exception;

    /**
     * 根据tagId得到儿子节点列表——按权重排序
     * @param tagId
     * @return
     * @throws SSException
     */
    public List<Tag> listChildrenById(int tagId) throws Exception;

    /**
     * 根据tagId查询根节点
     * @param tagId
     * @return Tag
     * @throws SSException
     */
    public Tag queryRootById(int tagId) throws Exception;

    /**
     * 获得所有根Tag——按权重排序
     * type为0时候表示获得所有根节点
     * @param type
     * @return List<Tag>
     * @throws Exception
     */
    public List<Tag> listAllRootByType(int type) throws Exception;

    /**
     * 获得所有Tag
     * @return
     * @throws Exception
     */
    public List<Tag> listAll() throws Exception;

    /**
     * 由当前节点获取展开tag
     * @param tagId
     * @return List<Tag>
     * @throws SSException
     */
    public List<Tag> listByCurrentId(int tagId) throws Exception;

    /**
     * 由当前节点获取展开tag组成嵌套List
     * @param tagId
     * @return List<TagDto>
     * @throws Exception
     */
    public List<TagDto> listDtoByCurrentId(int tagId) throws Exception;

    /**
     * 根据id查找tag,返回tag的clone对象，防止直接修改内存数据
     * @param tagId
     * @return Tag
     * @throws Exception
     */
    public Tag queryCloneById(int tagId) throws Exception;

    /**
     * 添加一个Tag——添加到缓存中
     * 使用时需要先添加到数据库获得返回的id
     * @param tag
     * @return Tag
     * @throws Exception
     */
    public Tag newTag(Tag tag) throws Exception;

    /**
     * 删除一个tag
     * @param tagId
     * @throws SSException
     */
    public void delById(int tagId) throws Exception;

    /**
     * 根据Id修改Tag
     * @param tag
     * @throws Exception
     */
    public void updateTag(Tag tag) throws Exception;

    /**
     * 根据ID修改Tag名称
     * @param tagId
     * @param name
     */
    public void updateName(int tagId, String name) throws Exception;

    /**
     * 更改tag权重
     * @param tagId
     * @param weight
     * @throws SSException
     */
    public void updateWeight(int tagId, int weight) throws Exception;

    /**
     * 更改tag的父亲节点
     * @param tagId
     * @param pId
     */
    public void updatePid(int tagId, int pId) throws Exception;

    /**
     * 根据ID联级删除Tag
     * @param tagId
     * @throws SSException
     */
    public void delCascadeById(int tagId) throws Exception;

    /**
     * 查询tag的祖先路径
     * @param tagId
     * @return
     * @throws Exception
     */
    public List<Tag> listPathById(int tagId) throws Exception;

}
