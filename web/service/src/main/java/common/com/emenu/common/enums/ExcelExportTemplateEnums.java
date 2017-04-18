package com.emenu.common.enums;

import com.pandawork.core.pweio.excel.ExcelTemplateEnum;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;

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
            "classpath:template/excel/admin_storage_report_excel_template.xls"),

    AdminStockDocumentsList("新版库存单据",
            "classpath:template/excel/admin_stock_documents_excel_template.xls"),

    AdminStorageList("库存原配料",
            "classpath:template/excel/admin_storage_ingredient_excel_template.xls"),


    AdminStorageItemList("库存物品",
            "classpath:template/excel/admin_storage_item_excel_template.xls"),

    AdminRankDishSaleList("菜品销售排行",
            "classpath:template/excel/admin_rank_dishsale_excel_template.xls"),

    AdminRankBigTagList("菜品大类销售排行",
            "classpath:template/excel/admin_rank_bigtag_excel_template.xls"),

    AdminBackDishCountList("退项清单",
            "classpath:template/excel/admin_revenue_backdish_excel_template.xls"),

    AdminBillAuditList("账单稽查清单",
            "classpath:template/excel/admin_revenue_billaudit_excel_template.xls");


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
