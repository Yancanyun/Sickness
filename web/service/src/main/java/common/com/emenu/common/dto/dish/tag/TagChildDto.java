package com.emenu.common.dto.dish.tag;

/**
 * TagChildDto
 * Tag的儿子的结构，主要是为了解决多线程排序问题——gaoyang
 * @author dujuan
 * @date 2015/10/22
 */
public class TagChildDto implements Comparable<TagChildDto>{

    //类别的ID
    private Integer tagId;

    //类别的权重
    private Integer tagWeight;

    //构造方法
    public TagChildDto(Integer tagId, Integer tagWeight){
        this.tagId = tagId;
        this.tagWeight = tagWeight;
    }

    /********************* getter and setter ***********************/
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getTagWeight() {
        return tagWeight;
    }

    public void setTagWeight(Integer tagWeight) {
        this.tagWeight = tagWeight;
    }

    /**
     * 默认是按权重从小往大排序，权重一样则再按tagid排序
     */
    @Override
    public int compareTo(TagChildDto o) {
        if(!this.tagWeight.equals(o.tagWeight)){
            return this.tagWeight - o.tagWeight;
        }else {
            return this.tagId - o.tagId;
        }
    }
}
