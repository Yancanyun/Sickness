package com.emenu.common.dto.dish.tag;

import com.emenu.common.entity.dish.tag.Tag;

import java.util.ArrayList;
import java.util.List;
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
    SortedMap<TagChildDto, Integer> childrenMap = new ConcurrentSkipListMap<TagChildDto, Integer>();

    //List子类别列表
    List<TagDto> childTagList = new ArrayList<TagDto>();

    /********************* childrenMap ***********************/

    /**
     * 添加一个子类别
     * @param tag
     */
    public void addChildMap(Tag tag){
        TagChildDto childTagDto = new TagChildDto(tag.getId(),tag.getWeight());
        childrenMap.put(childTagDto, childTagDto.getTagId());
    }

    /**
     * 删除一个子类别
     * @param tagId
     * @param weight
     */
    public  void removeChildMap(Integer tagId,Integer weight){
        TagChildDto childTagDto = new TagChildDto(tagId,weight);
        childrenMap.remove(childTagDto);
    }

    /********************* getter and setter ***********************/
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public SortedMap<TagChildDto, Integer> getChildrenTagMap() {
        return childrenMap;
    }

    public void setChildrenMap(SortedMap<TagChildDto, Integer> childrenTagMap) {
        this.childrenMap = childrenTagMap;
    }

    public List<TagDto> getChildTagList() {
        return childTagList;
    }

    public void setChildTagList(List<TagDto> childTagList) {
        this.childTagList = childTagList;
    }
}
