package com.emenu.service.party.group.impl;

import com.emenu.common.entity.party.group.Party;
import com.emenu.common.exception.PartyException;
import com.emenu.service.party.group.PartyService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 当事人Service实现
 *
 * @author: zhangteng
 * @time: 15/10/16 上午9:20
 */
@Service("partyService")
public class PartyServiceImpl implements PartyService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public Party newParty(Party party) throws SSException {
        try {
            return commonDao.insert(party);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

}
