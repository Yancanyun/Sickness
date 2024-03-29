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
     * 分页获取数据
     * @return
     * @throws SSException
     */
    public List<Taste> listByPage(@Param("offset") int offset,
                                  @Param("pageSize") int pageSize) throws Exception;

    /**
     * 获取全部数据
     * @return
     * @throws SSException
     */
    public List<Taste> listAll() throws Exception;

    /**
     * 计算口味总数
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
