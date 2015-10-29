package com.emenu.test.dish.tag;

import com.emenu.common.entity.dish.tag.Tag;
import com.emenu.service.dish.tag.TagCacheService;
import com.emenu.service.dish.tag.TagService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * TagCacheTest
 * 菜品和原料类别测试——缓存
 * @author dujuan
 * @date 2015/10/24
 */
public class TagCacheTest extends AbstractTestCase{

    @Autowired
    TagCacheService tagCacheService;

    @Autowired
    TagService tagService;

    @Test
    public void testClone() throws CloneNotSupportedException {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("111111");
        tag.setType(2);
        Tag ctag = (Tag) tag.clone();
        System.out.println(ctag.getName());
    }
    @Test
    public void refresh() throws Exception {
        tagCacheService.refreshTagCache();
    }

    @Test
    public void queryParentById() throws Exception {
        System.out.println(tagCacheService.queryParentTagById(8).getName());
    }

    @Test
    public void listChildrenTagById() throws Exception{
        List<Tag> tagList = tagCacheService.listChildrenTagById(1);
        for(Tag tag : tagList){
            System.out.println(tag.getName()+"      "+tag.getWeight());
        }
    }

    @Test
    public void queryRootTagById() throws Exception {
        System.out.println(tagCacheService.queryRootTagById(11).getName());
    }

    @Test
    public void listAllRootTag() throws Exception {
        List<Tag> tagList = tagCacheService.listAllRootTag(1);
        for (Tag tag : tagList){
            System.out.println(tag.getName() + "     "+ tag.getWeight());
        }
    }

    @Test
    public void listTagByCurrentId() throws Exception {
        List<Tag> tagList = tagCacheService.listTagByCurrentId(1);
        for (Tag tag : tagList){
            System.out.println(tag.getName() + "     "+ tag.getWeight());
        }
    }

    @Test
    public void queryTagCloneById() throws Exception {
        System.out.println(tagCacheService.queryTagCloneById(10).getName());
    }

    @Test
    public void listAllTag() throws Exception {
        List<Tag> tagList = tagCacheService.listAllTag();
        for (Tag tag : tagList){
            System.out.println(tag.getName() + " "+ tag.getWeight()+ "  "+tag.getpId());
        }
    }

    @Test
    public void newTag() throws Exception {
        listAllTag();
        Tag tag = new Tag();
        tag.setName("分类2");
        tag.setWeight(19);
        tag.setpId(3);
        tag.setType(1);
        tag = tagService.newTag(tag);
        tagCacheService.newTag(tag);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        listAllTag();

    }

    @Test
    public void updateTagName() throws Exception {
        listAllTag();
        tagCacheService.updateTagName(14, "匪类3");
        listAllTag();
    }

    @Test
    public void updateTagWeight() throws Exception {
        listAllTag();
        tagCacheService.updateTagWeight(14, 400);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        listAllTag();
    }

    @Test
    public void updateTagPid() throws Exception {
        listAllTag();
        tagCacheService.updateTagPid(14, 9);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        listAllTag();
    }

    @Test
    public void delTagCascadeById() throws Exception {
        listAllTag();
        tagCacheService.delTagCascadeById(3);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        listAllTag();
    }

    @Test
    public void queryPathByTagId() throws Exception {
        List<Tag> tagList = tagCacheService.listPathByTagId(14);
        for(Tag tag : tagList){
            System.out.println(tag.getName()+"  "+tag.getpId());
        }
    }
}
