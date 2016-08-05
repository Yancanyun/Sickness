package com.emenu.service.order.impl;

import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.order.PrintOrderDishDto;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.dish.DishTaste;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderDishAutoPrintStatusEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.enums.order.ServeTypeEnums;
import com.emenu.common.enums.printer.PrinterDishEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.CommonUtil;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.PrintUtils;
import com.emenu.service.cook.CookTableCacheService;
import com.emenu.service.dish.DishPackageService;
import com.emenu.service.dish.DishService;
import com.emenu.service.dish.DishTasteService;
import com.emenu.service.dish.TasteService;
import com.emenu.service.dish.tag.TagService;
import com.emenu.service.order.OrderDishPrintService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.emenu.service.printer.DishTagPrinterService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

/**
 * OrderDishPrintService
 * 后厨管理端打印订单菜品
 *
 * @author: quanyibo
 * @time: 2016/6/24
 */
@Service("orderDishPrintService")
public class OrderDishPrintServiceImpl implements OrderDishPrintService{

    @Autowired
    DishService dishService;

    @Autowired
    TagService tagService;

    @Autowired
    DishTasteService dishTasteService;

    @Autowired
    DishPackageService dishPackageService;

    @Autowired
    DishTagPrinterService dishTagPrinterService;

    @Autowired
    OrderDishService orderDishService;

    @Autowired
    TableService tableService;

    @Autowired
    OrderService orderService;

    @Autowired
    TasteService tasteService;

    @Autowired
    CookTableCacheService cookTableCacheService;

