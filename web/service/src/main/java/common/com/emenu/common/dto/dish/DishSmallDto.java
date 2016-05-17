package com.emenu.common.dto.dish;

/**
 * DishSmallDto
 *
 * @author xubaorong
 * @date 2016/5/16.
 */
public class DishSmallDto {

    // 主键
    private Integer id;

    // 名称
    private String name;

    // 助记码
    private String assistantCode;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssistantCode() {
        return assistantCode;
    }

    public void setAssistantCode(String assistantCode) {
        this.assistantCode = assistantCode;
    }

}
