package com.emenu.test.dish;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.service.dish.DishService;
import com.emenu.test.AbstractTestCase;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * DishServiceTest
 *
 * @author: zhangteng
 * @time: 2015/11/25 15:24
 **/
public class DishServiceTest extends AbstractTestCase {

    @Autowired
    private DishService dishService;

    @Test
    public void queryById() throws Exception {
        DishDto dishDto = dishService.queryById(17);

        System.out.println(BeanUtils.describe(dishDto));
    }
}
