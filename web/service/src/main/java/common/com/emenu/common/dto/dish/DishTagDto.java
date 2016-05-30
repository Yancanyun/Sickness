package com.emenu.common.dto.dish;

import com.emenu.common.entity.dish.DishImg;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.emenu.common.enums.dish.TagEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * DishTagDto
 *
 * @author: zhangteng
 * @time: 2015/12/3 15:25
 **/
public class DishTagDto {

    // 主键
    private Integer id;

    // 分类ID
    private Integer tagId;

    // 菜品ID
    private Integer dishId;

    // 菜品编号
    private String dishNumber;

    // 菜品助记码
    private String dishAssistantCode;

    // 菜品名称
    private String dishName;

    // 菜品单位名称
    private String dishUnitName;

    // 菜品定价
    private BigDecimal dishPrice;

    // 菜品售价
    private BigDecimal dishSalePrice;

    // 菜品状态
    private Integer dishStatus;

    // 菜品折扣
    private Float dishDiscount;

    // 菜品状态
    private String dishStatusStr;

    // 菜品小类ID
    private Integer dishCategoryId;

    // 总分类名称
    private String categoryNameStr;

    // 菜品小类名称
    private String tagNameStr;

    // 小图
    private DishImg smallImg;

    // 大图
    private List<DishImg> bigImgList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getDishNumber() {
        return dishNumber;
    }

    public void setDishNumber(String dishNumber) {
        this.dishNumber = dishNumber;
    }

    public String getDishAssistantCode() {
        return dishAssistantCode;
    }

    public void setDishAssistantCode(String dishAssistantCode) {
        this.dishAssistantCode = dishAssistantCode;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishUnitName() {
        return dishUnitName;
    }

    public void setDishUnitName(String dishUnitName) {
        this.dishUnitName = dishUnitName;
    }

    public BigDecimal getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(BigDecimal dishPrice) {
        this.dishPrice = dishPrice;
    }

    public BigDecimal getDishSalePrice() {
        return dishSalePrice;
    }

    public void setDishSalePrice(BigDecimal dishSalePrice) {
        this.dishSalePrice = dishSalePrice;
    }

    public Integer getDishStatus() {
        return dishStatus;
    }

    public void setDishStatus(Integer dishStatus) {
        this.dishStatus = dishStatus;
        DishStatusEnums statusEnums = DishStatusEnums.valueOf(dishStatus);
        this.dishStatusStr = statusEnums == null ? "" : statusEnums.getStatus();
    }

    public Float getDishDiscount() {
        return dishDiscount;
    }

    public void setDishDiscount(Float dishDiscount) {
        this.dishDiscount = dishDiscount;
    }

    public String getDishStatusStr() {
        return dishStatusStr;
    }

    public void setDishStatusStr(String dishStatusStr) {
        this.dishStatusStr = dishStatusStr;
    }

    public Integer getDishCategoryId() {
        return dishCategoryId;
    }

    public void setDishCategoryId(Integer dishCategoryId) {
        this.dishCategoryId = dishCategoryId;
    }

    public String getCategoryNameStr() {
        return categoryNameStr;
    }

    public void setCategoryNameStr(String categoryNameStr) {
        this.categoryNameStr = categoryNameStr;
    }

    public String getTagNameStr() {
        return tagNameStr;
    }

    public void setTagNameStr(String tagNameStr) {
        this.tagNameStr = tagNameStr;
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
}
