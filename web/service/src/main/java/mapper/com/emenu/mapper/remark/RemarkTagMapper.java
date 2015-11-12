package com.emenu.mapper.remark;

import com.emenu.common.entity.remark.RemarkTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RemarkTagMapper
 *
 * @author: yangch
 * @time: 2015/11/7 13:48
 */
public interface RemarkTagMapper {
    /**
     * 查询全部备注分类
     * @return List<RemarkTag>
     * @throws Exception
     */
    public List<RemarkTag> listAll() throws Exception;

    /**
     * 查询某备注分类名称的数量
     * @param name
     * @return int
     * @throws Exception
     */
    public int countByName(@Param("name") String name) throws Exception;

    /**
     * 根据ID修改备注分类状态
     * @param id
     * @param state
     * @throws Exception
     */
    public void updateState(@Param("id") int id, @Param("state") int state) throws Exception;

    /**
     * 根据父ID查询子分类
     * @param pId
     * @return
     * @throws Exception
     */
    public List<RemarkTag> listByParentId(@Param("pId") int pId) throws Exception;
}
