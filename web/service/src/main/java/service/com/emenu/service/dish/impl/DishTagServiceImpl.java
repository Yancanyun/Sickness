package com.emenu.service.dish.impl;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishTagDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.DishTag;
import com.emenu.common.enums.TrueEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.DishTagMapper;
import com.emenu.service.dish.DishPackageService;
import com.emenu.service.dish.DishService;
import com.emenu.service.dish.DishTagService;
import com.emenu.service.dish.tag.TagFacadeService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private DishService dishService;

    @Autowired
    private DishPackageService dishPackageService;

    @Autowired
    private TagFacadeService tagFacadeService;

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
    public void newByTagId(int tagId, Integer[] dishIds) throws SSException {
        try {
            Assert.lessOrEqualZero(tagId, EmenuException.TagIdError);
            if (Assert.isNull(dishIds)
                    || dishIds.length < 0) {
                return ;
            }

            List<DishTag> dishTagList = new ArrayList<DishTag>();
            for (Integer dishId : dishIds) {
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

    @Override
    public List<Dish> listDishByTagId(int tagId) throws SSException {
        List<Dish> dishList = Collections.emptyList();
        try {
            List<Integer> dishIdList = dishTagMapper.listDishIdByTagId(tagId);
            DishSearchDto searchDto = new DishSearchDto();
            searchDto.setDishIdList(dishIdList);
             dishList = dishService.listBySearchDto(searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTagQueryFiled, e);
        }
        return dishList;
    }

    @Override
    public List<DishTagDto> listDtoByTagId(int tagId) throws SSException {
        List<DishTagDto> dishTagDtoList = Collections.emptyList();
        try {
            dishTagDtoList = dishTagMapper.listDtoByTagId(tagId);
            // 把菜品总分类及小类名称加进去
            for (DishTagDto dishTagDto: dishTagDtoList) {
                dishTagDto.setCategoryNameStr(tagFacadeService.queryById(dishTagDto.getDishCategoryId()).getName());
                dishTagDto.setTagNameStr(tagFacadeService.queryById(dishTagDto.getTagId()).getName());
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTagQueryFiled, e);
        }

        return dishTagDtoList;
    }

    @Override
    public List<Dish> listNotSelectedByTagId(int tagId, List<Integer> searchTagIdList) throws SSException {
        List<Dish> dishList = new ArrayList<Dish>();
        try {
            // 先查询分类下已有的菜品
            List<Integer> selectedDishIdList = dishTagMapper.listDishIdByTagId(tagId);

            // 根据搜索分类查询菜品
            DishSearchDto searchDto = new DishSearchDto();
            searchDto.setTagIdList(searchTagIdList);
            dishList.addAll(dishService.listBySearchDto(searchDto));
            dishList.addAll(dishPackageService.listBySearchDto(searchDto)); // 套餐
            // 把菜品总分类及小类名称加进去
            for (Dish dish: dishList) {
                dish.setCategoryNameStr(tagFacadeService.queryById(dish.getCategoryId()).getName());
                dish.setTagNameStr(tagFacadeService.queryById(dish.getTagId()).getName());
            }

            // 去掉已有的菜品
            Map<Integer, Boolean> dishIdMap = new HashMap<Integer, Boolean>();
            for (Integer dishId : selectedDishIdList) {
                dishIdMap.put(dishId, true);
            }
            Iterator<Dish> dishIterator = dishList.iterator();
            while (dishIterator.hasNext()) {
                Dish dish = dishIterator.next();
                if (dishIdMap.containsKey(dish.getId())) {
                    dishIterator.remove();
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishTagQueryFiled, e);
        }
        return dishList;
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
