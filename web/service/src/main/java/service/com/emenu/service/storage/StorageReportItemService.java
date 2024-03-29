package com.emenu.service.storage;

import com.emenu.common.dto.storage.StorageReportItemDto;
import com.emenu.common.entity.storage.StorageReportItem;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 单据详情Service
 * @author xiaozl
 * @date 2015/11/12
 * @time 14:30
 */
public interface StorageReportItemService {

    /**
     * 新
     * 添加单据详情
     * @param reportItem
     * @return
     * @throws SSException
     */
    public StorageReportItem newReportItem(StorageReportItem reportItem) throws SSException;

    /**
     * 新
     * 获取所有单据详情信息
     * @return
     * @throws SSException
     */
    public List<StorageReportItem> listAll() throws SSException;

    /**
     * 根据单据ID获取单据详情信息
     * @param reportId
     * @return
     * @throws SSException
     */
    public StorageReportItem queryByReportId(int reportId) throws SSException;


    /**
     * 获取所有相同reportId的单据详情
     * @param reportId
     * @return
     * @throws SSException
     */
    public List<StorageReportItem> listByReportId(int reportId) throws SSException;

    /**
     * 获取所有相同reportId的单据详情dto
     *
     * @param reportId
     * @return
     * @throws SSException
     */
    public List<StorageReportItemDto> listDtoByReportId(int reportId) throws SSException;

    /**
     * 根据id修改单据详情
     * @param reportItem
     * @return
     * @throws SSException
     */
    public void updateById(StorageReportItem reportItem) throws SSException;

    /**
     * 根据reportId和itemIdList获取单据详情信息
     * @param reportId
     * @param itemIdList
     * @return
     * @throws SSException
     */
    public List<StorageReportItem> listByReportIdAndItemIdList(int reportId,List<Integer> itemIdList) throws SSException;

    /**
     * 新
     * 根据单据id删除单据详情
     * @param id
     * @return
     * @throws SSException
     */
    public void delByReportId(int id) throws SSException;

    /**
     * 根据id删除单据详情
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;
}
