package com.emenu.mapper.stock;

import com.emenu.common.entity.stock.Specifications;
import org.apache.ibatis.annotations.Param;

/**
 * Created by apple on 17/2/27.
 */
public interface SpecificationsMapper {
    public void add(@Param("spec") Specifications specifications) throws Exception;

    public void deleteById(@Param("id") int id) throws Exception;

    public void update(@Param("spec") Specifications specifications) throws Exception;

    public Specifications queryById(@Param("id") int id) throws Exception;
}
