package com.emenu.mapper.stock;

import com.emenu.common.entity.stock.Specifications;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by apple on 17/2/27.
 */
public interface SpecificationsMapper {
    /**
     * 增加一条规格信息
     *
     * @param specifications
     * @throws SSException
     */
    public void add(@Param("spec") Specifications specifications) throws SSException;

    /**
     * 根据id删除一条规格信息
     *
     * @param id
     * @throws SSException
     */
    public void deleteById(@Param("id") int id) throws SSException;

    /**
     * 更改一条规格信息
     *
     * @param id
     * @param specifications
     * @throws SSException
     */
    public void update(@Param("id") Integer id, @Param("spec") Specifications specifications) throws SSException;

    /**
     * 根据id查询一条规格信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Specifications queryById(@Param("id") int id) throws SSException;

    /**
     * 显示所有规格信息
     *
     * @return
     * @throws SSException
     */
    public List<Specifications> listAll() throws SSException;
}
