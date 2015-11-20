package com.emenu.test.dish.tag;

import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.service.dish.tag.TagCacheService;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.dish.tag.TagService;
import com.emenu.test.AbstractTestCase;
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

    @Autowired
    TagFacadeService tagFacadeService;

    @Test
    public void testClone() throws CloneNotSupportedException {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("111111");
        Tag ctag = (Tag) tag.clone();
        System.out.println(ctag.getName());
    }
    @Test
    public void refresh() throws Exception {
        tagCacheService.refreshCache();
    }

    @Test
    public void queryParentById() throws Exception {
        System.out.println(tagCacheService.queryParentById(8).getName());
    }

    @Test
    public void listChildrenTagById() throws Exception{
        List<Tag> tagList = tagCacheService.listChildrenById(1);
        for(Tag tag : tagList){
            System.out.println(tag.getName()+"      "+tag.getWeight());
        }
    }

    @Test
    public void queryRootTagById() throws Exception {
        System.out.println(tagCacheService.queryRootById(11).getName());
    }

    @Test
    public void listAllRootTag() throws Exception {
        List<Tag> tagList = tagCacheService.listAllRootByType(1);
        for (Tag tag : tagList){
            System.out.println(tag.getName() + "     "+ tag.getWeight());
        }
    }

    @Test
    public void listDtoTagByCurrentId() throws Exception {
        List<TagDto> tagList = tagCacheService.listDtoByCurrentId(1);
        for (TagDto tagDto : tagList){
            System.out.println(tagDto.getTag().getName()+"   "+tagDto.getTag().getpId());
            //子节点
            for(TagDto tagDto1 :tagDto.getChildTagList()){
                System.out.println(tagDto1.getTag().getName()+"   "+tagDto1.getTag().getpId());
                //孙子节点
                for(TagDto tagDto2 :tagDto1.getChildTagList()){
                    System.out.println(tagDto2.getTag().getName()+"   "+tagDto2.getTag().getpId());
                }
            }
        }
    }

    @Test
    public void queryTagCloneById() throws Exception {
        System.out.println(tagCacheService.queryCloneById(10).getName());
    }

    @Test
    public void listAllTag() throws Exception {
        List<Tag> tagList = tagCacheService.listAll();
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
        tag = tagService.newTag(tag);
        tagCacheService.newTag(tag);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        listAllTag();

    }

    @Test
    public void updateTagName() throws Exception {
        listAllTag();
        tagCacheService.updateName(14, "匪类3");
        listAllTag();
    }

    @Test
    public void updateTagWeight() throws Exception {
        listAllTag();
        tagCacheService.updateWeight(14, 400);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        listAllTag();
    }

    @Test
    public void updateTagPid() throws Exception {
        listAllTag();
        tagCacheService.updatePid(14, 9);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        listAllTag();
    }

    @Test
    public void delTagCascadeById() throws Exception {
        listAllTag();
        tagCacheService.delCascadeById(3);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
        listAllTag();
    }

    @Test
    public void delById() throws Exception{
        listAllTag();
        tagCacheService.delById(9);
        listAllTag();
    }

    @Test
    public void queryPathByTagId() throws Exception {
        List<Tag> tagList = tagCacheService.listPathById(14);
        for(Tag tag : tagList){
            System.out.println(tag.getName()+"  "+tag.getpId());
        }
    }

    @Test
    public void listDishByCurrentId()throws Exception{
        List<TagDto> tagDtoList = tagFacadeService.listDishByCurrentId(1);
        for(TagDto tagDto : tagDtoList){
            System.out.println(tagDto.getTag().getName()+"      "+tagDto.getTag().getWeight()+"      "+tagDto.getTag().getpId());
            for(TagDto tagDto1 : tagDto.getChildTagList()){
                System.out.println(tagDto1.getTag().getName()+"      "+tagDto1.getTag().getWeight()+"      "+tagDto1.getTag().getpId());
            }
        }
    }

    @Test
    public void listDishTagForPrinter() throws Exception {
        List<Tag> tagList = tagFacadeService.listDishTagForPrinter();
        for(Tag tag : tagList){
            System.out.println(tag.getName());
        }
    }

    @Test
    public void listByTagId() throws Exception {
        List<Tag> tagList =tagFacadeService.listByTagId(TagEnum.Dishes.getId());
        for(Tag tag: tagList){
            System.out.println(tag.getName());
        }
    }
}
