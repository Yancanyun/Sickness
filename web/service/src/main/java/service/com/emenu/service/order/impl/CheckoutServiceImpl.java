package com.emenu.service.order.impl;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.CheckoutPay;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.checkout.CheckOutStatusEnums;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.CheckoutConsumptionTypeEnums;
import com.emenu.common.enums.order.OrderDishPresentedEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.enums.printer.PrinterTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.PrintUtils;
import com.emenu.mapper.order.CheckoutMapper;
import com.emenu.service.dish.DishService;
import com.emenu.service.order.CheckoutPayService;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private CheckoutPayService checkoutPayService;

    @Override
    public Checkout queryByTableIdAndStatus(int tableId, int status) throws SSException {
        Checkout checkout = null;
        try {
            Assert.lessOrEqualZero(tableId, EmenuException.TableIdError);
            Assert.lessOrEqualZero(tableId, EmenuException.CheckoutStatusError);

            checkout = checkoutMapper.queryByTableIdAndStatus(tableId, status);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCheckoutByTableIdFailed, e);
        }
        return checkout;
    }

    @Override
    public Checkout queryByTableId(int tableId) throws SSException {
        Checkout checkout = null;
        try {
            Assert.lessOrEqualZero(tableId, EmenuException.TableIdError);

            checkout = checkoutMapper.queryByTableId(tableId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCheckoutByTableIdFailed, e);
        }
        return checkout;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public Checkout newCheckout(Checkout checkout) throws SSException {
        Checkout checkout1 = null;
        try {
            Assert.isNotNull(checkout, EmenuException.CheckoutIsNotNull);

            checkout1= commonDao.insert(checkout);
            List<Order> orderList = orderService.listByTableIdAndStatus(checkout.getTableId(), OrderStatusEnums.IsBooked.getId());
            for (Order order : orderList) {
                order.setCheckoutId(checkout1.getId());
                orderService.updateOrder(order);
            }
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewCheckoutFailed, e);
        }
        return checkout1;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateCheckout(Checkout checkout) throws SSException {
        try {
            Assert.isNotNull(checkout, EmenuException.CheckoutIsNotNull);

            commonDao.update(checkout);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateCheckoutFailed, e);
        }
    }

    @Override
    public JSONObject printCheckOutByTableId(Integer tableId) throws SSException {
        List<Order> orders = new ArrayList<Order>();
        List<OrderDish> orderDishs = new ArrayList<OrderDish>();
        Table table = new Table();
        String str = new String();
        JSONObject jsonObject = new JSONObject();
        Checkout checkout = new Checkout();
        // 记录套餐标识
        Map<Integer,Integer> packageFlagMap = new HashMap<Integer, Integer>();
        try{
            // 只有未结账的结账单才能打印
            checkout = checkoutMapper.queryByTableIdAndStatus(tableId,CheckOutStatusEnums.IsNotCheckOut.getId());
            // 获取对应餐桌未结账的所有订单
            orders = orderService.listByTableIdAndStatus(tableId,OrderStatusEnums.IsBooked.getId());
            if(checkout!=null)
            {
                // 获取订单的所有菜品
                if(orders!=null&&!orders.isEmpty()) {
                    for(Order dto : orders) {
                        orderDishs.addAll(orderDishService.listByOrderId(dto.getId()));
                    }
                }
                // 查询出餐桌的信息
                table = tableService.queryById(tableId);
                str += "餐台名称:"+table.getName()+"\n";
                str += "菜品名称          数量    单价\n";
                int len, i;
                // 应收金额包括赠送菜品的金额
                BigDecimal shoulePayMoney = new BigDecimal(0);
                // 打印的菜品信息
                for(OrderDish dto : orderDishs) {
                    Integer orderDishStatus = dto.getStatus();
                    Integer orderDishPresentedStatus = dto.getIsPresentedDish();
                    // 是套餐
                    if(dto.getIsPackage()== PackageStatusEnums.IsPackage.getId()) {
                        // 之前未打印过该套餐,则打印出来
                        if(packageFlagMap.get(dto.getPackageFlag())==null) {
                            // 查询出套餐的信息
                            DishDto dishDto = dishService.queryById(dto.getPackageId());
                            str += dishDto.getName();
                            if(orderDishPresentedStatus == OrderDishPresentedEnums.IsPresentedDish.getId())
                                str += "(赠)";
                            if(orderDishStatus == OrderDishStatusEnums.IsBack.getId())
                                str +="(退)";
                            // 加空格以保证对齐
                            len = dishDto.getName().length() * 2;
                            for(i = 0; i < 18 - len; i++)str += " ";
                            str += String.valueOf(dto.getPackageQuantity());
                            len = String.valueOf(dto.getPackageQuantity()).length();
                            for(i = 0; i < 8 - len; i++)str += " ";
                            str += String.valueOf(dishDto.getSalePrice()) + "\n";
                            shoulePayMoney = shoulePayMoney.add(new BigDecimal(dishDto.getSalePrice().floatValue()*dto.getPackageQuantity()));
                            // 此套餐已经打印过
                            packageFlagMap.put(tableId,1);
                        }
                    }
                    // 非套餐
                    else {
                        // 查询出套餐的信息
                        DishDto dishDto = dishService.queryById(dto.getDishId());
                        str += dishDto.getName();
                        if(orderDishPresentedStatus == OrderDishPresentedEnums.IsPresentedDish.getId())
                            str += "(赠)";
                        if(orderDishStatus == OrderDishStatusEnums.IsBack.getId())
                            str +="(退)";
                        // 加空格以保证对齐
                        len = dishDto.getName().length() * 2;
                        for(i = 0; i < 18 - len; i++)str += " ";
                        str += String.valueOf(dto.getDishQuantity());
                        len = String.valueOf(dto.getDishQuantity()).length();
                        for(i = 0; i < 8 - len; i++)str += " ";
                        str += String.valueOf(dishDto.getSalePrice()) + "\n";
                        shoulePayMoney = shoulePayMoney.add(new BigDecimal(dishDto.getSalePrice().floatValue()*dto.getDishQuantity()));
                    }
                }
                // 如果是第一次消费则打印餐位费和餐台费,第二次消费的话不再收取这些钱
                if(checkout.getConsumptionType()== CheckoutConsumptionTypeEnums.IsFirstConsumption.getId()){

                    str += "餐台费用:"+table.getTableFee()+"\n";
                    // 餐位费用等于实际用餐人数*每一位的费用
                    str += "餐位费用: ="+table.getSeatFee() + " * " +table.getPersonNum()+ " = " +table.getSeatFee().floatValue()*table.getPersonNum().floatValue()+"\n";
                    shoulePayMoney = shoulePayMoney.add(new BigDecimal(table.getTableFee().floatValue()+table.getSeatFee().floatValue()*table.getPersonNum().floatValue()+orderService.returnOrderTotalMoney(tableId).floatValue()));
                }

                // 保留两位小数
                java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
                String moneyTemp = myformat.format(shoulePayMoney);
                str +="应收金额："+moneyTemp + "\n";
                str += "--------------------------------\n";
                // 实际消费金额,不包括赠送的菜品
                BigDecimal actualPayMoney = new BigDecimal(0);
                actualPayMoney = shoulePayMoney;
                // 上面用到过,这里还要用到,要初始化一下
                packageFlagMap = new HashMap<Integer, Integer>();
                // 下面显示所有的赠送菜品
                for(OrderDish dto : orderDishs) {
                    Integer orderDishPresentedStatus = dto.getIsPresentedDish();
                    Integer orderDishStatus = dto.getStatus();
                    if(orderDishPresentedStatus == OrderDishPresentedEnums.IsPresentedDish.getId()){

                        DishDto dishDto = dishService.queryById(dto.getDishId());
                        str += dishDto.getName()+"(赠)";
                        // 加空格以保证对齐
                        len = dishDto.getName().length() * 2;
                        for(i = 0; i < 18 - len; i++)str += " ";
                        str += String.valueOf(dto.getDishQuantity());
                        len = String.valueOf(dto.getDishQuantity()).length();
                        for(i = 0; i < 8 - len; i++)str += " ";
                        str += String.valueOf(dishDto.getSalePrice()) + "\n";
                        // 赠送的菜品是套餐
                        if(orderDishStatus==PackageStatusEnums.IsPackage.getId()){
                            // 未出现过的套餐
                            if(packageFlagMap.get(dto.getPackageFlag())==null){
                                actualPayMoney.subtract(new BigDecimal(dishDto.getSalePrice().floatValue()*dto.getPackageQuantity()));
                                packageFlagMap.put(dto.getPackageFlag(),1);
                            }
                        }
                        // 非套餐
                        else{
                            actualPayMoney.subtract(new BigDecimal(dishDto.getSalePrice().floatValue()*dto.getDishQuantity()));
                        }
                    }
                    moneyTemp = myformat.format(actualPayMoney);
                    str +="实际消费金额: " + moneyTemp +"\n";
                    str += "聚客多移动电子点餐系统由吉林省裕昌恒科技有限公司提供，合作洽谈请拨打热线电话:13234301365\n";
                }
                Socket socket = new Socket();
                InputStream is = null;
                OutputStream os = null;

                // 获取吧台打印机的Id
                Integer printerId = PrinterTypeEnums.BarPrinter.getId();
                Printer printer = new Printer();
                printer = printerService.queryById(printerId);
                // 未设置打印机的ip地址
                if(printer.getIpAddress()==null){
                    jsonObject.put("code",2);
                    return jsonObject;
                }
                // 连接打印机
                socket.connect(new InetSocketAddress(printer.getIpAddress(), 9100), 10000);
                // 成功建立了连接
                if (socket.isConnected()) {
                    os = socket.getOutputStream();
                    // 打印
                    // 初始化打印机
                    os.write(PrintUtils.initPrinter());

                    // 设置0为左对齐,1的话为设置为居中,2为右对齐
                    os.write(PrintUtils.setLocation(0));

                    os.write(PrintUtils.printText(str));//打印信息

                    os.write(PrintUtils.println(4));
                    // 切纸
                    os.write(PrintUtils.cutPaper());
                }
                else{
                    // 打印机连接失败
                    jsonObject.put("code",1);
                    return jsonObject;
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.PrintCheckoutFail);
        }
        jsonObject.put("code",0);
        return jsonObject;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void checkout(int orderId, Checkout checkout, CheckoutPay checkoutPay) throws SSException {
        try {
            Assert.lessOrEqualZero(orderId, EmenuException.OrderIdError);
            if (Assert.isNull(checkout)) {
                throw SSException.get(EmenuException.CheckoutIsNull);
            }

            // 把订单状态改为已结账
            Order order = orderService.queryById(orderId);
            if (Assert.isNull(order)) {
                throw SSException.get(EmenuException.OrderIdError);
            }
            order.setStatus(OrderStatusEnums.IsCheckouted.getId());
            orderService.updateOrder(order);

            checkout.setStatus(CheckOutStatusEnums.IsCheckOut.getId());
            checkout.setCheckoutTime(new Date());

            // 修改结账单中的消费金额
            Table table = tableService.queryById(order.getTableId());
            BigDecimal tableFee = table.getTableFee();
            BigDecimal totalSeatFee = table.getSeatFee().multiply(new BigDecimal(table.getPersonNum()));
            // 计算此订单的金额
            BigDecimal orderCost = new BigDecimal(0);
            List<OrderDishDto> orderDishDtoList = new ArrayList<OrderDishDto>(); // 所有的订单菜品
            orderDishDtoList.addAll(orderDishService.listDtoByOrderId(orderId));
            for (OrderDishDto orderDishDto : orderDishDtoList) {
                if (orderDishDto.getIsPackage() == 0) {
                    orderCost = orderCost.add(new BigDecimal(orderDishDto.getSalePrice().doubleValue() * orderDishDto.getDishQuantity()));
                } else {
                    orderCost = orderCost.add(new BigDecimal(orderDishDto.getSalePrice().doubleValue() * orderDishDto.getPackageQuantity()));
                }
            }
            // TODO: 若本餐桌第二次及之后消费，则无需重复加餐台费及餐位费
            // 消费金额等于餐台费+餐位费*人数+此订单金额
            BigDecimal totalCost = new BigDecimal(0);
            totalCost = totalCost.add(tableFee);
            totalCost = totalCost.add(totalSeatFee);
            totalCost = totalCost.add(orderCost);
            checkout.setConsumptionMoney(totalCost);

            // 根据消费金额及抹零金额计算出实付金额
            checkout.setShouldPayMoney(totalCost.subtract(checkout.getWipeZeroMoney()));

            // 根据宾客付款、预付金额及实付金额计算出找零金额
            checkout.setChangeMoney((checkout.getTotalPayMoney().add(checkout.getPrepayMoney())).subtract(checkout.getShouldPayMoney()));

            updateCheckout(checkout);

            // 新增结账-支付信息
            checkoutPayService.newCheckoutPay(checkoutPay);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckoutFailed, e);
        }
    }
}
