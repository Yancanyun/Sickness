package com.emenu.mapper.dish;

import com.emenu.common.entity.dish.Unit;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UnitMapper
 * 单位Mapper
 * @author xubaorong
 * @date 2015/10/23.
 */
public interface UnitMapper {

    /**
     * 获取所有单位
     * @throws SSException
     */
    public List<Unit> listAll() throws Exception;

    /**
     * 根据分页显示获取列表
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Unit> listByPage(@Param("offset")int offset,
                                 @Param("pageSize")int pageSize) throws Exception;

    /**
     * 获取单位总数
     * @return
     * @throws SSException
     */
    public int countAll() throws Exception;

    /**
     * 检查是否有重名
     * @param name
     * @return
     * @throws SSException
     */
    public int checkNameIsExist(@Param("name") String name,
                                @Param("oldname") String oldname) throws Exception;
}
