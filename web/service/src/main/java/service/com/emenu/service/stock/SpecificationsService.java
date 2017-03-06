package com.emenu.service.stock;


import com.emenu.common.entity.stock.Specifications;

/**
 * Created by apple on 17/2/27.
 */
public interface SpecificationsService {

    public void add(Specifications specifications) throws Exception;

    public void deleteById(int id) throws Exception;

    public void update(Specifications specifications) throws Exception;

    public Specifications queryById(int id) throws Exception;

}
