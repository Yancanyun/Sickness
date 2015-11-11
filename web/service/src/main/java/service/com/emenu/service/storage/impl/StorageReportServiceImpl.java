package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.exception.EmenuException;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;

/**
 * 单据操作service实现
 * @author xiaozl
 * @date 2015/11/11
 * @time 14:42
 */
public class StorageReportServiceImpl {

    public void newReport(StorageReportDto reportDto) throws SSException {
       try {

       } catch (Exception e) {
           LogClerk.errLog.error(e);
           throw SSException.get(EmenuException.InsertReportFail, e);
       }
    }

}
