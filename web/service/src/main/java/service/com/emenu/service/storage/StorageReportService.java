package com.emenu.service.storage;

import com.emenu.common.dto.storage.StorageReportDto;
import com.pandawork.core.common.exception.SSException;

/**
 * 单据操作service
 * @author xiaozl
 * @date 2015/11/11
 * @time 14:32
 */
public interface StorageReportService {

    /**
     * 添加单据
     * @param reportDto
     * @throws SSException
     */
    public void newReport(StorageReportDto reportDto) throws SSException;


}
