package com.emenu.service.rank;

import com.emenu.common.dto.rank.DishSaleRankDto;
import com.pandawork.core.common.exception.SSException;

import java.util.Date;
import java.util.List;

/**
 * DishTagRankService
 *
 * @Author guofengrui
 * @Date 2016/7/27.
 */
public interface DishTagRankService {

    /**
     * 根据开始时间和结束时间查找大类
     * 存在DishSaleRankDto，此时里面的消费金额和数量对应的是大类的金额和数量
     * @param startTime
     * @param endTime
     * @return
     * @throws SSException
     */
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeriod(Date startTime ,Date endTime) throws SSException;
}
