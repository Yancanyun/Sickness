package com.emenu.test.storage;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.service.storage.StorageItemService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * StorageItemTest
 *
 * @author xiaozl
 * @date: 2016/5/26
 */
public class StorageItemTest extends AbstractTestCase{

    @Autowired
    StorageItemService storageItemService;

    @Test
    public void listBySearchDto() throws SSException{
        ItemAndIngredientSearchDto searchDto = new ItemAndIngredientSearchDto();
        searchDto.setPageSize(10);
        searchDto.setPageNo(1);
        List<StorageItem> storageItemList = storageItemService.listBySearchDto(searchDto);
        for (StorageItem storageItem : storageItemList){
            System.out.println(storageItem.getName());
        }
    }

}
