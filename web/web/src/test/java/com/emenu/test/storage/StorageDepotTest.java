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
 * StorageDepotTest
 * 存放点单元测试
 *
 * @author xubr
 * @author 2015/11/12.
 */
public class StorageDepotTest extends AbstractTestCase {

    @Autowired
    @Qualifier("storageDepotService")
    private StorageDepotService storageDepotService;

    @Test
    public void newStorageDepot() throws SSException {

        StorageDepot storageDepot = new StorageDepot();
        storageDepot.setName("存放点10");
        storageDepot.setIntroduction("存放点10简介存放点10简介存放点9");
        storageDepotService.newStorageDepot(storageDepot);
    }

    @Test
    public void delById() throws SSException {
        storageDepotService.delById(10);
    }

    @Test
    public void updateStorageDepot() throws SSException {

        StorageDepot storageDepot = new StorageDepot();
        storageDepot.setId(9);
        storageDepot.setName("存放点77");
        storageDepot.setIntroduction("77简介简介简介简介");
        storageDepotService.updateStorageDepot(storageDepot);
    }

    @Test
    public void queryByName() throws SSException {
        System.out.print(storageDepotService.queryByName("存放点点点").getIntroduction());
    }

    @Test
    public void queryById() throws SSException {
        System.out.print(storageDepotService.queryById(3).getIntroduction());
    }
    @Test
    public void listByPage() throws SSException {
        List<StorageDepot> list = Collections.<StorageDepot>emptyList();

        list = storageDepotService.listByPage(2, 2);
        System.out.print(storageDepotService.countAll());
        for (StorageDepot storageDepot : list) {
            System.out.print(storageDepot.getIntroduction());
        }
    }

    @Test
    public void listAll() throws SSException {
        List<StorageDepot> list1 = Collections.<StorageDepot>emptyList();

        list1 = storageDepotService.listAll();

        for (StorageDepot storageDepot : list1) {
            System.out.print(storageDepot.getIntroduction());
        }
    }

}
