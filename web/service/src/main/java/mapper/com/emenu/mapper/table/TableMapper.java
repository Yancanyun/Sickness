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
    public List<Table> listAllTableItself();

    /**
     * 根据区域ID查询餐台（仅餐台表本身的信息）
     * @param areaId
     * @return List<Table>
     */
    public List<Table> listTableItselfByAreaId(@Param("areaId") int areaId);

    /**
     * 根据状态查询餐台（仅餐台表本身的信息）
     * @param state
     * @return List<Table>
     */
    public List<Table> listTableItselfByState(@Param("state") int state);

    /**
     * 根据区域及状态查询餐台（仅餐台表本身的信息）
     * @param areaId
     * @param state
     * @return List<Table>
     */
    public List<Table> listTableItselfByAreaIdAndState(@Param("areaId") int areaId, @Param("state") int state);

    /**
     * 根据ID查询餐台状态
     * @param id
     * @return int : 0、停用；1、可用；2、占用已结账；3、占用未结账4、已并桌；5、已预订；6、已删除
     */
    public int queryTableStateById(@Param("id") int id);

    /**
     * 检查是否有重复的区域名称存在
     * @param name
     * @return int : 0、未重复；1、重复
     */
    public int checkTableName(@Param("name") String name);

    /**
     * 根据区域ID查询区域内餐台的数量
     * @param areaId
     * @return int
     */
    public int countTableNumByAreaId(@Param("areaId") int areaId);

    /**
     * 根据ID删除餐台（假删）
     * @param id
     */
    public void delTableById(@Param("id") int id);
}
