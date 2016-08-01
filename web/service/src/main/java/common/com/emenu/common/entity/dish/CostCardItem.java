package com.emenu.common.entity.dish;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CostcardItem成本卡详细实体
 *
 * @author xubaorong
 * @date 2016/5/16.
 */
@Entity
@Table(name="t_cost_card_item")
public class CostCardItem extends AbstractEntity{

    //主键
    @Id
    private Integer id;

    //原料id
    @Column(name="ingredient_id")
    private Integer ingredientId;

    //成本卡id
    @Column(name = "cost_card_id")
    private Integer costCardId;

    //原料类别,0为主料，1为辅料，2为调料
    @Column(name = "item_type")
    private Integer itemType;

    //净料用量
    @Column(name = "net_count")
    private BigDecimal netCount;

    //毛料用量
    @Column(name = "total_count")
    private BigDecimal otherCount;

    //班结时是否自动出库,1为是，0为否
    @Column(name = "is_auto_out")
    private Integer isAutoOut;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最后修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    /**********getter and setter********************/
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCostCardId() {
        return costCardId;
    }

    public void setCostCardId(Integer costCardId) {
        this.costCardId = costCardId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public BigDecimal getNetCount() {
        return netCount;
    }

    public void setNetCount(BigDecimal netCount) {
        this.netCount = netCount;
    }

    public BigDecimal getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(BigDecimal otherCount) {
        this.otherCount = otherCount;
    }

    public Integer getIsAutoOut() {
        return isAutoOut;
    }

    public void setIsAutoOut(Integer isAutoOut) {
        this.isAutoOut = isAutoOut;
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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        CostCardItem obj1 = (CostCardItem) obj;
        boolean flag = true;
        if(obj1.getId()!=null && id!=null){
            if(obj1.getId().equals(id)){
                flag = false;
            }
        }
        if(!obj1.getIngredientId().equals(ingredientId)){
            flag = false;
        }

        return flag;
    }

    /**
     * 重写hashcode 方法，返回的hashCode 不一样才认定为不同的对象
     */
    @Override
    public int hashCode() {
        if(costCardId!=null) {
            return ingredientId.hashCode()*costCardId.hashCode();
        }else {
            return ingredientId.hashCode();
        }
    }


}
