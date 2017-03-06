package com.emenu.service.stock.impl;

import com.emenu.common.entity.stock.Specifications;
import com.emenu.mapper.stock.SpecificationsMapper;
import com.emenu.service.stock.SpecificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by apple on 17/2/27.
 */
@Service("SpecificationsService")
public class SpecificationsServiceImpl implements SpecificationsService {

    @Autowired
    private SpecificationsMapper specificationsMapper;

    @Override
    public void add(Specifications specifications) throws Exception {
        if (specifications == null) return;

        try {
            specificationsMapper.add(specifications);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) throws Exception {
        if (id <= 0) return;

        try {
            specificationsMapper.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void update(Integer id, Specifications specifications) throws Exception {
        if (specifications == null) return;
        if (id <= 0) return;

        try {
            specificationsMapper.update(id, specifications);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Specifications queryById(int id) throws Exception {
        if (id <= 0) return null;

        try {
            return specificationsMapper.queryById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Specifications> listAll() throws Exception {
        try {
            return specificationsMapper.listAll();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
