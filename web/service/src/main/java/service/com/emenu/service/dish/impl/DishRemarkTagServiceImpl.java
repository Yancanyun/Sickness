package com.emenu.service.dish.impl;

import com.emenu.common.entity.dish.DishRemarkTag;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.DishRemarkTagMapper;
import com.emenu.service.dish.DishRemarkTagService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DishRemarkTagServiceImpl
 *
 * @author xubaorong
 * @date 2016/6/2.
 */
@Service("dishRemarkTagService")
public class DishRemarkTagServiceImpl implements DishRemarkTagService {

    @Autowired
    private DishRemarkTagMapper dishRemarkTagMapper;

    @Override
    public void delBytTagId(int tagId) throws SSException {
        try {
            if(Assert.lessOrEqualZero(tagId)){
                throw SSException.get(EmenuException.TagIdError);
            }
            dishRemarkTagMapper.delByTagId(tagId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelByDishTagIdFailed);
        }
    }

    @Override
    public void newDishRemarkTags(List<DishRemarkTag> dishRemarkTags) throws SSException {
        try {
            if (Assert.isNotNull(dishRemarkTags) && dishRemarkTags.size() != 0) {
                dishRemarkTagMapper.newDishRemarkTag(dishRemarkTags);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewDishRemarkTagsFailed);
        }
    }
}
