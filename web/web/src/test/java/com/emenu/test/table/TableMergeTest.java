package com.emenu.test.table;

import com.emenu.common.entity.table.TableMerge;
import com.emenu.service.table.TableMergeService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * TableMergeTest
 *
 * @author: yangch
 * @time: 2016/6/23 10:40
 */
public class TableMergeTest extends AbstractTestCase {
    @Autowired
    private TableMergeService tableMergeService;

    @Test
    public void listByMergeId() throws SSException {
        int mergeId = 2;
        List<TableMerge> tableMergeList = tableMergeService.listByMergeId(mergeId);

        for (TableMerge tableMerge : tableMergeList) {
            System.out.println(tableMerge.getTableId());
        }
    }

    @Test
    public void queryByTableId() throws SSException {
        int tableId = 4;
        TableMerge tableMerge = tableMergeService.queryByTableId(tableId);

        System.out.println(tableMerge.getMergeId());
    }

    @Test
    public void queryLastMergeId() throws SSException {
        System.out.println(tableMergeService.queryLastMergeId());
    }

    @Test
    public void mergeTable() throws SSException {
        List<Integer> tableIdList = new ArrayList<Integer>();
//        tableIdList.add(4);
//        tableIdList.add(5);
//        tableIdList.add(6);
        tableIdList.add(17);
        tableIdList.add(18);

        List<TableMerge> tableMergeList = tableMergeService.mergeTable(tableIdList);

        for (TableMerge tableMerge : tableMergeList) {
            System.out.println(tableMerge.getTableId());
        }
    }

    @Test
    public void delTableMerge() throws SSException {
        int tableId = 1;
        tableMergeService.delTableMerge(tableId);
    }
}
