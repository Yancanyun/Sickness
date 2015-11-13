package com.emenu.common.dto.storage;

import java.util.List;

/**
 * StorageItemSearchDto
 *
 * @author: zhangteng
 * @time: 2015/11/12 8:54
 **/
public class StorageItemSearchDto {

    // 关键词（名称、编号、助记码）
    private String keyword;

    // 供货商ID
    private Integer supplierId;

    // 分类ID
    private List<Integer> tagIdList;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public List<Integer> getTagId() {
        return tagIdList;
    }

    public void setTagId(List<Integer> tagId) {
        this.tagIdList = tagId;
    }
}
