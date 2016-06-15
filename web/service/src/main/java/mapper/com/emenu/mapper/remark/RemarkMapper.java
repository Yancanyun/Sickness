package com.emenu.mapper.remark;

import com.emenu.common.entity.remark.Remark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RemarkMapper
 *
 * @author: yangch
 * @time: 2015/11/7 15:40
 */
public interface RemarkMapper {
    /**
     * 查询全部备注(仅包含备注表本身的信息)
     * @return List<Remark>
     * @throws Exception
     */
    public List<Remark> listAll() throws Exception;

    /**
     * 根据备注分类ID查询备注(仅包含备注表本身的信息)
     * @param remarkTagId
     * @return List<Remark>
     * @throws Exception
     */
    public List<Remark> listByRemarkTagId(@Param("remarkTagId") int remarkTagId) throws Exception;

    /**
     * 查询某备注名称的数量
     * @param name
     * @return int
     * @throws Exception
     */
    public int countByName(@Param("name") String name) throws Exception;

    /**
     * 根据备注分类ID查询备注分类内备注的数量
     * @param remarkTagId
     * @return int
     * @throws Exception
     */
    public int countByRemarkTagId(@Param("remarkTagId") int remarkTagId) throws Exception;

    /**
     * 根据ID修改备注状态
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatus(@Param("id") int id, @Param("status") int status) throws Exception;

    /**
     * 根据菜品ID来查询菜品备注
     * @param dishId
     * @return int
     * @throws Exception
     */
    public List<String> queryDishRemarkByDishId(@Param("dishId")int dishId) throws Exception;
}
