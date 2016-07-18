package com.emenu.service.order.impl;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.CheckOrderDto;
import com.emenu.common.entity.dish.DishImg;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.order.OrderMapper;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.Log;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * OrderServiceImpl
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private OrderDishService orderDishService;

    @Override
    public List<Order> listByTableIdAndStatus(int tableId, int status) throws SSException {
        List<Order> orderList = Collections.emptyList();
        try{
            Assert.lessOrEqualZero(tableId, EmenuException.TableIdError);
            Assert.lessOrEqualZero(status,EmenuException.OrderStatusError);
            if(status==1){
                status = OrderStatusEnums.IsBooked.getId();
            }
            if(status == 2){
                status = OrderStatusEnums.IsCheckouted.getId();
            }
            orderList = orderMapper.listByTableIdAndStatus(tableId,status);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListByTableIdAndStatusFailed,e);
        }
        return orderList;
    }

    @Override
    public void newOrder(Order order) throws SSException {
        try{
            Assert.isNotNull(order,EmenuException.OrderIsNotNull);
            commonDao.insert(order);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewOrderFailed,e);
        }
    }

    @Override
    public void updateOrder(Order order) throws SSException {
        try{
            Assert.isNotNull(order,EmenuException.OrderIsNotNull);
            commonDao.update(order);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateOrderFailed,e);
        }
    }

    @Override
    public List<CheckOrderDto> listCheckOrderDtoForCheck(Integer status
            ,Integer isSettlemented
            ,Date date) throws SSException
    {
        List<CheckOrderDto> checkOrderDtos = new ArrayList<CheckOrderDto>();
        List<Order> orders = new ArrayList<Order>();
        try{
            if(Assert.isNotNull(isSettlemented)
                    &&Assert.isNotNull(date))
            {
                //查询出对应的订单
                orders = orderMapper.listOrderByStatusAndIsCheckAndDate(status,isSettlemented,date);
                for(Order dto : orders)
                {
                    CheckOrderDto checkOrderDto = new CheckOrderDto();
                    checkOrderDto.setOrder(dto);
                    checkOrderDto.setOrderDishs(orderDishService.listByOrderId(dto.getId()));
                    checkOrderDtos.add(checkOrderDto);
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCheckOrderDtoFail,e);
        }
        return checkOrderDtos;
    }
    @Override
    public void updateOrderIsSettlementedById(int id , int isSettlemented) throws SSException
    {
        Order order = new Order();
        try{
            if(!Assert.lessOrEqualZero(id)&&Assert.isNotNull(isSettlemented))
            order=orderMapper.queryOrderById(id);
            if(order!=null)
            {
                order.setIsSettlemented(isSettlemented);
                commonDao.update(order);
            }
            else
                throw SSException.get(EmenuException.OrderNotExist);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateOrderIsSettlementedFail,e);
        }
    }

    @Override
    public List<CheckOrderDto> queryOrderByTimePeroid1(Date startTime ,Date endTime) throws SSException
    {
        List<CheckOrderDto> checkOrderDtos = new ArrayList<CheckOrderDto>();
        List<Order> orders = new ArrayList<Order>();
        try{
          if(Assert.isNotNull(startTime)
                  &&Assert.isNotNull(endTime))
          {
              if(startTime.getTime()<endTime.getTime())//开始时间不能大于结束时间
              {
                  orders=orderMapper.queryOrderByTimePeroid1(startTime,endTime);
                  for(Order dto : orders)
                  {
                      CheckOrderDto checkOrderDto = new CheckOrderDto();
                      checkOrderDto.setOrder(dto);
                      checkOrderDto.setOrderDishs(orderDishService.listByOrderId(dto.getId()));
                      checkOrderDtos.add(checkOrderDto);
                  }
              }
              else
              throw SSException.get(EmenuException.TimeUnreasonable1);
          }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderByTimePeroidFail,e);
        }
        return checkOrderDtos;
    }

    @Override
    public List<CheckOrderDto> queryOrderByTimePeroid2(Date startTime,Date endTime) throws SSException
    {
        List<CheckOrderDto> checkOrderDtos = new ArrayList<CheckOrderDto>();
        List<Order> orders = new ArrayList<Order>();
        try{
            if(Assert.isNotNull(startTime)
                    &&Assert.isNotNull(endTime))
            {
                if(startTime.getTime()<=endTime.getTime())//开始时间不能大于结束时间
                {
                    orders=orderMapper.queryOrderByTimePeroid2(startTime,endTime);
                    for(Order dto : orders)
                    {
                        CheckOrderDto checkOrderDto = new CheckOrderDto();
                        checkOrderDto.setOrder(dto);
                        checkOrderDto.setOrderDishs(orderDishService.listByOrderId(dto.getId()));
                        checkOrderDtos.add(checkOrderDto);
                    }
                }
                else
                    throw SSException.get(EmenuException.TimeUnreasonable2);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderByTimePeroidFail,e);
        }
        return checkOrderDtos;
    }

    @Override
    public BigDecimal returnOrderTotalMoney(Integer tabldId) throws SSException
    {
        List<Order> orders = new ArrayList<Order>();
        List<OrderDish> orderDishs = new ArrayList<OrderDish>();
        BigDecimal totalMoney = new BigDecimal(0);
        try{
            orders=this.listByTableIdAndStatus(tabldId,OrderStatusEnums.IsBooked.getId());//获取餐桌的未结账的订单
            if(orders!=null)
            {
                for(Order dto : orders)
                {
                    orderDishs.addAll(orderDishService.listByOrderId(dto.getId()));//获取订单菜品
                }
            }
            if(orderDishs!=null)
            {
                HashMap<Integer,Integer> packageFlagMap = new HashMap<Integer, Integer>();//用来判断套餐标识是否出现过
                for(OrderDish orderDishDto :orderDishs)
                {
                    if(orderDishDto.getIsPackage()== PackageStatusEnums.IsNotPackage.getId()
                            &&orderDishDto.getStatus()!= OrderDishStatusEnums.IsBack.getId())//非套餐，status为4的时候为退菜,退了的菜不做处理
                    {
                        totalMoney= totalMoney.add(new BigDecimal(orderDishDto.getSalePrice().doubleValue()*orderDishDto.getDishQuantity()));
                    }
                    else if(orderDishDto.getIsPackage()==PackageStatusEnums.IsPackage.getId()
                            &&orderDishDto.getStatus()!=OrderDishStatusEnums.IsBack.getId())//套餐的话会有重复，在数据库里套餐被拆成菜品
                    {
                        if(packageFlagMap.get(orderDishDto.getPackageFlag())==null)//没有出现过的套餐
                        {
                            totalMoney=totalMoney.add(new BigDecimal(orderDishDto.getSalePrice().doubleValue()*orderDishDto.getPackageQuantity()));
                            packageFlagMap.put(orderDishDto.getPackageFlag(),1);//标记为出现过
                        }
                    }
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ReturnTableOrderTotalMoneyFail,e);
        }
        return totalMoney;
    }

    @Override
    public Order queryById(Integer orderId) throws SSException{
        try{
            if (Assert.lessOrEqualZero(orderId)){
                throw SSException.get(EmenuException.OrderIdError);
            }
            Order order = commonDao.queryById(Order.class, orderId);
            return order;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ReturnTableOrderTotalMoneyFail,e);
        }
    }
}
