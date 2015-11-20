package com.emenu.common.dto.dish;

import java.util.List;

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

    // 分类id
    private List<Integer> tagIdList;

    public DishSearchDto() {
        this.pageNo = -1;
        this.pageSize = -1;
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

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }
}
