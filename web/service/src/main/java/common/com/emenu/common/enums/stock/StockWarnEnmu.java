package com.emenu.common.enums.stock;

/**
 * StockWarnEnmu
 *
 * @author yuzhengfei
 * @date 2017/3/18 13:52
 */
public enum StockWarnEnmu {

     Untreated(0, "未处理"),
     Ignored(1, "已忽略"),
     Resolved(2, "已解决");

    private Integer id;

    private String description;

    StockWarnEnmu(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}



