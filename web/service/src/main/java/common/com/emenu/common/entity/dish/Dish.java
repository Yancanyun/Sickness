package com.emenu.common.entity.dish;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.pandawork.core.common.entity.AbstractEntity;
import com.pandawork.core.common.log.LogClerk;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 菜品实体
 *
 * @author: zhangteng
 * @time: 2015/11/16 10:16
 **/
@Entity
@Table(name = "t_dish")
public class Dish extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 名称
    private String name;

    // 菜品编号
    @Column(name = "dish_number")
    private String dishNumber;

    // 助记码
    @Column(name = "assistant_code")
    private String assistantCode;

    // 单位ID
    @Column(name = "unit_id")
    private Integer unitId;

    // 定价
    private BigDecimal price;

    // 促销方式(1-无促销,2-折扣,3-促销价格)
    @Column(name = "sale_type")
    private Integer saleType;

    // 折扣
    private Float discount;

    // 售价
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    // 总分类ID
    @Column(name = "category_id")
    private Integer categoryId;

    // 菜品小类ID
    @Column(name = "tag_id")
    private Integer tagId;

    // 简介
    private String description;

    // 状态(0-停售,1-销售中,2-标缺,3-已删除)
    private Integer status;

    // 点赞人数
    @Column(name = "like_nums")
    private Integer likeNums;

    // 是否网络可点(0-不可用,1-可用)
    @Column(name = "is_network_available")
    private Integer isNetworkAvailable;

    // 是否启用会员价(0-不可用,1-可用)
    @Column(name = "is_vip_price_available")
    private Integer isVipPriceAvailable;

    // 是否可用代金卷(0-不可用,1-可用)
    @Column(name = "is_voucher_available")
    private Integer isVoucherAvailable;

    // 上菜时限(0-无限制)
    @Column(name = "time_limit")
    private Integer timeLimit;

    // 创建者partyId
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    // 单位名称
    @Transient
    private String unitName;

    // 状态
    @Transient
    private String statusStr;

    // 总分类名称
    @Transient
    private String categoryNameStr;

    // 菜品小类名称
    @Transient
    private String tagNameStr;

    public Integer getId() {
        return id;
    }

    @Override
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
        DishStatusEnums statusEnums = DishStatusEnums.valueOf(status);
        this.statusStr = statusEnums == null ? "" : statusEnums.getStatus();
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
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

    /**
     * 从dto构造
     *
     * @param dishDto
     * @return
     */
    public Dish constructFromDto(DishDto dishDto) {
        Class thisClass = this.getClass();
        Class dtoClass = dishDto.getClass();
        Field[] thisFields = thisClass.getDeclaredFields();
        for (Field field : thisFields) {
            try {
                Field dtoField;
                try {
                    dtoField = dtoClass.getDeclaredField(field.getName());
                } catch (NoSuchFieldException e) {
                    dtoField = null;
                }
                Object value = null;
                if (dtoField != null) {
                    boolean needToRest = !dtoField.isAccessible();
                    dtoField.setAccessible(true);
                    value = dtoField.get(dishDto);
                    if (needToRest) {
                        dtoField.setAccessible(false);
                    }
                }
                if (value != null) {
                    boolean needToRest = !field.isAccessible();
                    field.setAccessible(true);
                    field.set(this, value);
                    if (needToRest) {
                        field.setAccessible(false);
                    }
                }
            } catch (IllegalAccessException e) {
                LogClerk.errLog.error(e);
            }
        }
        return this;
    }
}
