package com.emenu.test.page;

import com.emenu.common.entity.page.Keywords;
import com.emenu.common.enums.page.KeywordsEnum;
import com.emenu.service.page.KeywordsService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * KeywordsServiceTest
 *
 * @author Wang LiMing
 * @date 2015/10/23 10:47
 */
public class KeywordsServiceTest extends AbstractTestCase {

    @Autowired
    private KeywordsService keywordsService;

    @Test
    public void newKeywords() throws SSException{
        Keywords keywords = new Keywords();
        keywords.setKey("鹅肉");
        keywords.setType(0);

        keywordsService.newKeywords(keywords);
    }

    @Test
    public void queryKeywordsByType() throws SSException{
        List<Keywords> orderingList = keywordsService.listByType(KeywordsEnum.Ordering);
        List<Keywords> waiterList = keywordsService.listByType(KeywordsEnum.WaiterSystem);

        for (Keywords keywords:orderingList){
            System.out.println("id:"+keywords.getId()+"  key:"+keywords.getKey()+"  type:"+keywords.getType());
        }
        for (Keywords keywords:waiterList){
            System.out.println("id:"+keywords.getId()+"  key:"+keywords.getKey()+"  type:"+keywords.getType());
        }
    }

    @Test
    public void deleteKeywordsById() throws SSException{
        keywordsService.delById(6);
    }
}
