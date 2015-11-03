package com.emenu.test.other;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreateMetadata {
    private Connection cn = null;
    private List<String> executedTables = null;

    private String driver;
    private String url;
    private String uid;
    private String pwd;
    private String catalog;
    private String schema;

    public CreateMetadata() throws Exception {
        initData();

        // initSqlServerDBParams();
        // initOracleDBParams();
        initMysqlParams();

        Class.forName(driver);
        this.cn = DriverManager.getConnection(url, uid, pwd);
    }

    private void initData() {
        // 排除以下表
        executedTables = new ArrayList<String>();
    }

    /**
     * 数据库连接参数
     */
    private void initMysqlParams() {
        catalog = null; // SqlServer的数据库名
        schema = null;

        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://192.168.1.25:3306/information_schema?defaultFetchSize=25&amp;useLocalSessionState=true&amp;elideSetAutoCommit=true&amp;useUsageAdvisor=false&amp;useReadAheadInput=false&amp;useUnbufferedInput=false&amp;cacheServerConfiguration=true&amp;autoReconnect=true&amp;zeroDateTimeBehavior=convertToNull";
        uid = "root";
        pwd = "mysql";
    }

    /**
     * 取得一个表的所有主键字段
     */
    private String getTablePrimaryKeys(String tableName) {
        try {
            DatabaseMetaData dbmd = cn.getMetaData();
            ResultSet rs = dbmd.getPrimaryKeys(catalog, schema, tableName);
            StringBuffer sb = new StringBuffer(",");
            while (rs.next()) {
                sb.append(rs.getString("COLUMN_NAME") + ",");
            }
            rs.close();

            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public void createTableMetadataMutile(String fileName, List<String> dbNames)
            throws Exception {
        if (fileName == null || fileName.length() == 0) {
            throw new IllegalArgumentException("fileName is null");
        }

        System.out.println("fileName：" + fileName);
        File file = new File(fileName);

        // delete old file
        if (file.exists() && file.isFile()) {
            file.delete();
        }

        int sheetIndex = 0;

        // create sheet
        WritableWorkbook book = Workbook.createWorkbook(new FileOutputStream(
                file));

        try {
            for (String dbName : dbNames) {
                WritableSheet sheet = book.createSheet(dbName + "数据字典",
                        sheetIndex);
                createTableMetadata(sheet, dbName);
            }

            book.write();
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null)
                    cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成数据字典
     *
     * @param dbName
     * @throws SQLException
     */
    private void createTableMetadata(WritableSheet sheet, String dbName) throws SQLException {
        int rowIndex = 0;
        int tableCount = 0;

        List<Table> tabs = generatTables(dbName);

        for (Table tab : tabs) {

            try {
                String tableName = tab.getName();
                System.out.println("tableName：" + tableName);

                // 排除表
                if (executedTables.contains(tableName.toLowerCase()))
                    continue;

                tableCount++;
                System.out.println(tableCount + "、" + tableName + " doing...");

                // 表名
                sheet.mergeCells(0, rowIndex, 7, rowIndex); // 合并单元格，5数字要与表头的cell个数一致
                sheet.addCell(new Label(0, rowIndex, tableCount + "、"
                        + tableName + "(备注：" + tab.getComment() + ")"));
                rowIndex++;

                // 表头
                sheet.addCell(new Label(0, rowIndex, "序号"));
                sheet.addCell(new Label(1, rowIndex, "字段名"));
                sheet.addCell(new Label(2, rowIndex, "字段类型"));
                sheet.addCell(new Label(3, rowIndex, "整数位"));
                sheet.addCell(new Label(4, rowIndex, "小数位"));
                sheet.addCell(new Label(5, rowIndex, "允许空值"));
                sheet.addCell(new Label(6, rowIndex, "缺省值"));
                sheet.addCell(new Label(7, rowIndex, "备注说明"));
                rowIndex++;

                PreparedStatement ps = null;
                ps = cn.prepareStatement(this.getSqlStr(dbName, tableName));
                ResultSet res = ps.executeQuery();
                int colCnt = res.getMetaData().getColumnCount();
                int recordIndex = 1;
                while (res.next()) {
                    sheet.addCell(new Label(0, rowIndex, String
                            .valueOf(recordIndex)));
                    for (int i = 1; i <= colCnt; i++) {
                        sheet.addCell(new Label(i, rowIndex, res.getString(i)));
                    }
                    recordIndex++;
                    rowIndex++;
                }
                rowIndex += 2;
                res.close();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("DONE");
    }

    /**
     * 取得一个表的所有主键字段
     */
    private String getSqlStr(String dbName, String tableName) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT column_name AS '字段名'")
                .append(", column_type AS '数据类型'")
                .append(", numeric_precision AS '整数位'")
                .append(", numeric_scale AS '小数位'")
                .append(", is_nullable AS '允许空值'")
                .append(", column_default AS '缺省值'")
                .append(", column_comment as '备注' ");

        sql.append("   FROM  information_schema.columns ");
        sql.append(" WHERE").append(" table_schema = '").append(dbName)
                .append("'").append(" AND table_name = '").append(tableName)
                .append("'");

        System.out.println("sql:" + sql.toString());
        return sql.toString();

    }

    private List<Table> generatTables(String dbName) throws SQLException {
        String sql = "SELECT table_name as `name`, table_comment as `comment` "
                + "FROM   information_schema.tables "
                + "where table_schema = '" + dbName + "'";

        // 去infomation_schema里面查询出和数据库相对应的表结构
        PreparedStatement ps = null;
        ps = cn.prepareStatement(sql);
        ResultSet res = ps.executeQuery();

        List<Table> tabs = new ArrayList<Table>();

        // 创建标信息
        while (res.next()) {
            Table tab = new Table();
            tab.setName(res.getString("name"));
            tab.setComment(res.getString("comment"));

            tabs.add(tab);
        }

        // 关闭信息
        res.close();

        return tabs;
    }

    /**
     * 表的实体信息
     *
     * @author Administrator
     *
     */
    class Table {
        // 表名
        private String name;
        // 标的注释
        private String comment;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    public static void main(String[] args) {
        try {
            List<String> dbNames = new ArrayList<String>();
            // dbNames.add("db_wl");
            // dbNames.add("db_wl_admin");
            dbNames.add("db_juhui");
            CreateMetadata md = new CreateMetadata();
            md.createTableMetadataMutile("E:\\pandawork\\DOC\\sql\\db_juhui.xls", dbNames);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
