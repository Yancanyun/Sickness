package com.emenu.mapper.storage;

import com.emenu.common.dto.storage.StorageReportItemDto;
import com.emenu.common.entity.storage.StorageReportItem;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaozl
 * @date 2015/11/12
 * @time 16:59
 */
public interface StorageReportItemMapper {

    /**
     * @return
     * @throws Exception
     */
    public List<StorageReportItem> listAll() throws Exception;

    /**
     * @param reportId
     * @return
     * @throws Exception
     */
    public StorageReportItem queryByReportId(@Param("reportId") int reportId) throws Exception;

    /**
     * 获取所有相同reportId的单据详情
     *
     * @param reportId
     * @return
     * @throws Exception
     */
    public List<StorageReportItem> listByReportId(@Param("reportId") int reportId) throws Exception;

    /**
     * 获取所有相同reportId的单据详情dto
     *
     * @param reportId
     * @return
     * @throws Exception
     */
    public List<StorageReportItemDto> listDtoByReportId(@Param("reportId") int reportId) throws Exception;

    /**
     * 根据reportId和itemIdList获取单据详情信息
     * @param reportId
     * @param itemIdList
     * @return
     * @throws SSException
     */
    public List<StorageReportItem> listByReportIdAndItemIdList(@Param("reportId")int reportId, @Param("itemIdList")List<Integer> itemIdList) throws Exception;

    /**
     * 根据reportId删除单据详情
     * @param id
     * @return
     * @throws SSException
     */
    public boolean delByReportId(@Param("id")int id) throws Exception;
}


