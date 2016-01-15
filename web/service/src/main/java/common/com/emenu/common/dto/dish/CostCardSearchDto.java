package com.emenu.common.dto.dish;

import java.util.List;

/**
 * 成本卡搜索Dto
 *
 * @author: zhangteng
 * @time: 2015/12/15 11:16
 **/
public class CostCardSearchDto {

    // 页码
    private Integer pageNo;

    private Integer pageSize;

    // 菜品名称
    private String keyword;

    // 菜品分类ID
    private List<Integer> dishTagIdList;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getDishTagIdList() {
        return dishTagIdList;
    }

    public void setDishTagIdList(List<Integer> dishTagIdList) {
        this.dishTagIdList = dishTagIdList;
    }
}
