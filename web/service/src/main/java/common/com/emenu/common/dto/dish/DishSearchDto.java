package com.emenu.common.dto.dish;

import java.util.List;

/**
 * DishSearchDto
 *
 * @author: zhangteng
 * @time: 2015/11/17 16:29
 **/
public class DishSearchDto {

    private String name;

    private String dishNumber;

    private String assistantCode;

    private List<Integer> tagIdList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDishNumber() {
        return dishNumber;
    }

    public void setDishNumber(String dishNumber) {
        this.dishNumber = dishNumber;
    }

    public String getAssistantCode() {
        return assistantCode;
    }

    public void setAssistantCode(String assistantCode) {
        this.assistantCode = assistantCode;
    }

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }
}
