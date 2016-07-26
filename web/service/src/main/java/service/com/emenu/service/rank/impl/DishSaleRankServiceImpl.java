package com.emenu.service.rank.impl;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.order.CheckOrderDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderDishPresentedEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.service.dish.DishPackageService;
import com.emenu.service.dish.DishService;
import com.emenu.service.dish.tag.TagService;
import com.emenu.service.order.OrderService;
import com.emenu.service.rank.DishSaleRankService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import freemarker.template.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2016/7/26.
 */
@Service("dishSaleRankService")
public class DishSaleRankServiceImpl implements DishSaleRankService {
    @Autowired private OrderService orderService;

    @Autowired
    private DishPackageService dishPackageService;

    @Autowired
    private DishService dishService;

    @Autowired
    private TagService tagService;

    @Override
    public List<OrderDish> queryOrderDishByTimePeroid(Date startTime ,Date endTime) throws SSException{
        // 得到这个时间段中所下的订单，CheckOrderDto里面包含订单信息和订单菜品信息
        try{
            List<CheckOrderDto> checkOrderDtoList= orderService.queryOrderByTimePeroid2(startTime, endTime);
            List<OrderDish> orderDishList = new ArrayList<OrderDish>();
            if(checkOrderDtoList!=null){
                for(CheckOrderDto checkOrderDto :checkOrderDtoList){
                    // 某个订单的订单菜品的List，中间可能包含赠送的和退菜的
                    List<OrderDish> list = checkOrderDto.getOrderDishs();
                    // 把集合中退菜和赠菜的菜品去除
                    for(OrderDish orderDish :list){
                        if(orderDish.getIsPresentedDish() != OrderDishPresentedEnums.IsPresentedDish.getId()
                                ||orderDish.getIsPresentedDish() != OrderDishStatusEnums.IsBack.getId()){
                            orderDishList.add(orderDish);
                        }
                    }
                }
            }
            return orderDishList;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetOrderDishByTimePeroidFailed,e);
        }
    }
    @Override
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeroid(Date startTime ,Date endTime) throws SSException{
        try{
            List<DishSaleRankDto> dishSaleRankDtoList= new ArrayList<DishSaleRankDto>();
            List<OrderDish> orderDishList = this.queryOrderDishByTimePeroid(startTime ,endTime);
            // 存菜品(套餐)的数量的map
            Map<Integer,BigDecimal> mapQuality = new HashMap<Integer, BigDecimal>();
            // 存菜品(套餐)的总金额的map
            Map<Integer,BigDecimal> mapMoney = new HashMap<Integer, BigDecimal>();
            // 存菜品
            // 点的菜品里面包括套餐和普通菜品，要分开讨论
            if(orderDishList != null){
                for(OrderDish orderDish:orderDishList){
                    if(orderDish.getIsPackage() == PackageStatusEnums.IsPackage.getId()){
                        // 套餐的数量
                        if(mapQuality.containsKey(orderDish.getPackageId())){
                            mapQuality.put(orderDish.getPackageId(),mapQuality.get(orderDish.getPackageId()).add(BigDecimal.valueOf(orderDish.getPackageQuantity())));
                        }else{
                            mapQuality.put(orderDish.getPackageId(),BigDecimal.valueOf(orderDish.getPackageQuantity()));
                        }
                        // 会员和非会员的价格
                        if(orderDish.getVipDishPrice()!=null){
                            BigDecimal money = orderDish.getSalePrice().multiply(BigDecimal.valueOf(orderDish.getPackageQuantity())).multiply(orderDish.getDiscount()).multiply(BigDecimal.valueOf(0.1));
                            if(mapMoney.containsKey(orderDish.getPackageId())){
                                mapMoney.put(orderDish.getPackageId(),mapMoney.get(orderDish.getPackageId()).add(money));
                            }else{
                                mapMoney.put(orderDish.getPackageId(),money);
                            }
                        }else{
                            BigDecimal money = orderDish.getVipDishPrice();
                            if(mapMoney.containsKey(orderDish.getPackageId())){
                                mapMoney.put(orderDish.getPackageId(),mapMoney.get(orderDish.getPackageId()).add(money));
                            }else{
                                mapMoney.put(orderDish.getPackageId(),money);
                            }
                        }
                    }else{
                        // 菜品的数量
                        if(mapQuality.containsKey(orderDish.getDishId())){
                            mapQuality.put(orderDish.getDishId(),mapQuality.get(orderDish.getDishId()).add(BigDecimal.valueOf(orderDish.getDishQuantity())));
                        }else{
                            mapQuality.put(orderDish.getDishId(),BigDecimal.valueOf(orderDish.getDishQuantity()));
                        }
                        // 会员和非会员的价格
                        if(orderDish.getVipDishPrice()!=null){
                            BigDecimal money = orderDish.getSalePrice().multiply(BigDecimal.valueOf(orderDish.getDishQuantity())).multiply(orderDish.getDiscount()).multiply(BigDecimal.valueOf(0.1));
                            if(mapMoney.containsKey(orderDish.getPackageId())){
                                mapMoney.put(orderDish.getDishId(),mapMoney.get(orderDish.getDishId()).add(money));
                            }else{
                                mapMoney.put(orderDish.getDishId(),money);
                            }
                        }else{
                            BigDecimal money = orderDish.getVipDishPrice();
                            if(mapMoney.containsKey(orderDish.getPackageId())){
                                mapMoney.put(orderDish.getDishId(),mapMoney.get(orderDish.getDishId()).add(money));
                            }else{
                                mapMoney.put(orderDish.getDishId(),money);
                            }
                        }
                    }
                }
            }
            // 遍历map
            for (Map.Entry<Integer, BigDecimal> entry : mapMoney.entrySet()) {
                DishSaleRankDto dishSaleRankDto = new DishSaleRankDto();
                dishSaleRankDto.setDishId(entry.getKey());
                dishSaleRankDto.setConsumeSum(entry.getValue());
                dishSaleRankDto.setDishName(dishService.queryById(entry.getKey()).getName());
                dishSaleRankDto.setNum(mapQuality.get(entry.getKey()).intValue());
                dishSaleRankDto.setTagId(dishService.queryById(entry.getKey()).getTagId());
                dishSaleRankDto.setTagName(tagService.queryById(dishService.queryById(entry.getKey()).getTagId()).getName());
                dishSaleRankDtoList.add(dishSaleRankDto);
            }
            return dishSaleRankDtoList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishSaleRankDtoByTimePeroidFailed,e);
        }
    }

    @Override
    public List<DishSaleRankDto> listAll() throws SSException{
        try{
            Date startTime = new Date(0,0,0,0,0,0);
            Date endTime = new Date(1000,0,0,0,0,0);
            List<DishSaleRankDto> dishSaleRankDtoList = this.queryDishSaleRankDtoByTimePeroid(startTime,endTime);
            return dishSaleRankDtoList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListAllDishSaleRankDtoFailed,e);
        }
    }
}
