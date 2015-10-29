package com.emenu.common.dto.dish.tag;

import com.emenu.common.entity.dish.tag.Tag;

import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * TagDto
 *
 * @author dujuan
 * @date 2015/10/22
 */
public class TagDto {

    //Tag
    private Tag tag;

    //子类别列表
    SortedMap<TagChildDto, Integer> childrenTagMap = new ConcurrentSkipListMap<TagChildDto, Integer>();

    /**
     * 添加一个子类别
     * @param tag
     */
    public void addChildTagMap(Tag tag){
        TagChildDto childTagDto = new TagChildDto(tag.getId(),tag.getWeight());
        childrenTagMap.put(childTagDto, childTagDto.getTagId());
    }

    /**
     * 删除一个子类别
     * @param tagId
     * @param weight
     */
    public  void removeChildTagMap(Integer tagId,Integer weight){
        TagChildDto childTagDto = new TagChildDto(tagId,weight);
        childrenTagMap.remove(childTagDto);
    }

    /********************* getter and setter ***********************/
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public SortedMap<TagChildDto, Integer> getChildrenTagMap() {
        return childrenTagMap;
    }

    public void setChildrenTagMap(SortedMap<TagChildDto, Integer> childrenTagMap) {
        this.childrenTagMap = childrenTagMap;
    }
}
