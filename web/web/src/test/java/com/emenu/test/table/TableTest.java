package com.emenu.test.table;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.service.table.TableService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * TableTest
 *
 * @author: yangch
 * @time: 2015/10/23 19:18
 */
public class TableTest extends AbstractTestCase {
    @Autowired
    private TableService tableService;

//    @Test
//    public void newTable() throws SSException {
//        Table table = new Table();
//        table.setAreaId(2);
//        table.setName("6号桌");
//        table.setSeatNum(10);
//        table.setSeatFee(new BigDecimal(9));
//        table.setTableFee(new BigDecimal(20));
//        table.setMinCost(new BigDecimal(99));
//        tableService.newTable(table);
//    }

    @Test
    public void queryAllTableItself() throws SSException {
        List<Table> tableList = tableService.listAll();

        for (Table table:tableList){
            System.out.println("name:" + table.getName() + "  seat_num:" + table.getSeatNum()
                    + "  seat_fee:" + table.getSeatFee() + "  table_fee:" + table.getTableFee()
                    + "  min_cost:" + table.getMinCost());
        }
    }

    @Test
    public void queryAllTableDto() throws SSException {
        List<TableDto> tableDtoList = tableService.listAllTableDto();

        for (TableDto tableDto:tableDtoList){
            System.out.println("area_name:" + tableDto.getAreaName() +
                    "   name:" + tableDto.getTable().getName() + "  seat_num:" + tableDto.getTable().getSeatNum()
                    + "  seat_fee:" + tableDto.getTable().getSeatFee() + "  table_fee:" + tableDto.getTable().getTableFee()
                    + "  min_cost:" + tableDto.getTable().getMinCost());
        }
    }

    @Test
    public void queryTableItselfByAreaId() throws SSException {
        List<Table> tableList = tableService.listByAreaId(2);

        for (Table table:tableList){
            System.out.println("name:" + table.getName() + "  seat_num:" + table.getSeatNum()
                    + "  seat_fee:" + table.getSeatFee() + "  table_fee:" + table.getTableFee()
                    + "  min_cost:" + table.getMinCost());
        }
    }

    @Test
    public void queryTableDtoByAreaId() throws SSException {
        List<TableDto> tableDtoList = tableService.listTableDtoByAreaId(1);
        for (TableDto tableDto:tableDtoList){
            System.out.println("area_name:" + tableDto.getAreaName() +
                    "   name:" + tableDto.getTable().getName() + "  seat_num:" + tableDto.getTable().getSeatNum()
                    + "  seat_fee:" + tableDto.getTable().getSeatFee() + "  table_fee:" + tableDto.getTable().getTableFee()
                    + "  min_cost:" + tableDto.getTable().getMinCost());
        }
    }

    @Test
    public void queryTableItselfById() throws SSException {
        Table table = tableService.queryById(1);

        System.out.println("name:" + table.getName() + "  seat_num:" + table.getSeatNum()
                + "  seat_fee:" + table.getSeatFee() + "  table_fee:" + table.getTableFee()
                + "  min_cost:" + table.getMinCost());
    }

    @Test
    public void queryTableDtoById() throws SSException {
        TableDto tableDto = tableService.queryTableDtoById(1);

        System.out.println("area_name:" + tableDto.getAreaName() +
                "   name:" + tableDto.getTable().getName() + "  seat_num:" + tableDto.getTable().getSeatNum()
                + "  seat_fee:" + tableDto.getTable().getSeatFee() + "  table_fee:" + tableDto.getTable().getTableFee()
                + "  min_cost:" + tableDto.getTable().getMinCost());
    }

    @Test
    public void queryTableStatusById() throws SSException {
        int status = tableService.queryStatusById(1);

        System.out.println("status:" + status);
    }

//    @Test
//    public void updateTable() throws SSException {
//        Table table = new Table();
//        table.setId(2);
//        table.setName("8号桌");
//        tableService.updateTable(2, table);
//    }

    @Test
    public void delTableById() throws SSException {
        tableService.delById(5);
    }

    @Test
    public void countTableNumByAreaId() throws SSException {
        int num = tableService.countByAreaId(1);
        System.out.println(num);
    }

    @Test
    public void queryTableDtoByAreaIdAndStatus() throws SSException {
        List<TableDto> tableDtoList = tableService.listTableDtoByAreaIdAndStatus(1, TableStatusEnums.Enabled);
        for (TableDto tableDto:tableDtoList){
            System.out.println("area_name:" + tableDto.getAreaName() +
                    "   name:" + tableDto.getTable().getName() + "  seat_num:" + tableDto.getTable().getSeatNum()
                    + "  seat_fee:" + tableDto.getTable().getSeatFee() + "  table_fee:" + tableDto.getTable().getTableFee()
                    + "  min_cost:" + tableDto.getTable().getMinCost());
        }
    }

    @Test
    public void openTable() throws SSException {
        tableService.openTable(1, 5);
    }

    @Test
    public void changeTable() throws SSException {
        tableService.changeTable(2, 1);
    }

    @Test
    public void cleanTable() throws SSException {
        tableService.cleanTable(2);
    }

    @Test
    public void mergeTable() throws SSException {
        tableService.cleanTable(2);
    }
}
