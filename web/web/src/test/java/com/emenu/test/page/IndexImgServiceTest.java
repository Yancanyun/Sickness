package com.emenu.test.page;

import com.emenu.common.entity.page.IndexImg;
import com.emenu.service.page.IndexImgService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * IndexImgServiceTest
 *
 * @author Wang LiMing
 * @date 2015/10/24 11:04
 */
public class IndexImgServiceTest extends AbstractTestCase {

    @Autowired
    private IndexImgService indexImgService;

    @Test
    public void newIndexImg() throws SSException {
        IndexImg indexImg = new IndexImg();
        indexImg.setImgPath("11333");
        indexImg.setState(0);
        indexImgService.newIndexImg(indexImg);
    }

    @Test
    public void updateIndexImg() throws SSException {
        IndexImg indexImg = indexImgService.queryByState(1);
        //indexImgService.updateStateById(indexImg.getId(), 0);
    }

    @Test
    public void queryAllIndexImg() throws SSException {
        List<IndexImg> indexImgList = indexImgService.listAll();
        for (IndexImg indexImg : indexImgList) {
            System.out.println("id:" + indexImg.getId() + "    imgPath:" + indexImg.getImgPath() + "   state:" + indexImg.getState());
        }
    }

    @Test
    public void deleteIndexImgById() throws SSException{
        indexImgService.delById(100);
    }
}