    @Override
    public void printOrderDishById(Integer orderDishId) throws SSException
    {
        PrintOrderDishDto printOrderDishDto = new PrintOrderDishDto();
        try{
            printOrderDishDto = this.getPrintOrderDishDtoById(orderDishId);//获取菜品打印信息
            if(printOrderDishDto!=null)
            {
                if(printOrderDishDto.getPrinterIp()!=null
                        &&!printOrderDishDto.getPrinterIp().equals(""))
                this.printOrderDish(printOrderDishDto);//打印菜品
                else
                    throw SSException.get(EmenuException.PrinterIpIsNull);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
        }
    }

    @Override
    public PrintOrderDishDto getPrintOrderDishDtoById(Integer orderDishId) throws SSException
    {
        PrintOrderDishDto printOrderDishDto = new PrintOrderDishDto();
        OrderDish orderDish = new OrderDish();
        DishDto dishDto = new DishDto();
        Table table = new Table();

        try{
            orderDish = orderDishService.queryById(orderDishId);//查询出订单菜品
            //非套餐
            if(orderDish.getIsPackage()== PackageStatusEnums.IsNotPackage.getId())
            {
                dishDto = dishService.queryById(orderDish.getDishId());//查询出菜品详细信息
                PrintOrderDishDto temp = new PrintOrderDishDto();//临时变量
                temp.setOrderDishId(orderDishId);//订单菜品主键
                temp.setDishName(dishDto.getName());//菜品名字
                temp.setDishBigTagName(tagService.queryLayer2TagByDishId(orderDish.getDishId()).getName());//菜品大类即菜品二级分类
                temp.setNum(orderDish.getDishQuantity());//菜品数量
                temp.setRemark(orderDish.getRemark());//订单菜品备注
                temp.setServerType(ServeTypeEnums.valueOf(orderDish.getServeType()).getType());//上菜方式
                temp.setTableName(tableService.queryById(orderDishService.queryOrderDishTableId(orderDishId)).getName());//点菜餐桌的名称
                // 下单时间
                temp.setOrderTime(orderDish.getOrderTime());
                // 上菜时限
                temp.setTimeLimit(dishService.queryById(orderDish.getDishId()).getTimeLimit());
                // 催菜
                temp.setIsCall(orderDish.getIsCall());
                // 菜品主键
                temp.setDishId(orderDish.getDishId());

                String tasteName = new String();//菜品口味名称
                if(orderDish.getTasteId()!=null
                        &&orderDish.getTasteId()>0)//tasteId为0的话则为没选择口味
                {
                    if(tasteService.queryById(dishDto.getTasteId())!=null)
                    {
                        tasteName=tasteService.queryById(dishDto.getTasteId()).getName();
                        temp.setTaste(tasteName);
                    }
                }
                // 菜品小类对应的打印机的ip地址或者是菜品对应的打印机,要看类型
                Printer printer = new Printer();
                // 1-菜品类别，2-具体某一个菜
                printer = dishTagPrinterService.queryByTagIdAndType(orderDish.getDishId(),PrinterDishEnum.DishPrinter.getId());//根据dishId查,看能否查到
                if(printer!=null &&printer.getIpAddress()!=null)
                {
                    //菜品直接关联的打印机要比小类关联的打印机优先级高
                    temp.setPrinterIp(printer.getIpAddress());
                }
                //根据菜品小类查询关联打印机
                else{

                    if(dishDto.getTagId()!=null) {

                        Integer tagId = dishDto.getTagId();//菜品小类
                        printer=dishTagPrinterService.queryByTagIdAndType(tagId,PrinterDishEnum.TagPrinter.getId());
                        if(printer!=null&&printer.getIpAddress()!=null)
                            temp.setPrinterIp(printer.getIpAddress());
                    }
                }

                printOrderDishDto = temp;
            }
            else//为套餐
            {
                dishDto = dishService.queryById(orderDish.getDishId());//查询出菜品详细信息
                PrintOrderDishDto temp = new PrintOrderDishDto();//临时变量
                temp.setOrderDishId(orderDishId);//订单菜品主键
                temp.setDishName(dishDto.getName());//套餐中的菜品名字
                temp.setDishBigTagName(tagService.queryLayer2TagByDishId(orderDish.getPackageId()).getName());//菜品大类即菜品二级分类
                temp.setNum(orderDish.getDishQuantity());//菜品数量
                temp.setRemark(orderDish.getRemark());//订单菜品备注
                temp.setServerType(ServeTypeEnums.valueOf(orderDish.getServeType()).getType());//上菜方式
                temp.setTableName(tableService.queryById(orderDishService.queryOrderDishTableId(orderDishId)).getName());//点菜餐桌的名称
                // 下单时间
                temp.setOrderTime(orderDish.getOrderTime());
                // 上菜时限
                temp.setTimeLimit(dishService.queryById(orderDish.getPackageId()).getTimeLimit());
                // 催菜
                temp.setIsCall(orderDish.getIsCall());
                // 菜品主键
                temp.setDishId(orderDish.getPackageId());

                String tasteName = new String();//菜品口味名称
                if(orderDish.getTasteId()!=null
                        &&orderDish.getTasteId()>0)//tasteId为0的话则为没选择口味
                {
                    if(tasteService.queryById(dishDto.getTasteId())!=null)
                    {
                        tasteName=tasteService.queryById(dishDto.getTasteId()).getName();
                        temp.setTaste(tasteName);
                    }
                }
                //菜品小类对应的打印机的ip地址或者是菜品对应的打印机,要看类型
                Printer printer = new Printer();
                printer = dishTagPrinterService.queryByTagIdAndType(orderDish.getDishId(),PrinterDishEnum.DishPrinter.getId());//根据dishId查,看能否查到
                if(printer!=null &&printer.getIpAddress()!=null)
                {
                    //菜品直接关联的打印机要比小类关联的打印机优先级高
                    temp.setPrinterIp(printer.getIpAddress());
                }
                //根据菜品小类查询关联打印机
                else{

                    if(dishDto.getTagId()!=null) {

                        Integer tagId = dishDto.getTagId();//菜品小类
                        printer=dishTagPrinterService.queryByTagIdAndType(tagId,PrinterDishEnum.TagPrinter.getId());
                        if(printer!=null&&printer.getIpAddress()!=null)
                            temp.setPrinterIp(printer.getIpAddress());
                    }
                }
                // 打印机Ip集合
                printOrderDishDto = temp;
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
        }
        return printOrderDishDto;
    }

    @Override
    public void printOrderDish(PrintOrderDishDto printOrderDishDto) throws SSException
    {
        Socket socket = new Socket();
        InputStream is = null;
        OutputStream os = null;
        List<String> printerIps = new ArrayList<String>();
        int ok = 1;
        try{
            // 服务器的IP和端口,和服务器建立通信
            if(printOrderDishDto.getPrinterIp()==null
                    ||printOrderDishDto.getPrinterIp().isEmpty())
                throw SSException.get(EmenuException.PrinterIpIsNull);

                socket.connect(new InetSocketAddress(printOrderDishDto.getPrinterIp(), 9100), 10000);
                if (socket.isConnected()) {//成功建立了连接

                    os = socket.getOutputStream();

                    // 初始化打印机
                    os.write(PrintUtils.initPrinter());

                    //设置0为左对齐,1的话为设置为居中,2为右对齐
                    os.write(PrintUtils.setLocation(0));

                    String str = "";
                    //套餐下的所有菜品的orderDishId均相同
                    //第一条显示,用来提醒上菜的服务员这个菜品可能为套餐,套餐的话服务员应该等所有菜品都做完了后再进行上菜扫码
                    str+= "菜品大类: " + printOrderDishDto.getDishBigTagName() + "\n";
                    str += "桌名: " + printOrderDishDto.getTableName() + "\n";
                    str += "菜品名称: " + printOrderDishDto.getDishName() + "\n";

                    // 先打印一波,因为数量要打印的大一点,所以分开打印
                    os.write(PrintUtils.printText(str));
                    str = "";

                    str += "数量: " + String.valueOf(printOrderDishDto.getNum()) + "\n";
                    // 数量要打印的大一点
                    os.write(PrintUtils.setSize(1,1));
                    os.write(PrintUtils.printText(str));
                    str = "";

                    // 初始化一下
                    // 初始化打印机
                    os.write(PrintUtils.initPrinter());

                    //设置0为左对齐,1的话为设置为居中,2为右对齐
                    os.write(PrintUtils.setLocation(0));

                    str += " 口味: ";
                    if (printOrderDishDto.getTaste() != null)
                    {
                        str+=printOrderDishDto.getTaste()+"\n";
                    }
                    else//没有选择菜品口味
                    {
                        str+="正常\n";
                    }
                    str += "上菜方式: " + printOrderDishDto.getServerType()+ "\n";
                    if (printOrderDishDto.getRemark() != null && !printOrderDishDto.getRemark().equals(""))
                        str += "备注: " + printOrderDishDto.getRemark() + "\n";
                    else
                        str += "备注: 无" + "\n";


                    os.write(PrintUtils.printText(str));//打印信息

                    // 打印条码
                    String orderDishIdStr = printOrderDishDto.getOrderDishId().toString();//条形码信息为OrderDishId
                    os.write(PrintUtils.newPrintBarCode(orderDishIdStr));

                    // 打印订单id和时间
                    os.write(PrintUtils.printText(orderDishIdStr + "\n"));
                    os.write(PrintUtils.printText(DateUtils.formatDate(new Date()) + "\n"));//打印菜品的时间

                    os.write(PrintUtils.printText("--------------------------------\n"));
                    os.write(PrintUtils.printText("聚客多移动电子点餐系统由吉林省裕昌恒科技有限公司提供，合作洽谈请拨打热线电话:13234301365\n"));

                    os.write(PrintUtils.println(4));
                    //切纸
                    os.write(PrintUtils.cutPaper());

                    //修改订单菜品的状态
                    OrderDish orderDish = new OrderDish();
                    orderDish=orderDishService.queryById(printOrderDishDto.getOrderDishId());//查询出订单菜品
                    orderDish.setStatus(OrderDishStatusEnums.IsMake.getId());//订单菜品状态：1.已下单  2.正在做  3.已上菜
                    orderDishService.updateOrderDish(orderDish);//更新订单菜品的状态

                    //修改餐台版本号
                    Integer tableId = orderDishService.queryOrderDishTableId(orderDish.getId());//根据订单菜品id获取到tableId
                    cookTableCacheService.updateTableVersion(tableId);//更新餐桌版本号
                }
                else
                    throw SSException.get(EmenuException.ConnectPrinterFail);

        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.PrintOrderDishFail,e);
        }
        finally {
            if (os != null) {
                try {
                    os.close();//关闭输出流
                } catch (IOException e) {
                    LogClerk.errLog.error(e.getMessage());
                    throw SSException.get(EmenuException.SystemException, e);
                }
            }
            if (socket != null) {
                try {
                    socket.close();//断开连接
                } catch (IOException e) {
                    LogClerk.errLog.error(e.getMessage());
                    throw SSException.get(EmenuException.SystemException, e);
                }
            }
        }
    }

    @Override
    public String checkOrderDishPrinter() throws SSException{

        List<Order> orders = new ArrayList<Order>();
        List<OrderDish> orderDishs = new ArrayList<OrderDish>();

        // 关键字为打印机Ip地址,1的话代表打印机存在问题,0的话没问题
        Map<Printer,Integer> printerMap = new HashMap<Printer, Integer>();

        // 关键字为菜品的Id,相同菜品若打印机不可用的话只显示一次
        Map<Integer,Integer> dishMap = new HashMap<Integer, Integer>();
        String exceptionStr = "";
        try{
            // 所有已下单的订单
            orders = orderService.listOrdersByStatus(OrderStatusEnums.IsBooked.getId());
            for(Order order :orders){
                // 所有已下单的菜品
                orderDishs.addAll(orderDishService.listOrderDishByOrderIdAndStatus(order.getId(),OrderDishStatusEnums.IsBooked.getId()));
            }

            for(OrderDish orderDish :orderDishs){

                Socket socket = new Socket();
                // 菜品小类对应的打印机的ip地址或者是菜品对应的打印机,要看类型
                DishDto dishDto = new DishDto();
                dishDto = dishService.queryById(orderDish.getDishId());
                Printer printer = new Printer();
                printer = dishTagPrinterService.queryByTagIdAndType(orderDish.getDishId(),PrinterDishEnum.DishPrinter.getId());//根据dishId查,看能否查到
                // 判断打印机是否好使
                if(printer!=null &&printer.getIpAddress()!=null) {

                    // 尝试建立连接,如果打印机未打开的话会抛异常
                    try{

                        socket.connect(new InetSocketAddress(printer.getIpAddress(), 9100), 10000);
                    }catch (Exception e){
                        LogClerk.errLog.error(e);
                        throw SSException.get(EmenuException.PrinterNotOpen);
                    }

                    // 成功建立连接证明该打印好使
                    if(socket.isConnected()){
                        // 第一次加入
                        if(printerMap.get(printer)==null)
                            printerMap.put(printer,0);
                        // 断开连接
                        socket.close();
                    }
                    // 不能和打印机成功建立连接,打印机存在问题
                    else
                        printerMap.put(printer, 1);
                }
                // 根据菜品小类查询关联打印机
                else{

                    if(dishDto.getTagId()!=null) {

                        Integer tagId = dishDto.getTagId();//菜品小类
                        printer=dishTagPrinterService.queryByTagIdAndType(tagId,PrinterDishEnum.TagPrinter.getId());
                        // 判断打印机是否好使
                        if(printer!=null&&printer.getIpAddress()!=null){

                            // 尝试建立连接,如果打印机未打开的话会抛异常
                            try{
                                socket.connect(new InetSocketAddress(printer.getIpAddress(), 9100), 10000);
                            }catch (Exception e){
                                LogClerk.errLog.error(e);
                                throw SSException.get(EmenuException.PrinterNotOpen);
                            }

                            // 成功建立连接证明该打印好使
                            if(socket.isConnected()){
                                // 第一次加入
                                if(printerMap.get(printer)==null)
                                    printerMap.put(printer,0);
                                // 断开连接
                                socket.close();
                            }
                            // 不能和打印机成功建立连接,打印机存在问题
                            else
                                printerMap.put(printer,1);
                        }
                        // 为将菜品直接关联到打印机也没有将菜品的小类关联到打印机(认为菜品打印机未设置)
                        else{
                            // 这个菜品未被加到Map中
                            if(dishMap.get(orderDish.getDishId())==null){

                                exceptionStr += "菜品： "+dishDto.getName()+ "的打印机未设置导致该菜品无法正常打印!\n";
                                dishMap.put(orderDish.getDishId(),1);
                            }
                        }

                    }
                }
            }
            // 检查是否有不好使的打印机
            for(Map.Entry<Printer,Integer> entry :printerMap.entrySet()){

                Printer tempPrinter = entry.getKey();
                Integer status = entry.getValue();
                // 打印机不好使
                if(status==1)
                    exceptionStr += tempPrinter.getName() + "存在问题导致部分菜品无法正常打印,请检查打印机!\n";
            }

        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckOrderDishPrinter,e);
        }
        return exceptionStr;
    }

