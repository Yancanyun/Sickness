package com.emenu.common.dto.table;

/**
 * QrCodeDto
 *
 * @author: yangch
 * @time: 2015/10/24 14:24
 */
public class QrCodeDto {
    String areaId;
    String areaName;
    String tableName;
    String qrCodePath;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getQrCodePath() {
        return qrCodePath;
    }

    public void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }
}
