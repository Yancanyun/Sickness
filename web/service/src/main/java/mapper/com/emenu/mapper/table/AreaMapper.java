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
     * @throws Exception
     */
    public List<Area> listAll() throws Exception;

    /**
     * 根据ID查询区域状态
     * @param id
     * @return int : 1、可用；2、已删除
     * @throws Exception
     */
    public int queryStatusById(@Param("id") int id) throws Exception;

    /**
     * 查询某区域名称的数量
     * @param name
     * @return int
     * @throws Exception
     */
    public int countByName(@Param("name") String name) throws Exception;

    /**
     * 根据ID修改区域状态
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatus(@Param("id") int id, @Param("status") int status) throws Exception;
}