    @Override
    public void autoPrintOrderDish (Integer orderId) throws SSException{

        List<OrderDish> orderDishs = new ArrayList<OrderDish>();
        try{
            if(Assert.isNotNull(orderId)
                    &&!Assert.lessOrEqualZero(orderId)){

                orderDishs=orderDishService.listByOrderId(orderId);

                for(OrderDish dto : orderDishs){

                    // 是套餐的话,根据套餐的Id来查询tag,小类需要自动打印且当前菜品状态为已下单的时候再打印
                    if(dto.getIsPackage()==PackageStatusEnums.IsPackage.getId()){

                        if(dto.getStatus()==OrderDishStatusEnums.IsBooked.getId()){

                            // 查询小类，看是否下单后立即打印
                            Tag tag = new Tag();
                            DishDto dishDto = new DishDto();
                            dishDto = dishService.queryById(dto.getPackageId());
                            tag = tagService.queryById(dishDto.getTagId());
                            // 小类需要下单后立即打印
                            if(tag.getPrintAfterConfirmOrder()== OrderDishAutoPrintStatusEnums.PrintAtOnce.getId()){

                                this.printOrderDishById(dto.getId());
                            }
                        }
                    }
                    else{
                        if(dto.getStatus()==OrderDishStatusEnums.IsBooked.getId()){

                            // 查询小类，看是否下单后立即打印
                            Tag tag = new Tag();
                            DishDto dishDto = new DishDto();
                            dishDto = dishService.queryById(dto.getDishId());
                            tag = tagService.queryById(dishDto.getTagId());
                            // 小类需要下单后立即打印
                            if(tag.getPrintAfterConfirmOrder()== OrderDishAutoPrintStatusEnums.PrintAtOnce.getId()){

                                this.printOrderDishById(dto.getId());
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.PrintOrderDishAtOnceFail,e);
        }
    }
}
