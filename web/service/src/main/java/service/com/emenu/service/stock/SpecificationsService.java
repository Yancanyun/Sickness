package com.emenu.service.stock;


import com.emenu.common.entity.stock.Specifications;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * Created by apple on 17/2/27.
 */
public interface SpecificationsService {

    /**
     * 增加一条规格信息
     *
     * @param specifications
     * @throws SSException
     */
    public void add(Specifications specifications) throws SSException;

    /**
     * 根据id删除一条规格信息
     *
     * @param id
     * @throws SSException
     */
    public void deleteById(int id) throws SSException;

    /**
     * 根据id更改规格信息
     *
     * @param specifications
     * @throws SSException
     */
    public void update(Specifications specifications) throws SSException;

    /**
     * 根据id查询规格信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Specifications queryById(int id) throws SSException;

    /**
     * 显示所有规格信息
     *
     * @return
     * @throws SSException
     */
    public List<Specifications> listAll() throws SSException;

    /**
     * 查询分页信息
     *
     * @return
     * @throws SSException
     */
    public List<Specifications> listByPage(int offset,int pageSize) throws SSException;

    /**
     * 获取数据总量
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;


}
