package com.emenu.service.party.group.supplier;

import com.emenu.common.entity.party.group.supplier.Supplier;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 供货商Service
 *
 * @author: zhangteng
 * @time: 2015/11/7 16:16
 **/
public interface SupplierService {

    /**
     * 添加一个供货商
     *
     * @param supplier
     * @param optPartyId    操作人的id
     * @throws SSException
     */
    public Supplier newSupplier(Supplier supplier,
                                Integer optPartyId) throws SSException;

    /**
     * 更新
     *
     * @param supplier
     * @throws SSException
     */
    public void updateSupplier(Supplier supplier) throws SSException;

    /**
     * 获取全部供货商
     *
     * @return
     * @throws SSException
     */
    public List<Supplier> listAll() throws SSException;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Supplier queryById(int id) throws SSException;

    /**
     * 根据id删除
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 查询名称是否存在
     *
     * @param name
     * @param id
     * @return
     * @throws SSException
     */
    public boolean queryNameIsExistById(String name, Integer id) throws SSException;
}
