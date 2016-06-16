package com.emenu.mapper.storage;


import com.emenu.common.entity.storage.StorageReportIngredient;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StorageReportMapper
 * @author xiaozl
 * @date 2015/11/12
 * @time 16:47
 */
public interface StorageReportIngredientMapper {

    /**
     * 根据单据id获取单据原配料详情
     * @param reportId
     * @return
     * @throws SSException
     */
    public List<StorageReportIngredient> listByReportId(@Param("reportId") Integer reportId) throws Exception;

    /**
     * 根据单据id删除单据原配料
     * @param reportId
     * @return
     * @throws Exception
     */
    public boolean delByReportId(@Param("reportId") Integer reportId) throws Exception;

}
