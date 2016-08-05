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
import com.emenu.common.enums.vip.grade.IntegralEnableStatusEnums;
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
import org.springframework.beans.factory.annotation.Qualifier;
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
                        Integer autoPrintStartStatus = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.AutoPrintOrderDishStartStatus.getKey()));
                        // 记录已经打印出来的菜品
                        Map<PrintOrderDishDto,Integer> printFinshMap = new HashMap<PrintOrderDishDto, Integer>();

                        // 智能排菜服务启用
                        if(autoPrintStartStatus == AutoPrintStartStatusEnums.IsStart.getId()){

                            // 形成智能排菜队列
                            AutoPrintOrderDishServiceImpl.this.createQue();
                            //autoPrintOrderDishService.createQue();

                            // 迭代器
                            if(AutoPrintOrderDishServiceImpl.getOrderDishQue()!=null
                                    &&AutoPrintOrderDishServiceImpl.getOrderDishQue().size()!=0){

                                Iterator<PrintOrderDishDto> ite = AutoPrintOrderDishServiceImpl.getOrderDishQue().iterator();
                                //Iterator<PrintOrderDishDto> ite = autoPrintOrderDishService.getOrderDishQue().iterator();

                                while(ite.hasNext()){
                                    //临时变量
                                    PrintOrderDishDto temp = new PrintOrderDishDto();
                                    temp = ite.next();

                                    //首先判断当前菜品是否被打印过
                                    // 若被打印过则去执行下一次循环
                                    if(printFinshMap.get(temp)!=null)
                                        continue;
                                    // 未查找到关联打印机
                                    if(temp.getPrinterIp()==null
                                            ||temp.getPrinterIp().equals("")){
                                        throw SSException.get(EmenuException.OrderDishPrinterIsNotSet);
                                    }
                                    Integer printerPrintTotalDish = 0;

                                    // 获取当前菜品关联到的打印机打出来正在做的菜品个数

                                        if(AutoPrintOrderDishServiceImpl.getPrinterPrintTotalDishMap()!=null)
                                            printerPrintTotalDish = AutoPrintOrderDishServiceImpl.getPrinterPrintTotalDishMap().get(temp.getPrinterIp());

                                        // 打印机未打过任何菜品,或打正在做的菜品数量小于最多正在做的数量
                                        if(printerPrintTotalDish==null
                                                ||printerPrintTotalDish < Integer.parseInt(constantService.queryValueByKey(ConstantEnum.PrinterPrintMaxNum.getKey()))){

                                            // 口味和备注相同的菜品存储数组
                                            List<PrintOrderDishDto> printOrderDishDtos = new ArrayList<PrintOrderDishDto>();

                                            // 重新定义一个迭代器
                                            Iterator<PrintOrderDishDto> ite1 =
                                                    AutoPrintOrderDishServiceImpl.getOrderDishQue().iterator();

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
                                                // 总数量不超过最大可做数量且下单时间差小于等于上菜时限(做菜所用时间)
                                                // 上菜时限为0的话代表无限制
                                                // 屏蔽掉的部分为口味和备注均相同
                                                if(/*((temp.getTaste()!=null
                                                        &&temp1.getTaste()!=null
                                                        &&temp.getTaste()==temp1.getTaste())
                                                        ||(temp.getTaste()==null&&temp1.getTaste()==null))
                                                        &&((temp.getRemark()!=null
                                                        &&temp1.getRemark()!=null
                                                        &&temp1.getRemark()==temp.getRemark())
                                                        ||(temp.getRemark()==null&&temp1.getRemark()==null))
                                                        &&*/
                                                        temp1.getDishId()==temp.getDishId()
                                                        &&currentSameDishQuantity + (int)temp1.getNum()<maxMakeQuantity
                                                        &&((temp.getTimeLimit()!=0
                                                        &&Math.abs(temp.getOrderTime().getTime()-temp1.getOrderTime().getTime())/1000<=temp.getTimeLimit())
                                                        ||temp.getTimeLimit()==0)
                                                        &&printFinshMap.get(temp1)==null){

                                                    printFinshMap.put(temp1,1);
                                                    printOrderDishDtos.add(temp1);
                                                    currentSameDishQuantity += (int)temp1.getNum();
                                                }
                                            }
                                            for(PrintOrderDishDto dto : printOrderDishDtos){

                                                   // 打印菜品
                                                orderDishPrintService.printOrderDish(dto);
                                            }

                                               // 打印机打印出的未做的菜品个数+1
                                            if(printerPrintTotalDish==null)
                                                AutoPrintOrderDishServiceImpl.updatePrinterPrintTotalDishMap(temp.getPrinterIp(), 1);
                                            else
                                                AutoPrintOrderDishServiceImpl.updatePrinterPrintTotalDishMap(temp.getPrinterIp(), printerPrintTotalDish + 1);
                                        }
                                }
                            }
                        }
                        // 清空智能排菜队列,实时保证排菜队列为空
                        else{
                            AutoPrintOrderDishServiceImpl.clearOrderDishQue();
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

                                // 若订单时间相同则按照上菜时限升序进行排序
                                if(o1.getTimeLimit()>o2.getTimeLimit()){
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

                            //先按照订单时间升序排序
                            if(o1.getOrderTime().getTime()>o2.getOrderTime().getTime())
                                return 1;

                            // 若订单时间相同则按照上菜时限降序进行排序
                            if(o1.getOrderTime().getTime()==o2.getOrderTime().getTime()){

                                if(o1.getTimeLimit()>o2.getTimeLimit()){
                                    return 1;
                                }
                                if(o1.getTimeLimit()==o2.getTimeLimit())
                                    return 0;
                                return -1;
                            }
                            return -1;
                        }
                        return 0;
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

    /**
     * 清空智能排菜队列
     */
    public static void clearOrderDishQue() {
            orderDishQue.clear();
    }

    /**
     * 获取智能排菜队列
     */
    public static ConcurrentLinkedQueue<PrintOrderDishDto> getOrderDishQue() {
        return orderDishQue;
    }

    /**
     * 获取打印机打印出的正在做菜品的map
     */
    public static Map<String, Integer> getPrinterPrintTotalDishMap() {
        return printerPrintTotalDishMap;
    }

    /**
     * 更新打印机打印出的正在做菜品的map
     */
    public static void updatePrinterPrintTotalDishMap(String printerIp,Integer dishQuantity) {
        printerPrintTotalDishMap.put(printerIp,dishQuantity);
    }
}
