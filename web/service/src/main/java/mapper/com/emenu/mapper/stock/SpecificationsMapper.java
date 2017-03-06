package com.emenu.mapper.stock;

import com.emenu.common.entity.stock.Specifications;
import org.apache.ibatis.annotations.Param;

/**
 * Created by apple on 17/2/27.
 */
public interface SpecificationsMapper {
    /**
     * 增加一条规格信息
     *
     * @param specifications
     * @throws Exception
     */
    public void add(@Param("spec") Specifications specifications) throws Exception;

    /**
     * 根据id删除一条规格信息
     *
     * @param id
     * @throws Exception
     */
    public void deleteById(@Param("id") int id) throws Exception;

    /**
     * 更改一条规格信息
     * @param id
     * @param specifications
     * @throws Exception
     */
    public void update(@Param("id") Integer id, @Param("spec") Specifications specifications) throws Exception;

    /**
     * 根据id查询一条规格信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Specifications queryById(@Param("id") int id) throws Exception;
}
