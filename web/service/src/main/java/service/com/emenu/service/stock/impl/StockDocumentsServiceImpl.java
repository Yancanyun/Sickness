package com.emenu.service.stock.impl;

import com.emenu.common.dto.stock.DocumentsDto;
import com.emenu.common.dto.stock.DocumentsSearchDto;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.stock.*;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.stock.StockDocumentsTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.stock.StockDocumentsMapper;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.service.stock.*;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * StockDocumentsServiceImpl
 *
 * @author renhongshuai
 *         Created by admin.
 * @Time 2017/3/7 15:40.
 */
@Service("stockDocumentsService")
public class StockDocumentsServiceImpl implements StockDocumentsService{

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private SerialNumService serialNumService;

    @Autowired
    private StockDocumentsItemService stockDocumentsItemService;

    @Autowired
    private StockItemService stockItemService;

    @Autowired
    private StockDocumentsMapper stockDocumentsMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SpecificationsService specificationsService;

    @Autowired
    private StockItemDetailService stockItemDetailService;

    @Autowired
    private StockKitchenItemService stockKitchenItemService;


    /**
     * 添加单据Dto
     *
     * @param documentsDto
     * @throws SSException
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newDocumentsDto(DocumentsDto documentsDto) throws SSException{

        try{
            //判断单据类型
            if (Assert.isNull(documentsDto.getStockDocuments().getType()) ||
                    Assert.lessOrEqualZero(documentsDto.getStockDocuments().getType())){
                throw SSException.get(EmenuException.DocumentsTypeError);
            }
            //判断是否为空
            if(Assert.isNull(documentsDto.getStockDocuments())){
                throw SSException.get(EmenuException.DocumentsIsNotNull);
            }
            //根据单据类型生成单据编号
            String serialNumber="";
            switch(documentsDto.getStockDocuments().getType()){
                case 1:
                    serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockInSerialNum);
                    break;
                case 2:
                    serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockOutSerialNum);
                    break;
                case 3:
                    serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockBackSerialNum);
                    break;
                case 4:
                    serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.IncomeOnSerialNum);
                    break;
                case 5:
                    serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.LossOnSerialNum);
                    break;
                default: throw SSException.get(EmenuException.DocumentsTypeError);
            }
            documentsDto.getStockDocuments().setSerialNumber(serialNumber);
            //若为入库单则计算入库单总计金额
            if(documentsDto.getStockDocuments().getType()== StockDocumentsTypeEnum.StockInDocuments.getId()){
                //单据明细判空
                if(Assert.isNull(documentsDto.getStockDocumentsItemList())||Assert.lessOrEqualZero(documentsDto.getStockDocumentsItemList().size())){
                    throw SSException.get(EmenuException.DocumentsItemListIsNotNull);
                }
                //单据总计金额
                BigDecimal documentsMoney = new BigDecimal("0.00");
                for(StockDocumentsItem stockDocumentsItem:documentsDto.getStockDocumentsItemList()){
                    documentsMoney = documentsMoney.add(stockDocumentsItem.getPrice());
                }
                documentsDto.getStockDocuments().setMoney(documentsMoney);
            }
            //添加到单据表返回实体
            StockDocuments stockDocuments = this.newDocuments(documentsDto.getStockDocuments());
            if(stockDocuments.getType()== StockDocumentsTypeEnum.StockInDocuments.getId()){
                //入库单
                //设置单据明细表中的单据Id
                for(StockDocumentsItem stockDocumentsItem:documentsDto.getStockDocumentsItemList()){
                    stockDocumentsItem.setDocumentsId(stockDocuments.getId());
                    //添加单据明细表
                    stockDocumentsItemService.newDocumentsItem(stockDocumentsItem);
                    //根据入库信息修改库存物品
                    StockItem stockItem = stockItemService.queryById(stockDocumentsItem.getItemId());
                    //查询规格
                    Specifications specifications = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                    //通过规格转换为成本卡单位更新库存
                    BigDecimal itemQuantity = stockItem.getStorageQuantity().add(toCostCardUnitQuantity(stockDocumentsItem,stockItem,specifications));
                    //更新总库存
                    stockItem.setStorageQuantity(itemQuantity);
                    stockItem.setCostCardUnitId(specifications.getCostCardId());
                    stockItemService.updateStockItem(stockItem);

                    //添加物品明细
                    StockItemDetail stockItemDetail = new StockItemDetail();
                    stockItemDetail.setItemId(stockDocumentsItem.getItemId());
                    stockItemDetail.setKitchenId(documentsDto.getStockDocuments().getKitchenId());
                    stockItemDetail.setSpecificationId(stockDocumentsItem.getSpecificationId());
                    stockItemDetail.setQuantity(stockDocumentsItem.getQuantity());
                    stockItemDetail.setUnitId(stockDocumentsItem.getUnitId());
                    stockItemDetailService.newStockItemDetail(stockItemDetail);
                }
            }else if(stockDocuments.getType() == StockDocumentsTypeEnum.StockOutDocuments.getId()){
                //领用单
                //设置单据明细表中的单据Id
                for(StockDocumentsItem stockDocumentsItem:documentsDto.getStockDocumentsItemList()){
                    stockDocumentsItem.setDocumentsId(stockDocuments.getId());
                    //添加单据明细表
                    stockDocumentsItemService.newDocumentsItem(stockDocumentsItem);
                    //根据入库信息修改库存物品
                    StockItem stockItem = stockItemService.queryById(stockDocumentsItem.getItemId());
                    //查询规格
                    Specifications specifications = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                    //通过规格转换为成本卡单位
                    BigDecimal itemQuantity =  stockItem.getStorageQuantity().subtract(toCostCardUnitQuantity(stockDocumentsItem,stockItem,specifications));
                    //更新总库存
                    stockItem.setStorageQuantity(itemQuantity);
                    stockItem.setCostCardUnitId(specifications.getCostCardId());
                    stockItemService.updateStockItem(stockItem);
                    //更新领用厨房库存
//                    StockKitchenItem stockKitchenItem = stockKitchenItemService.queryByItemId(stockItem.getId());
//                    if(stockKitchenItem.getStatus()!=3){
//                        if(stockDocumentsItem.getSpecificationId() == stockKitchenItem.getSpecifications()){
//
//                        }
//                    }
                    //添加物品明细
                    StockItemDetail stockItemDetail = new StockItemDetail();
                    stockItemDetail.setItemId(stockDocumentsItem.getItemId());
                    stockItemDetail.setKitchenId(documentsDto.getStockDocuments().getKitchenId());
                    stockItemDetail.setSpecificationId(stockDocumentsItem.getSpecificationId());
                    stockItemDetail.setQuantity(stockDocumentsItem.getQuantity());
                    stockItemDetail.setUnitId(stockDocumentsItem.getUnitId());
                    stockItemDetailService.newStockItemDetail(stockItemDetail);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertDocumentsFail, e);
        }
    }



    /**---------------------------------------------------私有方法---------------------------------------------------**/

