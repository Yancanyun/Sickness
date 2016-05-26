package com.emenu.common.dto.storage;

import java.util.List;

/**
 * ItemAndIngredientSearchDto
 *
 * @author xiaozl
 * @date: 2016/5/14
 */
public class ItemAndIngredientSearchDto {

    // 页码
    private Integer pageNo;

    // 分页size
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    // 关键词（名称、编号、助记码）
    private String keyword;

    // 供货商ID
    private Integer supplierPartyId;

    // 分类ID
    private List<Integer> tagIdList;

    public ItemAndIngredientSearchDto() {
        this.pageNo = -1;
        this.pageSize = -1;
        this.offset = -1;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getSupplierPartyId() {
        return supplierPartyId;
    }

    public void setSupplierPartyId(Integer supplierPartyId) {
        this.supplierPartyId = supplierPartyId;
    }

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
