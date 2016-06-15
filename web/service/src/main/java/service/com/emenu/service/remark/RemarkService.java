package com.emenu.service.remark;

import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.entity.remark.Remark;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * RemarkService
 *
 * @author: yangch
 * @time: 2015/11/7 15:31
 */
public interface RemarkService {
    /**
     * 查询全部备注(仅包含备注表本身的信息)
     * @return List<Remark>
     * @throws SSException
     */
    public List<Remark> listAll() throws SSException;

    /**
     * 查询全部备注(包含备注分类表的信息)
     * @return List<RemarkDto>
     * @throws SSException
     */
    public List<RemarkDto> listAllRemarkDto() throws SSException;

    /**
     * 根据备注分类ID查询备注(仅包含备注表本身的信息)
     * @param remarkTagId
     * @return List<Remark>
     * @throws SSException
     */
    public List<Remark> listByRemarkTagId(int remarkTagId) throws SSException;

    /**
     * 根据备注分类ID查询备注(包含备注分类表的信息)
     * @param remarkTagId
     * @return List<RemarkDto>
     * @throws SSException
     */
    public List<RemarkDto> listRemarkDtoByRemarkTagId(int remarkTagId) throws SSException;
    
    /**
     * 根据ID查询备注(仅包含备注表本身的信息)
     * @param id
     * @return Remark
     * @throws SSException
     */
    public Remark queryById(int id) throws SSException;

    /**
     * 根据ID查询备注(包含备注分类表的信息)
     * @param id
     * @return RemarkDto
     * @throws SSException
     */
    public RemarkDto queryRemarkDtoById(int id) throws SSException;

    /**
     * 添加备注
     * @param remark
     * @return
     * @throws SSException
     */
    public Remark newRemark (Remark remark) throws SSException;

    /**
     * 修改备注
     * @param id
     * @param remark
     * @throws SSException
     */
    public void updateRemark(Integer id, Remark remark) throws SSException;

    /**
     * 删除备注
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 检查是否有重复的备注名称存在
     * @param name
     * @return boolean : true: 存在；false：不存在
     * @throws SSException
     */
    public boolean checkNameIsExist(String name) throws SSException;

    /**
     * 根据备注分类ID查询备注分类内备注的数量
     * @param remarkTagId
     * @return int
     * @throws SSException
     */
    public int countByRemarkTagId(int remarkTagId) throws SSException;

    /**
     * 根据菜品ID来查询菜品备注
     * @param dishId
     * @return int
     * @throws SSException
     */
    public List<String> queryDishRemarkByDishId(int dishId) throws SSException;
}
