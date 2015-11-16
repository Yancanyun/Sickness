package com.emenu.mapper.storage;

import com.emenu.common.entity.storage.StorageReport;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * StorageReportMapper
 * @author xiaozl
 * @date 2015/11/12
 * @time 16:47
 */
public interface StorageReportMapper {

    /**
     * 获取所有单据信息
     * @return
     * @throws Exception
     */
    public List<StorageReport> listAll() throws Exception;

    /**
     * 分页获取单据信息
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<StorageReport> listByPage(@Param("offset")int offset,@Param("pageSize")int pageSize ) throws Exception;

    public List<StorageReport> listStorageReportByCondition(@Param("startTime")Date startTime,
                                                            @Param("endTime")Date endTime,
                                                            @Param("serialNumber")String serialNumber,
                                                            @Param("depotId")int depotId,
                                                            @Param("handlerPartyId")int handlerPartyId,
                                                            @Param("createdPartyId")int createdPartyId,
                                                            @Param("offset")int offset,
                                                            @Param("pageSize")int pageSize) throws Exception;
}
