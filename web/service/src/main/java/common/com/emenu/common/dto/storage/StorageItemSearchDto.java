package com.emenu.common.dto.storage;

import java.util.List;

/**
 * StorageItemSearchDto
 *
 * @author: zhangteng
 * @time: 2015/11/12 8:54
 **/
public class StorageItemSearchDto {

    // 页码
    private Integer pageNo;

    // 分页size
    private Integer pageSize;

    // 关键词（名称、编号、助记码）
    private String keyword;

    // 供货商ID
    private Integer supplierPartyId;

    // 分类ID
    private List<Integer> tagIdList;

    public StorageItemSearchDto() {
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
}
