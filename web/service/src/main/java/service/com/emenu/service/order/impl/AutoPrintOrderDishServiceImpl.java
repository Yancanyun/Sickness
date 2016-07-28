package com.emenu.service.order.impl;

import com.emenu.common.cache.call.CallCache;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.PrintOrderDishDto;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderDishCallStatusEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.dish.DishService;
import com.emenu.service.order.AutoPrintOrderDishService;
import com.emenu.service.order.OrderDishPrintService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.emenu.service.other.ConstantService;
import com.emenu.service.printer.PrinterService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * AutoPrintOrderDishServiceImpl
 * 智能排菜(自动打印菜品)ServiceImpl
 * @author: quanyibo
 * @time: 2016/7/27
 */
@Service("autoPrintOrderDishService")
public class AutoPrintOrderDishServiceImpl implements AutoPrintOrderDishService {

    @Autowired
    OrderDishPrintService orderDishPrintService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDishService orderDishService;

    @Autowired
    DishService dishService;

    @Autowired
    PrinterService printerService;

    @Autowired
    TableService tableService;

    @Autowired
    ConstantService constantService;

    // 智能排菜队列,线程安全
    private ConcurrentLinkedQueue<PrintOrderDishDto> orderDishQue
            = new ConcurrentLinkedQueue<PrintOrderDishDto>();

    // 菜品打印机可能有多个,map用来维护打印机打出的菜品正在做的个数
    // map的key为打印机的ip地址,值为菜品数量
    // 若遇到相同菜品并可在一个锅能做出多份的话则有几份算几份
    private Map<String,Integer> printerPrintTotalDishMap = new HashMap<String, Integer>();

    //服务器启动时跑定时器,每隔一段时间来判断智能排菜功能是否启动
   /* @PostConstruct
    public void init() throws Exception{

        Timer timer = new Timer(true);
        try{
            // 每隔5秒钟执行一次操作
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try{
                        if(constantService.queryValueByKey(ConstantEnum.AutoPrintOrderDishStartStatus.getKey())){

                        }
                    }
                    catch (SSException e){
                        LogClerk.errLog.error(e);
                        throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
                    }
                }
            },0,5*1000);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
        }
    }*/

    @Override
    public void createQue() throws SSException{
        List<Order> orders = new ArrayList<Order>();
        List<OrderDish> orderDishs = new ArrayList<OrderDish>();
        List<PrintOrderDishDto> printOrderDishDtos = new ArrayList<PrintOrderDishDto>();
        try{

            // 获取所有未结账已下单的订单
            orders = orderService.listOrdersByStatus(OrderStatusEnums.IsBooked.getId());
            for(Order order :orders){

                // 获取状态为已下单的订单菜品
                orderDishs.addAll(orderDishService.listOrderDishByOrderIdAndStatus(order.getId(),OrderDishStatusEnums.IsBooked.getId()));
            }

            // 获取打印信息
            for(OrderDish orderDish : orderDishs){

                // 临时存储变量用来获取打印需要的信息
                PrintOrderDishDto printOrderDishDto= new PrintOrderDishDto();
                printOrderDishDto = orderDishPrintService.getPrintOrderDishDtoById(orderDish.getId());
                if(printOrderDishDto!=null)
                    printOrderDishDtos.add(printOrderDishDto);
            }

            // 将得到的打印信息按条件进行排序
            // 首先按照被催的状态进行排序,被催的菜品放在前面,若都为被催的菜品则将等待时间较长的菜品放在前面
            // 上菜时限若为0则表示无限制,排序后上菜时限的顺序是把0放在最后,按照升序进行排序
            // 所以要进行两次排序
            if(!printOrderDishDtos.isEmpty()){

                Collections.sort(printOrderDishDtos, new Comparator<PrintOrderDishDto>() {//将缓存重新排序一下
                    @Override  // 排序函数
                    public int compare(PrintOrderDishDto o1, PrintOrderDishDto o2) {

                        // 为1的时候是被催菜了,0为没有被催菜
                        if(o1.getIsCall()<o2.getIsCall())
                            return 1;
                        if(o1.getIsCall()==o2.getIsCall()
                                &&o1.getIsCall()== OrderDishCallStatusEnums.IsCall.getId()){

                            // 若均被催菜则按照等待时间由大到小进行排序
                            if(new Date().getTime()-o1.getOrderTime().getTime()
                                    <new Date().getTime()-o2.getOrderTime().getTime()){
                                return 1;
                            }
                            if(new Date().getTime()-o1.getOrderTime().getTime()
                                    ==new Date().getTime()-o2.getOrderTime().getTime())
                            return 0;
                            return -1;
                        }
                        // 没有被催菜的情况下
                        if(o1.getIsCall()==o2.getIsCall()
                                &&o1.getIsCall()== OrderDishCallStatusEnums.IsNotCall.getId()){

                            // 按照订单时间升序进行排序
                            if(o1.getOrderTime().getTime() > o2.getOrderTime().getTime()){
                                return 1;
                            }
                            if(o1.getOrderTime().getTime() == o2.getOrderTime().getTime()) {

                                // 若订单时间相同则按照上菜时限降序进行排序
                                if(o1.getTimeLimit()<o2.getTimeLimit()){
                                    return 1;
                                }
                                if(o1.getTimeLimit()==o2.getTimeLimit())
                                    return 0;
                                return -1;
                            }
                            return -1;
                        }
                       return -1;
                    }
                });

                Collections.sort(printOrderDishDtos, new Comparator<PrintOrderDishDto>() {//将缓存重新排序一下
                    @Override  //排序函数
                    public int compare(PrintOrderDishDto o1, PrintOrderDishDto o2) {

                        // 若上菜时限大于0且未被催菜则按照上菜时间的升序排序
                        if(o1.getTimeLimit()>0
                                && o2.getTimeLimit()>0
                                && o1.getIsCall()==OrderDishCallStatusEnums.IsNotCall.getId()
                                && o2.getIsCall()==OrderDishCallStatusEnums.IsNotCall.getId()) {

                            // 若订单时间相同则按照上菜时限降序进行排序
                            if(o1.getTimeLimit()>o2.getTimeLimit()){
                                return 1;
                            }
                            if(o1.getTimeLimit()==o2.getTimeLimit())
                                return 0;
                            return -1;
                        }
                        return -1;
                    }
                });
            }
            // 首先初始化一下
            orderDishQue.clear();
            for(PrintOrderDishDto printOrderDishDto : printOrderDishDtos){

                // 增加到队列中,已经排好序
                orderDishQue.add(printOrderDishDto);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CreateQueFail,e);
        }
    }

    @Override
    public void clearOrderDishQue() throws SSException{

        try{

        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
        }
    }

    @Override
    public void reducePrinterMakeDishQuantity(Integer orderDishId) throws SSException{

        try{

        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
        }
    }
}
