package com.emenu.service.order.impl;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.dto.order.PrintOrderDishDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.auto.AutoPrintStartStatusEnums;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderDishCallStatusEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.dish.DishService;
import com.emenu.service.order.*;
import com.emenu.service.other.ConstantService;
import com.emenu.service.printer.DishTagPrinterService;
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

    @Autowired
    DishTagPrinterService dishTagPrinterService;


    // 智能排菜队列,线程安全
    private static ConcurrentLinkedQueue<PrintOrderDishDto> orderDishQue
            = new ConcurrentLinkedQueue<PrintOrderDishDto>();

    // 菜品打印机可能有多个,map用来维护打印机打出的菜品正在做的个数
    // map的key为打印机的ip地址,值为菜品数量
    // 若遇到相同菜品并可在一个锅能做出多份的话则有几份算几份
    // 用static声明被所有类的实例所共享
    private static Map<String,Integer> printerPrintTotalDishMap = new HashMap<String, Integer>();

    //时间戳
    private static final  long TIMESTAMP = 10*1000;

    //服务器启动执行下面的这个函数,跑定时器,每隔一段时间来判断智能排菜功能是否启动
    @PostConstruct
    public void autoPrint() throws Exception{

        Timer timer = new Timer(true);
        try{

            // 若开启了智能排菜则每隔10秒钟执行一次,若符合条件则打印
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    try{
                        List<Printer> printers = new ArrayList<Printer>();
                        // 智能排菜服务启用
                        Integer autoPrintStartStatus = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.AutoPrintOrderDishStartStatus.getKey()));
                        if(autoPrintStartStatus == AutoPrintStartStatusEnums.IsStart.getId()){

                            // 形成智能排菜队列
                            AutoPrintOrderDishServiceImpl.this.createQue();
                            //autoPrintOrderDishService.createQue();

                            // 迭代器
                            if(AutoPrintOrderDishServiceImpl.this.getOrderDishQue()!=null
                                    &&AutoPrintOrderDishServiceImpl.this.getOrderDishQue().size()!=0){

                                Iterator<PrintOrderDishDto> ite = AutoPrintOrderDishServiceImpl.this.getOrderDishQue().iterator();
                                //Iterator<PrintOrderDishDto> ite = autoPrintOrderDishService.getOrderDishQue().iterator();

                                while(ite.hasNext()){

                                    //临时变量
                                    PrintOrderDishDto temp = new PrintOrderDishDto();
                                    temp = ite.next();

                                    // 未设置菜品打印机
                                    if(temp.getPrinterIp()==null
                                            ||temp.getPrinterIp()==""){
                                        throw SSException.get(EmenuException.OrderDishPrinterIsNotSet);
                                    }
                                    Integer printerPrintTotalDish = new Integer(0);

                                    // 获取当前菜品关联到的打印机打出来正在做的菜品个数
                                    if(AutoPrintOrderDishServiceImpl.this.getPrinterPrintTotalDishMap()!=null)
                                     printerPrintTotalDish = AutoPrintOrderDishServiceImpl.this.getPrinterPrintTotalDishMap().get(temp.getPrinterIp());
//                                    printerPrintTotalDish =
//                                            autoPrintOrderDishService.getPrinterPrintTotalDishMap().get(temp.getPrinterIp());

                                    // 打印机未打过任何菜品,或打正在做的菜品数量小于最多正在做的数量
                                    if(printerPrintTotalDish==null
                                            ||printerPrintTotalDish < Integer.parseInt(constantService.queryValueByKey(ConstantEnum.PrinterPrintMaxNum.getKey()))){

                                        // 口味和备注相同的菜品存储数组
                                        List<PrintOrderDishDto> printOrderDishDtos =
                                                new ArrayList<PrintOrderDishDto>();

                                        // 重新定义一个迭代器
                                        Iterator<PrintOrderDishDto> ite1 =
                                                AutoPrintOrderDishServiceImpl.this.getOrderDishQue().iterator();

                                        // 查询出菜品,然后根据菜品的小类Id查询tag
                                        OrderDishDto orderDishDto = new OrderDishDto();
                                        orderDishDto = orderDishService.queryDtoById(temp.getOrderDishId());
                                        DishDto dishDto = new DishDto();
                                        if(orderDishDto.getIsPackage()== PackageStatusEnums.IsPackage.getId()){

                                            dishDto=dishService.queryById(orderDishDto.getPackageId());
                                        }
                                        else{

                                            dishDto=dishService.queryById(orderDishDto.getDishId());
                                        }
                                        Tag tag = dishTagPrinterService.queryTagByTagId(dishDto.getTagId());

                                        // 属于同一菜品小类的菜品一个锅同时可做最大数量
                                        Integer maxMakeQuantity = tag.getMaxPrintNum();

                                        //当前可做相同菜品数量
                                        Integer currentSameDishQuantity = 0;

                                        // 遍历队列找出口味相同备注相同的菜品,一起打印出来
                                        while (ite1.hasNext()){

                                            PrintOrderDishDto temp1 = new PrintOrderDishDto();
                                            temp1 = ite1.next();
                                            // 口味和备注相同且总数量不超过最大可做数量的菜品可以一起做
                                            if(temp1.getDishId()==temp.getDishId()
                                            &&((temp.getTaste()!=null
                                                    &&temp1.getTaste()!=null
                                                    &&temp.getTaste()==temp1.getTaste())
                                            ||(temp.getTaste()==null&&temp1.getTaste()==null))
                                                    &&((temp.getRemark()!=null
                                                    &&temp1.getRemark()!=null
                                                    &&temp1.getRemark()==temp.getRemark())
                                                    ||(temp.getRemark()==null&&temp1.getRemark()==null))
                                                    &&currentSameDishQuantity<maxMakeQuantity){

                                                printOrderDishDtos.add(temp1);
                                                currentSameDishQuantity++;
                                                // 移除的时候可能存在问题
                                                ite1.remove();
                                            }
                                        }
                                        if(printOrderDishDtos!=null){

                                            for(PrintOrderDishDto dto : printOrderDishDtos){

                                                // 打印菜品
                                                orderDishPrintService.printOrderDish(dto);
                                            }

                                            // 打印机打印出的未做的菜品个数+1
                                            if(printerPrintTotalDish==null)
                                                AutoPrintOrderDishServiceImpl.this
                                                        .updatePrinterPrintTotalDishMap(temp.getPrinterIp(), 1);
                                            else
                                                AutoPrintOrderDishServiceImpl.this
                                                        .updatePrinterPrintTotalDishMap(temp.getPrinterIp(), printerPrintTotalDish + 1);
                                        }
                                    }
                                }
                            }

                        }
                        // 清空智能排菜队列,实时保证排菜队列为空
                        else{
                            AutoPrintOrderDishServiceImpl.this.clearOrderDishQue();
                        }
                    }
                    catch (SSException e){
                        LogClerk.errLog.error(e);
                    }
                }

            }, 0, TIMESTAMP);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
        }
    }

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
            orderDishQue.clear();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
        }
    }

    @Override
    public ConcurrentLinkedQueue<PrintOrderDishDto> getOrderDishQue() {
        return orderDishQue;
    }

    @Override
    public Map<String, Integer> getPrinterPrintTotalDishMap() {
        return printerPrintTotalDishMap;
    }

    @Override
    public void updatePrinterPrintTotalDishMap(String printerIp,Integer dishQuantity) throws SSException {

        try{
            printerPrintTotalDishMap.put(printerIp,dishQuantity);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
        }
    }
}
