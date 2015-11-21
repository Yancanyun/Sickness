package com.emenu.service.dish.tag;

import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.Tag;
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
    public void updateTag(Tag tag) throws Exception;

    /**
     * 根据Id查询
     * @param tagId
     * @return
     * @throws SSException
     */
    public Tag queryById(int tagId) throws Exception;

    /**
     * 根据tagId获取儿子节点
     * @param tagId
     * @return
     * @throws SSException
     */
    public List<Tag> listChildrenByTagId(int tagId) throws Exception;

    /**
     * 删除一个tag
     * @param tagId
     * @throws SSException
     */
    public void delById(int tagId) throws Exception;

    /**
     * 根据ID获取当前ID展开的Tag
     * 嵌套List
     * @param tagId
     * @return
     * @throws Exception
     */
    public List<TagDto> listByCurrentId(int tagId) throws Exception;

    /**
     * 根据tagId和常量参数获取所有子节点
     * 如果常量级数为2只获取二级菜单
     * 如果常量级数为3只获取三级菜单
     * @param tagId
     * @return
     * @throws SSException
     */
    public List<Tag> listAllByTagId(int tagId) throws Exception;

    /**
     * 获取菜品的分类List
     * 菜品分类后台管理专用方法
     * @param tagId
     * @return List<TagDto>
     * @throws Exception
     */
    public List<TagDto> listDishByCurrentId(Integer tagId) throws Exception;

    /**
     * 给打印机显示菜品分类的列表，不用分层级
     * 只显示最后两级分类
     * @return List<Tag>
     * @throws Exception
     */
    public List<Tag> listDishTagForPrinter() throws Exception;

    /**
     * 新增菜品分类以及打印机关联
     * @param tag
     * @param printerId
     * @throws SSException
     */
    public Tag newTagPrinter(Tag tag, Integer printerId) throws Exception;

    /**
     * 修改菜品分类以及打印机关联
     * @param tag
     * @param printerId
     * @throws SSException
     */
    public void updateTagPrinter(Tag tag, Integer printerId) throws Exception;

    /**
     * 删除菜品分类以及打印机关联
     * @param tagId
     * @throws SSException
     */
    public void delTagPrinter(int tagId) throws Exception;

}
