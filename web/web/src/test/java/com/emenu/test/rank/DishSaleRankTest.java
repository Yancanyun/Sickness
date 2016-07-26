package com.emenu.test.rank;

import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.utils.DateUtils;
import com.emenu.service.rank.DishSaleRankService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import freemarker.template.utility.DateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author  guofengrui
 * @date 2016/7/26.
 */
public class DishSaleRankTest extends AbstractTestCase {
    @Autowired
    private DishSaleRankService dishSaleRankService;

    @Test
    public void queryOrderDishByTimePeroid1()throws SSException{
        Date startTime = DateUtils.getFirstDayOfWeek();
        Date endTime = DateUtils.getLastDayOfWeek();
        List<OrderDish> list = dishSaleRankService.queryOrderDishByTimePeroid(startTime,endTime);
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i).getId());
        }
    }

    @Test
    public void queryDishSaleRankDtoByTimePeroid() throws SSException{
        Date startTime = DateUtils.getFirstDayOfWeek();
        Date endTime = DateUtils.getLastDayOfWeek();
        List<DishSaleRankDto> list = dishSaleRankService.queryDishSaleRankDtoByTimePeroid(startTime,endTime);
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i).getDishName()+"   ");
            System.out.print(list.get(i).getTagName() + "   ");
            System.out.print(list.get(i).getNum() + "   ");
            System.out.println(list.get(i).getConsumeSum());
        }
    }

    @Test
    public void listAll() throws SSException{
        List<DishSaleRankDto> list = dishSaleRankService.listAll();
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i).getDishName()+"   ");
            System.out.print(list.get(i).getTagName() + "   ");
            System.out.print(list.get(i).getNum() + "   ");
            System.out.println(list.get(i).getConsumeSum());
        }
    }
}
