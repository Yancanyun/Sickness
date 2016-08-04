package com.emenu.service.order.impl;

import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.revenue.BackDishCountDto;
import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.order.BackDishMapper;
import com.emenu.service.cook.CookTableCacheService;
import com.emenu.service.dish.DishPackageService;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 退菜service
 *
 * @author chenyuting
 * @date 2016/7/18 11:26
 */
@Service("backDishService")
public class BackDishServiceImpl implements BackDishService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private BackDishMapper backDishMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDishService orderDishService;

    @Autowired
    private DishPackageService dishPackageService;
    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void backDishByOrderDishId(Integer orderDishId, Float backNumber, String backRemarks, Integer partyId) throws SSException{
        try{
            if (Assert.lessOrEqualZero(orderDishId)){
                throw SSException.get(EmenuException.OrderDishIdError);
            }
            if (backNumber <= 0){
                throw SSException.get(EmenuException.BackOrderNumberError);
            }

            OrderDish orderDish = orderDishService.queryById(orderDishId);
            // 剩余菜品数量
            Float surplusDishNumber = 0.0f;

            // 判断是否是套餐,修改对应数量和菜品状态
            if (orderDish.getIsPackage() == PackageStatusEnums.IsPackage.getId()){ // 套餐
                // 计算套餐剩余数量
                surplusDishNumber = orderDish.getPackageQuantity() - backNumber;
                // 如果剩余套餐数量小于0，抛出异常
                if (surplusDishNumber < 0){
                    throw SSException.get(EmenuException.BackOrderNumberError);
                }

                // 套餐下菜品数量变化
                // 查询出该订单下所有菜品，若菜品属于这个套餐，则修改相应的数量
                List<OrderDish> orderDishList = orderDishService.listByOrderId(orderDish.getOrderId());
                for(OrderDish tempOrderDish: orderDishList){
                    if (tempOrderDish.getPackageFlag() == orderDish.getPackageFlag()){
                        // 如果剩余套餐数量为0，状态变为已退菜
                        if (surplusDishNumber == 0 ){
                            // 设置状态为已退菜
                            tempOrderDish.setStatus(OrderDishStatusEnums.IsBack.getId());
                        }
                        // 套餐菜品原始数量
                        Float dishNumber = tempOrderDish.getDishQuantity();// 12
                        // 套餐菜品剩余数量
                        //Float tempSurplusDishNumber = (dishNumber*backNumber)/tempOrderDish.getPackageQuantity();
                        Float tempSurplusDishNumber = (dishNumber/orderDish.getPackageQuantity())*surplusDishNumber;
                        Float f =  (float)(Math.round(tempSurplusDishNumber*100))/100; // 保留两位小数
                        tempOrderDish.setDishQuantity(f);
                        // 套餐数量
                        tempOrderDish.setPackageQuantity(surplusDishNumber.intValue());
                        // 更新orderDish信息
                        commonDao.update(tempOrderDish);
                        // 插入一条退菜记录
                        BackDish backDish = new BackDish();
                        backDish.setOrderId(tempOrderDish.getOrderId());
                        backDish.setOrderDishId(tempOrderDish.getId());
                        backDish.setBackNumber(dishNumber - tempSurplusDishNumber);
                        backDish.setBackRemarks(backRemarks);
                        backDish.setTasteId(tempOrderDish.getTasteId());
                        backDish.setEmployeePartyId(partyId);
                        backDish.setBackTime(new Date());
                        commonDao.insert(backDish);
                    }
                }
            }else if (orderDish.getIsPackage() == PackageStatusEnums.IsNotPackage.getId()){ // 非套餐
                surplusDishNumber = orderDish.getDishQuantity() - backNumber;
                // 如果剩余菜品数量为0，状态变为已退菜
                if (surplusDishNumber == 0 ){
                    // 设置菜品状态为已退菜
                    orderDish.setStatus(OrderDishStatusEnums.IsBack.getId());
                }else if (surplusDishNumber < 0){
                    throw SSException.get(EmenuException.BackOrderNumberError);
                }
                // 修改菜品数量
                orderDish.setDishQuantity(surplusDishNumber);
                // 修改t_order_dish表对应的记录
                commonDao.update(orderDish);

                // 创建t_back_dish表的一条记录
                BackDish backDish = new BackDish();
                backDish.setOrderId(orderDish.getOrderId());
                backDish.setOrderDishId(orderDish.getId());
                backDish.setBackNumber(backNumber);
                backDish.setBackRemarks(backRemarks);
                backDish.setTasteId(orderDish.getTasteId());
                backDish.setEmployeePartyId(partyId);
                backDish.setBackTime(new Date());
                commonDao.insert(backDish);
            }

        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.BackOrderDishFailed, e);
        }
    }

    @Override
    public List<BackDish> queryBackDishListByOrderId(Integer orderId) throws SSException{
        List<BackDish> backDishList = new ArrayList<BackDish>();
        try{
            if (Assert.lessOrEqualZero(orderId)){
                throw SSException.get(EmenuException.OrderIdError);
            }
            List<BackDish> allBackDishList = backDishMapper.queryBackDishListByOrderId(orderId);
            // 套餐菜品计数标记
            Integer flag = 0;
            for (BackDish backDish : allBackDishList){
                // 查询订单菜品信息
                OrderDish orderDish = orderDishService.queryById(backDish.getOrderDishId());
                // 如果退的菜品是套餐
                if (orderDish.getIsPackage() == PackageStatusEnums.IsPackage.getId()){
                    Integer packageFlag = orderDish.getPackageFlag();
                    List<OrderDish> orderDishList = orderDishService.queryPackageOrderDishesByPackageFlag(packageFlag);
                    // 只把套餐的第一个退菜菜品放入List
                    if (flag < 1){
                        // 套餐中菜品的数量
                        Integer number = dishPackageService.queryDishQuantityByPackageIdAndDishId(orderDish.getPackageId(),orderDish.getDishId());
                        // 套餐的退菜数量
                        Float backNumber = backDish.getBackNumber()/number;
                        backDish.setBackNumber(backNumber);
                        backDishList.add(backDish);
                        flag++;
                    }
                    // 一个套餐的菜品遍历完成后，标记归零
                    if (flag >= orderDishList.size()){
                        flag = 0;
                    }
                } else {
                    // 非套餐
                    backDishList.add(backDish);
                }
            }
            return backDishList;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryBackDishListFailed, e);
        }
    }

    @Override
    public List<BackDish> queryOrderByTimePeriod(Date startTime,Date endTime) throws SSException{
        try{
            return backDishMapper.queryOrderByTimePeriod(startTime,endTime);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryBackDishListFailed, e);
        }
    }

    @Override
    public List<BackDishCountDto>queryBackDishCountDtoByTimePeriod (Date startTime,Date endTime) throws SSException{
        try{
            List<BackDish> backDishList = this.queryOrderByTimePeriod(startTime,endTime);
            List<BackDishCountDto> backDishCountDtoList = new ArrayList<BackDishCountDto>();
            for(BackDish backDish : backDishList){
                BackDishCountDto backDishCountDto = new BackDishCountDto();


            }
            return backDishCountDtoList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryBackDishCountDtoFailed, e);
        }
    }

    @Override
    public BackDish queryBackDishById(Integer id) throws SSException{

        BackDish backDish = new BackDish();
        try{
            backDish.setId(id);
           if(Assert.isNotNull(id)
                   &&!Assert.lessOrEqualZero(id))
               backDish = backDishMapper.queryBackDishById(id);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryBackDishByIdFailed, e);
        }
        return backDish;
    }
}