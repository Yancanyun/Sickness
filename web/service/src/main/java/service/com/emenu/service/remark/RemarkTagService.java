package com.emenu.service.remark;

import com.emenu.common.entity.remark.RemarkTag;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * RemarkTagService
 *
 * @author: yangch
 * @time: 2015/11/7 13:40
 */
public interface RemarkTagService {
    /**
     * 查询全部备注分类
     * @return List<RemarkTag>
     * @throws SSException
     */
    public List<RemarkTag> listAll() throws SSException;

    /**
     * 根据ID查询备注分类
     * @param id
     * @return RemarkTag
     * @throws SSException
     */
    public RemarkTag queryById(int id) throws SSException;

    /**
     * 添加备注分类
     * @param remarkTag
     * @return
     * @throws SSException
     */
    public RemarkTag newRemarkTag (RemarkTag remarkTag) throws SSException;

    /**
     * 修改备注分类
     * @param id
     * @param remarkTag
     * @throws SSException
     */
    public void updateRemarkTag(int id, RemarkTag remarkTag) throws SSException;

    /**
     * 删除备注分类
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 检查是否有重复的备注分类名称存在
     * @param name
     * @return boolean : true: 存在；false：不存在
     * @throws SSException
     */
    public boolean checkNameIsExist(String name) throws SSException;
}
