package com.emenu.test.dish;

import com.emenu.common.entity.dish.DishRemarkTag;
import com.emenu.common.entity.remark.Remark;
import com.emenu.service.dish.DishRemarkTagService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DishRamarkTagTest
 *
 * @author xubaorong
 * @date 2016/6/2.
 */
public class DishRamarkTagTest extends AbstractTestCase {
    @Autowired
    private DishRemarkTagService dishRemarkTagService;

    @Test
    public void delByDishTagId() throws SSException{
        dishRemarkTagService.delBytTagId(88);
    }

    @Test
    public void queryTagId() throws SSException{
        List<Remark> remarks = Collections.emptyList();
        remarks = dishRemarkTagService.queryByTagId(102);
        for(Remark remark:remarks){
            System.out.print(remark.getName());
        }
    }

    @Test
    public void newDishRemarkTags() throws SSException{
        List<DishRemarkTag> dishRemarkTagList = new ArrayList<DishRemarkTag>();
        DishRemarkTag dishRemarkTag = new DishRemarkTag();
        dishRemarkTag.setTagId(88);
        dishRemarkTag.setRemarkTagId(6);
        dishRemarkTagList.add(dishRemarkTag);
        DishRemarkTag dishRemarkTag1 = new DishRemarkTag();
        dishRemarkTag1.setTagId(89);
        dishRemarkTag1.setRemarkTagId(7);
        dishRemarkTagList.add(dishRemarkTag);
        DishRemarkTag dishRemarkTag2 = new DishRemarkTag();
        dishRemarkTag2.setTagId(91);
        dishRemarkTag2.setRemarkTagId(8);
        dishRemarkTagList.add(dishRemarkTag);
        dishRemarkTagService.newDishRemarkTags(dishRemarkTagList);
    }
}
