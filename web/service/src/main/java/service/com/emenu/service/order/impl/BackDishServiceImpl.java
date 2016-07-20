package com.emenu.service.order.impl;

import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.order.BackDishMapper;
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
            Integer backDishId = 0;
            // 剩余菜品数量
            Float surplusDishNumber = 0.0f;

            // 创建t_back_dish表的一条记录
            BackDish backDish = new BackDish();
            // 判断是否是套餐,修改对应数量和菜品状态
            // TODO 套餐下菜品如何变化
            if (orderDish.getIsPackage() == PackageStatusEnums.IsPackage.getId()){
                // 套餐
                backDishId = orderDish.getPackageId();
                surplusDishNumber = orderDish.getPackageQuantity() - backNumber;
                // 如果剩余套餐数量为0，状态变为已退菜
                if (surplusDishNumber == 0 ){
                    // 设置套餐状态为已退菜
                    orderDish.setStatus(OrderDishStatusEnums.IsBack.getId());
                }else if (surplusDishNumber < 0){
                    throw SSException.get(EmenuException.BackOrderNumberError);
                }
                // 修改套餐数量
                orderDish.setPackageQuantity(surplusDishNumber.intValue());
            }else if (orderDish.getIsPackage() == PackageStatusEnums.IsNotPackage.getId()){
                // 非套餐
                backDishId = orderDish.getDishId();
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
            }

            backDish.setOrderId(orderDish.getOrderId());
            backDish.setOrderDishId(orderDish.getId());
            backDish.setBackNumber(backNumber);
            backDish.setBackRemarks(backRemarks);
            backDish.setTasteId(orderDish.getTasteId());
            backDish.setEmployeePartyId(partyId);
            backDish.setBackTime(new Date());
            commonDao.insert(backDish);

            // 修改t_order_dish表对应的记录
            commonDao.update(orderDish);

        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.BackOrderDishFailed, e);
        }
    }

    @Override
    public List<BackDish> queryBackDishListByOrderId(Integer orderId) throws SSException{
        List<BackDish> backDishList = Collections.emptyList();
        try{
            if (Assert.lessOrEqualZero(orderId)){
                throw SSException.get(EmenuException.OrderIdError);
            }
            backDishList = backDishMapper.queryBackDishListByOrderId(orderId);
            return backDishList;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryBackDishListFailed, e);
        }
    }
}