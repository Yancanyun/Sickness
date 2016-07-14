package com.emenu.test.party;

import com.emenu.common.entity.party.security.SecurityPermission;
import com.emenu.service.party.security.SecurityPermissionService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * SecurityPermissionServiceTest
 *
 * @author: yangch
 * @time: 2016/7/12 19:27
 */
public class SecurityPermissionServiceTest extends AbstractTestCase {
    @Autowired
    private SecurityPermissionService securityPermissionService;

    @Test
    public void importFromText() throws Exception {
        String filePath = "C:\\houseyoung\\IdeaProjects\\emenu2\\emenu\\web\\db\\权限.txt";
        securityPermissionService.importFromText(filePath);
    }
}
