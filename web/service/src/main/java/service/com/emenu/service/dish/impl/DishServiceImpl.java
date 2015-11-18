package com.emenu.service.dish.impl;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.tag.DishDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.emenu.service.dish.DishService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜品Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/17 16:27
 **/
@Service("dishService")
public class DishServiceImpl implements DishService {

//    @Autowired
//    private DishMapper dishMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public List<Dish> listAll() throws SSException {
        return null;
    }

    @Override
    public List<Dish> listByPageAndSearchDto(int pageNo, int pageSize, DishSearchDto searchDto) throws SSException {
        return null;
    }

    @Override
    public List<Dish> countBySearchDto(DishSearchDto searchDto) throws SSException {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newDish(DishDto dishDto) throws SSException {

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateDish(DishDto dishDto) throws SSException {

    }

    @Override
    public void updateStatusById(int id, DishStatusEnums statusEnums) throws SSException {

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {

    }

    @Override
    public DishDto queryById(int id) throws SSException {
        return null;
    }
}
