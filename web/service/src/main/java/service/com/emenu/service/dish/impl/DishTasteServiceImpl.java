package com.emenu.service.dish.impl;

import com.emenu.common.entity.dish.DishTaste;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.DishTasteMapper;
import com.emenu.service.dish.DishTasteService;
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
 * 菜品口味Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/20 15:15
 **/
@Service("dishTasteService")
public class DishTasteServiceImpl implements DishTasteService {

    private DishTasteMapper dishTasteMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public void newDishTaste(int dishId, List<Integer> tasteIdList) throws SSException {
        try {
            if (Assert.lessOrEqualZero(dishId)) {
                throw SSException.get(EmenuException.DishIdNotNull);
            }
            if (Assert.isEmpty(tasteIdList)) {
                return ;
            }

            List<DishTaste> dishTasteList = new ArrayList<DishTaste>();
            for (Integer tasteId: tasteIdList) {
                DishTaste dishTaste = new DishTaste();
                dishTaste.setDishId(dishId);
                dishTaste.setTasteId(tasteId);
                dishTasteList.add(dishTaste);
            }

            commonDao.insertAll(dishTasteList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTasteInsertFailed, e);
        }
    }

    @Override
    public void newDishTaste(DishTaste dishTaste) throws SSException {
        try {
            if (!checkBeforeSave(dishTaste)) {
                return ;
            }
            commonDao.insert(dishTaste);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTasteInsertFailed, e);
        }
    }

    @Override
    public void updateDishTaste(DishTaste dishTaste) throws SSException {
        try {
            if (!checkBeforeSave(dishTaste)) {
                return ;
            }
            Assert.lessOrEqualZero(dishTaste.getId(), EmenuException.DishTasteIdError);

            commonDao.update(dishTaste);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTasteUpdateFailed, e);
        }
    }

    @Override
    public void updateDishTaste(int dishId, List<Integer> tasteIdList) throws SSException {
        try {
            if (Assert.lessOrEqualZero(dishId)) {
                throw SSException.get(EmenuException.DishIdNotNull);
            }
            if (Assert.isEmpty(tasteIdList)) {
                return;
            }
            // 先删除原来的
            this.delByDishId(dishId);
            // 再添加
            this.newDishTaste(dishId, tasteIdList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTasteUpdateFailed, e);
        }
    }

    @Override
    public void delByDishId(int dishId) throws SSException {
        if (Assert.lessOrEqualZero(dishId)) {
            return ;
        }
        try {
            dishTasteMapper.delByDishId(dishId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTasteDeleteFailed, e);
        }
    }

    @Override
    public List<DishTaste> listByDishId(int dishId) throws SSException {
        List<DishTaste> list = Collections.emptyList();
        if (Assert.lessOrEqualZero(dishId)) {
            return list;
        }
        try {
            list = dishTasteMapper.listByDishId(dishId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTasteQueryFailed, e);
        }
        return list;
    }

    private boolean checkBeforeSave(DishTaste dishTaste) throws SSException {
        if (Assert.isNull(dishTaste)) {
            return false;
        }

        if (Assert.isNull(dishTaste.getDishId())
                || Assert.lessOrEqualZero(dishTaste.getDishId())) {
            throw SSException.get(EmenuException.DishIdError);
        }
        if (Assert.isNull(dishTaste.getTasteId())
                || Assert.lessOrEqualZero(dishTaste.getTasteId())) {
            throw SSException.get(EmenuException.DishTasteIdError);
        }

        return true;
    }
}
