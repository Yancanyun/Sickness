package com.emenu.test.storage;

import com.emenu.common.entity.storage.Depot;
import com.emenu.service.storage.DepotService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collections;
import java.util.List;

/**
 * DepotTest
 * 存放点单元测试
 *
 * @author xubr
 * @author 2015/11/12.
 */
public class DepotTest extends AbstractTestCase {

    @Autowired
    @Qualifier("depotService")
    private DepotService depotService;

    @Test
    public void newDepot() throws SSException {

        Depot depot = new Depot();
        depot.setName("存放点9");
        depot.setIntroduction("存放点9简介存放点9简介存放点9");
        depotService.newDepot(depot);
    }

    @Test
    public void delById() throws SSException {
        depotService.delById(7);
    }

    @Test
    public void updateDepot() throws SSException {

        Depot depot = new Depot();
        depot.setId(10);
        depot.setName("存放点88");
        depot.setIntroduction("88简介简介简介简介");
        depotService.updateDepot(depot);
    }

    @Test
    public void queryByName() throws SSException {
        System.out.print(depotService.queryByName("存放点点点").getIntroduction());
    }

    @Test
    public void listByPage() throws SSException {
        List<Depot> list = Collections.<Depot>emptyList();

        list = depotService.listByPage(2, 2);
        System.out.print(depotService.countAll());
        for (Depot depot : list) {
            System.out.print(depot.getIntroduction());
        }
    }

    @Test
    public void listAll() throws SSException {
        List<Depot> list1 = Collections.<Depot>emptyList();

        list1 = depotService.listAll();

        for (Depot depot : list1) {
            System.out.print(depot.getIntroduction());
        }
    }

}
