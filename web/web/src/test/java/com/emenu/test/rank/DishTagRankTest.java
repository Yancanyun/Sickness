package com.emenu.test.rank;

import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.utils.DateUtils;
import com.emenu.service.rank.DishTagRankService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 *  @Author guofengrui
 *  @Date 2016/7/27.
 */
public class DishTagRankTest extends AbstractTestCase {
    @Autowired
    private DishTagRankService dishTagRankService;

    @Test
    public void queryDishTagRankDtoListByTimePeriod() throws SSException{
        Date startTime = DateUtils.getFirstDayOfWeek();
        Date endTime = DateUtils.getLastDayOfWeek();
        List<DishSaleRankDto> list = dishTagRankService.queryDishSaleRankDtoByTimePeriod(startTime, endTime);
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i).getTagId()+"   ");
            System.out.print(list.get(i).getTagName() + "   ");
            System.out.print(list.get(i).getNum() + "   ");
            System.out.println(list.get(i).getConsumeSum());
        }
    }
}
