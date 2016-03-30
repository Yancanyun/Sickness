package com.emenu.common.enums;

import com.pandawork.core.pweio.excel.ExcelTemplateEnum;

/**
 * ExcelExportTemplateEnums
 * Excel模板枚举
 * @author dujuan
 * @date 2015/12/1
 */
public enum ExcelExportTemplateEnums implements ExcelTemplateEnum {
    AdminSettlementCheckList("库存盘点列表",
            "classpath:template/excel/admin_storage_settlement_check_excel_export_template.xls"),
    AdminSettlementSupplierList("结算中心列表",
            "classpath:template/excel/admin_storage_settlement_supplier_excel_export_template.xls"),
    AdminStorageReportList("库存单据",
            "classpath:template/excel/admin_storage_report_excel_template.xls")
    ;


    private String name;
    private String filePath;

    ExcelExportTemplateEnums(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }
}
