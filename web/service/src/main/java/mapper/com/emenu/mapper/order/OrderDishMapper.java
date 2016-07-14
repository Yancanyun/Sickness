package com.emenu.mapper.order;

import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.order.OrderDish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OrderDishMapper
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public interface OrderDishMapper {

    /**
     * 根据订单id查询订单菜品列表
     * @param orderId
     * @return
     * @throws Exception
     */
    public List<OrderDish> listByOrderId(@Param("orderId")int orderId) throws Exception;

    /**
     * 根据订单菜品主键id查询订单菜品
     * @param id
     * @return
     * @throws Exception
     */
    public OrderDish queryById(@Param("id")int id) throws Exception;

    /**
     * 根据订单id查询订单菜品dto列表
     * @param orderId
     * @return
     * @throws Exception
     */
    public List<OrderDishDto> listDtoByOrderId(@Param("orderId")int orderId) throws Exception;

    /**
     * 根据订单菜品主键id查询订单菜品dto
     * @param id
     * @return
     * @throws Exception
     */
    public OrderDishDto queryDtoById(@Param("id")int id) throws Exception;

    /**
     * 根据订单菜品主键id修改上菜状态，下单，正在做，已上菜，1,2,3
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateDishStatus(@Param("id")int id,@Param("status")int status) throws Exception;

    /**
     *根据订单菜品主键id修改服务类型，即起1，叫起2
     * @param id
     * @param serveType
     * @throws Exception
     */
    public void updateServeType(@Param("id")int id,@Param("serveType")int serveType)throws Exception;

    /**
     * 根据订单菜品主键id确定是否赠送
     * @param id
     * @param isPresentedDish
     * @throws Exception
     */
    public void updatePresentedDish(@Param("id")int id,@Param("isPresentedDish")int isPresentedDish) throws Exception;

    /**
     * 根据tableId来查询出对应餐桌的订单菜品中是否存在未上菜的菜品
     * 订单菜品状态1.已下单  2.正在做  3.已上菜
     * @param tableId
     * @throws Exception
     */
    public int isTableHaveOrderDish(@Param("tableId") Integer tableId) throws Exception;

    /**
     * 根据订单菜品Id来查询出订单菜品对应的tableId
     * @param orderDishId
     * @throws Exception
     */
    public int queryOrderDishTableId(@Param("orderDishId") Integer orderDishId) throws Exception;


    /**
     * 获取到套餐标识的最大值
     * @param
     * @throws Exception
     */
    public List<Integer> queryMaxPackageFlag() throws Exception;

    /**
     * 判断具体订单是否存在未上菜的菜品
     * 未上菜包括已下单和正在做
     * @param
     * @throws Exception
     */
    public int isOrderHaveOrderDish(@Param("orderId")Integer orderId) throws Exception;
}
