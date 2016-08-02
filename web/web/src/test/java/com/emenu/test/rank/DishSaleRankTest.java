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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
        List<OrderDish> list = dishSaleRankService.queryOrderDishByTimePeriod(startTime,endTime);
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i).getId());
        }
    }

    @Test
    public void queryDishSaleRankDtoByTimePeroid() throws SSException{
        Date startTime = DateUtils.getFirstDayOfWeek();
        Date endTime = DateUtils.getLastDayOfWeek();
        List<DishSaleRankDto> list = dishSaleRankService.queryDishSaleRankDtoByTimePeriod(startTime, endTime);
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

    @Test
    public void getData() throws SSException{
        /*System.out.println("本月第一天：" + DateUtils.getCurrentMonthFirstDay());
        System.out.println("本月最后一天：" + DateUtils.getCurrentMonthLastDay());
        System.out.println("上月第一天：" + DateUtils.getLastMonthFirstDay());
        System.out.println("上月最后一天：" + DateUtils.getLastMonthLastDay());
        System.out.println("本周第一天：" + DateUtils.getCurrentWeekFirstDay());
        System.out.println("本周最后一天：" + DateUtils.getCurrentWeekLastDay());
        System.out.println("上周第一天：" + DateUtils.getLastWeekFirstDay());
        System.out.println("上周最后一天：" + DateUtils.getLastWeekLastDay());
        System.out.println("今天：" + DateUtils.getToday());
        System.out.println("昨天：" + DateUtils.getYesterday());*/
        System.out.println("本年的第一天："+DateUtils.getCurrentYearFirstDay());
        System.out.println("本年的最后一天："+DateUtils.getCurrentYearLastDay());
        System.out.println("去年的第一天："+DateUtils.getLastYearFirstDay());
        System.out.println("去年的最后一天：" + DateUtils.getLastYearLastDay());

    }

    @Test
    public void queryDishSaleRankDtoByTimeAndTagId() throws SSException{
        Date startTime = DateUtils.getFirstDayOfWeek();
        Date endTime = DateUtils.getLastDayOfWeek();
        List<DishSaleRankDto> list = dishSaleRankService.queryDishSaleRankDtoByTimePeriodAndTagId(startTime,endTime,92);
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i).getDishName()+"   ");
            System.out.print(list.get(i).getTagName() + "   ");
            System.out.print(list.get(i).getNum() + "   ");
            System.out.println(list.get(i).getConsumeSum());
        }
    }

    @Test
    public void queryDishTagRankPage() throws SSException{
        Date startTime = DateUtils.getFirstDayOfWeek();
        Date endTime = DateUtils.getLastDayOfWeek();
        List<DishSaleRankDto> list = dishSaleRankService.queryDishSaleRankDtoByTimePeriodAndTagIdAndPage(startTime, endTime, 92, 4, 1);
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i).getDishName()+"   ");
            System.out.print(list.get(i).getTagName() + "   ");
            System.out.print(list.get(i).getNum() + "   ");
            System.out.println(list.get(i).getConsumeSum());
        }
    }
    /*@Test
    public  void hh() {
        List<String> list = new ArrayList<String>();
        list.add("JavaWeb编程词典");        //向列表中添加数据
        list.add("Java编程词典");        //向列表中添加数据
        list.add("C#编程词典");         //向列表中添加数据
        list.add("ASP.NET编程词典");        //向列表中添加数据
        list.add("VC编程词典");         //向列表中添加数据
        list.add("SQL编程词典");        //向列表中添加数据
        Iterator<String> its = list.iterator();     //获取集合迭代器
        System.out.println("集合中所有元素对象：");
        while (its.hasNext()) {        //循环遍历集合
            System.out.print(its.next() + "  ");     //输出集合内容
        }
        List<String> subList = list.subList(3, 5);    //获取子列表
        System.out.println("\n截取集合中部分元素：");
        Iterator it = subList.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + "  ");
        }
    }*/
}
