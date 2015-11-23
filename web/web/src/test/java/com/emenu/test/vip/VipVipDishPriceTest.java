package com.emenu.test.vip;

import com.emenu.common.entity.vip.VipDishPrice;
import com.emenu.common.enums.TrueEnums;
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
    private VipDishPriceService vipDishPriceService;

    @Test
    public void newVipDishPrice() throws SSException{
        VipDishPrice vipDishPriceRecord = new VipDishPrice();
        BigDecimal price = new BigDecimal("30.00");
        vipDishPriceRecord.setDishId(1);
        vipDishPriceRecord.setVipDishPricePlanId(1);
        vipDishPriceRecord.setVipDishPrice(price);
        vipDishPriceService.newVipDishPrice(vipDishPriceRecord);
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
    public void generateVipDishPrice()throws SSException{

        List<Integer> ids = new ArrayList<Integer>();
        Integer id1 = 1;
        Integer id2 = 2;
        Integer id3 = 3;
        Integer id4 = 4;
        Integer id5 = 5;
        Integer id6 = 6;
        Integer id7 = 7;

        Integer id11 = 11;
        Integer id12 = 12;
        Integer id13 = 13;
        Integer id14 = 14;
        Integer id15 = 15;
        Integer id16 = 16;
        Integer id17 = 17;

        //ids
        /*ids.add(id1);
        ids.add(id2);
        ids.add(id3);
        ids.add(id4);
        ids.add(id5);
        ids.add(id6);
        ids.add(id7);*/

        ids.add(id11);
        ids.add(id12);
        ids.add(id13);
        ids.add(id14);
        ids.add(id15);
        ids.add(id16);
        ids.add(id17);

        //折扣
        Integer discount = null;
        Integer discount1 = 95;
        Integer discount2 = 92;
        Integer discount3 = 90;
        Integer discount4 = 88;
        Integer discount5 = 85;

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

        // 调用通用接口

        // 测试1: 自动生成-全部-折扣
        // ids为空；折扣95；最低价5.00；包含酒水；覆盖原有会员价；会员价方案id为1；
        //vipDishPriceService.generateVipDishPrice(ids, discount1, difference, lowPrice, includeDrinksTrue, coverTrue, vipDishPricePlanId1);

        // 测试2：选择菜品-折扣
        // ids为12346；折扣90；最低价5.00；不包含酒水；覆盖原有会员价；会员价方案id为1；
        //vipDishPriceService.generateVipDishPrice(ids, discount3, difference, lowPrice, includeDrinksFalse, coverTrue, vipDishPricePlanId1);

        // 测试3：自动生成-全部-差价
        // ids为空；差价-2；最低价5.00；包含酒水；覆盖原有会员价；会员价方案id为1；
        //vipDishPriceService.generateVipDishPrice(ids, discount, difference3, lowPrice, includeDrinksTrue, coverTrue, vipDishPricePlanId2);

        // 测试4：选择菜品-差价
        // ids为1234567；差价-6；最低价5.00；不包含酒水；覆盖原有会员价；会员价方案id为1；
        vipDishPriceService.generateVipDishPrice(ids, discount, difference2, lowPrice, includeDrinksFalse, coverTrue, vipDishPricePlanId2);
    }
}