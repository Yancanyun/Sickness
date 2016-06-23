package com.emenu.common.entity.order;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenyuting
 * @date 2016/6/1 16:29
 */
@Entity
@Table(name = "t_order_dish")
public class OrderDish extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 订单id
    @Column(name = "order_id")
    private Integer orderId;

    // 套餐id
    @Column(name = "package_id")
    private Integer packageId;

    // 菜品id
    @Column(name = "dish_id")
    private Integer dishId;

    // 套餐数量
    @Column(name = "package_quantity")
    private Integer packageQuantity;

    // 菜品数量
    @Column(name = "dish_quantity")
    private Float dishQuantity;

    // 菜品口味id
    @Column(name = "taste_id")
    private Integer tasteId;

    // 菜品（套餐）备注
    private String remark;

    // 备注总价格
    @Column(name = "remark_price")
    private BigDecimal remarkPrice;

    // 菜品（套餐）售价
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    // 菜品（套餐）会员价
    @Column(name = "vip_dish_price")
    private BigDecimal vipDishPrice;

    // 是否为套餐：0-非套餐；1-套餐
    @Column(name = "is_package")
    private Integer isPackage;

    // 菜品（套餐）折扣
    private BigDecimal discount;

    // 菜品状态：1-已下单；2-正在做；3-已上菜
    private Integer status;

    // 下单时间
    @Column(name = "order_time")
    private Date orderTime;

    // 上菜方式：1-即起；2-叫起
    @Column(name = "serve_type")
    private Integer serveType;

    // 是否赠送：0-非赠送；1-赠送
    @Column(name = "is_presented_dish")
    private Integer isPresentedDish;

    // 赠送备注
    @Column(name = "presented_remark_id")
    private Integer presentedRemarkId;

    //是否催菜
    @Column(name = "is_call")
    private Integer isCall;

    //是否换台
    @Column(name = "is_change")
    private Integer isChange;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;


    // setter、getter
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(Integer packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public Float getDishQuantity() {
        return dishQuantity;
    }

    public void setDishQuantity(Float dishQuantity) {
        this.dishQuantity = dishQuantity;
    }

    public Integer getTasteId() {
        return tasteId;
    }

    public void setTasteId(Integer tasteId) {
        this.tasteId = tasteId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getRemarkPrice() {
        return remarkPrice;
    }

    public void setRemarkPrice(BigDecimal remarkPrice) {
        this.remarkPrice = remarkPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getVipDishPrice() {
        return vipDishPrice;
    }

    public void setVipDishPrice(BigDecimal vipDishPrice) {
        this.vipDishPrice = vipDishPrice;
    }

    public Integer getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(Integer isPackage) {
        this.isPackage = isPackage;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getServeType() {
        return serveType;
    }

    public void setServeType(Integer serveType) {
        this.serveType = serveType;
    }

    public Integer getIsPresentedDish() {
        return isPresentedDish;
    }

    public void setIsPresentedDish(Integer isPresentedDish) {
        this.isPresentedDish = isPresentedDish;
    }

    public Integer getPresentedRemarkId() {
        return presentedRemarkId;
    }

    public void setPresentedRemarkId(Integer presentedRemarkId) {
        this.presentedRemarkId = presentedRemarkId;
    }
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getIsCall() {
        return isCall;
    }

    public void setIsCall(Integer isCall) {
        this.isCall = isCall;
    }

    public Integer getIsChange() {
        return isChange;
    }

    public void setIsChange(Integer isChange) {
        this.isChange = isChange;
    }
}