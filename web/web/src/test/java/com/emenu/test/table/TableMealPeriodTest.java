package com.emenu.test.table;

import com.emenu.common.entity.table.TableMealPeriod;
import com.emenu.service.table.TableMealPeriodService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * TableMealPeriodTest
 *
 * @author: yangch
 * @time: 2015/11/9 16:20
 */
public class TableMealPeriodTest extends AbstractTestCase {
    @Autowired
    private TableMealPeriodService tableMealPeriodService;

    @Test
    public void queryByTableId() throws SSException {
        int tableId = 1;

        List<Integer> MIdList = tableMealPeriodService.listMealPeriodIdByTableId(tableId);

        for (int i : MIdList) {
            System.out.println("MealPeriodID:" + i);
        }
    }

    @Test
    public void newTableMealPeriod() throws SSException{
        List<TableMealPeriod> tableMealPeriodList = new ArrayList<TableMealPeriod>();

        TableMealPeriod tableMealPeriod1 = new TableMealPeriod();
        tableMealPeriod1.setTableId(2);
        tableMealPeriod1.setMealPeriodId(3);
        TableMealPeriod tableMealPeriod2 = new TableMealPeriod();
        tableMealPeriod2.setTableId(2);
        tableMealPeriod2.setMealPeriodId(3);

        tableMealPeriodList.add(tableMealPeriod1);
        tableMealPeriodList.add(tableMealPeriod2);

        tableMealPeriodService.newTableMealPeriod(tableMealPeriodList);
    }

    @Test
    public void updateTableMealPeriod() throws SSException{
        List<TableMealPeriod> tableMealPeriodList = new ArrayList<TableMealPeriod>();

        TableMealPeriod tableMealPeriod1 = new TableMealPeriod();
        tableMealPeriod1.setTableId(2);
        tableMealPeriod1.setMealPeriodId(1);

        tableMealPeriodList.add(tableMealPeriod1);

        tableMealPeriodService.updateTableMealPeriod(tableMealPeriodList);
    }
}
