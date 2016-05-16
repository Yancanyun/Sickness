package com.emenu.common.dto.storage;

/**
 * ItemSearchDto
 *
 * @author xiaozl
 * @date: 2016/5/14
 */
public class ItemSearchDto {

    // 物品名称
    private String name;

    // 物品助记码
    private String assistantCode;

    // 物品编号
    private String itemNumber;

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

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }
}
