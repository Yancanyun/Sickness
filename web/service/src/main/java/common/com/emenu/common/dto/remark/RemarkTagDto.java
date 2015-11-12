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
    private List<RemarkTag> remarkTagList;
    private Remark remark;

    public List<RemarkTag> getRemarkTagList() {
        return remarkTagList;
    }

    public void setRemarkTagList(List<RemarkTag> remarkTagList) {
        this.remarkTagList = remarkTagList;
    }

    public Remark getRemark() {
        return remark;
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }
}
