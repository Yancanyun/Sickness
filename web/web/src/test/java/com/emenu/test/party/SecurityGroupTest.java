package com.emenu.test.party;

import com.emenu.common.entity.party.security.SecurityGroup;
import com.emenu.service.party.security.SecurityGroupService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * SecurityGroupTest
 *
 * @author dujuan
 * @date 2015/10/30
 */
public class SecurityGroupTest extends AbstractTestCase{

    @Autowired
    SecurityGroupService securityGroupService;

    @Test
    public void newSecurityGroup(){
        SecurityGroup securityGroup = new SecurityGroup();
        securityGroup.setName("技术组");
        securityGroup.setDescription("搞技术的");
        try {
            securityGroupService.newSecurityGroup(securityGroup);
        } catch (SSException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listSecurityGroup() throws SSException {
        List<SecurityGroup> securityGroupList = securityGroupService.listByPage(1,10);
        for(SecurityGroup securityGroup : securityGroupList){
            System.out.println(securityGroup.getName());
        }
    }
}
