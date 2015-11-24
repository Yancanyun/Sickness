package com.emenu.service.dish.impl;

import com.emenu.common.entity.dish.DishTag;
import com.emenu.common.enums.TrueEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.DishTagMapper;
import com.emenu.service.dish.DishTagService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜品-分类Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/23 09:31
 **/
@Service("dishTagService")
public class DishTagServiceImpl implements DishTagService {

    @Autowired
    private DishTagMapper dishTagMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public void newDishTag(DishTag dishTag) throws SSException {
        try {
            if (!checkBeforeSave(dishTag)) {
                return ;
            }
            commonDao.insert(dishTag);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTagInsertFailed, e);
        }
    }

    @Override
    public void updateFirstTag(int dishId, int tagId) throws SSException {
        try {
            Assert.lessOrEqualZero(dishId, EmenuException.DishIdError);
            Assert.lessOrEqualZero(tagId, EmenuException.TagIdError);

            dishTagMapper.updateFirstTag(dishId, tagId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTagUpdateFailed, e);
        }
    }

    @Override
    public void newByTagId(int tagId, List<Integer> dishIdList) throws SSException {
        try {
            Assert.lessOrEqualZero(tagId, EmenuException.TagIdError);
            if (Assert.isEmpty(dishIdList)) {
                return ;
            }

            List<DishTag> dishTagList = new ArrayList<DishTag>();
            for (Integer dishId : dishIdList) {
                DishTag dishTag = new DishTag();
                dishTag.setDishId(dishId);
                dishTag.setTagId(tagId);
                dishTag.setIsFirstTag(TrueEnums.False.getId());

                dishTagList.add(dishTag);
            }

            commonDao.insertAll(dishTagList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTagInsertFailed, e);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            commonDao.deleteById(DishTag.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTagDeleteFailed, e);
        }
    }

    private boolean checkBeforeSave(DishTag dishTag) throws SSException {
        if (Assert.isNull(dishTag)) {
            return false;
        }

        if (Assert.isNull(dishTag.getDishId())
                || Assert.lessOrEqualZero(dishTag.getDishId())) {
            throw SSException.get(EmenuException.DishIdError);
        }
        if (Assert.isNull(dishTag.getTagId())
                || Assert.lessOrEqualZero(dishTag.getTagId())) {
            throw SSException.get(EmenuException.TagIdError);
        }

        Assert.isNotNull(dishTag.getIsFirstTag(), EmenuException.IsFirstTagNotNull);

        return true;
    }
}
