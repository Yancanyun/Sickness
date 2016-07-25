package com.emenu.service.order.impl;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.CheckoutPay;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.checkout.CheckOutStatusEnums;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.*;
import com.emenu.common.enums.printer.PrinterTypeEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.PrintUtils;
import com.emenu.mapper.order.CheckoutMapper;
import com.emenu.service.dish.DishService;
import com.emenu.service.order.CheckoutPayService;
import com.emenu.service.order.CheckoutService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.emenu.service.printer.PrinterService;
import com.emenu.service.table.TableMergeService;
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

    @Autowired
    private TableMergeService tableMergeService;

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
                            str += String.valueOf(dto.getPackageQuantity());
                            len = String.valueOf(dto.getPackageQuantity()).length();
                            for (i = 0; i < 8 - len; i++) str += " ";
                            str += String.valueOf(dishDto.getSalePrice()) + "\n";
                            shoulePayMoney = shoulePayMoney.add(new BigDecimal(dishDto.getSalePrice().floatValue() * dto.getPackageQuantity()));
                            // 此套餐已经打印过
                            packageFlagMap.put(tableId, 1);
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
                        shoulePayMoney = shoulePayMoney.add(new BigDecimal(dishDto.getSalePrice().floatValue() * dto.getDishQuantity()));
                    }
                }
                // 如果是第一次消费则打印餐位费和餐台费,第二次消费的话不再收取这些钱
                if (checkout.getConsumptionType() == CheckoutConsumptionTypeEnums.IsFirstConsumption.getId()) {

                    str += "餐台费用:" + table.getTableFee() + "\n";
                    // 餐位费用等于实际用餐人数*每一位的费用
                    str += "餐位费用: =" + table.getSeatFee() + " * " + table.getPersonNum() + " = " + table.getSeatFee().floatValue() * table.getPersonNum().floatValue() + "\n";
                    shoulePayMoney = shoulePayMoney.add(new BigDecimal(table.getTableFee().floatValue() + table.getSeatFee().floatValue() * table.getPersonNum().floatValue() + orderService.returnOrderTotalMoney(tableId).floatValue()));
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
                        str += String.valueOf(dto.getDishQuantity());
                        len = String.valueOf(dto.getDishQuantity()).length();
                        for (i = 0; i < 8 - len; i++) str += " ";
                        str += String.valueOf(dishDto.getSalePrice()) + "\n";
                        // 赠送的菜品是套餐
                        if (orderDishStatus == PackageStatusEnums.IsPackage.getId()) {
                            // 未出现过的套餐
                            if (packageFlagMap.get(dto.getPackageFlag()) == null) {
                                actualPayMoney.subtract(new BigDecimal(dishDto.getSalePrice().floatValue() * dto.getPackageQuantity()));
                                packageFlagMap.put(dto.getPackageFlag(), 1);
                            }
                        }
                        // 非套餐
                        else {
                            actualPayMoney.subtract(new BigDecimal(dishDto.getSalePrice().floatValue() * dto.getDishQuantity()));
                        }
                    }
                    moneyTemp = myformat.format(actualPayMoney);
                    str += "实际消费金额: " + moneyTemp + "\n";
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
                if (printer.getIpAddress() == null) {
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
        }
        jsonObject.put("code", 0);
        return jsonObject;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void checkout(int tableId, int partyId, BigDecimal consumptionMoney,
                         BigDecimal wipeZeroMoney, BigDecimal totalPayMoney,
                         int checkoutType, String serialNum, int isInvoiced) throws SSException {
        try {
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
            checkout.setWipeZeroMoney(wipeZeroMoney);
            checkout.setTotalPayMoney(totalPayMoney);
            checkout.setIsInvoiced(isInvoiced);
            checkout.setCheckoutTime(new Date());

            // 设置结账-支付信息内容
            CheckoutPay checkoutPay = new CheckoutPay();
            checkoutPay.setPayMoney(consumptionMoney.subtract(wipeZeroMoney));
            checkoutPay.setCheckoutId(checkout.getId());
            checkoutPay.setCheckoutType(checkoutType);
            checkoutPay.setSerialNum(serialNum);

            // 无论是否并台，都先对本餐台自身进行结账
            checkoutOneTable(tableId, checkout, checkoutPay);

            // 把餐台状态改为"占用已结账"
            setTableStatusToCheckouted(tableId);

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

                        // 然后把餐台状态改为"占用已结账"
                        setTableStatusToCheckouted(t.getId());
                    }
                }

                // 若餐台为"已并台"状态，则将它从并台表中删除
                tableList.add(table);
                for (Table t : tableList) {
                    if (t.getStatus().equals(TableStatusEnums.Merged.getId())) {
                        tableMergeService.delTableMergeInfo(t.getId());
                    }
                }

                // TODO: 如果是会员卡结账，要做特殊操作
//                if (checkoutType == CheckoutTypeEnums.VipCard.getId()) {
//
//                }
            }
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
    public void freeOrder(int tableId, int partyId, BigDecimal consumptionMoney,
                          String freeRemark) throws SSException {
        try {
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

            // 把餐台状态改为"占用已结账"
            setTableStatusToCheckouted(tableId);

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

                        // 然后把餐台状态改为"占用已结账"
                        setTableStatusToCheckouted(t.getId());
                    }
                }

                // 若餐台为"已并台"状态，则将它从并台表中删除
                tableList.add(table);
                for (Table t : tableList) {
                    if (t.getStatus().equals(TableStatusEnums.Merged.getId())) {
                        tableMergeService.delTableMergeInfo(t.getId());
                    }
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CheckoutFailed, e);
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

    /**
     * 把餐台改成"占用已结账"状态
     *
     * @param tableId
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    private void setTableStatusToCheckouted(int tableId) throws SSException {
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
}