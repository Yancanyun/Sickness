package com.emenu.mapper.party.security;

import com.emenu.common.entity.party.security.SecurityGroupPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SecurityGroupPermissionMapper
 *
 * @author: zhangteng
 * @time: 15/10/13 下午1:50
 */
public interface SecurityGroupPermissionMapper {

    /**
     * 根据安全组ID删除
     *
     * @param groupId
     * @throws Exception
     */
    public void delByGroupId(@Param("groupId") int groupId) throws Exception;

    /**
     * 根据安全组ID获取数据
     *
     * @param groupId
     * @throws Exception
     */
    public List<SecurityGroupPermission> listByGroupId(@Param("groupId") int groupId) throws Exception;

    /**
     * 根据安全组ID获取权限ID
     *
     * @param groupId
     * @return
     * @throws Exception
     */
    public List<Integer> listPermissionIdByGroupId(@Param("groupId") int groupId) throws Exception;
}
