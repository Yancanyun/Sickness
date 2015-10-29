package com.emenu.test.party;

import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.enums.party.AccountTypeEnums;
import com.emenu.common.enums.party.EnableEnums;
import com.emenu.service.party.security.SecurityUserService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.CommonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * SecurtityUserServiceTest
 *
 * @author: zhangteng
 * @time: 15/10/16 上午11:05
 */
public class SecurtityUserServiceTest extends AbstractTestCase {

    @Autowired
    private SecurityUserService securityUserService;

    @Test
    public void newSecurityUser() throws SSException {
        SecurityUser securityUser = new SecurityUser();
        securityUser.setLoginName("wechat");
        securityUser.setPassword(CommonUtil.md5("123456"));
        securityUser.setAccountType(AccountTypeEnums.Wechat.getId());
        securityUser.setPartyId(1);
        securityUser.setStatus(EnableEnums.Enabled.getId());

        securityUserService.newSecurityUser(securityUser);
    }

    @Test
    public void updateStatusById() throws SSException {
        int id = 1;
        int status = 2;

        securityUserService.updateStatusById(id, EnableEnums.valueOf(status));
    }

    @Test
    public void queryByLoginName() throws SSException {
        String loginName = "sadmin";

        SecurityUser securityUser = securityUserService.queryByLoginName(loginName);

        System.out.println(securityUser.toString());
    }

    @Test
    public void checkLoginNameIsExist() throws SSException {
        String loginName = "sadmin";
        System.out.println("\n\n\n" + securityUserService.checkLoginNameIsExist(loginName));

        loginName = "admin";
        System.out.println("\n\n\n" + securityUserService.checkLoginNameIsExist(loginName));
    }

    @Test
    public void queryById() throws SSException {
        int id = 1;

        SecurityUser securityUser = securityUserService.queryById(id);
        System.out.println(securityUser.toString());
    }

    @Test
    public void listByPartyId() throws SSException {
        int partyId = 1;

        System.out.println("\n\n\n");
        List<SecurityUser> securityUserList = securityUserService.listByPartyId(partyId);
        for (SecurityUser securityUser : securityUserList) {
            System.out.println(securityUser.toString());
        }
    }

    @Test
    public void queryByPartyIdAndAccountType() throws SSException {
        int partyId = 1;
        int accountType = 2;

        SecurityUser securityUser = securityUserService.queryByPartyIdAndAccountType(partyId, AccountTypeEnums.valueOf(accountType));
        System.out.println(securityUser.toString());
    }
}
