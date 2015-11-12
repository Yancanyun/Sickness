package com.emenu.mapper.storage;

import com.emenu.common.entity.storage.StorageReportItem;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * @author xiaozl
 * @date 2015/11/12
 * @time 16:59
 */
public interface StorageReportItemMapper {

    public List<StorageReportItem> listAll() throws Exception;

    public StorageReportItem queryByReportId(int reportId) throws Exception;

}
