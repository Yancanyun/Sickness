package com.emenu.common.dto.dish;
import java.math.BigDecimal;
import java.math.BigDecimal;

/**
 * CostCardItemDto
 *
 * @author quanyibo
 * @date 2016/5/18.
 */
public class CostCardDto {

    //成本卡的主键
    private Integer id;

    //成本卡编号
    private String costCardNumber;

    //菜品名称
    private String name;

    //菜品助记码
    private String assistantNumber;

    //主料成本
    private BigDecimal mainCost;

    //辅料成本
    private BigDecimal assistCost;

    //调料成本
    private BigDecimal deliciousCost;

    //标准成本即总成本
    private BigDecimal standardCost;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(BigDecimal standardCost) {
        this.standardCost = standardCost;
    }

    public BigDecimal getDeliciousCost() {
        return deliciousCost;
    }

    public void setDeliciousCost(BigDecimal deliciousCost) {
        this.deliciousCost = deliciousCost;
    }

    public BigDecimal getAssistCost() {
        return assistCost;
    }

    public void setAssistCost(BigDecimal assistCost) {
        this.assistCost = assistCost;
    }

    public BigDecimal getMainCost() {
        return mainCost;
    }

    public void setMainCost(BigDecimal mainCost) {
        this.mainCost = mainCost;
    }

    public String getAssistantNumber() {
        return assistantNumber;
    }

    public void setAssistantNumber(String assistantNumber) {
        this.assistantNumber = assistantNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCostCardNumber() {
        return costCardNumber;
    }

    public void setCostCardNumber(String costCardNumber) {
        this.costCardNumber = costCardNumber;
    }

}

