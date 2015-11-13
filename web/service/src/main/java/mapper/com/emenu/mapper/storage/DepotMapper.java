package com.emenu.mapper.storage;

import com.emenu.common.entity.storage.Depot;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DepotMapper
 * 存放点mapper层
 *
 * @author xubr
 * @date 2015/11/10.
 */
public interface DepotMapper {

    /**
     * 查询所有存放点
     *
     * @return
     * @throws SSException
     */
    public List<Depot> listAll() throws SSException;

    /**
     * 分页获取存放点
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Depot> listByPage(@Param("offset") int offset,
                                 @Param("pageSize") int pageSize) throws SSException;

    /**
     * 根据名称查找存放点
     *
     * @param name
     * @return
     * @throws SSException
     */
    public Depot queryByName(@Param("name") String name) throws SSException;

    /**
     * 获取存放点总数
     *
     * @return
     * @throws SSException
     */
    public int countAll() throws SSException;

}
