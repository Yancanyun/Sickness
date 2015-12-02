package com.emenu.common.dto.dish;

import com.pandawork.core.common.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DishSearchDto
 *
 * @author: zhangteng
 * @time: 2015/11/17 16:29
 **/
public class DishSearchDto {

    // 页码
    private Integer pageNo;

    private Integer pageSize;

    // 菜品名称
    private String name;

    // 菜品编号
    private String dishNumber;

    // 助记码
    private String assistantCode;

    // 关键词
    private String keyword;

    // 菜品id
    private List<Integer> dishIdList;

    // 分类id
    private List<Integer> tagIdList;

    // 排序字段
    private String orderBy;

    private String orderByColumn;

    // 排序方式(0-降序,1-升序)
    private Integer orderType;

    private static Map<String, String> orderByMap = new HashMap<String, String>();

    static {
        orderByMap.put("salePrice", "sale_price");
        orderByMap.put("", "id");
    }

    public DishSearchDto() {
        this.pageNo = -1;
        this.pageSize = -1;
        this.orderType = 0;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getDishIdList() {
        return dishIdList;
    }

    public void setDishIdList(List<Integer> dishIdList) {
        this.dishIdList = dishIdList;
    }

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public void setOrderByColumn() {
        String orderByColumn = orderByMap.get(orderBy);
        if (Assert.isNull(orderByColumn)) {
            orderByColumn = "id";
        }
        setOrderByColumn(orderByColumn);
    }
}
