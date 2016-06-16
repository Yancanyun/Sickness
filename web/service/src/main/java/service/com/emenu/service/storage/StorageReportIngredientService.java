package com.emenu.service.storage;

import com.emenu.common.entity.storage.StorageReportIngredient;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * StorageReportIngredientService
 *
 * @author xiaozl
 * @date: 2016/6/14
 */
public interface StorageReportIngredientService{

    /**
     * 新建库存原配料详情
     * @param reportIngredient
     * @return
     * @throws SSException
     */
    public StorageReportIngredient newReportIngredient(StorageReportIngredient reportIngredient) throws SSException;

    /**
     * 根据单据id获取原配料详情
     * @param reportId
     * @return
     * @throws SSException
     */
    public List<StorageReportIngredient> listByReportId(Integer reportId) throws SSException;

    /**
     * 删除单据原配料详情by reportId
     * @param reportId
     * @return
     * @throws SSException
     */
    public boolean delByReportId(Integer reportId) throws SSException;
}
