package com.emenu.test.storage;

import com.emenu.common.dto.storage.StorageReportItemDto;
import com.emenu.service.storage.StorageReportItemService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * StorageReportItemTest
 *
 * @author WangLM
 * @date 2016/1/21 10:27
 */
public class StorageReportItemTest extends AbstractTestCase {

    @Autowired
    StorageReportItemService storageReportItemService;

    @Test
    public void listDtoByReportId() throws SSException{
        List<StorageReportItemDto> itemDtoList = storageReportItemService.listDtoByReportId(4);
        for (StorageReportItemDto itemDto : itemDtoList){
            System.out.println(itemDto.getItemName()+"  "+itemDto.getCreatedTime()+"  "+itemDto.getAssistantCode());
        }
    }
}
