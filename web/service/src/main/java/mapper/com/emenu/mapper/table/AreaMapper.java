package com.emenu.mapper.table;

import com.emenu.common.entity.table.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AreaMapper
 *
 * @author: yangch
 * @time: 2015/10/23 9:48
 */
public interface AreaMapper {

    /**
     * 查询全部区域
     * @return List<Area>
     */
    public List<Area> listAllArea();

    /**
     * 据ID查询区域状态
     * @param id
     * @return int : 0、可用；1、已删除
     */
    public int queryAreaStateById(@Param("id") int id);

    /**
     * 检查是否有重复的区域名称存在
     * @param name
     * @return int : 0、未重复；1、重复
     */
    public int checkAreaName(@Param("name") String name);

    /**
     * 根据ID删除区域（假删）
     * @param id
     */
    public void delTableById(@Param("id") int id);
}
