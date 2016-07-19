package com.emenu.service.order.impl;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.enums.printer.PrinterDishEnum;
import com.emenu.common.enums.printer.PrinterTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.PrintUtils;
import com.emenu.mapper.order.CheckoutMapper;
import com.emenu.service.dish.DishService;
import com.emenu.service.order.CheckoutService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.emenu.service.printer.PrinterService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

/**
 * CheckoutServiceImpl
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
@Service("checkoutService")
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CheckoutMapper checkoutMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private OrderDishService orderDishService;

    @Autowired
    private TableService tableService;

    @Autowired
    private DishService dishService;

    @Autowired
    private PrinterService printerService;

    @Override
    public Checkout queryByTableId(Integer tableId,Integer status) throws SSException {
        Checkout checkout = null;
        try {
            Assert.lessOrEqualZero(tableId,EmenuException.TableIdError);
            checkout = checkoutMapper.queryByTableId(tableId,status);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCheckoutByTableIdFailed);
        }
        return checkout;
    }

    @Override
    public Checkout newCheckout(Checkout checkout) throws SSException {
        Checkout checkout1 = null;
        try{
            Assert.isNotNull(checkout, EmenuException.CheckoutIsNotNull);

            checkout1= commonDao.insert(checkout);
            Assert.isNotNull(checkout,EmenuException.CheckoutIsNotNull);
            List<Order> orderList = orderService.listByTableIdAndStatus(checkout.getTableId(), OrderStatusEnums.IsBooked.getId());
            for(Order order:orderList){
                order.setCheckoutId(checkout1.getId());
                orderService.updateOrder(order);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewCheckoutFailed);
        }
        return checkout1;
    }

    @Override
    public void updateCheckout(Checkout checkout) throws SSException {
        try{
            Assert.isNotNull(checkout, EmenuException.CheckoutIsNotNull);
            commonDao.update(checkout);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateCheckoutFailed);
        }
    }

    @Override
    public JSONObject printCheckOutByTableId(Integer tableId) throws SSException
    {
        List<Order> orders = new ArrayList<Order>();
        List<OrderDish> orderDishs = new ArrayList<OrderDish>();
        Table table = new Table();
        String str = new String();
        JSONObject jsonObject = new JSONObject();
        // 记录套餐标识
        Map<Integer,Integer> packageFlagMap = new HashMap<Integer, Integer>();
        try{
            // 获取对应餐桌未结账的所有订单
            orders = orderService.listByTableIdAndStatus(tableId,OrderStatusEnums.IsBooked.getId());

            // 获取订单的所有菜品
            if(orders!=null&&!orders.isEmpty())
            {
                for(Order dto : orders)
                {

                    orderDishs.addAll(orderDishService.listByOrderId(dto.getId()));
                }
            }

            // 查询出餐桌的信息
            table = tableService.queryById(tableId);
            str += "餐台名称:"+table.getName()+"\n";
            str += "菜品名称          数量    单价\n";

            int len, i;
            // 打印的菜品信息
            for(OrderDish dto : orderDishs)
            {
                // 是套餐
                if(dto.getIsPackage()== PackageStatusEnums.IsPackage.getId())
                {
                    // 之前未打印过该套餐,则打印出来
                    if(packageFlagMap.get(dto.getPackageFlag())==null)
                    {
                        // 查询出套餐的信息
                        DishDto dishDto = dishService.queryById(dto.getPackageId());
                        str += dishDto.getName();

                        // 加空格以保证对齐
                        len = dishDto.getName().length() * 2;
                        for(i = 0; i < 18 - len; i++)str += " ";
                        str += String.valueOf(dto.getPackageQuantity());
                        len = String.valueOf(dto.getPackageQuantity()).length();
                        for(i = 0; i < 8 - len; i++)str += " ";
                        str += String.valueOf(dishDto.getSalePrice().floatValue()*dto.getPackageQuantity()) + "\n";
                        // 此套餐已经打印过
                        packageFlagMap.put(tableId,1);
                    }
                }
                // 非套餐
                else
                {
                    // 查询出套餐的信息
                    DishDto dishDto = dishService.queryById(dto.getDishId());
                    str += dishDto.getName();

                    // 加空格以保证对齐
                    len = dishDto.getName().length() * 2;
                    for(i = 0; i < 18 - len; i++)str += " ";
                    str += String.valueOf(dto.getDishQuantity());
                    len = String.valueOf(dto.getDishQuantity()).length();
                    for(i = 0; i < 8 - len; i++)str += " ";
                    str += String.valueOf(dishDto.getSalePrice().floatValue()*dto.getDishQuantity()) + "\n";
                }
            }

            str += "餐台费用:"+table.getTableFee()+"\n";
            // 餐位费用等于实际用餐人数*每一位的费用
            str += "餐位费用: ="+table.getSeatFee() + " * " +table.getPersonNum()+ " = " +table.getSeatFee().floatValue()*table.getPersonNum().floatValue()+"\n";

            // 总消费金额
            BigDecimal totalMoney = new BigDecimal(0);
            // 总消费金额等于餐台费用+餐位费用+订单菜品的总费用(包括了退的菜和赠送的菜)
            totalMoney = totalMoney.add(new BigDecimal(table.getTableFee().floatValue()+table.getSeatFee().floatValue()*table.getPersonNum().floatValue()+orderService.returnOrderTotalMoney(tableId).floatValue()));
            str +="应收金额："+String.valueOf(totalMoney) + "\n";
            str += "--------------------------------\n";
            str += "聚客多移动电子点餐系统由吉林省裕昌恒科技有限公司提供，合作洽谈请拨打热线电话:13234301365\n";

            Socket socket = new Socket();
            InputStream is = null;
            OutputStream os = null;

            // 获取吧台打印机的Id
            Integer printerId = PrinterTypeEnums.BarPrinter.getId();
            Printer printer = new Printer();
            printer = printerService.queryById(printerId);
            // 未设置打印机的ip地址
            if(printer.getIpAddress()==null)
                jsonObject.put("code",2);
            // 连接打印机
            socket.connect(new InetSocketAddress(printer.getIpAddress(), 9100), 10000);
            // 成功建立了连接
            if (socket.isConnected())
            {
                // 打印
                // 初始化打印机
                os.write(PrintUtils.initPrinter());

                //设置0为左对齐,1的话为设置为居中,2为右对齐
                os.write(PrintUtils.setLocation(0));

                os.write(PrintUtils.printText(str));//打印信息

                os.write(PrintUtils.println(4));
                //切纸
                os.write(PrintUtils.cutPaper());
            }

        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateCheckoutFailed);
        }
        return jsonObject;
    }
}
