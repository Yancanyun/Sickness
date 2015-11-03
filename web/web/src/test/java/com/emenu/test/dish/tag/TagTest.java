package com.emenu.test.dish.tag;

import com.emenu.common.entity.dish.tag.Tag;
import com.emenu.service.dish.tag.TagService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * TagTest
 * 菜品和原料类别测试
 * @author dujuan
 * @date 2015/10/23
 */
public class TagTest extends AbstractTestCase{

    @Autowired
    TagService tagService;

    @Test
    public void addTag() throws SSException {
        Tag tag = new Tag();
        tag.setName("商品10");
        tag.setpId(8 );
        tag.setType(1);
        tag.setWeight(13);
        tag.setTimeLimit(10);
        tagService.newTag(tag);
    }
//
//    @Test
//    public void query() throws SSException{
//        System.out.println(tagService.queryById(1).getName());
//    }
//
//    @Test
//    public void list() throws SSException{
//        List<Tag> tagList = tagService.listAll();
//        for(Tag tag : tagList){
//            System.out.println(tag.getName());
//        }
//    }
//
//    @Test
//    public void listByPage() throws SSException {
//        List<Tag> tagList = tagService.listByPage(1, 10);
//        System.out.println(tagService.countAll());
//        for(Tag tag : tagList){
//            System.out.println(tag.getName());
//        }
//    }
//
//    @Test
//    public void del() throws SSException {
////        tagService.deleteTag(6);
//        List<Integer> ids = new ArrayList<Integer>();
//        ids.add(5);
//        ids.add(7);
//        tagService.delByIds(ids);
//    }
//
//    @Test
//    public void updateByField() throws SSException {
//        Tag tag = new Tag();
//        tag.setId(14);
//        tag.setWeight(20);
//        tagService.update(tag);
//    }
}
