package com.emenu.service.party.group.supplier.impl;

import com.emenu.common.entity.party.group.Party;
import com.emenu.common.entity.party.group.supplier.Supplier;
import com.emenu.common.enums.party.PartyTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.party.group.PartyService;
import com.emenu.service.party.group.supplier.SupplierService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * 供货商Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/7 16:16
 **/
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private PartyService partyService;

    @Override
    public void newSupplier(Supplier supplier, Integer optPartyId) throws SSException {
        try {
            if (!checkBeforeSave(supplier)) {
                return;
            }

            // 先添加一条party记录
            Party party = new Party();
            party.setPartyTypeId(PartyTypeEnums.Supplier.getId());
            party.setCreatedUserId(optPartyId);
            party = partyService.newParty(party);

            // 再添加供货商
            supplier.setPartyId(party.getId());
            commonDao.insert(supplier);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SupplierInsertFail, e);
        }
    }

    @Override
    public void updateSupplier(Supplier supplier) throws SSException {

    }

    @Override
    public List<Supplier> listAll() throws SSException {
        return null;
    }

    @Override
    public Supplier queryById(int id) throws SSException {
        return null;
    }

    @Override
    public void delById(int id) throws SSException {

    }

    @Override
    public boolean queryNameIsExist(String name) throws SSException {
        return false;
    }

    private boolean checkBeforeSave(Supplier supplier) throws SSException {
        if (Assert.isNull(supplier)) {
            return false;
        }

        Assert.isNotNull(supplier.getName(), EmenuException.SupplierNameNotNull);

        // 检查名称是否存在
        if (queryNameIsExist(supplier.getName()) == true) {
            throw SSException.get(EmenuException.SupplierNameExist);
        }

        return true;
    }
}
