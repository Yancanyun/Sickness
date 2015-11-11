package com.emenu.service.party.group.supplier.impl;

import com.emenu.common.entity.party.group.Party;
import com.emenu.common.entity.party.group.supplier.Supplier;
import com.emenu.common.enums.party.PartyTypeEnums;
import com.emenu.common.enums.party.SupplierStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.party.group.supplier.SupplierMapper;
import com.emenu.service.party.group.PartyService;
import com.emenu.service.party.group.supplier.SupplierService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 供货商Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/7 16:16
 **/
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private PartyService partyService;

    @Override
    public Supplier newSupplier(Supplier supplier, Integer optPartyId) throws SSException {
        try {
            if (!checkBeforeSave(supplier)) {
                return null;
            }

            // 先添加一条party记录
            Party party = new Party();
            party.setPartyTypeId(PartyTypeEnums.Supplier.getId());
            party.setCreatedUserId(optPartyId);
            party = partyService.newParty(party);

            // 再添加供货商
            supplier.setPartyId(party.getId());
            supplier = commonDao.insert(supplier);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SupplierInsertFailed, e);
        }

        return supplier;
    }

    @Override
    public void updateSupplier(Supplier supplier) throws SSException {
        try {
            if (!checkBeforeSave(supplier)) {
                return ;
            }
            Assert.isNotNull(supplier, EmenuException.SupplierIdNotNull);

            commonDao.update(supplier);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SupplierUpdateFailed, e);
        }
    }

    @Override
    public List<Supplier> listAll() throws SSException {
        List<Supplier> list = Collections.emptyList();
        try {
            list = supplierMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SupplierQueryFailed, e);
        }
        return list;
    }

    @Override
    public Supplier queryById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return commonDao.queryById(Supplier.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SupplierQueryFailed, e);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            supplierMapper.updateStatusById(SupplierStatusEnums.Deleted.getId(), id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SupplierDeleteFailed, e);
        }
    }

    @Override
    public boolean queryNameIsExistById(String name, Integer id) throws SSException {
        if (Assert.isNull(id)) {
            id = -1;
        }
        Integer count = 0;
        try {
            count = supplierMapper.countByNameAndId(name, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SupplierQueryFailed, e);
        }
        if (!Assert.lessOrEqualZero(id)) {
            return count > 1;
        }
        return count > 0;
    }

    private boolean checkBeforeSave(Supplier supplier) throws SSException {
        if (Assert.isNull(supplier)) {
            return false;
        }

        Assert.isNotNull(supplier.getName(), EmenuException.SupplierNameNotNull);

        // 检查名称是否存在
        if (queryNameIsExistById(supplier.getName(), supplier.getId()) == true) {
            throw SSException.get(EmenuException.SupplierNameExist);
        }

        return true;
    }
}
