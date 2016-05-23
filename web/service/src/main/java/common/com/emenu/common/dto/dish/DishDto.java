package com.emenu.common.dto.dish;

import com.emenu.common.entity.dish.DishImg;
import com.emenu.common.entity.dish.DishMealPeriod;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.dish.Taste;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * DishDto
 *
 * @author: zhangteng
 * @time: 2015/11/17 16:38
 **/
public class DishDto {
    // 主键
    private Integer id;

    // 名称
    private String name;

    // 菜品编号
    private String dishNumber;

    // 助记码
    private String assistantCode;

    // 单位ID
    private Integer unitId;

    // 单位名称
    private String unitName;

    // 单位类型
    private Integer unitType;

    // 定价
    private BigDecimal price;

    // 促销方式(1-无促销,2-折扣,3-促销价格)
    private Integer saleType;

    // 折扣
    private Float discount;

    // 售价
    private BigDecimal salePrice;

    // 总分类ID
    private Integer categoryId;

    // 菜品小类ID
    private Integer tagId;

    // 简介
    private String description;

    // 状态(0-停售,1-销售中,2-标缺,3-已删除)
    private Integer status;

    // 点赞人数
    private Integer likeNums;

    // 是否网络可点(0-不可用,1-可用)
    private Integer isNetworkAvailable;

    // 是否启用会员价(0-不可用,1-可用)
    private Integer isVipPriceAvailable;

    // 是否可用代金卷(0-不可用,1-可用)
    private Integer isVoucherAvailable;

    // 上菜时限(0-无限制)
    private Integer timeLimit;

    // 创建者partyId
    private Integer createdPartyId;

    // 创建时间
    private Date createdTime;

    // 最近修改时间
    private Date lastModifiedTime;

    // 可点餐段ID
    private List<Integer> mealPeriodIdList;

    // 可点餐段
    private List<DishMealPeriod> mealPeriodList;

    // 打印机
    private Integer printerId;

    // 口味ID
    private List<Integer> tasteIdList;

    // 口味
    private List<Taste> tasteList;

    // 小图
    private DishImg smallImg;

    // 大图
    private List<DishImg> bigImgList;

    // 若为套餐中的菜品，则此处为此菜品在某套餐中对应的DishPackage。
    private DishPackage dishPackage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDishNumber() {
        return dishNumber;
    }

    public void setDishNumber(String dishNumber) {
        this.dishNumber = dishNumber;
    }

    public String getAssistantCode() {
        return assistantCode;
    }

    public void setAssistantCode(String assistantCode) {
        this.assistantCode = assistantCode;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLikeNums() {
        return likeNums;
    }

    public void setLikeNums(Integer likeNums) {
        this.likeNums = likeNums;
    }

    public Integer getIsNetworkAvailable() {
        return isNetworkAvailable;
    }

    public void setIsNetworkAvailable(Integer isNetworkAvailable) {
        this.isNetworkAvailable = isNetworkAvailable;
    }

    public Integer getIsVipPriceAvailable() {
        return isVipPriceAvailable;
    }

    public void setIsVipPriceAvailable(Integer isVipPriceAvailable) {
        this.isVipPriceAvailable = isVipPriceAvailable;
    }

    public Integer getIsVoucherAvailable() {
        return isVoucherAvailable;
    }

    public void setIsVoucherAvailable(Integer isVoucherAvailable) {
        this.isVoucherAvailable = isVoucherAvailable;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
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

    public List<Integer> getMealPeriodIdList() {
        return mealPeriodIdList;
    }

    public void setMealPeriodIdList(List<Integer> mealPeriodIdList) {
        this.mealPeriodIdList = mealPeriodIdList;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getPrinterId() {
        return printerId;
    }

    public void setPrinterId(Integer printerId) {
        this.printerId = printerId;
    }

    public List<Integer> getTasteIdList() {
        return tasteIdList;
    }

    public void setTasteIdList(List<Integer> tasteIdList) {
        this.tasteIdList = tasteIdList;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public List<DishMealPeriod> getMealPeriodList() {
        return mealPeriodList;
    }

    public void setMealPeriodList(List<DishMealPeriod> mealPeriodList) {
        this.mealPeriodList = mealPeriodList;
    }

    public List<Taste> getTasteList() {
        return tasteList;
    }

    public void setTasteList(List<Taste> tasteList) {
        this.tasteList = tasteList;
    }

    public DishImg getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(DishImg smallImg) {
        this.smallImg = smallImg;
    }

    public List<DishImg> getBigImgList() {
        return bigImgList;
    }

    public void setBigImgList(List<DishImg> bigImgList) {
        this.bigImgList = bigImgList;
    }

    public DishPackage getDishPackage() {
        return dishPackage;
    }

    public void setDishPackage(DishPackage dishPackage) {
        this.dishPackage = dishPackage;
    }
}