    /**
     * 添加单据
     *
     * @param stockDocuments
     * @return
     * @throws SSException
     */

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private StockDocuments newDocuments(StockDocuments stockDocuments) throws SSException {

        try {
            if(Assert.isNull(stockDocuments)){
                throw SSException.get(EmenuException.DocumentsIsNotNull);
            }
            //单据判空
            if(checkStockDocumentsBeforeSave(stockDocuments)){
                return commonDao.insert(stockDocuments);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertDocumentsFail, e);
        }
        return null;
    }

    /**
     * 检查单据信息是否有误
     *
     * @param stockDocuments
     * @return
     * @throws SSException
     */

    private boolean checkStockDocumentsBeforeSave(StockDocuments stockDocuments) throws SSException{

        if (Assert.isNull(stockDocuments)) {
            return false;
        }
        // 操作人
        if (Assert.isNull(stockDocuments.getCreatedPartyId()) || Assert.lessOrEqualZero(stockDocuments.getCreatedPartyId())){
            throw SSException.get(EmenuException.DocumentsCreatedPartyIdError);
        }
        // 经手人
        if (Assert.isNull(stockDocuments.getHandlerPartyId()) || Assert.lessOrEqualZero(stockDocuments.getHandlerPartyId())){
            throw SSException.get(EmenuException.DocumentsHandlerPartyId);
        }
        // 单据类型
        if (Assert.isNull(stockDocuments.getType())
                && Assert.lessOrEqualZero(stockDocuments.getType())){
            throw SSException.get(EmenuException.DocumentsTypeError);
        }
        // 入库单存放点
        if (stockDocuments.getType() == StockDocumentsTypeEnum.StockInDocuments.getId()){
            if (Assert.isNull(stockDocuments.getKitchenId())
                    || Assert.lessOrEqualZero(stockDocuments.getKitchenId())){
                throw SSException.get(EmenuException.KitchenIdError);

            }
        }
        // 单据编号
        Assert.isNotNull(stockDocuments.getSerialNumber(), EmenuException.SerialNumberError);
        // 单据金额
        Assert.isNotNull(stockDocuments.getMoney(), EmenuException.DocumentsMoneyError);
        return true;
    }

    /**
     * 将数量按照成本卡单位转化
     * @param stockDocumentsItem
     * @param stockItem
     * @param specifications
     * @return
     * @throws SSException
     */
    private BigDecimal toCostCardUnitQuantity(StockDocumentsItem stockDocumentsItem,StockItem stockItem,Specifications specifications)throws SSException{
        BigDecimal documentsItemQuantity = BigDecimal.ZERO;
        if(stockDocumentsItem.getUnitId() == specifications.getCostCardId()){
            documentsItemQuantity = stockItem.getStorageQuantity().subtract(stockDocumentsItem.getQuantity());
        }else if(stockDocumentsItem.getUnitId() == specifications.getOrderUnitId()){
            documentsItemQuantity = stockDocumentsItem.getQuantity().multiply(specifications.getOrderToStorage().multiply(specifications.getStorageToCost()));
        }else if(stockDocumentsItem.getUnitId() == specifications.getStorageUnitId()){
            documentsItemQuantity = stockDocumentsItem.getQuantity().multiply(specifications.getStorageToCost());
        }
        return documentsItemQuantity;
    }


