package com.emenu.mapper.party.security;

import com.emenu.common.entity.party.security.SecurityUser;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SecurityUserMapper
 *
 * @author: zhangteng
 * @time: 15/10/12 上午9:38
 */
public interface SecurityUserMapper {

    /**
     * 根据用户id更改用户状态
     *
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatusById(@Param("id") int id,
                                 @Param("status") int status) throws Exception;

    /**
     * 根据登录名查询用户
     *
     * @param loginName
     * @return
     * @throws Exception
     */
    public SecurityUser queryByLoginName(@Param("loginName") String loginName) throws Exception;

    /**
     * 根据登录名查询用户数量
     *
     * @param loginName
     * @return
     * @throws Exception
     */
    public int countByLoginName(@Param("loginName") String loginName) throws Exception;

    /**
     * 根据partyId获取用户
     *
     * @param partyId
     * @return
     * @throws Exception
     */
    public List<SecurityUser> listByPartyId(@Param("partyId") int partyId) throws Exception;

    /**
     * 根据partyId和账户类型获取用户
     *
     * @param partyId
     * @param accountType
     * @return
     * @throws Exception
     */
    public SecurityUser queryByPartyIdAndAccountType(@Param("partyId") int partyId,
                                                     @Param("accountType") int accountType) throws Exception;

    public SecurityUser queryByPartyId(@Param("partyId")int partyId) throws SSException;

    public void updateSecurityUser(SecurityUser securityUser);
}
