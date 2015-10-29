package com.emenu.test.party;

import com.emenu.common.entity.party.group.Party;
import com.emenu.common.enums.party.PartyTypeEnums;
import com.emenu.service.party.group.PartyService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * PartyServiceTest
 *
 * @author: zhangteng
 * @time: 15/10/16 上午10:29
 */
public class PartyServiceTest extends AbstractTestCase {

    @Autowired
    private PartyService partyService;

    @Test
    public void newParty() throws SSException {
        Party party = new Party();
        party.setPartyTypeId(PartyTypeEnums.Employee.getId());
        party.setDescription("测试");

        partyService.newParty(party);
    }
}