    /**************************************by chenwenyan ***************************************/
    /**
     * 获取单据list
     *
     * @return
     * @throws SSException
     */
    @Override
    public List<StockDocuments> listAll() throws SSException {
        List<StockDocuments> documentsList = Collections.emptyList();
        try {
            documentsList = stockDocumentsMapper.listAll();
            //设置经手人、操作人、审核人名字
            for (StockDocuments stockDocuments : documentsList) {
                setStockDOcumentsRelatedName(stockDocuments);
            }
            return documentsList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListDocumentsFail, e);
        }
    }

    /**
     * 根据查询条件获取单据
     *
     * @param documentsSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<StockDocuments> listStockDocumentsBySearchDto(DocumentsSearchDto documentsSearchDto) throws SSException {

        List<StockDocuments> documentsList = Collections.emptyList();

        int pageNo = 0;
        int offset = 0;

        try {
            if (Assert.isNotNull(documentsSearchDto.getPageNo())) {
                pageNo = documentsSearchDto.getPageNo() <= 0 ? 0 : documentsSearchDto.getPageNo();
            }
            if (Assert.isNotNull(documentsSearchDto.getOffset())) {
                offset = documentsSearchDto.getOffset() <= 0 ? 0 : documentsSearchDto.getOffset();
            }
            if (documentsSearchDto.getStartTime() != null) {
                documentsSearchDto.getStartTime().setHours(0);
                documentsSearchDto.getStartTime().setMinutes(0);
                documentsSearchDto.getStartTime().setSeconds(0);
            }
            if (documentsSearchDto.getEndTime() != null) {
                documentsSearchDto.getEndTime().setHours(23);
                documentsSearchDto.getEndTime().setMinutes(59);
                documentsSearchDto.getEndTime().setSeconds(59);
            }
            documentsList = stockDocumentsMapper.listDocumentsBySearchDto(documentsSearchDto);
            //设置经手人、操作人、审核人名字
            for (StockDocuments stockDocuments : documentsList) {
                setStockDOcumentsRelatedName(stockDocuments);
            }
            return documentsList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDocumentsByDtoFail, e);
        }
    }


    @Override
    public StockDocuments queryById(int id) throws SSException {
        StockDocuments stockDocuments = new StockDocuments();
        try {
            if (Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.DocumentsIdError);
            } else {
                stockDocuments = commonDao.queryById(StockDocuments.class, id);
                if (Assert.isNotNull(stockDocuments)) {
                    setStockDOcumentsRelatedName(stockDocuments);
                }
                return stockDocuments;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDocumentsByIdFailed, e);
        }
    }

    @Override
    public boolean delDocumentsDtoById(int id) throws SSException {
        StockDocuments stockDocuments = new StockDocuments();
        try {
            stockDocuments = queryById(id);
            if (Assert.isNull(stockDocuments)) {
                throw SSException.get(EmenuException.DocumentsIsNotExist);
            }
            //已审核，不能删除
            if (stockDocuments.getIsAudited().equals(1)) {
                return false;
            }
            //未审核，删除单据以及单据详情
            stockDocumentsItemService.delByDocumentsId(id);
            delById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelDocumentsByIdFailed, e);
        }
        return true;
    }

    /**
     * 根据Id删除单据信息
     *
     * @param id
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public boolean delById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.DocumentsIdError);
            }
            stockDocumentsMapper.delById(id);
            return true;
        } catch (Exception e) {
            throw SSException.get(EmenuException.DelDocumentsByIdFailed, e);
        }
    }

    /******************************** 私有方法 ********************************************/

    /**
     * 设置经手人、操作人、审核人名字
     *
     * @param stockDocuments
     * @throws SSException
     */
    private void setStockDOcumentsRelatedName(StockDocuments stockDocuments) throws SSException {
        // 设置经手人、操作人、审核人名字
        Employee createdEmployee = employeeService.queryByPartyIdWithoutDelete(stockDocuments.getCreatedPartyId());
        if (Assert.isNull(createdEmployee)) {
            throw SSException.get(EmenuException.SystemException);
        }
        stockDocuments.setCreatedName(createdEmployee.getName());

        Employee auditEmployee = employeeService.queryByPartyIdWithoutDelete(stockDocuments.getAuditPartyId());
        if (Assert.isNull(auditEmployee)) {
            stockDocuments.setAuditName("");
        } else {
            stockDocuments.setAuditName(auditEmployee.getName());
        }
        Employee handlerEmployee = employeeService.queryByPartyIdWithoutDelete(stockDocuments.getHandlerPartyId());
        if (Assert.isNull(handlerEmployee)) {
            throw SSException.get(EmenuException.SystemException);
        } else {
            stockDocuments.setHandlerName(handlerEmployee.getName());
        }
    }
}
