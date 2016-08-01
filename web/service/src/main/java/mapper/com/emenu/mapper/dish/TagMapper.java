package com.emenu.mapper.dish;

import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.Tag;
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
    public List<Tag> listAll() throws Exception;

    /**
     * 根据分页显示获取列表
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Tag> listByPage(@Param("offset") int offset,
                                @Param("pageSize") int pageSize) throws Exception;

    /**
     * 获取单位总数
     * @return
     * @throws SSException
     */
    public int countAll() throws Exception;

    /**
     * 批量删除Tag
     * @param ids
     * @throws SSException
     */
    public void delByIds(@Param("ids") List<Integer> ids) throws Exception;

    /**
     * 根据ID获取子节点个数
     * @param id
     * @return
     * @throws SSException
     */
    public int countChildrenById(@Param("id") int id) throws Exception;

    /**
     * 根据菜品id获取菜品分类
     * @param dishId
     * @return
     * @throws Exception
     */
    public Tag queryDishTagByDishId(@Param("dishId") int dishId) throws Exception;

    /**
     * 查找二级分类（菜品大类）
     * @return
     * @throws Exception
     */
    public List<Tag> listSecondTag() throws Exception;

    /**
     * 根据tagname获取tag
     * @param name
     * @return
     * @throws SSException
     */
    public Tag queryByName(String name) throws SSException;

    /**
     * 根据父id获取taglist
     * @param parentId
     * @return
     * @throws SSException
     */
    public List<Tag> listByParentId(int parentId) throws SSException;

    /**
     * 根据父id和name查询tag
     * @param pId
     * @param name
     * @return
     * @throws Exception
     */
    public Tag queryByPidAndName(@Param("pId") int pId, @Param("name") String name) throws Exception;
}
