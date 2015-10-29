package com.emenu.service.party.security.impl;

import com.emenu.common.entity.party.security.SecurityGroupPermission;
import com.emenu.common.entity.party.security.SecurityPermission;
import com.emenu.common.exception.PartyException;
import com.emenu.mapper.party.security.SecurityGroupPermissionMapper;
import com.emenu.service.other.EncodeService;
import com.emenu.service.party.security.SecurityGroupPermissionService;
import com.emenu.service.party.security.SecurityPermissionService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 安全组-权限service
 *
 * @author: zhangteng
 * @time: 15/10/12 上午11:14
 */
@Service("securityGroupPermissionService")
public class SecurityGroupPermissionServiceIml implements SecurityGroupPermissionService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SecurityGroupPermissionMapper securityGroupPermissionMapper;

    @Autowired
    private SecurityPermissionService securityPermissionService;

    @Autowired
    private EncodeService encodeService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public SecurityGroupPermission newSecurityGroupPermission(SecurityGroupPermission securityGroupPermission) throws SSException {
        if (!checkBeforeSave(securityGroupPermission)) {
            return null;
        }

        try {
            return commonDao.insert(securityGroupPermission);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)){
            return;
        }
        try {
            commonDao.deleteById(SecurityGroupPermission.class, id);
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delByGroupId(int groupId) throws SSException {
        if (Assert.lessOrEqualZero(groupId)){
            return ;
        }
        try {
            securityGroupPermissionMapper.delByGroupId(groupId);
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public List<SecurityGroupPermission> listByGroupId(int groupId) throws SSException {
        List<SecurityGroupPermission> list = Collections.emptyList();
        if (Assert.lessOrEqualZero(groupId)) {
            return list;
        }

        try {
            list = securityGroupPermissionMapper.listByGroupId(groupId);
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return list == null ? Collections.<SecurityGroupPermission>emptyList() : list;
    }

    @Override
    public List<SecurityPermission> listNotSelectedPermission(int groupId) throws SSException {
        List<SecurityPermission> list = Collections.emptyList();
        if (Assert.lessOrEqualZero(groupId)) {
            return list;
        }

        // 已经选择权限 的Idlist
        List<Integer> selectedIdList = Collections.emptyList();
        try {
            selectedIdList = securityGroupPermissionMapper.listPermissionIdByGroupId(groupId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        // 获取未选择的权限ID列表
        List<SecurityPermission> notSelectedList = Collections.emptyList();
        try {
            if (!Assert.isNotEmpty(selectedIdList)) {
                notSelectedList = securityPermissionService.listByIdList(selectedIdList, false);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return notSelectedList;
    }

    private boolean checkBeforeSave(SecurityGroupPermission securityGroupPermission) throws SSException {
        if (Assert.isNull(securityGroupPermission)) {
            return false;
        }

        Assert.isNotNull(securityGroupPermission.getGroupId(), PartyException.SecurityGroupIdNotNull);
        Assert.isNotNull(securityGroupPermission.getPermissionId(), PartyException.PermissionIdNotNull);

        return true;
    }
}
