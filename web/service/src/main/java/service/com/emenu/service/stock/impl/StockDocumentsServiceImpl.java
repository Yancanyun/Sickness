package com.emenu.service.stock.impl;

import com.emenu.common.dto.stock.DocumentsDto;
import com.emenu.common.entity.stock.StockDocuments;
import com.emenu.common.entity.stock.StockDocumentsItem;
import com.emenu.common.entity.stock.StockItem;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.stock.StockDocumentsTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.stock.StockDocumentsItemService;
import com.emenu.service.stock.StockDocumentsService;
import com.emenu.service.stock.StockItemService;
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
            //计算入库单总计金额
            if(documentsDto.getStockDocuments().getType()== StockDocumentsTypeEnum.StockInDocuments.getId()){
                //单据明细判空
                if(Assert.isNotNull(documentsDto.getStockDocumentsItemList())||Assert.lessOrEqualZero(documentsDto.getStockItemList().size())){
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
            if(documentsDto.getStockDocuments().getType()== StockDocumentsTypeEnum.StockInDocuments.getId()){
                //设置单据明细表中的单据Id
                for(StockDocumentsItem stockDocumentsItem:documentsDto.getStockDocumentsItemList()){
                    stockDocumentsItem.setDocumentsId(stockDocuments.getId());
                    //添加单据明细表
                    stockDocumentsItemService.newDocumentsItem(stockDocumentsItem);
                    //根据入库信息修改库存物品
                    StockItem stockItem = stockItemService.queryById(stockDocumentsItem.getItemId());
                    //入库物品增加数量
//                    BigDecimal itemQuantity = stockDocumentsItem.getQuantity().add(stockItem.getStorageQuantity());
//                    stockItem.setStorageQuantity(itemQuantity);
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

}
