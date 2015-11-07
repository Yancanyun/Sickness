package com.emenu.service.table;

import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.table.Area;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * AreaService
 *
 * @author: yangch
 * @time: 2015/10/23 9:22
 */
public interface AreaService {
    /**
     * 查询全部区域
     * @return List<Area>
     * @throws SSException
     */
    public List<Area> listAll() throws SSException;

    /**
     * 根据ID查询区域
     * @param id
     * @return Area
     * @throws SSException
     */
    public Area queryById(int id) throws SSException;

    /**
     * 根据ID查询区域状态
     * @param id
     * @return int : 1、可用；2、已删除
     * @throws SSException
     */
    public int queryStateById(int id) throws SSException;

    /**
     * 添加区域
     * @param area
     * @return Area
     * @throws SSException
     */
    public Area newArea(Area area) throws SSException;

    /**
     * 检查是否有重复的区域名称存在
     * @param name
     * @return boolean : true: 存在；false：不存在
     * @throws SSException
     */
    public boolean checkNameIsExist(String name) throws SSException;

    /**
     * 修改区域
     * @param id
     * @param area
     * @throws SSException
     */
    public void updateArea(int id, Area area) throws SSException;

    /**
     * 删除区域
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 查询区域及其拥有的餐台
     * @return
     * @throws SSException
     */
    public List<AreaDto> listDto() throws SSException;
}