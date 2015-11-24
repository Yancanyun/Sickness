package com.emenu.service.dish.impl;

import com.emenu.common.entity.dish.DishMealPeriod;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.DishMealPeriodMapper;
import com.emenu.service.dish.DishMealPeriodService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 菜品-餐段Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/24 14:14
 **/
@Service("dishMealPeriodService")
public class DishMealPeriodServiceImpl implements DishMealPeriodService {

    @Autowired
    private DishMealPeriodMapper dishMealPeriodMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public void newDishMealPeriod(int dishId, List<Integer> mealPeriodIdList) throws SSException {
        try {
            Assert.lessOrEqualZero(dishId, EmenuException.DishIdError);
            if (Assert.isEmpty(mealPeriodIdList)) {
                return ;
            }

            List<DishMealPeriod> dishMealPeriodList = new ArrayList<DishMealPeriod>();
            for (Integer mealPeriodId : mealPeriodIdList) {
                DishMealPeriod dishMealPeriod = new DishMealPeriod();
                dishMealPeriod.setDishId(dishId);
                dishMealPeriod.setMealPeriodId(mealPeriodId);

                dishMealPeriodList.add(dishMealPeriod);
            }

            commonDao.insertAll(dishMealPeriodList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishMealPeriodInsertFailed, e);
        }
    }

    @Override
    public void updateDishMealPeriod(int dishId, List<Integer> mealPeriodIdList) throws SSException {
        try {
            Assert.lessOrEqualZero(dishId, EmenuException.DishIdError);
            if (Assert.isEmpty(mealPeriodIdList)) {
                return ;
            }

            // 先删除原来的,再添加
            this.delByDishId(dishId);
            this.newDishMealPeriod(dishId, mealPeriodIdList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishMealPeriodUpdateFailed, e);
        }
    }

    @Override
    public void delByDishId(int dishId) throws SSException {
        if (Assert.lessOrEqualZero(dishId)) {
            return ;
        }
        try {
            dishMealPeriodMapper.delByDishId(dishId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishMealPeriodDeleteFailed, e);
        }
    }

    @Override
    public List<DishMealPeriod> listByDishId(int dishId) throws SSException {
        List<DishMealPeriod> list = Collections.emptyList();
        if (Assert.lessOrEqualZero(dishId)) {
            return list;
        }
        try {
            list = dishMealPeriodMapper.listByDishId(dishId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishMealPeriodQueryFailed, e);
        }
        return list;
    }
}
