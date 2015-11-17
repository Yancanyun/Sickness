package com.emenu.mapper.storage;

import com.emenu.common.entity.storage.StorageDepot;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StorageDepotMapper
 * 存放点mapper层
 *
 * @author xubr
 * @date 2015/11/10.
 */
public interface StorageDepotMapper {

    /**
     * 查询所有存放点
     *
     * @return
     * @throws SSException
     */
    public List<StorageDepot> listAll() throws SSException;

    /**
     * 分页获取存放点
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<StorageDepot> listByPage(@Param("offset") int offset,
                                 @Param("pageSize") int pageSize) throws SSException;

    /**
     * 根据id查找存放点
     * @param id
     * @return
     * @throws SSException
     */
    public StorageDepot queryById(@Param("id") int id) throws SSException;

    /**
     * 根据名称查找存放点
     *
     * @param name
     * @return
     * @throws SSException
     */

    public StorageDepot queryByName(@Param("name") String name) throws SSException;

    /**
     * 获取存放点总数
     *
     * @return
     * @throws SSException
     */
    public int countAll() throws SSException;

    /**
     * 检查存放点名称是否重名
     * @param name
     * @return
     * @throws SSException
     */
    public int checkNameIsExist(@Param("name")String name) throws SSException;

    /**
     * 检查存放点名称是否冲突
     * @param name
     * @param id
     * @return
     * @throws SSException
     */
    public int checkNameIsConflict(@Param("name")String name,@Param("id")int id) throws SSException;
}
