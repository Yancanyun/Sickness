package com.emenu.service.order.impl;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.revenue.CheckoutDto;
import com.emenu.common.dto.revenue.CheckoutEachItemSumDto;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.CheckoutPay;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.entity.table.Table;
import com.emenu.common.entity.vip.ConsumptionActivity;
import com.emenu.common.entity.vip.VipAccountInfo;
import com.emenu.common.enums.checkout.CheckOutStatusEnums;
import com.emenu.common.enums.checkout.CheckoutConsumptionTypeEnums;
import com.emenu.common.enums.checkout.CheckoutTypeEnums;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.*;
import com.emenu.common.enums.printer.PrinterTypeEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.enums.vip.ConsumptionActivityTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.PrintUtils;
import com.emenu.mapper.order.CheckoutMapper;
import com.emenu.mapper.party.group.vip.VipInfoMapper;
import com.emenu.service.dish.DishService;
import com.emenu.service.order.*;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.service.printer.PrinterService;
import com.emenu.service.sms.SmsService;
import com.emenu.service.table.TableMergeService;
import com.emenu.service.table.TableService;
import com.emenu.service.vip.VipAccountInfoService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

    @Autowired
    private TableMergeService tableMergeService;

    @Autowired
    private VipAccountInfoService vipAccountInfoService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private OrderDishCacheService orderDishCacheService;

    @Autowired
    private VipInfoMapper vipInfoMapper;

    @Override
    public Checkout queryByTableIdAndStatus(int tableId, int status) throws SSException {
        Checkout checkout = null;
        try {
            Assert.lessOrEqualZero(tableId, EmenuException.TableIdError);
            Assert.lessOrEqualZero(tableId, EmenuException.CheckoutStatusError);

            checkout = checkoutMapper.queryByTableIdAndStatus(tableId, status);
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

            checkout1 = commonDao.insert(checkout);
            List<Order> orderList = orderService.listByTableIdAndStatus(checkout.getTableId(), OrderStatusEnums.IsBooked.getId());
            for (Order order : orderList) {
                order.setCheckoutId(checkout1.getId());
                orderService.updateOrder(order);
            }
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        Map<Integer, Integer> packageFlagMap = new HashMap<Integer, Integer>();

        Socket socket = new Socket();
        InputStream is = null;
        OutputStream os = null;
        try {
            // 只有未结账的结账单才能打印
            checkout = checkoutMapper.queryByTableIdAndStatus(tableId, CheckOutStatusEnums.IsNotCheckOut.getId());
            // 获取对应餐桌未结账的所有订单
            orders = orderService.listByTableIdAndStatus(tableId, OrderStatusEnums.IsBooked.getId());
            if (checkout != null) {
                // 获取订单的所有菜品
                if (orders != null && !orders.isEmpty()) {
                    for (Order dto : orders) {
                        orderDishs.addAll(orderDishService.listByOrderId(dto.getId()));
                    }
                }
                // 查询出餐桌的信息
                table = tableService.queryById(tableId);
                str += "餐台名称:" + table.getName() + "\n";
                str += "菜品名称          数量    单价\n";
                int len, i;
                // 应收金额包括赠送菜品的金额
                BigDecimal shoulePayMoney = new BigDecimal(0);
                // 打印的菜品信息
                for (OrderDish dto : orderDishs) {
                    Integer orderDishStatus = dto.getStatus();
                    Integer orderDishPresentedStatus = dto.getIsPresentedDish();
                    // 是套餐
                    if (dto.getIsPackage() == PackageStatusEnums.IsPackage.getId()) {
                        // 之前未打印过该套餐,则打印出来
                        if (packageFlagMap.get(dto.getPackageFlag()) == null) {
                            // 查询出套餐的信息
                            DishDto dishDto = dishService.queryById(dto.getPackageId());
                            str += dishDto.getName();
                            if (orderDishPresentedStatus == OrderDishPresentedEnums.IsPresentedDish.getId())
                                str += "(赠)";
                            if (orderDishStatus == OrderDishStatusEnums.IsBack.getId())
                                str += "(退)";
                            // 加空格以保证对齐
                            len = dishDto.getName().length() * 2;
                            for (i = 0; i < 18 - len; i++) str += " ";
                            // 记录菜名中存在几个字母或数字或特殊符号
                            int letter = 0;
                            String dishName = dishDto.getName();
                            char[] chars = dishName.toCharArray();
                            for(int n = 0; n < chars.length;n++){
                                if((chars[n] >= 33 && chars[n] <= 126)){
                                    letter ++;
                                }
                            }
                            // 存在几个字母或数字，打印时就多空几格
                            for (i = 0; i < letter; i++) str += " ";

                            str += String.valueOf(dto.getPackageQuantity());
                            len = String.valueOf(dto.getPackageQuantity()).length();
                            for (i = 0; i < 8 - len; i++) str += " ";

                            str += String.valueOf(dishDto.getSalePrice()) + "\n";
                            shoulePayMoney = shoulePayMoney.add(new BigDecimal(dishDto.getSalePrice().floatValue()
                                    * dto.getPackageQuantity()*dto.getDiscount().floatValue()/10.0));
                            // 此套餐已经打印过
                            packageFlagMap.put(dto.getPackageFlag(), 1);
                        }
                    }
                    // 非套餐
                    else {
                        // 查询出套餐的信息
                        DishDto dishDto = dishService.queryById(dto.getDishId());
                        str += dishDto.getName();
                        if (orderDishPresentedStatus == OrderDishPresentedEnums.IsPresentedDish.getId())
                            str += "(赠)";
                        if (orderDishStatus == OrderDishStatusEnums.IsBack.getId())
                            str += "(退)";
                        // 加空格以保证对齐
                        len = dishDto.getName().length() * 2;
                        for (i = 0; i < 18 - len; i++) str += " ";
                        // 记录菜名中存在几个字母或数字或特殊符号
                        int letter = 0;
                        String dishName = dishDto.getName();
                        char[] chars = dishName.toCharArray();
                        for(int n = 0; n < chars.length;n++){
                            if((chars[n] >= 33 && chars[n] <= 126)){
                                letter ++;
                            }
                        }
                        // 存在几个字母或数字，打印时就多空几格
                        for (i = 0; i < letter; i++) str += " ";

                        str += String.valueOf(dto.getDishQuantity());
                        len = String.valueOf(dto.getDishQuantity()).length();
                        for (i = 0; i < 8 - len; i++) str += " ";
                        str += String.valueOf(dishDto.getSalePrice()) + "\n";
                        shoulePayMoney = shoulePayMoney.add(new BigDecimal(dishDto.getSalePrice().floatValue()
                                * dto.getDishQuantity()*dto.getDiscount().floatValue()/10.0));
                    }
                }
                // 如果是第一次消费则打印餐位费和餐台费,第二次消费的话不再收取这些钱
                if (checkout.getConsumptionType() == CheckoutConsumptionTypeEnums.IsFirstConsumption.getId()) {

                    str += "餐台费用:" + table.getTableFee() + "\n";
                    // 餐位费用等于实际用餐人数*每一位的费用
                    str += "餐位费用: =" + table.getSeatFee() + " * " + table.getPersonNum() + " = " + table.getSeatFee().floatValue() * table.getPersonNum().floatValue() + "\n";
                    shoulePayMoney = shoulePayMoney.add(new BigDecimal(table.getTableFee().floatValue() + table.getSeatFee().floatValue() * table.getPersonNum().floatValue()));
                }

                // 保留两位小数
                java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.00");
                String moneyTemp = myformat.format(shoulePayMoney);
                str += "应收金额：" + moneyTemp + "\n";
                str += "--------------------------------\n";
                // 实际消费金额,不包括赠送的菜品
                BigDecimal actualPayMoney = new BigDecimal(0);
                actualPayMoney = shoulePayMoney;
                // 上面用到过,这里还要用到,要初始化一下
                packageFlagMap = new HashMap<Integer, Integer>();
                // 下面显示所有的赠送菜品
                for (OrderDish dto : orderDishs) {
                    Integer orderDishPresentedStatus = dto.getIsPresentedDish();
                    Integer orderDishStatus = dto.getStatus();
                    if (orderDishPresentedStatus == OrderDishPresentedEnums.IsPresentedDish.getId()) {

                        DishDto dishDto = dishService.queryById(dto.getDishId());
                        str += dishDto.getName() + "(赠)";
                        // 加空格以保证对齐
                        len = dishDto.getName().length() * 2;
                        for (i = 0; i < 18 - len; i++) str += " ";
                        // 记录菜名中存在几个字母或数字或特殊符号
                        int letter = 0;
                        String dishName = dishDto.getName();
                        char[] chars = dishName.toCharArray();
                        for(int n = 0; n < chars.length;n++){
                            if((chars[n] >= 33 && chars[n] <= 126)){
                                letter ++;
                            }
                        }
                        // 存在几个字母或数字，打印时就多空几格
                        for (i = 0; i < letter; i++) str += " ";

                        str += String.valueOf(dto.getDishQuantity());
                        len = String.valueOf(dto.getDishQuantity()).length();
                        for (i = 0; i < 8 - len; i++) str += " ";
                        str += String.valueOf(dishDto.getSalePrice()) + "\n";
                        // 赠送的菜品是套餐
                        if (orderDishStatus == PackageStatusEnums.IsPackage.getId()) {
                            // 未出现过的套餐
                            if (packageFlagMap.get(dto.getPackageFlag()) == null) {
                                actualPayMoney.subtract(new BigDecimal(dishDto.getSalePrice().floatValue()
                                        * dto.getPackageQuantity()*dto.getDiscount().floatValue()/10.0));
                                packageFlagMap.put(dto.getPackageFlag(), 1);
                            }
                        }
                        // 非套餐
                        else {
                            actualPayMoney.subtract(new BigDecimal(dishDto.getSalePrice().floatValue()
                                    * dto.getDishQuantity()*dto.getDiscount().floatValue()/10.0));
                        }
                    }
                }

                moneyTemp = myformat.format(actualPayMoney);
                str += "实际消费金额: " + moneyTemp + "\n";
                str += "聚客多移动电子点餐系统由吉林省裕昌恒科技有限公司提供，合作洽谈请拨打热线电话:13234301365\n";

                // 获取吧台打印机的分类Id
                Integer typeId = PrinterTypeEnums.BarPrinter.getId();
                Printer printer = new Printer();
                List<Printer> printers = new ArrayList<Printer>();
                printers = printerService.listAll();
                for(Printer dto : printers){

                    if(dto.getType()==typeId){
                        printer = dto;
                        break;
                    }
                }
                // 未设置打印机的ip地址
                if (printer==null
                        ||printer.getIpAddress() == null) {
                    jsonObject.put("code", 2);
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
                } else {
                    // 打印机连接失败
                    jsonObject.put("code", 1);
                    return jsonObject;
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.PrintCheckoutFail);
        } finally {
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
        jsonObject.put("code", 0);
        return jsonObject;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public List<Checkout> checkout(int tableId, int partyId, BigDecimal consumptionMoney,
                                   BigDecimal wipeZeroMoney, BigDecimal totalPayMoney,
                                   int checkoutType, String serialNum, int isInvoiced) throws SSException {
        try {
            List<Checkout> checkoutList = new ArrayList<Checkout>();

            Assert.lessOrEqualZero(tableId, EmenuException.OrderIdError);
            Table table = tableService.queryById(tableId);
            if (Assert.isNull(table)) {
                throw SSException.get(EmenuException.TableIdError);
            }

            // 若为会员卡结账，若消费金额大于会员卡余额，不允许结账
            if (checkoutType == CheckoutTypeEnums.VipCard.getId()) {
                // 会员卡结账时，流水号字段中的内容是会员partyId
                Integer vipPartyId = Integer.valueOf(serialNum);
                // 查询会员账户信息
                VipAccountInfo vipAccountInfo = vipAccountInfoService.queryByPartyId(vipPartyId);
                if (totalPayMoney.compareTo(vipAccountInfo.getBalance()) == 1) {
                    throw SSException.get(EmenuException.VipBalanceNotEnough);
                }
            }

            // 设置结账单内容
            Checkout checkout = queryByTableIdAndStatus(tableId, CheckOutStatusEnums.IsNotCheckOut.getId());
            if (Assert.isNull(checkout)) {
                throw SSException.get(EmenuException.CheckoutIsNull);
            }
            checkout.setCheckerPartyId(partyId);
            checkout.setConsumptionMoney(consumptionMoney);
            checkout.setWipeZeroMoney(wipeZeroMoney);
            checkout.setTotalPayMoney(totalPayMoney);
            checkout.setIsInvoiced(isInvoiced);
            checkout.setCheckoutTime(new Date());

            // 设置结账-支付信息内容
            CheckoutPay checkoutPay = new CheckoutPay();
            checkoutPay.setPayMoney(consumptionMoney.subtract(wipeZeroMoney));
            checkoutPay.setCheckoutId(checkout.getId());
            checkoutPay.setCheckoutType(checkoutType);
            if (checkoutType != CheckoutTypeEnums.VipCard.getId()) {
                checkoutPay.setSerialNum(serialNum);
            }

            // 无论是否并台，都先对本餐台自身进行结账
            checkoutOneTable(tableId, checkout, checkoutPay);

            // 如果是会员卡结账，要对会员信息做操作
            if (checkoutType == CheckoutTypeEnums.VipCard.getId()) {
                // 会员卡结账时，流水号字段中的内容是会员partyId
                Integer vipPartyId = Integer.valueOf(serialNum);

                // 查询会员账户信息
                VipAccountInfo vipAccountInfo = vipAccountInfoService.queryByPartyId(vipPartyId);

                // 新增一条会员消费记录
                ConsumptionActivity consumptionActivity = new ConsumptionActivity();
                consumptionActivity.setPartyId(vipPartyId);
                // 原有金额
                consumptionActivity.setOriginalAmount(vipAccountInfo.getBalance());
                // 卡内余额
                consumptionActivity.setResidualAmount(vipAccountInfo.getBalance().subtract(totalPayMoney));
                // 消费金额
                consumptionActivity.setConsumptionAmount(consumptionMoney);
                // 实际付款
                consumptionActivity.setActualPayment(totalPayMoney);
                consumptionActivity.setType(ConsumptionActivityTypeEnums.Consumption.getId());
                consumptionActivity.setOperator(employeeService.queryByPartyId(partyId).getName());
                commonDao.insert(consumptionActivity);

                // 修改账户信息
                vipAccountInfo.setBalance(vipAccountInfo.getBalance().subtract(totalPayMoney));
                commonDao.update(vipAccountInfo);

                // 发送给会员短信
                VipInfo vipInfo = vipInfoMapper.queryByPartyId(vipPartyId);
                String text = "【聚客多】您的会员卡正在进行消费操作，消费金额：" + totalPayMoney + "元。交易后余额："
                        + vipAccountInfo.getBalance() + "元。";
                smsService.sendSms(vipInfo.getPhone(), text);
            }

            // 结账之后把已结账的Checkout加到List中
            checkoutList.add(checkout);

            // 把餐台状态改为"占用已结账"
            setTableStatusToCheckouted(tableId);

            // 把本餐台的缓存删掉
            orderDishCacheService.tableLockRemove(tableId);
            orderDishCacheService.cleanCacheByTableId(tableId);

            // 如果并台，则需要把和它并的所有的餐台的都结账
            if (table.getStatus().equals(TableStatusEnums.Merged.getId())) {
                // 与本餐台并台的其他餐台的列表
                List<Table> tableList = tableMergeService.listOtherTableByTableId(tableId);

                if (Assert.isNull(tableList) || tableList.size() == 0) {
                    throw SSException.get(EmenuException.MergeIdError);
                }

                // 依次对所有与本餐台并台的餐台进行结账
                for (Table t : tableList) {
                    Checkout c = queryByTableIdAndStatus(t.getId(), CheckOutStatusEnums.IsNotCheckOut.getId());
                    if (Assert.isNull(c)) {
                        // 若该并台餐台未生成结账单(即其未下单)，直接把餐台状态改为"占用已结账"即可
                        setTableStatusToCheckouted(t.getId());
                    } else {
                        // 若该并台餐台已生成结账单(即其已下单)，则调用私有方法对其结账
                        c.setCheckerPartyId(partyId);
                        c.setCheckoutTime(new Date());
                        checkoutOneTable(t.getId(), c);

                        // 结账之后把已结账的Checkout加到List中
                        checkoutList.add(c);

                        // 然后把餐台状态改为"占用已结账"
                        setTableStatusToCheckouted(t.getId());
                    }
                }

                // 把并台的缓存都删掉
                for (Table t : tableList) {
                    orderDishCacheService.tableLockRemove(t.getId());
                    orderDishCacheService.cleanCacheByTableId(t.getId());
                }

                // 若餐台为"已并台"状态，则将它从并台表中删除
                tableList.add(table);
                for (Table t : tableList) {
                    if (t.getStatus().equals(TableStatusEnums.Merged.getId())) {
                        tableMergeService.delTableMergeInfo(t.getId());
                    }
                }
            }

            return checkoutList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckoutFailed, e);
        }
    }

    @Override
    public BigDecimal calcConsumptionMoney(int tableId) throws SSException {
        try {
            BigDecimal totalCost = new BigDecimal(0); // 本次结账的消费金额等于餐台费+餐位费*人数+所有订单的金额

            Table table = tableService.queryById(tableId);
            if (Assert.isNull(table)) {
                throw SSException.get(EmenuException.TableIdError);
            }

            Checkout checkout = queryByTableIdAndStatus(tableId, CheckOutStatusEnums.IsNotCheckOut.getId());

            // 如果未并台，则只需要把该餐台中的所有未结账的订单进行计算即可
            if (!table.getStatus().equals(TableStatusEnums.Merged.getId())) {
                BigDecimal tableFee = table.getTableFee();
                BigDecimal totalSeatFee = table.getSeatFee().multiply(new BigDecimal(table.getPersonNum()));

                // 计算此餐台已下单未结账订单菜品的总金额
                BigDecimal orderCost = orderService.returnOrderTotalMoney(tableId);
                totalCost = totalCost.add(orderCost);

                // 若本餐台是第一次消费，则需加上餐台费及餐位费
                if (Assert.isNull(checkout)) {
                    throw SSException.get(EmenuException.CheckoutIsNull);
                }
                if (checkout.getConsumptionType() == 1) {
                    totalCost = totalCost.add(tableFee);
                    totalCost = totalCost.add(totalSeatFee);
                }
            }
            // 如果并台，则需要把和它并的所有的餐台的钱算出来
            else {
                // 与本餐台并台的其他餐台的列表
                List<Table> tableList = tableMergeService.listOtherTableByTableId(tableId);

                if (Assert.isNull(tableList) || tableList.size() == 0) {
                    throw SSException.get(EmenuException.MergeIdError);
                }

                // 先计算本餐台自身的消费金额
                BigDecimal tableFee = table.getTableFee();
                BigDecimal totalSeatFee = table.getSeatFee().multiply(new BigDecimal(table.getPersonNum()));
                BigDecimal orderCost = orderService.returnOrderTotalMoney(tableId);
                totalCost = totalCost.add(orderCost);

                // 若本餐台未生成结账单(即其未下单)，需加上它的餐台费及餐位费
                if (Assert.isNull(checkout)) {
                    totalCost = totalCost.add(tableFee);
                    totalCost = totalCost.add(totalSeatFee);
                } else {
                    // 若本餐台是第一次消费，则也需加上餐台费及餐位费
                    if (checkout.getConsumptionType() == 1) {
                        totalCost = totalCost.add(tableFee);
                        totalCost = totalCost.add(totalSeatFee);
                    }
                }

                // 再依次计算所有与本餐台并台的餐台的消费金额
                for (Table t : tableList) {
                    tableFee = t.getTableFee();
                    totalSeatFee = t.getSeatFee().multiply(new BigDecimal(t.getPersonNum()));
                    orderCost = orderService.returnOrderTotalMoney(t.getId());
                    totalCost = totalCost.add(orderCost);

                    Checkout c = queryByTableIdAndStatus(t.getId(), CheckOutStatusEnums.IsNotCheckOut.getId());
                    if (Assert.isNull(c)) {
                        // 若该并台餐台未生成结账单(即其未下单)，需加上它的餐台费及餐位费
                        totalCost = totalCost.add(tableFee);
                        totalCost = totalCost.add(totalSeatFee);
                    } else {
                        // 若该并台餐台是第一次消费，则也需加上餐台费及餐位费
                        if (c.getConsumptionType() == 1) {
                            totalCost = totalCost.add(tableFee);
                            totalCost = totalCost.add(totalSeatFee);
                        }
                    }
                }
            }

            // 保留两位小数
            totalCost = totalCost.setScale(2, BigDecimal.ROUND_HALF_UP);

            return totalCost;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckoutFailed, e);
        }
    }

    @Override
    public BigDecimal calcTableMoney(int tableId) throws SSException {
        try {
            BigDecimal tableMoney = new BigDecimal(0); // 本次结账的房间费用=餐位费*人数+餐台费

            Table table = tableService.queryById(tableId);
            if (Assert.isNull(table)) {
                throw SSException.get(EmenuException.TableIdError);
            }

            Checkout checkout = queryByTableIdAndStatus(tableId, CheckOutStatusEnums.IsNotCheckOut.getId());

            // 如果未并台，则只需要把该餐台中的所有未结账的订单进行计算即可
            if (!table.getStatus().equals(TableStatusEnums.Merged.getId())) {
                BigDecimal tableFee = table.getTableFee();
                BigDecimal totalSeatFee = table.getSeatFee().multiply(new BigDecimal(table.getPersonNum()));

                // 若本餐台是第一次消费，加餐台费及餐位费
                if (Assert.isNull(checkout)) {
                    throw SSException.get(EmenuException.CheckoutIsNull);
                }
                if (checkout.getConsumptionType() == 1) {
                    tableMoney = tableMoney.add(tableFee);
                    tableMoney = tableMoney.add(totalSeatFee);
                }
            }
            // 如果并台，则需要把和它并的所有的餐台的钱算出来
            else {
                // 与本餐台并台的其他餐台的列表
                List<Table> tableList = tableMergeService.listOtherTableByTableId(tableId);

                if (Assert.isNull(tableList) || tableList.size() == 0) {
                    throw SSException.get(EmenuException.MergeIdError);
                }

                BigDecimal tableFee = table.getTableFee();
                BigDecimal totalSeatFee = table.getSeatFee().multiply(new BigDecimal(table.getPersonNum()));

                // 若本餐台未生成结账单(即其未下单)，需加它的餐台费及餐位费
                if (Assert.isNull(checkout)) {
                    tableMoney = tableMoney.add(tableFee);
                    tableMoney = tableMoney.add(totalSeatFee);
                } else {
                    // 若本餐台是第一次消费，则也需加餐台费及餐位费
                    if (checkout.getConsumptionType() == 1) {
                        tableMoney = tableMoney.add(tableFee);
                        tableMoney = tableMoney.add(totalSeatFee);
                    }
                }

                // 再依次计算所有与本餐台并台的餐台的消费金额
                for (Table t : tableList) {
                    tableFee = t.getTableFee();
                    totalSeatFee = t.getSeatFee().multiply(new BigDecimal(t.getPersonNum()));

                    Checkout c = queryByTableIdAndStatus(t.getId(), CheckOutStatusEnums.IsNotCheckOut.getId());
                    if (Assert.isNull(c)) {
                        // 若该并台餐台未生成结账单(即其未下单)，需加上它餐台费及餐位费
                        tableMoney = tableMoney.add(tableFee);
                        tableMoney = tableMoney.add(totalSeatFee);
                    } else {
                        // 若该并台餐台是第一次消费，则也需加餐台费及餐位费
                        if (c.getConsumptionType() == 1) {
                            tableMoney = tableMoney.add(tableFee);
                            tableMoney = tableMoney.add(totalSeatFee);
                        }
                    }
                }
            }

            // 保留两位小数
            tableMoney = tableMoney.setScale(2, BigDecimal.ROUND_HALF_UP);

            return tableMoney;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckoutFailed, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public List<Checkout> freeOrder(int tableId, int partyId, BigDecimal consumptionMoney,
                                    String freeRemark) throws SSException {
        try {
            List<Checkout> checkoutList = new ArrayList<Checkout>();

            Assert.lessOrEqualZero(tableId, EmenuException.OrderIdError);
            Table table = tableService.queryById(tableId);
            if (Assert.isNull(table)) {
                throw SSException.get(EmenuException.TableIdError);
            }

            // 设置结账单内容
            Checkout checkout = queryByTableIdAndStatus(tableId, CheckOutStatusEnums.IsNotCheckOut.getId());
            if (Assert.isNull(checkout)) {
                throw SSException.get(EmenuException.CheckoutIsNull);
            }
            checkout.setCheckerPartyId(partyId);
            checkout.setConsumptionMoney(consumptionMoney);
            checkout.setIsFreeOrder(1);
            checkout.setFreeRemark(freeRemark);
            checkout.setCheckoutTime(new Date());

            // 无论是否并台，都先对本餐台自身进行免单
            freeOneTable(tableId, checkout);

            // 结账之后把已结账的Checkout加到List中
            checkoutList.add(checkout);

            // 把餐台状态改为"占用已结账"
            setTableStatusToCheckouted(tableId);

            // 把本餐台的缓存删掉
            orderDishCacheService.tableLockRemove(tableId);
            orderDishCacheService.cleanCacheByTableId(tableId);

            // 如果并台，则需要把和它并的所有的餐台的都免单
            if (table.getStatus().equals(TableStatusEnums.Merged.getId())) {
                // 与本餐台并台的其他餐台的列表
                List<Table> tableList = tableMergeService.listOtherTableByTableId(tableId);

                if (Assert.isNull(tableList) || tableList.size() == 0) {
                    throw SSException.get(EmenuException.MergeIdError);
                }

                // 依次对所有与本餐台并台的餐台进行免单
                for (Table t : tableList) {
                    Checkout c = queryByTableIdAndStatus(t.getId(), CheckOutStatusEnums.IsNotCheckOut.getId());
                    if (Assert.isNull(c)) {
                        // 若该并台餐台未生成结账单(即其未下单)，直接把餐台状态改为"占用已结账"即可
                        setTableStatusToCheckouted(t.getId());
                    } else {
                        // 若该并台餐台已生成结账单(即其已下单)，则调用私有方法对其免单
                        c.setCheckerPartyId(partyId);
                        c.setIsFreeOrder(1);
                        c.setFreeRemark(freeRemark);
                        c.setCheckoutTime(new Date());
                        freeOneTable(t.getId(), c);

                        // 结账之后把已结账的Checkout加到List中
                        checkoutList.add(c);

                        // 然后把餐台状态改为"占用已结账"
                        orderDishCacheService.tableLockRemove(t.getId());
                        setTableStatusToCheckouted(t.getId());
                    }
                }

                // 把并台的缓存都删掉
                for (Table t : tableList) {
                    orderDishCacheService.cleanCacheByTableId(t.getId());
                }

                // 若餐台为"已并台"状态，则将它从并台表中删除
                tableList.add(table);
                for (Table t : tableList) {
                    if (t.getStatus().equals(TableStatusEnums.Merged.getId())) {
                        tableMergeService.delTableMergeInfo(t.getId());
                    }
                }
            }

            return checkoutList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckoutFailed, e);
        }
    }

    @Override
    public Boolean isPrinterOk() throws SSException {
        Socket socket = new Socket();
        try {

            // 获取吧台打印机的ID
            Integer printerId = PrinterTypeEnums.BarPrinter.getId();
            Printer printer = new Printer();
            printer = printerService.queryById(printerId);

            // 未设置打印机的ip地址
            if (printer.getIpAddress() == null) {
                return false;
            }

            // 连接打印机(超时为1秒)
            socket.connect(new InetSocketAddress(printer.getIpAddress(), 9100), 1000);

            // 未建立连接
            if (!socket.isConnected()) {
                return false;
            }

            return true;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            return false;
        } finally {
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

    /**
     * 对单个餐台(未并台的餐台、并台餐台中进行结账操作的餐台)进行结账
     *
     * @param tableId
     * @param checkout
     * @param checkoutPay
     * @throws SSException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    private void checkoutOneTable(int tableId, Checkout checkout, CheckoutPay checkoutPay) throws SSException {
        try {
            List<Order> orderList = orderService.listByTableIdAndStatus(tableId, OrderStatusEnums.IsBooked.getId());
            if (Assert.isNull(orderList)) {
                throw SSException.get(EmenuException.OrderIdError);
            }

            // 把订单状态都改为已结账
            for (Order order : orderList) {
                order.setStatus(OrderStatusEnums.IsCheckouted.getId());
                orderService.updateOrder(order);
            }

            // 根据消费金额及抹零金额计算出实付金额
            checkout.setShouldPayMoney(checkout.getConsumptionMoney().subtract(checkout.getWipeZeroMoney()));

            // 根据宾客付款、预付金额及实付金额计算出找零金额
            checkout.setChangeMoney((checkout.getTotalPayMoney().add(checkout.getPrepayMoney())).subtract(checkout.getShouldPayMoney()));

            // 把结账单状态修改为"已结账"
            checkout.setStatus(CheckOutStatusEnums.IsCheckOut.getId());

            updateCheckout(checkout);

            // 新增结账-支付信息
            checkoutPayService.newCheckoutPay(checkoutPay);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckoutFailed, e);
        }
    }

    /**
     * 对单个餐台(并台餐台中由其他餐台结账且已点菜的餐台)进行结账
     *
     * @param tableId
     * @param checkout
     * @throws SSException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    private void checkoutOneTable(int tableId, Checkout checkout) throws SSException {
        try {
            List<Order> orderList = orderService.listByTableIdAndStatus(tableId, OrderStatusEnums.IsBooked.getId());
            if (Assert.isNull(orderList)) {
                throw SSException.get(EmenuException.OrderIdError);
            }

            // 把订单状态都改为已结账
            for (Order order : orderList) {
                order.setStatus(OrderStatusEnums.IsCheckouted.getId());
                orderService.updateOrder(order);
            }

            // 把结账单状态修改为"已结账"
            checkout.setStatus(CheckOutStatusEnums.IsCheckOut.getId());

            updateCheckout(checkout);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckoutFailed, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void setTableStatusToCheckouted(int tableId) throws SSException {
        try {
            Table table = tableService.queryById(tableId);
            if (Assert.isNull(table)) {
                throw SSException.get(EmenuException.TableIdError);
            }

            // 把餐台改成"占用已结账"状态
            TableDto tableDto = tableService.queryTableDtoById(tableId);
            table.setStatus(TableStatusEnums.Checkouted.getId());
            tableDto.setTable(table);
            tableService.forceUpdateTable(tableId, tableDto);

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckoutFailed, e);
        }
    }

    /**
     * 对单个餐台进行免单
     * @param tableId
     * @param checkout
     * @throws SSException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    private void freeOneTable(int tableId, Checkout checkout) throws SSException {
        try {
            List<Order> orderList = orderService.listByTableIdAndStatus(tableId, OrderStatusEnums.IsBooked.getId());
            if (Assert.isNull(orderList)) {
                throw SSException.get(EmenuException.OrderIdError);
            }

            // 把订单状态都改为已结账
            for (Order order : orderList) {
                order.setStatus(OrderStatusEnums.IsCheckouted.getId());
                orderService.updateOrder(order);
            }

            // 把结账单状态修改为"已结账"
            checkout.setStatus(CheckOutStatusEnums.IsCheckOut.getId());

            updateCheckout(checkout);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckoutFailed, e);
        }
    }

    public void printCheckOut(List<Checkout> checkouts) throws SSException{
        // checkouts里面的第一个元素为付款的餐桌,结账单的各个属性均为0
        List<Order> orders = new ArrayList<Order>();
        List<OrderDish> orderDishs = new ArrayList<OrderDish>();
        Map<Integer,Integer> packageFlagMap = new HashMap<Integer, Integer>();
        String str="            消费清单\n";
        Socket socket = new Socket();
        InputStream is = null;
        OutputStream os = null;
        Table table = new Table();
        try {
            if(Assert.isNotNull(checkouts)){
                // 获取所有的订单
                for(Checkout checkout : checkouts){
                    orders.addAll(orderService.queryOrdersByCheckoutId(checkout.getId()));
                }

                // 获取所有的订单菜品
                for(Order order :orders){
                    orderDishs.addAll(orderDishService.listByOrderId(order.getId()));
                }

                int len ,i;
                // 应收金额包括赠送菜品的金额
                BigDecimal shoulePayMoney = new BigDecimal(0);
                for (OrderDish dto : orderDishs) {
                    Integer orderDishStatus = dto.getStatus();
                    Integer orderDishPresentedStatus = dto.getIsPresentedDish();
                    // 是套餐
                    if (dto.getIsPackage() == PackageStatusEnums.IsPackage.getId()) {
                        // 之前未打印过该套餐,则打印出来
                        if (packageFlagMap.get(dto.getPackageFlag()) == null) {
                            // 查询出套餐的信息
                            DishDto dishDto = dishService.queryById(dto.getPackageId());
                            str += dishDto.getName();
                            if (orderDishPresentedStatus == OrderDishPresentedEnums.IsPresentedDish.getId())
                                str += "(赠)";
                            if (orderDishStatus == OrderDishStatusEnums.IsBack.getId())
                                str += "(退)";
                            // 加空格以保证对齐
                            len = dishDto.getName().length() * 2;
                            for (i = 0; i < 18 - len; i++) str += " ";
                            // 记录菜名中存在几个字母或数字或特殊符号
                            int letter = 0;
                            String dishName = dishDto.getName();
                            char[] chars = dishName.toCharArray();
                            for(int n = 0; n < chars.length;n++){
                                if((chars[n] >= 33 && chars[n] <= 126)){
                                    letter ++;
                                }
                            }
                            // 存在几个字母或数字，打印时就多空几格
                            for (i = 0; i < letter; i++) str += " ";

                            str += String.valueOf(dto.getPackageQuantity());
                            len = String.valueOf(dto.getPackageQuantity()).length();
                            for (i = 0; i < 8 - len; i++) str += " ";
                            str += String.valueOf(dishDto.getSalePrice()) + "\n";
                            shoulePayMoney = shoulePayMoney.add(new BigDecimal(dishDto.getSalePrice().floatValue()
                                    * dto.getPackageQuantity()*dto.getDiscount().floatValue()/10.0));
                            // 此套餐已经打印过
                            packageFlagMap.put(dto.getPackageFlag(), 1);
                        }
                    }
                    // 非套餐
                    else {
                        // 查询出套餐的信息
                        DishDto dishDto = dishService.queryById(dto.getDishId());
                        str += dishDto.getName();
                        if (orderDishPresentedStatus == OrderDishPresentedEnums.IsPresentedDish.getId())
                            str += "(赠)";
                        if (orderDishStatus == OrderDishStatusEnums.IsBack.getId())
                            str += "(退)";
                        // 加空格以保证对齐
                        len = dishDto.getName().length() * 2;
                        for (i = 0; i < 18 - len; i++) str += " ";
                        str += String.valueOf(dto.getDishQuantity());
                        len = String.valueOf(dto.getDishQuantity()).length();
                        for (i = 0; i < 8 - len; i++) str += " ";
                        str += String.valueOf(dishDto.getSalePrice()) + "\n";
                        shoulePayMoney = shoulePayMoney.add(new BigDecimal(dishDto.getSalePrice().floatValue()
                                * dto.getDishQuantity()*dto.getDiscount().floatValue()/10.0));
                    }
                }
                // 如果是第一次消费则打印餐位费和餐台费,第二次消费的话不再收取这些钱
                if (checkouts.get(0).getConsumptionType() == CheckoutConsumptionTypeEnums.IsFirstConsumption.getId()) {
                    table = tableService.queryById(checkouts.get(0).getTableId());
                    str += "餐台费用: ￥" + table.getTableFee() + "\n";
                    // 餐位费用等于实际用餐人数*每一位的费用
                    str += "餐位费用: ￥" + table.getSeatFee() + " * " + table.getPersonNum() + " = ￥" + table.getSeatFee().floatValue() * table.getPersonNum().floatValue() + "\n";
                    shoulePayMoney = shoulePayMoney.add(new BigDecimal(table.getTableFee().floatValue() + table.getSeatFee().floatValue() * table.getPersonNum().floatValue()));
                }

                // 保留两位小数
                java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.00");
                String moneyTemp = myformat.format(shoulePayMoney);
                str += "应收金额：￥" + moneyTemp + "\n";
                // 实际消费金额,不包括赠送的菜品
                BigDecimal actualPayMoney = new BigDecimal(0);
                actualPayMoney = shoulePayMoney;
                // 上面用到过,这里还要用到,要初始化一下
                packageFlagMap = new HashMap<Integer, Integer>();
                // 下面显示所有的赠送菜品
                for (OrderDish dto : orderDishs) {
                    Integer orderDishPresentedStatus = dto.getIsPresentedDish();
                    Integer orderDishStatus = dto.getStatus();
                    if (orderDishPresentedStatus == OrderDishPresentedEnums.IsPresentedDish.getId()) {

                        DishDto dishDto = dishService.queryById(dto.getDishId());
                        // 赠送的菜品是套餐
                        if (orderDishStatus == PackageStatusEnums.IsPackage.getId()) {
                            // 未出现过的套餐
                            if (packageFlagMap.get(dto.getPackageFlag()) == null) {
                                actualPayMoney.subtract(new BigDecimal(dishDto.getSalePrice().floatValue()
                                        * dto.getPackageQuantity()*dto.getDiscount().floatValue()/10.0));
                                packageFlagMap.put(dto.getPackageFlag(), 1);
                            }
                        }
                        // 非套餐
                        else {
                            actualPayMoney.subtract(new BigDecimal(dishDto.getSalePrice().floatValue()
                                    * dto.getDishQuantity()*dto.getDiscount().floatValue()/10.0));
                        }
                    }
                }

                moneyTemp = myformat.format(actualPayMoney);
                str += "实际消费金额: ￥" + moneyTemp + "\n";
                str += "付款方式: " + CheckoutTypeEnums.valueOf(checkoutPayService.queryByCheckoutId(checkouts.get(0).getId()).getCheckoutType()).getType() + "\n";
                str += "宾客付款: ￥" +checkouts.get(0).getTotalPayMoney().toString()+"\n";
                str += "找零: ￥" +checkouts.get(0).getChangeMoney().toString()+"\n";
                str += "结账桌号: " +tableService.queryById(checkouts.get(0).getTableId()).getName()+"\n";
                str += "结账时间: " + DateUtils.formatDate(checkouts.get(0).getCheckoutTime(), "yyyy-MM-dd HH:mm:ss") + "\n";
                str += "--------------------------------\n";
                str += "聚客多移动电子点餐系统由吉林省裕昌恒科技有限公司提供，合作洽谈请拨打热线电话:13234301365\n";

                // 获取吧台打印机的分类Id
                Integer typeId = PrinterTypeEnums.BarPrinter.getId();
                Printer printer = new Printer();
                List<Printer> printers = new ArrayList<Printer>();
                printers = printerService.listAll();
                for(Printer dto : printers){

                    if(dto.getType()==typeId){
                        printer = dto;
                        break;
                    }
                }
                // 未设置打印机的ip地址或者不存在吧台打印机
                if (printer==null
                        ||printer.getIpAddress() == null) {
                    throw SSException.get(EmenuException.BarPrinterIsNotExistOrIpNotSet);
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
                } else {
                    // 打印机连接失败
                    throw SSException.get(EmenuException. PrinterConnectFaiil);
                }
            }

        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.PrintCheckoutFail, e);
        } finally {
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
}