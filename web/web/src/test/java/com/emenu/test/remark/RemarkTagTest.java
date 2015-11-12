package com.emenu.test.remark;

import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.service.remark.RemarkTagService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * RemarkTagTest
 *
 * @author: yangch
 * @time: 2015/11/7 14:11
 */
public class RemarkTagTest extends AbstractTestCase {

    @Autowired
    private RemarkTagService remarkTagService;

    @Test
    public void newRemarkTag() throws SSException {
        RemarkTag remarkTag = new RemarkTag();
        remarkTag.setName("结账失败备注");
        remarkTag.setpId(5);
        remarkTagService.newRemarkTag(remarkTag);
    }

    @Test
    public void listAllRemarkTag() throws SSException {
        List<RemarkTag> remarkTagList = remarkTagService.listAll();

        for (RemarkTag remarkTag:remarkTagList){
            System.out.println("Name:" + remarkTag.getName() + " pid:" + remarkTag.getpId());
        }
    }

    @Test
    public void queryRemarkTagById() throws SSException {
        RemarkTag remarkTag = remarkTagService.queryById(6);

        System.out.println("Name:" + remarkTag.getName() + " pid:" + remarkTag.getpId());
    }

    @Test
    public void updateRemarkTag() throws SSException{
        RemarkTag remarkTag = new RemarkTag();
        remarkTag.setId(6);
        remarkTag.setName("顾客取消结账");
        remarkTagService.updateRemarkTag(6, remarkTag);
    }

    @Test
    public void delRemarkTagById() throws SSException{
        remarkTagService.delById(6);
    }

    @Test
    public void checkNameIsExist() throws SSException{
        System.out.println(remarkTagService.checkNameIsExist("普通备注"));
    }

    @Test
    public void listByParentId() throws SSException {
        int pId = 2;
        List<RemarkTag> remarkTagList = remarkTagService.listByParentId(pId);

        for (RemarkTag remarkTag:remarkTagList){
            System.out.println("Name:" + remarkTag.getName() + " pid:" + remarkTag.getpId());
        }
    }
}
