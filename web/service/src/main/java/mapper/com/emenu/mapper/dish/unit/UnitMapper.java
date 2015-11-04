package com.emenu.mapper.dish.unit;

import com.emenu.common.entity.dish.unit.Unit;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UnitMapper
 *
 * @author xubaorong
 * @date 2015/10/23.
 */
public interface UnitMapper {

    /**
     * 获取所有单位
     * @throws SSException
     */
    public List<Unit> listAll() throws SSException;

    /**
     * 根据分页显示获取列表
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Unit> listByPage(@Param("offset")int offset,
                               @Param("pageSize")int pageSize) throws SSException;

    /**
     * 获取单位总数
     * @return
     * @throws SSException
     */
    public int countAll() throws SSException;

    /**
     * 根据id查询一条单位信息
     * @param id
     * @throws SSException
     */
    public Unit queryById(@Param("id")int id)throws SSException;

    /**
     * 检查是否有重名
     * @param name
     * @return
     * @throws SSException
     */
    public int checkNameIsExist(@Param("name") String name,
                                @Param("oldname") String oldname) throws SSException;
}
