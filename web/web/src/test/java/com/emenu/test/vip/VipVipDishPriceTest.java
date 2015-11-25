package com.emenu.test.vip;

import com.emenu.common.dto.vip.VipDishPriceDto;
import com.emenu.common.entity.vip.VipDishPrice;
import com.emenu.common.enums.TrueEnums;
import com.emenu.mapper.vip.VipDishPriceMapper;
import com.emenu.service.vip.VipDishPriceService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员价测试用例
 *
 * @author chenyuting
 * @date 2015/11/21 9:58
 */
public class VipVipDishPriceTest extends AbstractTestCase{

    @Autowired
    private VipDishPriceMapper vipDishPriceMapper;

    @Autowired
    private VipDishPriceService vipDishPriceService;

    @Test
    public void listByVipDishPricePlanId() throws SSException{
        Integer vipDishPricePlanId  = 1;
        Integer count = 0;
        List<VipDishPrice> vipDishPriceList = vipDishPriceService.listByVipDishPricePlanId(vipDishPricePlanId);
        for (VipDishPrice vipDishPrice: vipDishPriceList){
            System.out.print("会员价方案id:" + vipDishPrice.getVipDishPricePlanId());
            System.out.print("会员价id" + vipDishPrice.getId());
            System.out.print("菜品id" + vipDishPrice.getDishId());
            System.out.println("菜品会员价：" + vipDishPrice.getVipDishPrice());
        }
        System.out.println(count);
    }

    @Test
    public void queryByDishIdAndVipDishPricePlanId() throws SSException{
        Integer dishId = 1;
        Integer vipDishPlanId = 1;
        VipDishPrice vipDishPrice = vipDishPriceService.queryByDishIdAndVipDishPricePlanId(dishId, vipDishPlanId);
        System.out.println(vipDishPrice.getDishId());
        System.out.println(vipDishPrice.getVipDishPricePlanId());
        System.out.println(vipDishPrice.getId());
        System.out.println(vipDishPrice);

    }

    @Test
    public void listVipDishPriceDtosByKeyword() throws SSException{
        Integer vipDishPricePlanId = 1;
        String keyword = "0003";
        Integer curPage = 1;
        Integer pageSize = 20;
        BigDecimal difference = new BigDecimal("0.00");
        List<VipDishPriceDto> vipDishPriceDtoList = vipDishPriceService.listVipDishPriceDtosByKeyword(vipDishPricePlanId, keyword);
        for (VipDishPriceDto vipDishPriceDto: vipDishPriceDtoList){
            difference = vipDishPriceDto.getPrice().subtract(vipDishPriceDto.getVipDishPrice());
            vipDishPriceDto.setPriceDifference(difference);
            System.out.print("菜品id:" + vipDishPriceDto.getDishId() + ";");
            System.out.print("菜品名称：" + vipDishPriceDto.getDishName() + ";");
            System.out.print("编号：" + vipDishPriceDto.getDishNumber() + ";");
            System.out.print("助记码：" + vipDishPriceDto.getAssistantCode() + ";");
            System.out.print("原价：" + vipDishPriceDto.getPrice() + ";");
            System.out.print("售价：" + vipDishPriceDto.getSalePrice() + ";");
            System.out.print("会员价：" + vipDishPriceDto.getVipDishPrice() + ";");
            System.out.println("差价" + vipDishPriceDto.getPriceDifference() + ";");
        }
    }

    @Test
    public void generateVipDishPrice()throws SSException{

        List<Integer> dishIds = new ArrayList<Integer>();
        Integer dishId1 = 1;
        Integer dishId2 = 2;
        Integer dishId3 = 3;
        Integer dishId4 = 4;
        Integer dishId5 = 5;
        Integer dishId6 = 6;
        Integer dishId7 = 7;

        Integer dishId17 = 17;

        //dishIds
        /*dishIds.add(dishId1);
        dishIds.add(dishId2);
        dishIds.add(dishId3);
        dishIds.add(dishId4);
        dishIds.add(dishId5);
        dishIds.add(dishId6);
        dishIds.add(dishId17);*/

        //折扣
        BigDecimal discount = null;
        BigDecimal discount1 = new BigDecimal("9.5");
        BigDecimal discount2 = new BigDecimal("9.2");
        BigDecimal discount3 = new BigDecimal("9.0");
        BigDecimal discount4 = new BigDecimal("8.8");
        BigDecimal discount5 = new BigDecimal("8.5");

        //差价
        BigDecimal difference = null;
        BigDecimal difference1 = new BigDecimal("2.00");
        BigDecimal difference2 = new BigDecimal("6.00");
        BigDecimal difference3 = new BigDecimal("9.00");
        BigDecimal difference4 = new BigDecimal("24.00");

        //最低价
        BigDecimal lowPrice = new BigDecimal("5.00");

        //是否包含酒水
        TrueEnums includeDrinksTrue = TrueEnums.True;
        TrueEnums includeDrinksFalse = TrueEnums.False;

        //是否覆盖已有会员价
        TrueEnums coverTrue = TrueEnums.True;
        TrueEnums coverFalse = TrueEnums.False;

        //会员价方案id
        Integer vipDishPricePlanId1 = 1;
        Integer vipDishPricePlanId2 = 2;
        Integer vipDishPricePlanId3 = 3;
        Integer vipDishPricePlanId4 = 4;
        Integer vipDishPricePlanId5 = 5;

        // 测试1:按照折扣生成会员价
        //生成所有会员价
        // ids为空；折扣95；最低价5.00；包含酒水；覆盖原有会员价；会员价方案id为1；
        //vipDishPriceService.generateVipDishPrice(dishIds, discount1, difference, lowPrice, includeDrinksTrue, coverTrue, vipDishPricePlanId1);

        // 测试2:按照差价生成会员价
        //生成所有会员价
        //vipDishPriceService.generateVipDishPrice(dishIds, discount, difference1, lowPrice, includeDrinksTrue, coverTrue, vipDishPricePlanId2);

        // 测试3:按照折扣生成会员价
        // 选择性更改
        // ids为空；折扣95；最低价5.00；包含酒水；覆盖原有会员价；会员价方案id为1；
        //vipDishPriceService.generateVipDishPrice(dishIds, discount3, difference, lowPrice, includeDrinksFalse, coverTrue, vipDishPricePlanId1);

        // 测试2:按照差价生成会员价
        // 选择性更改
        vipDishPriceService.generateVipDishPrice(dishIds, discount, difference1, lowPrice, includeDrinksTrue, coverFalse, vipDishPricePlanId3);
    }

    @Test
    public void insertAll() throws Exception {
        final VipDishPrice vipDishPrice = new VipDishPrice();
        vipDishPrice.setDishId(2);
        vipDishPrice.setVipDishPricePlanId(20);
        vipDishPrice.setVipDishPrice(new BigDecimal("200.00"));

        final VipDishPrice vipDishPrice1 = new VipDishPrice();
        vipDishPrice1.setDishId(3);
        vipDishPrice1.setVipDishPricePlanId(30);
        vipDishPrice1.setVipDishPrice(new BigDecimal("300.00"));

        List<VipDishPrice> vipDishPriceList = new ArrayList<VipDishPrice>(){{
            add(vipDishPrice);
            add(vipDishPrice1);
        }};

        vipDishPriceMapper.insertAll(vipDishPriceList);
    }
}