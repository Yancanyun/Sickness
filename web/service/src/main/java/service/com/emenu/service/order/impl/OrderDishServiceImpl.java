package com.emenu.service.order.impl;

import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.order.OrderDishCallStatusEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.enums.order.ServeTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.order.OrderDishMapper;
import com.emenu.service.cook.CookTableCacheService;
import com.emenu.service.order.BackDishService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * OrderDishServiceImpl
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
@Service("orderDishService")
public class OrderDishServiceImpl implements OrderDishService{

    @Autowired
    private OrderDishMapper orderDishMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BackDishService backDishService;

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<OrderDishDto> listDtoByOrderId(int orderId) throws SSException {
        List<OrderDishDto> orderDishDtoList = Collections.emptyList();
        try{
            Assert.lessOrEqualZero(orderId, EmenuException.OrderIdError);
            orderDishDtoList = orderDishMapper.listDtoByOrderId(orderId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListOrderDishByOrderIdFailed,e);
        }
        return orderDishDtoList;
    }

    @Override
    public List<OrderDish> listByOrderId(int orderId) throws SSException {
        List<OrderDish> orderDishList = Collections.emptyList();
        try{
            Assert.lessOrEqualZero(orderId, EmenuException.OrderIdError);
            orderDishList = orderDishMapper.listByOrderId(orderId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListOrderDishByOrderIdFailed,e);
        }
        return orderDishList;
    }

