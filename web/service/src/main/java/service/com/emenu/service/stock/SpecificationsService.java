package com.emenu.service.stock;


import com.emenu.common.entity.stock.Specifications;

/**
 * Created by apple on 17/2/27.
 */
public interface SpecificationsService {

    /**
     * 增加一条规格信息
     * @param specifications
     * @throws Exception
     */
    public void add(Specifications specifications) throws Exception;

    /**
     * 根据id删除一条规格信息
     * @param id
     * @throws Exception
     */
    public void deleteById(int id) throws Exception;

    /**
     * 根据id更改规格信息
     * @param id
     * @param specifications
     * @throws Exception
     */
    public void update(Integer id, Specifications specifications) throws Exception;

    /**
     * 根据id查询规格信息
     * @param id
     * @return
     * @throws Exception
     */
    public Specifications queryById(int id) throws Exception;

}
