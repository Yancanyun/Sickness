package com.emenu.common.dto.remark;

import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;

import java.util.List;

/**
 * RemarkDto
 *
 * @author: yangch
 * @time: 2015/11/7 15:33
 */
public class RemarkDto {
    private Remark remark;
    private String remarkTagId;
    private String remarkTagName;

    public Remark getRemark() {
        return remark;
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    public String getRemarkTagId() {
        return remarkTagId;
    }

    public void setRemarkTagId(String remarkTagId) {
        this.remarkTagId = remarkTagId;
    }

    public String getRemarkTagName() {
        return remarkTagName;
    }

    public void setRemarkTagName(String remarkTagName) {
        this.remarkTagName = remarkTagName;
    }
}
