package com.emenu.mapper.dish;

import com.emenu.common.entity.dish.Taste;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TasteMapper
 *
 * @author dujuan
 * @date 2015/11/23
 */

public interface TasteMapper {

    /**
     * 获取所有数据
     * @return
     * @throws SSException
     */
    public List<Taste> listAll(@Param("offset") int offset,
                               @Param("pageSize") int pageSize) throws SSException;

    /**
     * 计算口味总数
     * @return
     * @throws SSException
     */
    public int countAll() throws SSException;

    /**
     * 检查是否有重名
     * @param name
     * @return
     * @throws SSException
     */
    public int checkNameIsExist(@Param("name") String name,
                                @Param("oldname") String oldname) throws SSException;
}
