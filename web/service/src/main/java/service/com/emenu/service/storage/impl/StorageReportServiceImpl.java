package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageItemSearchDto;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.enums.storage.StorageReportStatusEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.mapper.storage.StorageReportMapper;
import com.emenu.service.storage.StorageItemService;
import com.emenu.service.storage.StorageReportItemService;
import com.emenu.service.storage.StorageReportService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 单据操作service实现
 * @author xiaozl
 * @date 2015/11/11
 * @time 14:42
 */
@Service("storageReportService")
public class StorageReportServiceImpl implements StorageReportService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private StorageReportItemService storageReportItemService;

    @Autowired
    private StorageReportMapper storageReportMapper;

    @Autowired
    private StorageItemService storageItemService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newReportDto(StorageReportDto storageReportDto) throws SSException {
       try {
           if(Assert.isNull(storageReportDto)){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }
           if(Assert.isNull(storageReportDto.getStorageReport())){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }
           if(Assert.isEmpty(storageReportDto.getStorageReportItemList())){
               throw SSException.get(EmenuException.ReportItemIsNotNull);
           }
           this.newReport(storageReportDto.getStorageReport());
           for(StorageReportItem storageReportItem : storageReportDto.getStorageReportItemList()){
           storageReportItemService.newStorageReportItem(storageReportItem);
           }
       } catch (Exception e) {
           LogClerk.errLog.error(e);
           throw SSException.get(EmenuException.InsertReportFail, e);
       }
    }

    /**
     * 添加单据
     * @param storageReport
     * @return
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private StorageReport newReport(StorageReport storageReport) throws SSException {

        try {
            if(Assert.isNull(storageReport)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            //关键字段不为空的情况下添加单据
            if(checkStorageReportBeforeSave(storageReport)){
                return commonDao.insert(storageReport);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertReportFail, e);
        }

        return null;
    }

    /**
     * 检查StorageReport中关键属性是否合法
     * @param storageReport
     * @return
     * @throws SSException
     */
    private boolean checkStorageReportBeforeSave(StorageReport storageReport) throws SSException{

        if (Assert.isNull(storageReport)) {
            return false;
        }
        if (Assert.isNull(storageReport.getCreatedPartyId())&& Assert.lessOrEqualZero(storageReport.getCreatedPartyId())){
            throw SSException.get(EmenuException.CreatedPartyIdError);
        }
        if (Assert.isNull(storageReport.getHandlerPartyId())&&Assert.lessOrEqualZero(storageReport.getHandlerPartyId())){
            throw SSException.get(EmenuException.HandlerPartyId);
        }
        if (Assert.isNull(storageReport.getDepotId())&&Assert.lessOrEqualZero(storageReport.getDepotId())){
            throw SSException.get(EmenuException.DepotIdError);
        }
        if (Assert.isNull(storageReport.getStatus())&&Assert.lessOrEqualZero(storageReport.getStatus())){
            throw SSException.get(EmenuException.ReportStatusError);
        }
        if (Assert.isNull(storageReport.getType())&&Assert.lessOrEqualZero(storageReport.getType())){
            throw SSException.get(EmenuException.ReportTypeError);
        }
        Assert.isNotNull(storageReport.getSerialNumber(), EmenuException.SerialNumberError);
        Assert.isNotNull(storageReport.getMoney(), EmenuException.ReportMoneyError);
        return true;
    }

    public List<StorageReport> listAll() throws SSException{

        List<StorageReport> storageReportList = Collections.emptyList();

        try {
            storageReportList = storageReportMapper.listAll();
            return storageReportList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }

    }

    @Override
    public List<StorageReportDto> listStorageReportDto() throws SSException {

        List<StorageReportDto> storageReportDtoList = new ArrayList();
        List<StorageReport> storageReportList = Collections.emptyList();
        try {
            //获取所有单据信息
            storageReportList = listAll();

            for (StorageReport storageReport : storageReportList){
                StorageReportDto storageReportDto = new StorageReportDto();
                List<StorageReportItem> storageReportItemList = new ArrayList();
                //根据单据id获取单据详情信息
                storageReportItemList = storageReportItemService.listByReportId(storageReport.getId());
                //数据存入reportDto
                storageReportDto.setStorageReport(storageReport);
                storageReportDto.setStorageReportItemList(storageReportItemList);

                storageReportDtoList.add(storageReportDto);
            }
            return storageReportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }

    }

    @Override
    public List<StorageReportDto> ListStorageReportDtoUnsettled(Date endTime) throws SSException {
        List<StorageReportDto> storageReportDtoList = new ArrayList();
        List<StorageReport> storageReportList = Collections.emptyList();
        try {
            //获取所有单据信息
            storageReportList = storageReportMapper.ListStorageReportUnsettled(endTime);
            if (Assert.isEmpty(storageReportList)){
                return storageReportDtoList;
            }
            for (StorageReport storageReport : storageReportList){
                StorageReportDto storageReportDto = new StorageReportDto();
                List<StorageReportItem> storageReportItemList = new ArrayList();
                //根据单据id获取单据详情信息
                storageReportItemList = storageReportItemService.listByReportId(storageReport.getId());
                //数据存入reportDto
                storageReportDto.setStorageReport(storageReport);
                storageReportDto.setStorageReportItemList(storageReportItemList);

                storageReportDtoList.add(storageReportDto);
            }
            return storageReportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageReportFail, e);
        }
    }

    @Override
    public List<StorageReportDto> listStorageReportDtoByPage(int page, int pageSize) throws SSException {
        int offset = page * pageSize;
        List<StorageReportDto> storageReportDtoList = Collections.emptyList();
        List<StorageReport> storageReportList = Collections.emptyList();
        if(Assert.lessZero(offset)){
            return storageReportDtoList;
        }
        storageReportDtoList = new ArrayList<StorageReportDto>();
        try {
            storageReportList = storageReportMapper.listByPage(offset,pageSize);
            for (StorageReport storageReport : storageReportList){
                StorageReportDto storageReportDto = new StorageReportDto();
                List<StorageReportItem> storageReportItemList = new ArrayList();
                //根据单据id获取单据详情信息
                storageReportItemList = storageReportItemService.listByReportId(storageReport.getId());
                //数据存入reportDto
                storageReportDto.setStorageReport(storageReport);
                storageReportDto.setStorageReportItemList(storageReportItemList);

                storageReportDtoList.add(storageReportDto);
            }
            return storageReportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public List<StorageReportDto> listStorageReportDtoByCondition(Date startTime,
                                                                  Date endTime,
                                                                  String serialNumber,
                                                                  int depotId,
                                                                  int handlerPartyId,
                                                                  int createdPartyId,
                                                                  int page,
                                                                  int pageSize) throws SSException {

        page = page <= 0 ? 0 : page - 1;
        int offset = page * pageSize;
        if (startTime == null && endTime == null) {
            startTime = DateUtils.getTodayStartTime();
            endTime = DateUtils.getTodayEndTime();
        }
        if (endTime != null) {
            endTime.setHours(23);
            endTime.setMinutes(59);
            endTime.setSeconds(59);
        }
        List<StorageReportDto> storageReportDtoList = Collections.emptyList();
        List<StorageReport> storageReportList = Collections.emptyList();
        if(Assert.lessZero(offset)){
            return storageReportDtoList;
        }
        storageReportDtoList = new ArrayList<StorageReportDto>();
        try {
            storageReportList = storageReportMapper.listStorageReportByCondition(startTime,endTime,serialNumber,depotId,handlerPartyId,createdPartyId,offset,pageSize);

            for (StorageReport storageReport : storageReportList) {
                StorageReportDto storageReportDto = new StorageReportDto();
                List<StorageReportItem> storageReportItemList = new ArrayList();
                //根据单据id获取单据详情信息

                storageReportItemList = storageReportItemService.listByReportId(storageReport.getId());
                //数据存入reportDto
                storageReportDto.setStorageReport(storageReport);
                storageReportDto.setStorageReportItemList(storageReportItemList);
                storageReportDtoList.add(storageReportDto);
            }
            return storageReportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageReportFail, e);
        }
    }

    @Override
    public List<StorageReportDto> listStorageReportDtoByCondition1(StorageReport storageReport,
                                                                     int page,
                                                                     int pageSize) throws SSException {
        page = page <= 0 ? 0 : page - 1;
        int offset = page * pageSize;
        List<StorageReportDto> storageReportDtoList =  Collections.emptyList();
        List<StorageReport> storageReportList = Collections.emptyList();
        if(Assert.lessZero(offset)){
            return storageReportDtoList;
        }
        if(Assert.lessZero(pageSize)){
            return storageReportDtoList;
        }
        storageReportDtoList = new ArrayList();
        try {
            //storageReportList = storageReportMapper.listStorageReportByCondition1(id,depotId,handlerPartyId,createdPartyId,offset,pageSize);
            storageReportList = storageReportMapper.listStorageReportByCondition1(storageReport,offset,pageSize);
            for (StorageReport storageReport1 : storageReportList) {
                StorageReportDto storageReportDto = new StorageReportDto();
                List<StorageReportItem> storageReportItemList = new ArrayList();
                //根据单据id获取单据详情信息
                storageReportItemList = storageReportItemService.listByReportId(storageReport1.getId());
                //数据存入reportDto
                storageReportDto.setStorageReport(storageReport1);
                storageReportDto.setStorageReportItemList(storageReportItemList);
                storageReportDtoList.add(storageReportDto);
            }
            return storageReportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageReportFail, e);
        }
    }

    @Override
    public void updateById(StorageReport storageReport) throws SSException {
        try {
            if(Assert.isNull(storageReport)){
                throw SSException.get(EmenuException.ReportIsNotNull);
              }
            if (Assert.isNull(storageReport.getId())&&Assert.lessOrEqualZero(storageReport.getId())){
                throw SSException.get(EmenuException.ReportIdError);
            }
            commonDao.update(storageReport);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStorageReportFail, e);
        }
    }

    @Override
    public void updateStatusById(int id, StorageReportStatusEnum storageReportStatusEnum) throws SSException {
        try {
            storageReportMapper.updateStatusById(id,storageReportStatusEnum.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStorageReportFail, e);
        }
    }

    @Override
    public StorageReport queryById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.ReportIdError);
            }
            return commonDao.queryById(StorageReport.class,id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryStorageReportFail, e);
        }
    }

    @Override
    public List<StorageReportDto> listStorageReportDtoByCondition2(Date startTime, Date endTime, List<Integer> depotIdList, List<Integer> tagIdList) throws SSException {

        if (startTime == null && endTime == null) {
            startTime = DateUtils.getTodayStartTime();
            endTime = DateUtils.getTodayEndTime();
        }

        if (endTime != null) {
            endTime.setHours(23);
            endTime.setMinutes(59);
            endTime.setSeconds(59);
        }

        List<StorageReportDto> storageReportDtoList =  Collections.emptyList();
        List<StorageReport> storageReportList = Collections.emptyList();

        try {
            //根据tagId获取分类下的物品
            StorageItemSearchDto searchDto = new StorageItemSearchDto();
            searchDto.setTagIdList(tagIdList);

            List<StorageItem> storageItemList = Collections.emptyList();
            storageItemList = storageItemService.listBySearchDto(searchDto);

            if(Assert.isNull(storageItemList)){
                return storageReportDtoList;
            }

            storageReportDtoList = new ArrayList();

            //获取原料idList
            List<Integer> itemIdList = new ArrayList<Integer>();
            for(StorageItem storageItem : storageItemList){
                itemIdList.add(storageItem.getId());
            }

            storageReportList = storageReportMapper.listByDepotIdList(startTime,endTime,depotIdList);

            for (StorageReport storageReport : storageReportList) {
                StorageReportDto storageReportDto = new StorageReportDto();
                List<StorageReportItem> storageReportItemList = new ArrayList();
                //根据单据id获取单据详情信息

                storageReportItemList = storageReportItemService.listByReportIdAndItemIdList(storageReport.getId(), itemIdList);

                //数据存入reportDto
                storageReportDto.setStorageReport(storageReport);
                storageReportDto.setStorageReportItemList(storageReportItemList);

                storageReportDtoList.add(storageReportDto);
            }

            return storageReportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageReportFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public boolean delReportDtoById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                return false;
            }
            this.delById(id);
            storageReportItemService.delByReportId(id);
            return true;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelReportOrItemFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public boolean delById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                return false;
            }
            storageReportMapper.delById(id);
            return true;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelReportOrItemFail, e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public boolean updateStorageReportDto(StorageReportDto storageReportDto) throws SSException {
        try {
            if (Assert.isNull(storageReportDto)){
                return false;
            }
            delReportDtoById(storageReportDto.getStorageReport().getId());
            this.newReportDto(storageReportDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStorageReportFail, e);
        }
        return false;
    }

    @Override
    public int count() throws SSException {
        Integer count = 0;
        try {
            count = storageReportMapper.count();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountReportFail, e);
        }
        return count == null ? 0 : count;
    }

}
