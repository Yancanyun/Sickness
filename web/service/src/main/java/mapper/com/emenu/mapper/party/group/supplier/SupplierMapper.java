package com.emenu.mapper.party.group.supplier;

import com.emenu.common.entity.party.group.supplier.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SupplierMapper
 *
 * @author: zhangteng
 * @time: 2015/11/9 11:11
 **/
public interface SupplierMapper {

    /**
     * 获取全部供应商
     *
     * @return
     * @throws Exception
     */
    public List<Supplier> listAll() throws Exception;

    /**
     * 根据id更新状态
     *
     * @param status
     * @param id
     * @throws Exception
     */
    public void updateStatusById(@Param("status") int status,
                                 @Param("id") int id) throws Exception;

    /**
     * 根据名称查询记录数
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int countByNameAndId(@Param("name") String name,
                                @Param("id") int id) throws Exception;
}
