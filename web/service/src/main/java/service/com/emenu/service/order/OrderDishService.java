package com.emenu.service.order;

import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;
import org.apache.xpath.operations.Or;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

/**
 * OrderDishService
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public interface OrderDishService {

    /**
     * 根据订单id查询订单菜品列表
     * @param orderId
     * @return
     * @throws Exception
     */
    public List<OrderDish> listByOrderId(int orderId) throws SSException;

    /**
     * 根据订单菜品主键id查询订单菜品
     * @param id
     * @return
     * @throws Exception
     */
    public OrderDish queryById(int id) throws SSException;

    /**
     * 根据订单id查询订单菜品列表dto列表
     * @param orderId
     * @return
     * @throws Exception
     */
    public List<OrderDishDto> listDtoByOrderId(int orderId) throws SSException;

    /**
     * 根据订单菜品主键id查询订单菜品dto
     * @param id
     * @return
     * @throws Exception
     */
    public OrderDishDto queryDtoById(int id) throws SSException;

    /**
     * 根据订单菜品主键id修改上菜状态，下单，正在做，已上菜，1,2,3
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateDishStatus(int id,int status) throws SSException;

    /**
     *根据订单菜品主键id修改服务类型，即起1，叫起2
     * @param id
     * @param serveType
     * @throws Exception
     */
    public void updateServeType(int id,int serveType)throws SSException;

    /**
     * 根据订单菜品主键id确定是否赠送
     * @param id
     * @param isPresentedDish
     * @throws Exception
     */
    public void updatePresentedDish(int id,int isPresentedDish) throws SSException;

    /**
     *修改订单菜品
     * @param orderDish
     * @throws SSException
     */
    public void updateOrderDish(OrderDish orderDish) throws SSException;

    /**
     *添加一个订单菜品
     * @param orderDish
     * @throws SSException
     */
    public void newOrderDish(OrderDish orderDish) throws SSException;

    /**
     * 批量添加订单菜品
     * @param orderDishs
     * @throws SSException
     */
    public void newOrderDishs(List<OrderDish> orderDishs) throws SSException;

    /**
     * 判断一个餐桌是否有未上的菜
     * @param tableId
     * @throws SSException
     */
    public int isTableHaveOrderDish(Integer tableId) throws SSException;

    /**
     * 根据订单菜品Id来查询出订单菜品对应的tableId
     * @param orderDishId
     * @throws Exception
     */
    public int queryOrderDishTableId(Integer orderDishId) throws SSException;

    /**
     * 根据订单菜品主键进行上菜扫码(划单)
     * 扫单后要判断这个餐桌是否还存在菜品,不存在的话清除餐桌版本号
     * 这个判断要在controller方法里面进行,在service方法里进行的话会报错
     * @param orderDishId
     * @throws SSException
     */
    public void wipeOrderDish(Integer orderDishId) throws SSException;

    /**
     * 获取到套餐标识的最大值
     * @param
     * @throws Exception
     */
    public int queryMaxPackageFlag() throws SSException;

    /**
     * 判断具体订单是否存在未上菜的菜品
     * 未上菜包括已下单和正在做
     * @param
     * @throws Exception
     */
    public int isOrderHaveOrderDish(Integer orderId) throws SSException;

    /**
     * 根据订单菜品id催菜
     * @param orderDishId
     * @throws SSException
     */
    public void callDish(Integer orderDishId)throws SSException;

    /**
     * 根据餐台查询所有的订单菜品
     * 不包含已退菜
     * 包含赠送
     * @param tableId
     * @return
     * @throws SSException
     */
    public List<OrderDishDto> queryOrderDishListByTableId(Integer tableId) throws SSException;

    /**
     * 确认订单的时候返回什么菜品原材料不足只能做几份
     * @param tableOrderCache
     * @author pengpengp
     * @return
     * @throws SSException
     */
    public void isOrderHaveEnoughIngredient(TableOrderCache tableOrderCache) throws SSException;

    /**
     *
     * @param orderId
     * @param packageId
     * @return
     * @throws SSException
     */
    public List<OrderDish> listByOrderIdAndPackageId(int orderId, int packageId) throws SSException;

    /**
     * 根据餐桌号查所有菜品（套餐包含的菜品合并为一个套餐，输出套餐）
     * @param tableId
     * @return
     * @author pengpeng
     * @throws SSException
     */
    public List<OrderDishDto> queryOrderDishAndCombinePackageByTableId(Integer tableId) throws SSException;

    /**
     * 根据套餐第一个菜品的orderDishId，查询整个套餐的订单菜品
     * @param orderDishId
     * @return
     * @throws SSException
     */
    public List<OrderDish> queryPackageOrderDishByFirstOrderDishId(Integer orderDishId) throws SSException;

    /**
     * 根据套餐第一个菜品的orderDishId判断整个套餐的状态
     * @param orderDishId
     * @return
     * @throws SSException
     */
    public OrderDishStatusEnums queryPackageStatusByFirstOrderDishId(Integer orderDishId) throws SSException;
}
