package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.storage.StorageReportService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;


/**
 * 单据操作service实现
 * @author xiaozl
 * @date 2015/11/11
 * @time 14:42
 */
public class StorageReportServiceImpl implements StorageReportService {

    public void newReport(StorageReportDto reportDto) throws SSException {
       try {
           if(Assert.isNull(reportDto)){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }
           if(Assert.isNull(reportDto.getStorageReport())){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }
           if(Assert.isNull(reportDto.getStorageReportItem())){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }



       } catch (Exception e) {
           LogClerk.errLog.error(e);
           throw SSException.get(EmenuException.InsertReportFail, e);
       }
    }

    private boolean checkStorageReportBeforeSave(StorageReport storageReport) throws SSException{

        if (Assert.isNull(storageReport)) {
            return false;
        }

/*        Assert.isNotNull(storageReport.getCreatedPartyId(),  );
        Assert.isNotNull(storageReport.getHandlerPartyId(),  );
        Assert.isNotNull(storageReport.getDepotId(),  );
        Assert.isNotNull(storageReport.getSerialNumber(), );
        Assert.isNotNull(storageReport.getStatus(), );
        Assert.isNotNull(storageReport.getMoney(),  );
        Assert.isNotNull(storageReport.getType(),  );*/

        return true;
    }

    private boolean checkStorageReportItemBeforeSave(SecurityUser securityUser) throws SSException{
        return false;
    }

}
