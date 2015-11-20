package com.emenu.test.storage;

import com.emenu.service.storage.StorageTagService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * StorageItemServiceTEst
 *
 * @author: zhangteng
 * @time: 2015/11/20 8:59
 **/
public class StorageItemServiceTest extends AbstractTestCase {

    @Autowired
    private StorageTagService storageTagService;

    @Test
    public void listAll() throws SSException {
        storageTagService.listAll();
    }
}
