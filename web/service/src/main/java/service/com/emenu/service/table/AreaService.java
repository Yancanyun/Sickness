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
    public List<Area> listAllArea() throws SSException;

    /**
     * 根据ID查询区域
     * @return Area
     * @throws SSException
     */
    public Area queryAreaById(int id) throws SSException;

    /**
     * 根据ID查询区域状态
     * @param id
     * @return int : 0、可用；1、已删除
     * @throws SSException
     */
    public int queryAreaStateById(int id) throws SSException;

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
    public boolean checkAreaName(String name) throws SSException;

    /**
     * 修改区域
     * @param area
     * @throws SSException
     */
    public void updateArea(Area area) throws SSException;

    /**
     * 删除区域
     * @param id
     * @throws SSException
     */
    public void delAreaById(int id) throws SSException;

    /**
     * 查询区域及其拥有的餐台
     * @return
     * @throws SSException
     */
    public List<AreaDto> listAreaAndTable() throws SSException;
}