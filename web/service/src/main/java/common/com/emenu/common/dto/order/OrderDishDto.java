package com.emenu.common.dto.order;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * OrderDishDto
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public class OrderDishDto {
    // 主键
    private Integer id;

    // 订单id
    private Integer orderId;

    // 套餐id
    private Integer packageId;

    // 菜品id
    private Integer dishId;

    //菜品名称
    private String dishName;

    //菜品图片路径
    private String imgPath;

    // 套餐数量
    private Integer packageQuantity;

    // 菜品数量
    private Float dishQuantity;

    // 菜品口味id
    private Integer tasteId;

    //菜品口味名称
    private String tasteName;

    //菜品口味关联收费
    private  BigDecimal relatedCharge;

    // 菜品（套餐）备注
    private String remark;

    // 备注总价格
    private BigDecimal remarkPrice;

    // 菜品（套餐）售价
    private BigDecimal salePrice;

    //菜品(套餐)定价
    private BigDecimal price;

    // 菜品（套餐）会员价
    private BigDecimal vipDishPrice;

    // 是否为套餐：0-非套餐；1-套餐
    private Integer isPackage;

    // 菜品（套餐）折扣
    private BigDecimal discount;

    // 菜品状态：1-已下单；2-正在做；3-已上菜
    private Integer status;

    // 下单时间
    private Date orderTime;

    // 上菜方式：1-即起；2-叫起
    private Integer serveType;

    // 是否赠送：0-非赠送；1-赠送
    private Integer isPresentedDish;

    // 赠送备注
    private Integer presentedRemarkId;

    //赠送备注名称
    private String presentedRemarkName;

    //是否催菜

    private Integer isCall;

    //是否换台

    private Integer isChange;

    /********************getter and setter***************/

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }


    public BigDecimal getPrice() {
        return price;
    }

    // 设置定价，定价等于售价加上折扣
    public void setPrice()
    {
        BigDecimal temp = new BigDecimal(0);
        temp=temp.add(this.getDiscount());
        temp=temp.add(this.getSalePrice());
        this.price =temp;
    }

    public String getPresentedRemarkName() {
        return presentedRemarkName;
    }

    public void setPresentedRemarkName(String presentedRemarkName) {
        this.presentedRemarkName = presentedRemarkName;
    }

    public Integer getId() {
        return id;
    }

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

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
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

    public String getTasteName() {
        return tasteName;
    }

    public void setTasteName(String tasteName) {
        this.tasteName = tasteName;
    }

    public BigDecimal getRelatedCharge() {
        return relatedCharge;
    }

    public void setRelatedCharge(BigDecimal relatedCharge) {
        this.relatedCharge = relatedCharge;
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

    public void setPrice(BigDecimal price) {
        this.price = price;
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
