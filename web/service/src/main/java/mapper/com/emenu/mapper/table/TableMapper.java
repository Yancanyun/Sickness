package com.emenu.mapper.table;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TableMapper
 *
 * @author: yangch
 * @time: 2015/10/23 10:36
 */
public interface TableMapper {
    /**
     * 查询全部餐台（仅餐台表本身的信息）
     * @return List<Table>
     */
    public List<Table> listAll() throws Exception;

    /**
     * 根据区域ID查询餐台（仅餐台表本身的信息）
     * @param areaId
     * @return List<Table>
     */
    public List<Table> listByAreaId(@Param("areaId") int areaId) throws Exception;

    /**
     * 根据状态查询餐台（仅餐台表本身的信息）
     * @param state
     * @return List<Table>
     */
    public List<Table> listByState(@Param("state") int state) throws Exception;

    /**
     * 根据区域及状态查询餐台（仅餐台表本身的信息）
     * @param areaId
     * @param state
     * @return List<Table>
     */
    public List<Table> listByAreaIdAndState(@Param("areaId") int areaId, @Param("state") int state) throws Exception;

    /**
     * 根据ID查询餐台状态
     * @param id
     * @return int : 0、停用；1、可用；2、占用已结账；3、占用未结账4、已并桌；5、已预订；6、已删除
     */
    public int queryStateById(@Param("id") int id) throws Exception;

    /**
     * 查询某餐台名称的数量
     * @param name
     * @return int
     */
    public int countByName(@Param("name") String name) throws Exception;

    /**
     * 根据区域ID查询区域内餐台的数量
     * @param areaId
     * @return int
     */
    public int countByAreaId(@Param("areaId") int areaId) throws Exception;

    /**
     * 根据ID修改餐台状态
     * @param id
     * @param state
     * @throws Exception
     */
    public void updateState(@Param("id") int id, @Param("state") int state) throws Exception;
}
