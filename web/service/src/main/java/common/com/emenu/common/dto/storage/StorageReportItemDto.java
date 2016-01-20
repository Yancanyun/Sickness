package com.emenu.common.dto.storage;

import java.math.BigDecimal;
import java.util.Date;

/**
 * StorageReportItemDto
 * 库存单据物品dto，供库存单据dto使用
 *
 * @author Wang Liming
 * @date 2016/1/20 18:35
 */
public class StorageReportItemDto {

    private Integer id;

    //原料id
    private Integer itemId;

    //原料名称
    private String itemName;

    //入库数量、出库数量、盘盈数量、盘亏数量
    private BigDecimal quantity;

    //计数
    private BigDecimal count;

    //成本价
    private BigDecimal price;

    //单据详情备注
    private String comment;

    //单据id
    private Integer reportId;

    // 创建时间
    private Date createdTime;
}