    @Override
    public OrderDishDto queryDtoById(int id) throws SSException {
        OrderDishDto orderDishDto = null;
        try{
            Assert.lessOrEqualZero(id,EmenuException.OrderDishIdError);
            orderDishDto = orderDishMapper.queryDtoById(id);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishByIdFailed,e);
        }
        return orderDishDto;
    }

    @Override
    public OrderDish queryById(int id) throws SSException {
        OrderDish orderDish = null;
        try{
            Assert.lessOrEqualZero(id,EmenuException.OrderDishIdError);
            orderDish = orderDishMapper.queryById(id);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishByIdFailed,e);
        }
        return orderDish;
    }

    @Override
    public void updateDishStatus(int id, int status) throws SSException {
        try{
            Assert.lessOrEqualZero(id,EmenuException.OrderDishIdError);
            Assert.lessOrEqualZero(status,EmenuException.OrderDishStatusError);
            if(status==1){
                status = OrderStatusEnums.IsBooked.getId();
            }
            if(status==2){
                status = OrderStatusEnums.IsCheckouted.getId();
            }
            orderDishMapper.updateDishStatus(id, status);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateDishStatusFailed,e);
        }
    }

    @Override
    public void updateServeType(int id, int serveType) throws SSException {
        try{
            Assert.lessOrEqualZero(id,EmenuException.OrderDishIdError);
            Assert.lessOrEqualZero(serveType,EmenuException.OrderServeTypeError);
            if(serveType==1){
                serveType = ServeTypeEnums.Instant.getId();
            }
            if(serveType==2){
                serveType = ServeTypeEnums.Later.getId();
            }
            orderDishMapper.updateServeType(id, serveType);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateServeTypeFailed,e);
        }
    }

    @Override
    public void updatePresentedDish(int id, int isPresentedDish) throws SSException {
        try{
            Assert.lessOrEqualZero(id, EmenuException.OrderDishIdError);
            orderDishMapper.updatePresentedDish(id, isPresentedDish);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdatePresentedDishFailed,e);
        }
    }

    @Override
    public void updateOrderDish(OrderDish orderDish) throws SSException {
        try {
            Assert.isNotNull(orderDish, EmenuException.OrderDishIsNotNull);
            commonDao.update(orderDish);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateOrderDishFailed,e);
        }
    }

    @Override
    public void newOrderDish(OrderDish orderDish) throws SSException {
        try {
            Assert.isNotNull(orderDish, EmenuException.OrderDishIsNotNull);
            commonDao.insert(orderDish);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewOrderDishFailed,e);
        }

    }

    @Override
    public void newOrderDishs(List<OrderDish> orderDishs) throws SSException {
        try{
            Assert.isNotNull(orderDishs,EmenuException.OrderDishIsNotNull);
            Assert.isNotEmpty(orderDishs, EmenuException.OrderdishsIsNotEmpty);
            commonDao.insertAll(orderDishs);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewOrderDishsFailed,e);
        }
    }

    @Override
    public int isTableHaveOrderDish(Integer tableId) throws SSException
    {
        int count = 0;//未上菜的菜品个数
        try{
            if(!Assert.lessOrEqualZero(tableId))
                count = orderDishMapper.isTableHaveOrderDish(tableId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryIsHaveOrderDishFailed,e);
        }
        return count;
    }

    @Override
    public int queryOrderDishTableId(Integer orderDishId) throws SSException
    {
        Integer tableId = new Integer(0);
        try{
            if(!Assert.lessOrEqualZero(orderDishId))
            tableId = orderDishMapper.queryOrderDishTableId(orderDishId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishTableIdFail,e);
        }
        return tableId;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void wipeOrderDish(Integer orderDishId) throws SSException
    {
        try{
            if(!Assert.lessOrEqualZero(orderDishId))
            {
                OrderDish orderDish = new OrderDish();
                orderDish = this.queryById(orderDishId);//查询出菜品信息
                if(orderDish==null)//不存在该订单菜品
                {
                    throw SSException.get(EmenuException.OrderDishNotExist);
                }
                else
                {
                    if(orderDish.getStatus()== OrderDishStatusEnums.IsBooked.getId())//划单的菜品状态必须是2.正在做 1为已下单,打印了菜品的话菜品的状态会从1变为2
                        throw SSException.get(EmenuException.OrderDishStatusWrong);
                    else if(orderDish.getStatus()==OrderDishStatusEnums.IsFinsh.getId())
                        throw SSException.get(EmenuException.OrderDishWipeIsFinsh);
                    else//修改订单菜品状态
                    {
                        orderDish.setStatus(OrderDishStatusEnums.IsFinsh.getId());
                        this.updateOrderDish(orderDish);
                    }
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.WipeOrderDishFail,e);
        }
    }

    @Override
    public int queryMaxPackageFlag() throws SSException
    {
        List<Integer> packageFlagList = new ArrayList<Integer>();
        try{
            packageFlagList=orderDishMapper.queryMaxPackageFlag();
            if(packageFlagList==null||packageFlagList.isEmpty())
            return 0;
            else
                return packageFlagList.get(packageFlagList.size()-1);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException. QueryMaxFalgFail,e);
        }
    }

    @Override
    public int isOrderHaveOrderDish(Integer orderId) throws SSException
    {
        int count = 0;
        try{
            if(!Assert.lessOrEqualZero(orderId))
            {
                count=orderDishMapper.isOrderHaveOrderDish(orderId);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException. QueryMaxFalgFail,e);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void callDish(Integer orderDishId)throws SSException{
        try{
            if (Assert.lessOrEqualZero(orderDishId)){
                throw SSException.get(EmenuException.OrderDishIdError);
            }

            // TODO 多次催菜实现多次记录，现在只做1次催菜记录

            OrderDish orderDish = orderDishMapper.queryById(orderDishId);
            orderDish.setIsCall(OrderDishCallStatusEnums.IsCall.getId());
            commonDao.update(orderDish);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException. CallDishFailed,e);
        }
    }

    @Override
    public List<OrderDishDto> queryOrderDishListByTableId(Integer tableId) throws SSException{
        List<OrderDishDto> orderDishDtoList = new ArrayList<OrderDishDto>();
        try{
            if (Assert.lessOrEqualZero(tableId)){
                throw SSException.get(EmenuException.TableIdError);
            }

            List<com.emenu.common.entity.order.Order> orderList = new ArrayList<com.emenu.common.entity.order.Order>();
            // 查询出对应餐桌所有已下单的订单, 已结账的订单不显示
            orderList = orderService.listByTableIdAndStatus(tableId,  OrderStatusEnums.IsBooked.getId());
            if (Assert.isNotNull(orderList)) {
                for (com.emenu.common.entity.order.Order order : orderList) {
                    Integer orderId = order.getId();
                    orderDishDtoList.addAll(this.listDtoByOrderId(orderId));
                }
            }
            return orderDishDtoList;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishListFailed,e);
        }
    }

    @Override
    public List<BackDish> queryBackDishListByTableIdList(List<Integer> tableIdList) throws SSException{
        List<BackDish> backDishList = new ArrayList<BackDish>();
        try{
            if (Assert.isNull(tableIdList)){
                throw SSException.get(EmenuException.QueryBackDishListFailed);
            }
            if (!tableIdList.isEmpty()){
                for (Integer tableId: tableIdList){
                    // 查询该桌的所有订单
                    List<Order> orderList = orderService.listByTableIdAndStatus(tableId, OrderStatusEnums.IsBooked.getId());
                    for (Order order: orderList){
                        List<BackDish> backDishChildrenList = backDishService.queryBackDishListByOrderId(order.getId());
                        backDishList.addAll(backDishChildrenList);
                    }
                }
            }else {
                throw SSException.get(EmenuException.TableIdError);
            }
            return backDishList;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryBackDishListFailed,e);
        }
    }
}
