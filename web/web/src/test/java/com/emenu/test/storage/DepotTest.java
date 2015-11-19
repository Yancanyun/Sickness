package com.emenu.test.storage;

import com.emenu.common.entity.storage.StorageDepot;
import com.emenu.service.storage.StorageDepotService;
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
    @Qualifier("storageDepotService")
    private StorageDepotService storageDepotService;

    @Test
    public void newDepot() throws SSException {

        StorageDepot depot = new StorageDepot();
        depot.setName("存放点9");
        depot.setIntroduction("存放点9简介存放点9简介存放点9");
        storageDepotService.newStorageDepot(depot);
    }

    @Test
    public void delById() throws SSException {
        storageDepotService.delById(7);
    }

    @Test
    public void updateDepot() throws SSException {
        StorageDepot depot = new StorageDepot();
        depot.setId(10);
        depot.setName("存放点88");
        depot.setIntroduction("88简介简介简介简介");
        storageDepotService.updateStorageDepot(depot);
    }

    @Test
    public void queryByName() throws SSException {
        System.out.print(storageDepotService.queryByName("存放点点点").getIntroduction());
    }

    @Test
    public void listByPage() throws SSException {
        List<StorageDepot> list = Collections.<StorageDepot>emptyList();

        list = storageDepotService.listByPage(2, 2);
        System.out.print(storageDepotService.countAll());
        for (StorageDepot depot : list) {
            System.out.print(depot.getIntroduction());
        }
    }

    @Test
    public void listAll() throws SSException {
        List<StorageDepot> list1 = Collections.<StorageDepot>emptyList();
        list1 = storageDepotService.listAll();
        for (StorageDepot depot : list1) {
            System.out.print(depot.getIntroduction());
        }
    }

}
