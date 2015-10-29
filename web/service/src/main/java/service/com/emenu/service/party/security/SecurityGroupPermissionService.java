package com.emenu.service.party.security;

import com.emenu.common.entity.party.security.SecurityGroupPermission;
import com.emenu.common.entity.party.security.SecurityPermission;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 安全组-权限service
 *
 * @author: zhangteng
 * @time: 15/10/12 上午11:14
 */
public interface SecurityGroupPermissionService {

    /**
     * 新增
     *
     * @param securityGroupPermission
     * @throws SSException
     */
    public SecurityGroupPermission newSecurityGroupPermission(SecurityGroupPermission securityGroupPermission) throws SSException;

    /**
     * 根据Id删除
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据groupId删除
     *
     * @param groupId
     * @throws SSException
     */
    public void delByGroupId(int groupId) throws SSException;

    /**
     * 获取权限组的权限
     *
     * @param groupId
     * @return
     * @throws SSException
     */
    public List<SecurityGroupPermission> listByGroupId(int groupId) throws SSException;

    /**
     * 获取当前组未选择的权限
     *
     * @param groupId
     * @return
     * @throws SSException
     */
    public List<SecurityPermission> listNotSelectedPermission(int groupId) throws SSException;

}
