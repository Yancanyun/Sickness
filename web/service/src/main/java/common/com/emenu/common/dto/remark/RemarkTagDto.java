package com.emenu.common.dto.remark;

import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;

import java.util.List;

/**
 * RemarkTagDto
 *
 * @author: yangch
 * @time: 2015/11/12 19:56
 */
public class RemarkTagDto {
    private RemarkTag remarkTag;
    private List<RemarkDto> remarkDtoList;

    public RemarkTag getRemarkTag() {
        return remarkTag;
    }

    public void setRemarkTag(RemarkTag remarkTag) {
        this.remarkTag = remarkTag;
    }

    public List<RemarkDto> getRemarkDtoList() {
        return remarkDtoList;
    }

    public void setRemarkDtoList(List<RemarkDto> remarkDtoList) {
        this.remarkDtoList = remarkDtoList;
    }
}
