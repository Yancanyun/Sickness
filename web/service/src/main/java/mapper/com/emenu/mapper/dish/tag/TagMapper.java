package com.emenu.mapper.dish.tag;

import com.emenu.common.entity.dish.tag.Tag;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TagMapper
 * 菜品和原料类别Mapper层
 * @author dujuan
 * @date 2015/10/22
 */
public interface TagMapper {
    /**
     * 获取所有的类别
     * @return
     * @throws SSException
     */
    public List<Tag> listTag() throws SSException;

    /**
     * 根据分页显示获取列表
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Tag> listTagByPage(@Param("offset") int offset,
                                   @Param("pageSize") int pageSize) throws SSException;

    /**
     * 获取单位总数
     * @return
     * @throws SSException
     */
    public int countTag() throws SSException;

    /**
     * 批量删除Tag
     * @param ids
     * @throws SSException
     */
    public void delTags(@Param("ids") List<Integer> ids) throws SSException;

}
